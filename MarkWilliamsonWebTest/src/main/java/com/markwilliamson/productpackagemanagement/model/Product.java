package com.markwilliamson.productpackagemanagement.model;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/** 
 * POJO representing a Product.
 * @author mwilliamson
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product implements Serializable {

	// default serial version id
	private static final long serialVersionUID = 1L;

	// Unique product ID
	private String id;
	
	// Product name
	private String name;
	
	// Product price in USD cents 
	private double usdPrice;	
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the usdPrice (in cents by default)
	 */
	@JsonIgnore
	public double getUsdPriceInCents() {
		return usdPrice;
	}

	/**
	 * @param usdPrice the usdPrice to set
	 */
	public void setUsdPrice(double usdPrice) {
		this.usdPrice = usdPrice;
	}
	
	/**
	 * @return the usdPrice in dollars
	 */
	public double getUsdPrice() {
		return usdPrice / 100;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}
    
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (obj == this) return true;
        if (!(obj instanceof Product)) return false;
        Product p = (Product) obj;
        return Objects.equals(getId(), p.getId());
    }
	
}
