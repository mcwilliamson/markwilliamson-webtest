package com.markwilliamson.productpackagemanagement.service.currencyconversion;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidCurrencyException extends RuntimeException {
	
	// Default serial version uid
	private static final long serialVersionUID = 1L;
	
	private String currency;
	
	public InvalidCurrencyException(String currency) {
		this.currency = currency;
	}
	
	@Override
	public String getMessage() {
		return "Invalid currency : " + currency;
	}
    
}
