package com.markwilliamson.productpackagemanagement.service.currencyconversion;

import java.util.Map;

public interface CurrencyConversionService {
	
	public double convert(double value, String fromCurrency, String toCurrency);
	
	public Map<String, String> getSupportedCurrencySymbols();

}
