package com.markwilliamson.productpackagemanagement.service.productpackage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.markwilliamson.productpackagemanagement.model.Product;
import com.markwilliamson.productpackagemanagement.model.ProductPackageInput;
import com.markwilliamson.productpackagemanagement.model.domain.ProductPackage;
import com.markwilliamson.productpackagemanagement.model.domain.ProductPackageProductLink;
import com.markwilliamson.productpackagemanagement.model.domain.ProductPackageProductLinkPK;
import com.markwilliamson.productpackagemanagement.repository.ProductPackageProductLinkRepository;
import com.markwilliamson.productpackagemanagement.repository.ProductPackageRepository;
import com.markwilliamson.productpackagemanagement.service.currencyconversion.CurrencyConversionService;
import com.markwilliamson.productpackagemanagement.service.product.ProductService;

/**
 * Service for working with ProductPackages.
 * 
 * @author mwilliamson
 *
 */
@Service
public class PackageServiceImpl implements PackageService {

	private static final Logger log = LoggerFactory.getLogger(PackageServiceImpl.class.getName());
	
	@Value("${default.currency}")
	private String _BaseCurrency;
	
	@Autowired
	ProductPackageRepository productPackageRepository;
	
	@Autowired
	ProductPackageProductLinkRepository productPackageProductLinkRepository;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	CurrencyConversionService fixerIoCurrencyConversionService;
	
	/**
	 * Retrieve all available product packages
	 * 
	 * @return list of ProductPackage objects
	 */
	@Override
	public List<ProductPackage> getAllPackages() {
		
		List<ProductPackage> packages = new ArrayList<ProductPackage>();
		List<ProductPackage> finalPackageList = new ArrayList<ProductPackage>();
		
		log.debug("in PackageServiceImpl.getAllPackages()");
		packages = productPackageRepository.findAll();
		for (ProductPackage prodpkg : packages) {
			finalPackageList.add(setAdditionalProductPackageAttributes(prodpkg, _BaseCurrency));
		}
		
		return finalPackageList;
		
	}
	
	/**
	 * Retrieve a product package using the passed id, converting total price to the optional specified currency, if supplied.
	 * @param id id of the product package to fetch
	 * @param currency
	 * 
	 * @return the ProductPackage object
	 */
	@Override
	public ProductPackage getPackage(String id, String currency) {
		log.debug("in PackageServiceImpl.getPackage(String id)");
		
		// Lookup from storage
		Optional<ProductPackage> op = productPackageRepository.findById(id);

		// throw an exception if it doesn't exist
		if(!op.isPresent()) {
			throw new ProductPackageNotFoundException(id);
		}
		
		ProductPackage p = op.get();
		
		// Set the additional transient attributes
		setAdditionalProductPackageAttributes(p, currency.isEmpty()?_BaseCurrency:currency);
				
		// return the package
		return p;
	}
	
	/**
	 * Create a new product package
	 * @param productPackageIn a ProductPackageInput object
	 * 
	 * @return the created ProductPackage object
	 */
	@Override
	public ProductPackage createPackage(ProductPackageInput productPackageIn) {
		log.debug("in PackageServiceImpl.createPackage(ProductPackageInput productPackage)");
		
		// Check it doesn't already exist
		try{
			getPackage(productPackageIn.getId(), _BaseCurrency);
			// If it already exists, throw exception
			throw new ProductPackageExistsException(productPackageIn.getId());
		} catch (ProductPackageNotFoundException e) {
			// ignore this exception as this is correct and we don't want it to already exist		
		}
		
		// Build the list of products whilst validating all products exist
		buildAndValidateProductList(productPackageIn.getProductIds());
		
		// Create a ProductPackage object from the ProductPackageInput object
		ProductPackage p = createProductPackage(productPackageIn);
		
		// Store the package
		productPackageRepository.save(p);
		
		// Add the productPackageProductLinks
		p.setProductLinks(createProductLinksFromIds(p, productPackageIn.getProductIds()));
		productPackageRepository.save(p);
		productPackageRepository.flush();
		
		// Set the additional transient attributes
		setAdditionalProductPackageAttributes(p, _BaseCurrency);
		
		// Return the package
		return p;
	}
	
