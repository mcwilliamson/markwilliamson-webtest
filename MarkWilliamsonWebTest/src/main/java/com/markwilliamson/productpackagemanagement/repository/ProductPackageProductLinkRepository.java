package com.markwilliamson.productpackagemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.markwilliamson.productpackagemanagement.model.domain.ProductPackageProductLink;
import com.markwilliamson.productpackagemanagement.model.domain.ProductPackageProductLinkPK;

@Repository
public interface ProductPackageProductLinkRepository extends JpaRepository<ProductPackageProductLink, ProductPackageProductLinkPK>{

}
