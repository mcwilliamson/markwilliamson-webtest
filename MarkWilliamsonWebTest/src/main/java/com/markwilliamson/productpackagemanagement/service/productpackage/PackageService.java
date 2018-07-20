package com.markwilliamson.productpackagemanagement.service.productpackage;

import java.util.List;

import com.markwilliamson.productpackagemanagement.model.ProductPackageInput;
import com.markwilliamson.productpackagemanagement.model.domain.ProductPackage;

public interface PackageService {

	public List<ProductPackage> getAllPackages();
	
	public ProductPackage getPackage(String id, String currency);
	
	public ProductPackage createPackage(ProductPackageInput productPackage);
	
	public ProductPackage updatePackage(ProductPackageInput productPackage);
	
	public void deletePackageById (String id);
	
}
