package com.markwilliamson.productpackagemanagement.web;

import java.util.Date;

/**
 * Helper class to provide restricted and formatted exception details. Used for all REST exceptions
 * @author mwilliamson
 *
 */
public class RestErrorDetails {

	private Date timestamp;
	private String message;
	private String details;

	public RestErrorDetails(Date timestamp, String message, String details) {
		super();
		this.timestamp = timestamp;
	    this.message = message;
	    this.details = details;
	}

	/**
	 * @return the timestamp
	 */
	public Date getTimestamp() {
		return timestamp;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @return the details
	 */
	public String getDetails() {
		return details;
	}
}
