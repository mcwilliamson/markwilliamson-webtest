package com.markwilliamson.productpackagemanagement.web;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.markwilliamson.productpackagemanagement.model.Product;
import com.markwilliamson.productpackagemanagement.model.ProductPackageInput;
import com.markwilliamson.productpackagemanagement.model.domain.ProductPackage;
import com.markwilliamson.productpackagemanagement.service.product.ProductService;
import com.markwilliamson.productpackagemanagement.service.productpackage.PackageService;

/**
 * Rest controller class defining all REST endpoints.
 * @author mwilliamson
 *
 */

@RestController
@RequestMapping("/api/v1/productpackages")
public class ProductPackageController {
	
	Logger log = LoggerFactory.getLogger(this.getClass().getName());

	@Autowired
	private PackageService packageService;
	
	@Autowired
	private ProductService productService;
	
	/**
	 * Get a list of all product packages
	 * @return a list of ProductPackage as a JSON response
	 */
	@RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
	public List<ProductPackage> getPackages() {
		return packageService.getAllPackages();
	}
	
	
	/**
	 * Get a product package by id
	 * @param id
	 * @param currency
	 * @return the ProductPackage as a JSON response
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ProductPackage getPackage(@PathVariable("id") String id, @RequestParam(name="currency", defaultValue="") String currency) {
		return packageService.getPackage(id, currency);
	}
	

	/**
	 * Create a new product package
	 * @param productPackage serialized from an input JSON string 
	 * @return  the created ProductPackage as a JSON response
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public ProductPackage createPackage(@Valid @RequestBody ProductPackageInput productPackage) {
		return packageService.createPackage(productPackage);
	}
	

	/**
	 * Update an existing product package
	 * @param productPackage serialized from an input JSON string 
	 * @return  the updated ProductPackage as a JSON response
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public ProductPackage updatePackage(@Valid @RequestBody ProductPackageInput productPackage) {
		return packageService.updatePackage(productPackage);
	}
	
	/**
	 * Delete a package by id
	 * @param id
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public void deletePackage(@PathVariable("id") String id) {
		packageService.deletePackageById(id);
	}
	
	/**
	 * List all available products
	 * @return
	 */
	@RequestMapping(value = {"", "/products"}, method = RequestMethod.GET)
	public List<Product> getAvailableProducts() {
		return productService.getAllProducts();
	}
	
}
