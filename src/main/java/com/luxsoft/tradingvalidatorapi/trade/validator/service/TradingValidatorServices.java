package com.luxsoft.tradingvalidatorapi.trade.validator.service;

import java.util.List;
import java.util.Optional;

import com.luxsoft.tradingvalidatorapi.errormessage.model.ErrorMessage;
import com.luxsoft.tradingvalidatorapi.trade.model.Trade;

public interface TradingValidatorServices {
	
	Optional<List<ErrorMessage>> validate(Trade trade);
	String getType();
}