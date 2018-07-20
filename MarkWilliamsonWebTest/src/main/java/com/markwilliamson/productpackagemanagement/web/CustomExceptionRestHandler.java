package com.markwilliamson.productpackagemanagement.web;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.markwilliamson.productpackagemanagement.service.currencyconversion.InvalidCurrencyException;
import com.markwilliamson.productpackagemanagement.service.product.NoProductsFoundException;
import com.markwilliamson.productpackagemanagement.service.product.ProductNotFoundException;
import com.markwilliamson.productpackagemanagement.service.productpackage.ProductPackageExistsException;
import com.markwilliamson.productpackagemanagement.service.productpackage.ProductPackageNotFoundException;

/**
 * Custom exception handler to ensure exceptions are converted to an appropriate JSON format before being sent to the client
 * @author mwilliamson
 *
 */
@ControllerAdvice
@RestController
public class CustomExceptionRestHandler extends ResponseEntityExceptionHandler {
	
	Logger log = LoggerFactory.getLogger(this.getClass().getName());
	
	@ExceptionHandler(ProductNotFoundException.class)
	public final ResponseEntity<Object> handleProductNotFoundException(ProductNotFoundException ex, WebRequest request) {
		log.error(ex.getMessage(), ex);
		RestErrorDetails errorDetails = new RestErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<Object>(errorDetails, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ProductPackageNotFoundException.class)
	public final ResponseEntity<Object> handleProductPackageNotFoundException(ProductPackageNotFoundException ex, WebRequest request) {
		log.error(ex.getMessage(), ex);
		RestErrorDetails errorDetails = new RestErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<Object>(errorDetails, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ProductPackageExistsException.class)
	public final ResponseEntity<Object> handleProductPackageExistsException(ProductPackageExistsException ex, WebRequest request) {
		log.error(ex.getMessage(), ex);
		RestErrorDetails errorDetails = new RestErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<Object>(errorDetails, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NoProductsFoundException.class)
	public final ResponseEntity<Object> handleNoProductsFoundException(NoProductsFoundException ex, WebRequest request) {
		log.error(ex.getMessage(), ex);
		RestErrorDetails errorDetails = new RestErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<Object>(errorDetails, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InvalidCurrencyException.class)
	public final ResponseEntity<Object> handleInvalidCurrencyException(InvalidCurrencyException ex, WebRequest request) {
		log.error(ex.getMessage(), ex);
		RestErrorDetails errorDetails = new RestErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<Object>(errorDetails, HttpStatus.BAD_REQUEST);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, 
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		
		
		String errorMessage = ex.getBindingResult().getFieldErrors().stream()
	                 .map(DefaultMessageSourceResolvable::getDefaultMessage)
	                 .findFirst()
	                 .orElse(ex.getMessage());
		
		log.error(errorMessage, ex);
	    
		RestErrorDetails errorDetails = new RestErrorDetails(new Date(), errorMessage, request.getDescription(false));
		return new ResponseEntity<Object>(errorDetails, HttpStatus.BAD_REQUEST);
	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.error(ex.getMessage(), ex);
		RestErrorDetails errorDetails = new RestErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<Object>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
