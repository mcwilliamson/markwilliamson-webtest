package com.markwilliamson.productpackagemanagement.model;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * POJO representing the Rest response from http://fixer.io "symbols" endpoint
 * @author mwilliamson
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FixerIoSupportedCurrencySymbols implements Serializable {

	// default serial version id
	private static final long serialVersionUID = 1L;
	private Map<String, String> symbols;

	/**
	 * @return the symbols
	 */
	public Map<String, String> getSymbols() {
		return symbols;
	}

	/**
	 * @param symbols the symbols to set
	 */
	public void setSymbols(Map<String, String> symbols) {
		this.symbols = symbols;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getSymbols());
	}
    
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (obj == this) return true;
        if (!(obj instanceof FixerIoSupportedCurrencySymbols)) return false;
        FixerIoSupportedCurrencySymbols p = (FixerIoSupportedCurrencySymbols) obj;
        return Objects.equals(getSymbols(), p.getSymbols());
    }
	
}
