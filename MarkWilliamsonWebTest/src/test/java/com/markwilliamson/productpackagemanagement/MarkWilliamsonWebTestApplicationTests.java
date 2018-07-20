package com.markwilliamson.productpackagemanagement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.markwilliamson.productpackagemanagement.model.Product;
import com.markwilliamson.productpackagemanagement.service.currencyconversion.CurrencyConversionService;
import com.markwilliamson.productpackagemanagement.service.currencyconversion.InvalidCurrencyException;
import com.markwilliamson.productpackagemanagement.service.product.HerokuappProductService;
import com.markwilliamson.productpackagemanagement.service.product.ProductNotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MarkWilliamsonWebTestApplicationTests {
	
	@Autowired
	CurrencyConversionService fixerIoCurrencyConversionService;
	
	@Autowired
	HerokuappProductService herokuappProductService;
	
	@Test
	public void testCurrencyConversionService() {
		
		double output = fixerIoCurrencyConversionService.convert(10.00, "USD", "GBP");
		
		output = fixerIoCurrencyConversionService.convert(10.00, "USD", "USD");
		assertEquals(output, 10.00, 0);
	}
	
	@Test(expected = InvalidCurrencyException.class)
	public void testCurrencyConversionServiceInvalidCurrency() {
		
		fixerIoCurrencyConversionService.convert(10.00, "USD", "ABC");
		
	}
	
	@Test(expected = ProductNotFoundException.class)
	public void testHerokuappProductServiceGetProductInvalid() {
		
		herokuappProductService.getProduct("1234");
		
	}
	
	@Test
	public void testHerokuappProductServiceGetProduct() {
		
		String productId = "7dgX6XzU3Wds";
		Product prod = herokuappProductService.getProduct(productId);
		
		assertTrue(prod.getId().equals(productId));
		assertTrue(prod.getName().equals("Sword"));
	}
	
	@Test
	public void testHerokuappProductServiceGetAllProducts() {
		
		List<Product> products = new ArrayList<Product>();
		products = herokuappProductService.getAllProducts();
		
		assertTrue(products.size()>0);
		
	}

}
