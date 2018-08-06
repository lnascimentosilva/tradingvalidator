package com.luxsoft.tradingvalidatorapi.trade.spot.validator.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.luxsoft.tradingvalidatorapi.errormessage.model.ErrorMessage;
import com.luxsoft.tradingvalidatorapi.trade.model.Trade;
import com.luxsoft.tradingvalidatorapi.trade.validator.service.BaseValidatorServices;

@Service(value="spotValidator")
public class SpotValidatorServices extends BaseValidatorServices{
	
	private static final String TYPE = "Spot";
	@Value("${error.spot.date.not.twoDaysAhead}")
	private String twoDaysAhead;
	
	public Optional<List<ErrorMessage>> validate(Trade trade) {
		Optional<List<ErrorMessage>> errorsOptional = super.validate(trade);
		
		List<ErrorMessage> errors = errorsOptional.orElseGet(ArrayList::new);
		
		if (trade != null) {
			
			//checks if value date is 2 business days ahead from trade date
			LocalDate tradeDate = trade.getTradeDate();
			if (trade.getValueDate() != null 
					&& tradeDate != null) {
				for(int i = 0; i < 2; i++) {
					tradeDate = tradeDate.plusDays(1L);
					while(dateServices.isNotWorkingDay(tradeDate)) {
						tradeDate = tradeDate.plusDays(1L);
					}
				}
				if (!tradeDate.isEqual(trade.getValueDate())) {
					errors.add(new ErrorMessage(VALUE_DATE, twoDaysAhead));
				}				
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