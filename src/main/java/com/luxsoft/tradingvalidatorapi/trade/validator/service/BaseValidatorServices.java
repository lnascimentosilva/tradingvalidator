package com.luxsoft.tradingvalidatorapi.trade.validator.service;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.luxsoft.tradingvalidatorapi.common.exception.IntegrationException;
import com.luxsoft.tradingvalidatorapi.customer.model.Customer;
import com.luxsoft.tradingvalidatorapi.customer.service.CustomerServices;
import com.luxsoft.tradingvalidatorapi.date.service.DateServices;
import com.luxsoft.tradingvalidatorapi.errormessage.model.ErrorMessage;
import com.luxsoft.tradingvalidatorapi.legalentity.model.LegalEntity;
import com.luxsoft.tradingvalidatorapi.legalentity.service.LegalEntityServices;
import com.luxsoft.tradingvalidatorapi.trade.model.Trade;

@Service(value="baseValidator")
public class BaseValidatorServices implements TradingValidatorServices{
	
	private static final String TYPE = "BASE";

	private static final String TRADE = "trade";

	private static final String LEGAL_ENTITY = "legalEntity";

	protected static final String VALUE_DATE = "valueDate";

	private static final String TRADE_DATE = "tradeDate";

	private static final String CCY_PAIR = "ccyPair";

	private static final String CUSTOMER = "customer";

	@Value("${error.value.empty}")
	protected String empty;
	
	@Value("${error.date.before.tradeDate}")
	private String beforeTradeDate;
	
	@Value("${error.date.nonWorkingDays}")
	private String nonWorkingDays;
	
	@Value("${error.value.notSupported}")
	protected String notSupported;
	
	@Value("${error.withoutSupportedCustomers}")
	private String withoutSupportedCustomers;
	
	@Value("${error.currency.wrongLength}")
	private String wrongLength;
	
	@Value("${error.currency.nonISO4217}")
	private String nonISO4217;
	
	@Value("${error.withoutAuthorizedLegalEntities}")
	private String withoutAuthorizedLegalEntities;
	
	@Autowired
	protected DateServices dateServices;
	
	@Autowired
	private CustomerServices customerServices;
	
	@Autowired
	private LegalEntityServices legalEntityServices;
	
	public Optional<List<ErrorMessage>> validate(Trade trade) {
		List<ErrorMessage> errors = new ArrayList<>();
		
		if (trade == null) {
			errors.add(new ErrorMessage(TRADE, empty));
		} else {		
			validateEmpty(trade, errors);
			
			Optional<Set<LegalEntity>> legalEntities = legalEntityServices.getAuthorized();
			if (!isEmpty(trade.getLegalEntity())
					&& !legalEntities.orElseThrow(
							() -> new IntegrationException(withoutAuthorizedLegalEntities)
							).contains(new LegalEntity(trade.getLegalEntity()))) {
				errors.add(new ErrorMessage(LEGAL_ENTITY, notSupported));	
			}
			
			//checks if value date is before trade date
			if (trade.getValueDate() != null 
					&& trade.getTradeDate() != null 
					&& trade.getValueDate().isBefore(trade.getTradeDate())) {
				errors.add(new ErrorMessage(VALUE_DATE, beforeTradeDate));
			}
			
			//checks if value date fall on a non working day
			if (trade.getValueDate() != null 
					&& dateServices.isNotWorkingDay(trade.getValueDate())) {
				errors.add(new ErrorMessage(VALUE_DATE, nonWorkingDays));
			}
			
			//checks if the customer is supported
			Optional<Set<Customer>> supportedCustomers = customerServices.getSupported();
			if (!isEmpty(trade.getCustomer())
					&& !supportedCustomers.orElseThrow(
							() -> new IntegrationException(withoutSupportedCustomers)
							).contains(new Customer(trade.getCustomer()))) {
				errors.add(new ErrorMessage(CUSTOMER, notSupported));
			}
			
			if (!isEmpty(trade.getCcyPair())) {
				if(trade.getCcyPair().length() != 6) {
					errors.add(new ErrorMessage(CCY_PAIR, wrongLength));
					
				} else {
					String currency1 = trade.getCcyPair().substring(0,3);
					String currency2 = trade.getCcyPair().substring(3);
				
					validateCurrency(currency1, CCY_PAIR, errors);
					validateCurrency(currency2, CCY_PAIR, errors);
				}	
			}
			
		}	
		
		if(errors.isEmpty()) {
			return Optional.empty();
		} else {
			return Optional.of(errors);
		}
	}

	protected void validateCurrency(String currencyCode, String fieldName, List<ErrorMessage> errors) {
		try {
			Currency.getInstance(currencyCode);
		} catch (IllegalArgumentException e) {
			errors.add(new ErrorMessage(fieldName, String.format(nonISO4217, currencyCode)));
		}
	}

	private void validateEmpty(Trade trade, List<ErrorMessage> errors) {
		if (isEmpty(trade.getCustomer())) {
			errors.add(new ErrorMessage(CUSTOMER, empty));			
		} 
		
		if (isEmpty(trade.getCcyPair())) {
			errors.add(new ErrorMessage(CCY_PAIR, empty));			
		} 
		
		if (trade.getTradeDate() == null) {
			errors.add(new ErrorMessage(TRADE_DATE, empty));			
		} 
		
		if (trade.getValueDate() == null) {
			errors.add(new ErrorMessage(VALUE_DATE, empty));			
		} 
		
		if (isEmpty(trade.getLegalEntity())) {
			errors.add(new ErrorMessage(LEGAL_ENTITY, empty));			
		}
	}
	
	protected boolean isEmpty(String value) {
		return value == null || value.trim().isEmpty();
	}

	@Override
	public String getType() {
		return TYPE;
	}
}