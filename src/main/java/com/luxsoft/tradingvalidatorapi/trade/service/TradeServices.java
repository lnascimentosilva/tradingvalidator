package com.luxsoft.tradingvalidatorapi.trade.service;

import java.util.List;
import java.util.Optional;

import com.luxsoft.tradingvalidatorapi.errormessage.model.ErrorMessage;
import com.luxsoft.tradingvalidatorapi.trade.model.Trade;

public interface TradeServices {
	
	public Optional<List<ErrorMessage>> validate(Trade trade);
	public List<Trade> validate(List<Trade> trades);

}
