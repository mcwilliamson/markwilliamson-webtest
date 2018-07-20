package com.markwilliamson.productpackagemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.markwilliamson.productpackagemanagement.model.domain.ProductPackage;

@Repository
public interface ProductPackageRepository extends JpaRepository<ProductPackage, String> {
	
}
