package com.luxsoft.tradingvalidatorapi.trade.service;

import static java.util.Arrays.asList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luxsoft.tradingvalidatorapi.errormessage.model.ErrorMessage;
import com.luxsoft.tradingvalidatorapi.trade.model.Trade;
import com.luxsoft.tradingvalidatorapi.trade.validator.service.TradingValidatorServices;

@Service
public class TradeServicesImpl implements TradeServices {

	private final Map<String, TradingValidatorServices> servicesByType = new HashMap<>();

	@Autowired
	public TradeServicesImpl(List<TradingValidatorServices> services) {
		for (TradingValidatorServices service : services) {
			register(service.getType(), service);
		}
	}

	private void register(String type, TradingValidatorServices service) {
		this.servicesByType.put(type, service);
	}
	
	private TradingValidatorServices getTradingValidatorServices(Trade trade){
		TradingValidatorServices defaultValue = this.servicesByType.get("BASE");
		if (trade == null || trade.getType() == null) {
			return defaultValue;
		}
		return this.servicesByType.getOrDefault(trade.getType(), defaultValue);
	  }

	@Override
	public Optional<List<ErrorMessage>> validate(Trade trade) {
		TradingValidatorServices tradingValidatorServices = getTradingValidatorServices(trade);
		return tradingValidatorServices.validate(trade);
	}

	@Override
	public List<Trade> validate(List<Trade> trades) {
		if(trades == null || trades.isEmpty()) {
			TradingValidatorServices tradingValidatorServices = getTradingValidatorServices(null);
			Optional<List<ErrorMessage>> errors = tradingValidatorServices.validate(null);
			Trade trade = new Trade();
			trade.setErrors(errors.orElseGet(null));
			return asList(trade);
		}
		
		for (Trade trade : trades) {
			TradingValidatorServices tradingValidatorServices = getTradingValidatorServices(trade);
			Optional<List<ErrorMessage>> errors = tradingValidatorServices.validate(trade);
			trade.setErrors(errors.orElse(null));
		}
		
		return trades;
	}
}