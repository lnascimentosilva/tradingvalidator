package com.luxsoft.tradingvalidatorapi.trade.forward.validator.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.luxsoft.tradingvalidatorapi.errormessage.model.ErrorMessage;
import com.luxsoft.tradingvalidatorapi.trade.model.Trade;
import com.luxsoft.tradingvalidatorapi.trade.validator.service.BaseValidatorServices;

@Service(value="forwardValidator")
public class ForwardValidatorServices extends BaseValidatorServices{
	
	private static final String TYPE = "Forward";
	@Value("${error.spot.date.not.afterCurrentDate}")
	private String afterCurrentDate;
	
	public Optional<List<ErrorMessage>> validate(Trade trade) {
		Optional<List<ErrorMessage>> errorsOptional = super.validate(trade);
		
		List<ErrorMessage> errors = errorsOptional.orElseGet(ArrayList::new);
		
		if (trade != null) {
			
			//checks if value date is after current date
			if (trade.getValueDate() != null 
					&& !dateServices.getCurrentDate().isBefore(trade.getValueDate())) {
					errors.add(new ErrorMessage(VALUE_DATE, afterCurrentDate));				
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