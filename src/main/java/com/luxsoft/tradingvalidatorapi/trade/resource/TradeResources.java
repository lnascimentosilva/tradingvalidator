package com.luxsoft.tradingvalidatorapi.trade.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luxsoft.tradingvalidatorapi.errormessage.model.ErrorMessage;
import com.luxsoft.tradingvalidatorapi.trade.model.Trade;
import com.luxsoft.tradingvalidatorapi.trade.service.TradeServices;

@RestController
@RequestMapping("/trades")
public class TradeResources {
	
	@Autowired
	private TradeServices tradeServices;

	@PostMapping
	public ResponseEntity<?> validate(@RequestBody Trade trade) {
		return tradeServices.validate(trade)
				.map(errors -> new ResponseEntity<List<ErrorMessage>>(errors, HttpStatus.BAD_REQUEST))
				.orElse(ResponseEntity.noContent().build());
	}
	
	@PostMapping("/bulk")
	public ResponseEntity<?> validate(@RequestBody List<Trade> trades) {
		List<Trade> tradesResponse = tradeServices.validate(trades);
		
		return tradesResponse.stream()
				.filter(trade -> trade.getErrors() != null)
				.findFirst()
				.map(trade -> new ResponseEntity<List<Trade>>(tradesResponse, HttpStatus.BAD_REQUEST))
				.orElse(ResponseEntity.noContent().build());
	}

}
