package com.markwilliamson.productpackagemanagement.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO object representing an input package of products. 
 * The products are stored elsewhere and it is assumed that products can be created but not deleted. 
 * Whether they can be updated is unknown, so to cover this we simply store the product IDs against the 
 * package. We can then fetch the current version of each product when the package is looked up. 
 * This also ensures we have the latest version of each product for each fetch of the package.
 *    
 * @author mwilliamson
 *
 */
public class ProductPackageInput implements Serializable {

	// default serial version id
	private static final long serialVersionUID = 1L;

	// Unique id for the package
	@Id
	@Size(max=30, message="max length for id is 30 characters")
	@NotNull(message="id attribute is required")
	private String id;
	
	// Name of the package
	@Size(max=30, message="max length for name is 30 characters")
	@NotNull(message="name attribute is required")
	private String name;
	
	// description for the package
	@Size(max=255, message="max length for description is 255 characters")
	private String description;
	
	// string list of ids for products included in this package
	@NotEmpty(message="productIds attribute is required and should be a string array of product ids")
	private List<String> productIds = new ArrayList<String>();
	
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
	 * @return the String list of product ids
	 */
	public List<String> getProductIds() {
		return productIds;
	}
	
	/**
	 * @param productIds string list of product ids
	 */
	public void setProductIds(List<String> productIds) {
		this.productIds = productIds;
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}
    
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (obj == this) return true;
        if (!(obj instanceof ProductPackageInput)) return false;
        ProductPackageInput p = (ProductPackageInput) obj;
        return Objects.equals(getId(), p.getId());
    }
	
	
}
