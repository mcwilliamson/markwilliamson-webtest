package com.markwilliamson.productpackagemanagement.service.productpackage;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductPackageNotFoundException extends RuntimeException {

	// default serial version id
	private static final long serialVersionUID = 1L;
		
	public ProductPackageNotFoundException(String id) {
		super("Product Package not found for id : " + id);
	}
}
