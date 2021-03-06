package com.markwilliamson.productpackagemanagement.service.product;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductNotFoundException extends RuntimeException {

	// default serial version id
	private static final long serialVersionUID = 1L;
	
	public ProductNotFoundException(String productId) {
		super("Product not found - id: " + productId);
	}
	
}
