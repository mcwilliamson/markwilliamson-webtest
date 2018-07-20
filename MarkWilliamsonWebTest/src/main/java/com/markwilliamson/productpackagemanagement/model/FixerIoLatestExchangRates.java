package com.markwilliamson.productpackagemanagement.model;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * POJO representing the Rest response from http://fixer.io "latest" endpoint
 * @author mwilliamson
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FixerIoLatestExchangRates implements Serializable {

	/* JSON response format:
	 * {"success":true,"timestamp":1531832646,"base":"EUR","date":"2018-07-17","rates":{"USD":1.170824,"GBP":0.887367}}
	 */
	
	// default serial version id
	private static final long serialVersionUID = 1L;

	private boolean success;
	
	private int timestamp;
	
	private String base;
	
	private String date;
	
	private Map<String, Float> rates;

	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * @param success the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 * @return the timestamp
	 */
	public int getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(int timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return the base
	 */
	public String getBase() {
		return base;
	}

	/**
	 * @param base the base to set
	 */
	public void setBase(String base) {
		this.base = base;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the rates
	 */
	public Map<String, Float> getRates() {
		return rates;
	}

	/**
	 * @param rates the rates to set
	 */
	public void setRates(Map<String, Float> rates) {
		this.rates = rates;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getTimestamp(), getDate());
	}
    
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (obj == this) return true;
        if (!(obj instanceof FixerIoLatestExchangRates)) return false;
        FixerIoLatestExchangRates p = (FixerIoLatestExchangRates) obj;
        return Objects.equals(getTimestamp(), p.getTimestamp()) && Objects.equals(getDate(), p.getDate());
    }	
	
}
