package com.markwilliamson.productpackagemanagement.model.domain;

import java.util.Objects;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Domain object representing a Package -> product link 
 * The products are stored elsewhere and it is assumed that products can be created but not deleted. 
 * Whether they can be updated is unknown, so to cover this we simply store the product IDs against the 
 * package. We can then fetch the current version of each product when the package is looked up.
 *    
 * @author mwilliamson
 *
 */
@Entity
@Table(name="PROD_PKG_PRODUCT_LNK")
public class ProductPackageProductLink {
	
	@EmbeddedId
    protected ProductPackageProductLinkPK pk;
	
	/**
	 * @return the ProductPackageProductLinkPK
	 */
	public ProductPackageProductLinkPK getProductPackageProductLinkPK() {
		return pk;
	}

	/**
	 * @param productPackageProductLinkPK the ProductPackageProductLinkPK to set
	 */
	public void setProductPackageProductLinkPK(ProductPackageProductLinkPK pk) {
		this.pk = pk;
	}
	

	@Override
	public int hashCode() {
		return Objects.hash(getProductPackageProductLinkPK());
	}
    
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (obj == this) return true;
        if (!(obj instanceof ProductPackageProductLink)) return false;
        ProductPackageProductLink p = (ProductPackageProductLink) obj;
        return Objects.equals(getProductPackageProductLinkPK(), p.getProductPackageProductLinkPK());
    }
	
}
