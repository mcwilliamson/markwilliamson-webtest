package com.markwilliamson.productpackagemanagement.service.currencyconversion;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.markwilliamson.productpackagemanagement.model.FixerIoLatestExchangRates;
import com.markwilliamson.productpackagemanagement.model.FixerIoSupportedCurrencySymbols;

@Service
public class FixerIoCurrencyConversionService implements CurrencyConversionService {
	
	@Value("${fixerio.access.key}")
	private String _FixerIoAccessKey;
	
	@Value("${fixerio.base.url}")
	private String _FixerIoBaseUrl;
	
	private Map<String, String> supportedCurrencySymbols = null;

	@Override
	public double convert(double value, String fromCurrency, String toCurrency) {
		
		// http://data.fixer.io/api/latest?access_key=<ACCESS_KEY>&symbols=<FROM_CURRENCY>,<TO_CURRENCY>
		
		// Validate the currency symbols supplied are valid
		if(!currencySymbolIsValid(fromCurrency)) {
			throw new InvalidCurrencyException(fromCurrency);
		}
		
		if(!currencySymbolIsValid(toCurrency)) {
			throw new InvalidCurrencyException(toCurrency);
		}
		
		if(fromCurrency.equals(toCurrency)) {
			// No conversion required, just return the value
			return value;
		}
		
		// Retrieve the latest exchange rates from Fixer.io.
		/* NOTE: The free API key license restricts the endpoints allowed, so I'm restricted to "latest" without any way to change the "Base" exchange rate value
		 * As such, I need to do the calculation manually
		 */
		
		RestTemplate restTemplate = new RestTemplate();
		FixerIoLatestExchangRates currencyExchangeRates = restTemplate.getForObject(_FixerIoBaseUrl + "/latest?access_key=" + _FixerIoAccessKey + "&symbols=" + fromCurrency + "," + toCurrency, FixerIoLatestExchangRates.class); 
		
		
		// Do the conversion calculations
		Float conversionRate = (1 / currencyExchangeRates.getRates().get(fromCurrency)) * currencyExchangeRates.getRates().get(toCurrency);
		return value * conversionRate;
		
	}
	
	@Override
	public Map<String, String> getSupportedCurrencySymbols() {

		//	http://data.fixer.io/api/symbols?access_key=<ACCESS_KEY>
		
		if (supportedCurrencySymbols!=null) {
			return supportedCurrencySymbols;
		}
		
		RestTemplate restTemplate = new RestTemplate();
		FixerIoSupportedCurrencySymbols fixerIoSupportedCurrencySymbols = restTemplate.getForObject(_FixerIoBaseUrl + "/symbols?access_key=" + _FixerIoAccessKey, FixerIoSupportedCurrencySymbols.class);
		
		supportedCurrencySymbols = fixerIoSupportedCurrencySymbols.getSymbols();
		return supportedCurrencySymbols;
		
	}
	
	private boolean currencySymbolIsValid(String currency) {
		return getSupportedCurrencySymbols().get(currency) != null;
	}

}