	/**
	 * Update an existing ProductPackage
	 * @param productPackageIn a ProductPackageInput object
	 * 
	 * @return the updated ProductPackage object
	 */
	@Override
	public ProductPackage updatePackage(ProductPackageInput productPackageIn) {
		log.debug("in PackageServiceImpl.updatePackage(ProductPackageInput productPackage)");
		
		// Get the package with the supplied id - throws a relevant exception if it doesn't exist
		ProductPackage p = getPackage(productPackageIn.getId(), _BaseCurrency);
		
		// Build the list of products whilst validating all products exist
		buildAndValidateProductList(productPackageIn.getProductIds());
		
		// If so, update the package
		p.setName(productPackageIn.getName());
		p.setDescription(productPackageIn.getDescription());
		
		// Delete the existing packageProductLinks first
		List<ProductPackageProductLink> links = p.getProductLinks();
		for(ProductPackageProductLink link : links) {
			productPackageProductLinkRepository.delete(link);
		}
		productPackageProductLinkRepository.flush();
		
		// Set the new packageProductLinks
		p.setProductLinks(createProductLinksFromIds(p, productPackageIn.getProductIds()));
				
		// Set the additional transient attributes
		setAdditionalProductPackageAttributes(p, _BaseCurrency);
		
		// Return the package
		return p;
		
	}
	
	/**
	 * Delete a product package based on it's id
	 * @param id the string id for the ProductPackage to delete
	 */
	@Override
	public void deletePackageById(String id) {
		log.debug("in PackageServiceImpl.deletePackageById(String id)");

		Optional<ProductPackage> op = productPackageRepository.findById(id);
		
		if(!op.isPresent()) {
			throw new ProductPackageNotFoundException(id);
		}
		
		ProductPackage p = op.get();
		List<ProductPackageProductLink> links = p.getProductLinks();
		
		// Delete the packageProductLinks first
		for(ProductPackageProductLink link : links) {
			productPackageProductLinkRepository.delete(link);
		}
		productPackageProductLinkRepository.flush();
		
		// Delete the package
		productPackageRepository.delete(p);
		productPackageRepository.flush();
		
	}
	
	/* 
	 * Build a list of the product objects, validating at the same time, throwing an exception if any invalid products are found
	 */
	private List<Product> buildAndValidateProductList(List<String> productIds) {
	
		List<Product> products = new ArrayList<Product>();
		
		for(String productId: productIds) {
			products.add(productService.getProduct(productId));
		}
		
		return products;
	}
	
	/* 
	 * Set additional properties on the package. 
	 * - products - Looked up via the ProductService
	 * - totalPrice - calculates the total price of all products in the package, using 
	 * 					the CurrencyConversionService to convert the price based on the supplied currency.
	 * - currency - the currency the total price is in.
	 */
	private ProductPackage setAdditionalProductPackageAttributes(ProductPackage p, String currency) {
	
		List<Product> products = new ArrayList<Product>();
		double totalProductPrice = 0;
		
		// Build a list of the product objects while calculating the total cost of the products
		products = buildAndValidateProductList(p.getProductLinksAsIds());
		for(Product product: products) {
			totalProductPrice += product.getUsdPrice();
		}
		
		p.setProducts(products);
		p.setTotalPrice(fixerIoCurrencyConversionService.convert(totalProductPrice, _BaseCurrency, currency));
		p.setCurrency(currency);
		
		return p;
		
	}
	
	/*
	 * Create ProductPackageProductList objects for a ProductPackage from a string list of product ids
	 */
	private List<ProductPackageProductLink> createProductLinksFromIds(ProductPackage p, List<String> productIds) {
		
		List<ProductPackageProductLink> ppplList = new ArrayList<ProductPackageProductLink>();
		
		for(String productId : productIds) {
			ProductPackageProductLink pppl = new ProductPackageProductLink();
			pppl.setProductPackageProductLinkPK(new ProductPackageProductLinkPK(p.getId(), productId));
			productPackageProductLinkRepository.save(pppl);
			ppplList.add(pppl);
		}
		productPackageProductLinkRepository.flush();
		
		return ppplList;
	}
	
	/*
	 * Create a basic ProductPackage object from a ProductPackageInput object
	 */
	private ProductPackage createProductPackage(ProductPackageInput productPackageIn) {
		ProductPackage p = new ProductPackage();
		p.setId(productPackageIn.getId());
		p.setName(productPackageIn.getName());
		p.setDescription(productPackageIn.getDescription());
		return p;
	}

}
