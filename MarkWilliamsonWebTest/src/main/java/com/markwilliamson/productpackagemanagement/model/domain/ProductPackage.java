package com.markwilliamson.productpackagemanagement.model.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.markwilliamson.productpackagemanagement.model.Product;



/**
 * Domain object representing a package of products. 
 * The products are stored elsewhere and it is assumed that products can be created but not deleted. 
 * Whether they can be updated is unknown, so to cover this we simply store the product IDs against the 
 * package. We can then fetch the current version of each product when the package is looked up.
 *    
 * @author mwilliamson
 *
 */
@Entity
@Table(name="PROD_PACKAGES")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductPackage {

	// Unique id for the package
	@Id
	@Column(name="id")
	@Size(max=30)
	private String id;
	
	// Name of the package
	@Column(name="name")
	@Size(max=30)
	@NotNull
	private String name;
	
	// description for the package
	@Column(name="description")
	@Size(max=255)
	private String description;
	
	// product links included in this package
	@OneToMany
	@JoinColumn(name="product_package_id", referencedColumnName="id")
	@JsonIgnore
	private List<ProductPackageProductLink> productLinks = new ArrayList<ProductPackageProductLink>();
	
	// Total price of all products in the package. A transient field, calculated on retrieval of the package, based on the latest exchange rates, etc.
	@Transient
	private double totalPrice;
	
	// Currency for the price. A transient field
	@Transient
	private String currency;
	
	// List of product objects. A transient field, looked up on retrieval of a package to ensure it contains the latest product information at that time
	@Transient
	private List<Product> products = new ArrayList<Product>();
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the productLinks
	 */
	@JsonIgnore
	public List<ProductPackageProductLink> getProductLinks() {
		return productLinks;
	}

	/**
	 * @param productLinks the productLinks to set
	 */
	public void setProductLinks(List<ProductPackageProductLink> productLinks) {
		this.productLinks = productLinks;
	}
	
	
	/**
	 * Return the product links as a string list of the ids only
	 * @return
	 */
	@JsonIgnore
	public List<String> getProductLinksAsIds() {		
		return productLinks.stream().map(pl->pl.getProductPackageProductLinkPK().getProductId()).collect(Collectors.toList());
	}

	/**
	 * @return the totalPrice
	 */
	@JsonIgnore
	public double getTotalPriceOriginal() {
		return totalPrice;
	}
	
	/**
	 * @return the totalPrice, rounded to 2 decimal places
	 */
	public double getTotalPrice() {
		return Math.round(totalPrice * 100.0) / 100.0;
	}

	/**
	 * @param totalPrice the totalPrice to set
	 */
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	/**
	 * @return the products
	 */
	public List<Product> getProducts() {
		return products;
	}

	/**
	 * @param products the products to set
	 */
	public void setProducts(List<Product> products) {
		this.products = products;
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}
    
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (obj == this) return true;
        if (!(obj instanceof ProductPackage)) return false;
        ProductPackage p = (ProductPackage) obj;
        return Objects.equals(getId(), p.getId());
    }
	
	
}
