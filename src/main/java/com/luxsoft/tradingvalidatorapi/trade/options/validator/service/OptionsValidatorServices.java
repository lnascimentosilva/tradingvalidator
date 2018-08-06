package com.luxsoft.tradingvalidatorapi.trade.options.validator.service;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.luxsoft.tradingvalidatorapi.errormessage.model.ErrorMessage;
import com.luxsoft.tradingvalidatorapi.trade.model.Trade;
import com.luxsoft.tradingvalidatorapi.trade.validator.service.BaseValidatorServices;

@Service(value="optionsValidator")
public class OptionsValidatorServices extends BaseValidatorServices{
	
	private static final String TYPE = "VanillaOption";
	private static final String DELIVERY_DATE = "deliveryDate";
	private static final String PREMIUM_DATE = "premiumDate";
	private static final String Expiry_DATE = "expiryDate";
	private static final String EXCERCISE_START_DATE = "excerciseStartDate";
	private static final String STYLE = "style";
	private static final String AMERICAN = "AMERICAN";
	private static final Set<String> SUPPORTED_STYLES = asList(new String[]{"EUROPEAN", AMERICAN}).stream().collect(toSet());
	
	@Value("${error.date.notBetweenExpiryDateAndTradeDate}")
	private String notBetweenExpiryDateAndTradeDate;
	
	@Value("${error.date.before.expiryDate}")
	private String beforeExpiryDate;
	
	@Value("${error.date.before.premiumDate}")
	private String beforePremiumDate;

	
	public Optional<List<ErrorMessage>> validate(Trade trade) {
		Optional<List<ErrorMessage>> errorsOptional = super.validate(trade);
		
		List<ErrorMessage> errors = errorsOptional.orElseGet(ArrayList::new);
		
		if (trade != null) {			
			
			if (isEmpty(trade.getStyle())) {
				errors.add(new ErrorMessage(STYLE, empty));
				
			} else if (!SUPPORTED_STYLES.contains(trade.getStyle())){
				errors.add(new ErrorMessage(STYLE, notSupported));
				
			}
			
			if (trade.getDeliveryDate() == null) {
				errors.add(new ErrorMessage(DELIVERY_DATE, empty));
			}
			
			if (trade.getExpiryDate() == null) {
				errors.add(new ErrorMessage(Expiry_DATE, empty));
			}
			
			if (AMERICAN.equals(trade.getStyle())) {
				if (trade.getExcerciseStartDate() == null) {
					errors.add(new ErrorMessage(EXCERCISE_START_DATE, empty));
				}
				
				if (trade.getExcerciseStartDate() != null
						&& trade.getExpiryDate() != null
						&& trade.getTradeDate() != null
						&& (!trade.getExcerciseStartDate().isAfter(trade.getTradeDate()) 
								|| !trade.getExcerciseStartDate().isBefore(trade.getExpiryDate()))) {
					
					errors.add(new ErrorMessage(EXCERCISE_START_DATE, notBetweenExpiryDateAndTradeDate));
				}
				
			}
			
			if (trade.getPremiumDate() == null) {
				errors.add(new ErrorMessage(PREMIUM_DATE, empty));
			}
			
			if (trade.getExpiryDate() != null
					&& trade.getDeliveryDate() != null
					&& !trade.getExpiryDate().isBefore(trade.getDeliveryDate())) {
				
				errors.add(new ErrorMessage(DELIVERY_DATE, beforeExpiryDate));
				
			}
			
			if (trade.getPremiumDate() != null
					&& trade.getDeliveryDate() != null
					&& !trade.getPremiumDate().isBefore(trade.getDeliveryDate())) {
				
				errors.add(new ErrorMessage(DELIVERY_DATE, beforePremiumDate));				
			}
		}	
		
		if(errors.isEmpty()) {
			return Optional.empty();
		} else {
			return Optional.of(errors);
		}
	}
	
	@Override
	public String getType() {
		return TYPE;
	}
}