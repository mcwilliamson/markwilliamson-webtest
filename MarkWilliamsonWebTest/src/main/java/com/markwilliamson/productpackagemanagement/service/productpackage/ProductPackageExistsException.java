package com.markwilliamson.productpackagemanagement.service.productpackage;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProductPackageExistsException extends RuntimeException {

	// default serial version id
	private static final long serialVersionUID = 1L;
	
	public ProductPackageExistsException(String id) {
		super("Product Package already exists for id : " + id);
	}
	
}
