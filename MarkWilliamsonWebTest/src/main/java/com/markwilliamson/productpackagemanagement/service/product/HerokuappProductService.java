package com.markwilliamson.productpackagemanagement.service.product;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownHttpStatusCodeException;

import com.markwilliamson.productpackagemanagement.model.Product;

/**
 * Service for working with Products. Products are looked up from an external REST web service.
 * 
 * @author mwilliamson
 *
 */

@Service
public class HerokuappProductService implements ProductService {
	
	private static final Logger log = LoggerFactory.getLogger(HerokuappProductService.class.getName());
		
	@Value("${herokuapp.products.api.base.url}")
	private String _apiBaseUrl;
	
	@Value("${herokuapp.products.api.basic.auth.user}")
	private String _apiBasicAuthUser;
	
	@Value("${herokuapp.products.api.basic.auth.pwd}")
	private String _apiBasicAuthPwd;
	
	@PostConstruct
	private void init() {
		log.debug("created instance of " + this.getClass().getName());
	}
	
	@Override
	public Product getProduct(String id) {
		
		Product product;
		
		try {
			
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.getInterceptors().add(getBasicAuthInterceptor());
			product = restTemplate.getForObject(_apiBaseUrl + "/" + id, Product.class);
			
		} catch (HttpClientErrorException e) {
			if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
				throw new ProductNotFoundException(id);
			} else {
				throw e;
			}
		}  catch (HttpServerErrorException | UnknownHttpStatusCodeException e) {
			throw e;
		}
		
		return product;
	}
	
	@Override
	public List<Product> getAllProducts() {

		List<Product> productList;
		
		try {
			
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.getInterceptors().add(getBasicAuthInterceptor());
			ResponseEntity<Product[]> responseEntity = restTemplate.getForEntity(_apiBaseUrl, Product[].class);
			Product[] productArr = responseEntity.getBody();
			productList = Arrays.asList(productArr);
			
		} catch (HttpClientErrorException e) {
			if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
				throw new NoProductsFoundException();
			} else {
				throw e;
			}
		}  catch (HttpServerErrorException | UnknownHttpStatusCodeException e) {
			throw e;
		}
		
		return productList;
		
	}
	
	private BasicAuthorizationInterceptor getBasicAuthInterceptor() {
		return new BasicAuthorizationInterceptor(_apiBasicAuthUser, _apiBasicAuthPwd);
	}

}
