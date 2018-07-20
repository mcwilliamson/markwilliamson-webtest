package com.markwilliamson.productpackagemanagement.model.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Composite Primary Key for ProductPackageProductLink
 * @author mwilliamson
 *
 */
@Embeddable
public class ProductPackageProductLinkPK implements Serializable {
	
	// default serial version id
	private static final long serialVersionUID = 1L;
	
	@Column(name="product_package_id")
	private String productPackageId;
	
	@Column(name="product_id")
    private String productId;
    
	public ProductPackageProductLinkPK() {
		// default constructor
	}
	
	public ProductPackageProductLinkPK(String productPackageId, String productId) {
		this.productPackageId = productPackageId;
		this.productId = productId;
	}
	
	/**
	 * @return the productPackageId
	 */
	public String getProductPackageId() {
		return productPackageId;
	}
	/**
	 * @param productPackageId the product package id to set
	 */
	public void setProductPackageId(String productPackageId) {
		this.productPackageId = productPackageId;
	}
	/**
	 * @return the productId
	 */
	public String getProductId() {
		return productId;
	}
	/**
	 * @param productId the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getProductPackageId(), getProductId());
	}
    
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (obj == this) return true;
        if (!(obj instanceof ProductPackageProductLinkPK)) return false;
        ProductPackageProductLinkPK pk = (ProductPackageProductLinkPK) obj;
        return Objects.equals(pk.getProductPackageId(), getProductPackageId()) && Objects.equals(pk.getProductId(), getProductId());
    }
       
    
}