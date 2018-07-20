package com.markwilliamson.productpackagemanagement.service.product;

import java.util.List;

import com.markwilliamson.productpackagemanagement.model.Product;

public interface ProductService {
	
	public Product getProduct(String id);
	
	public List<Product> getAllProducts();
	
}
