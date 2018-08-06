# Trading Validator Api

API developed for validation of trade operations.

# Using:
- Spring Boot
- Restful services
- Maven

# Important:
- Spot and Forward types, validation of value date:
I've read on https://www.investopedia.com/ask/answers/042315/what-difference-between-forward-rate-and-spot-rate.asp that for SPOT type, the value date must be 2 business days after the trade date, and for FORWARD type, the value date must be a future date towards the current date.

- Adding more non working days:
Put extra dates on com.luxsoft.tradingvalidatorapi.date.service.DateServicesImpl method getNonWorkingDays().

## How to start:
Run the main method of class com.luxsoft.tradingvalidatorapi.TradingValidatorApiApplication and a tomcat will automatically start on port: 8080.

## Unit tests:
The following classes are configured to run unit tests:
- com.luxsoft.tradingvalidatorapi.trade.spot.validator.service.SpotValidatorServicesUTest
- com.luxsoft.tradingvalidatorapi.date.service.DateServicesUTest
- com.luxsoft.tradingvalidatorapi.trade.service.TradeServicesUTest
- com.luxsoft.tradingvalidatorapi.trade.forward.validator.service.ForwardValidatorServicesUTest
- com.luxsoft.tradingvalidatorapi.trade.validator.service.BaseValidatorServicesUTest
- com.luxsoft.tradingvalidatorapi.trade.resource.TradeResourcesUTest
- com.luxsoft.tradingvalidatorapi.legalentity.service.LegalEntityServicesUTest
- com.luxsoft.tradingvalidatorapi.customer.service.CustomerServicesUTest
- com.luxsoft.tradingvalidatorapi.trade.options.validator.service.OptionsValidatorServicesUTest



URLs: 
###### Validate single trade operation
- **POST** http://localhost:8080/tradingvalidator-api/trades
- Returns **204** whether there are no errors.
- Returns **404** whether there are errors.
```
{
	"customer": "PLUTO1",
	"ccyPair": "EURUSD",
	"type": "Spot",
	"direction": "SELL",
	"tradeDate": "2016-08-11",
	"amount1": 1000000.00,
	"amount2": 1120000.00,
	"rate": 1.12,
	"valueDate": "2016-08-22",
	"legalEntity": "CS Zurich",
	"trader": "JohannBaumfiddler"
}
```

###### Validate bulk trade operations
- **POST** http://localhost:8080/tradingvalidator-api/trades/bulk
- Returns **204** whether there are no errors.
- Returns **404** whether there are errors. Errors are described inside errors attribute on every item which has any error.
```
[
	{
		"customer": "PLUTO1",
		"ccyPair": "EURUSD",
		"type": "Spot",
		"direction": "BUY",
		"tradeDate": "2016-08-11",
		"amount1": 1000000.00,
		"amount2": 1120000.00,
		"rate": 1.12,
		"valueDate": "2016-08-15",
		"legalEntity": "CS Zurich",
		"trader": "JohannBaumfiddler"
	},
	{
		"customer": "PLUTO1",
		"ccyPair": "EURUSD",
		"type": "Spot",
		"direction": "SELL",
		"tradeDate": "2016-08-11",
		"amount1": 1000000.00,
		"amount2": 1120000.00,
		"rate": 1.12,
		"valueDate": "2016-08-22",
		"legalEntity": "CS Zurich",
		"trader": "JohannBaumfiddler"
	}
]
