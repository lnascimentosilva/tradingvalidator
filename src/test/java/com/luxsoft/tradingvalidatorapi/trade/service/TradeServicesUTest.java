package com.luxsoft.tradingvalidatorapi.trade.service;

import static com.luxsoft.tradingvalidatorapi.commontests.customer.CustomersForTests.pluto1;
import static com.luxsoft.tradingvalidatorapi.commontests.customer.CustomersForTests.pluto2;
import static com.luxsoft.tradingvalidatorapi.commontests.errormessage.ErrorMessagesForTests.emptyCurrency;
import static com.luxsoft.tradingvalidatorapi.commontests.errormessage.ErrorMessagesForTests.emptyCustomer;
import static com.luxsoft.tradingvalidatorapi.commontests.errormessage.ErrorMessagesForTests.emptyLegalEntity;
import static com.luxsoft.tradingvalidatorapi.commontests.errormessage.ErrorMessagesForTests.emptyStyle;
import static com.luxsoft.tradingvalidatorapi.commontests.errormessage.ErrorMessagesForTests.emptyTrade;
import static com.luxsoft.tradingvalidatorapi.commontests.errormessage.ErrorMessagesForTests.emptyTradeDate;
import static com.luxsoft.tradingvalidatorapi.commontests.errormessage.ErrorMessagesForTests.emptyValueDate;
import static com.luxsoft.tradingvalidatorapi.commontests.errormessage.ErrorMessagesForTests.valueDateForwardNotAfterCurrentDate;
import static com.luxsoft.tradingvalidatorapi.commontests.errormessage.ErrorMessagesForTests.valueDateSpotNotTwoDaysAhead;
import static com.luxsoft.tradingvalidatorapi.commontests.legalentity.LegalEntitiesForTests.csZurich;
import static com.luxsoft.tradingvalidatorapi.commontests.trade.TradesForTests.empty;
import static com.luxsoft.tradingvalidatorapi.commontests.trade.TradesForTests.invalidForward;
import static com.luxsoft.tradingvalidatorapi.commontests.trade.TradesForTests.invalidOptionsEmptyStyle;
import static com.luxsoft.tradingvalidatorapi.commontests.trade.TradesForTests.invalidSpot;
import static com.luxsoft.tradingvalidatorapi.commontests.trade.TradesForTests.validForward;
import static com.luxsoft.tradingvalidatorapi.commontests.trade.TradesForTests.validOption;
import static com.luxsoft.tradingvalidatorapi.commontests.trade.TradesForTests.validSpot;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toSet;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.luxsoft.tradingvalidatorapi.customer.service.CustomerServices;
import com.luxsoft.tradingvalidatorapi.date.service.DateServices;
import com.luxsoft.tradingvalidatorapi.errormessage.model.ErrorMessage;
import com.luxsoft.tradingvalidatorapi.legalentity.service.LegalEntityServices;
import com.luxsoft.tradingvalidatorapi.trade.model.Trade;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TradeServicesUTest {

	@Autowired
	private TradeServices tradeServices;
	
	@MockBean
	private DateServices dateServices;
	
	@MockBean
	private CustomerServices customerServices;
	
	@MockBean
	private LegalEntityServices legalEntityServices;

	@Test
	public void validateNullTradeBaseType() {
		Trade trade = null;
		Optional<List<ErrorMessage>> errorMessages = tradeServices.validate(trade);
		
		assertThat(errorMessages.isPresent(), equalTo(true));
		assertThat(errorMessages.get().size(), equalTo(1));
		assertThat(errorMessages.get().equals(asList(emptyTrade())), equalTo(true));
	}
	
	@Test
	public void validateNewTradeBaseType() {
		Optional<List<ErrorMessage>> errorMessages = tradeServices.validate(empty());
		
		assertThat(errorMessages.isPresent(), equalTo(true));
		assertThat(errorMessages.get().size(), equalTo(5));
		List<ErrorMessage> errorsList = asList(emptyCustomer(), emptyCurrency(), emptyTradeDate(), emptyValueDate(), emptyLegalEntity());
		assertThat(errorMessages.get().equals(errorsList), equalTo(true));
	}
	
	@Test
	public void validateForwardType() {
		when(customerServices.getSupported()).thenReturn(Optional.of(asList(pluto1(), pluto2()).stream().collect(toSet())));
		when(dateServices.getCurrentDate()).thenReturn(LocalDate.of(2016, 8, 15));
		when(legalEntityServices.getAuthorized()).thenReturn(Optional.of(asList(csZurich()).stream().collect(toSet())));
		
		Optional<List<ErrorMessage>> errorMessages = tradeServices.validate(invalidForward());

		assertThat(errorMessages.isPresent(), equalTo(true));
		assertThat(errorMessages.get().size(), equalTo(1));
		List<ErrorMessage> errorsList = asList(valueDateForwardNotAfterCurrentDate());
		assertThat(errorMessages.get().equals(errorsList), equalTo(true));
	}
	
	@Test
	public void validateSpotType() {
		when(customerServices.getSupported()).thenReturn(Optional.of(asList(pluto1(), pluto2()).stream().collect(toSet())));
		when(dateServices.isNotWorkingDay(LocalDate.of(2016, 8, 13))).thenReturn(true);
		when(dateServices.isNotWorkingDay(LocalDate.of(2016, 8, 14))).thenReturn(true);
		when(legalEntityServices.getAuthorized()).thenReturn(Optional.of(asList(csZurich()).stream().collect(toSet())));
		
		Optional<List<ErrorMessage>> errorMessages = tradeServices.validate(invalidSpot());

		assertThat(errorMessages.isPresent(), equalTo(true));
		assertThat(errorMessages.get().size(), equalTo(1));
		List<ErrorMessage> errorsList = asList(valueDateSpotNotTwoDaysAhead());
		assertThat(errorMessages.get().equals(errorsList), equalTo(true));
	}
	
	@Test
	public void validateOptionsType() {
		when(customerServices.getSupported()).thenReturn(Optional.of(asList(pluto1(), pluto2()).stream().collect(toSet())));
		when(legalEntityServices.getAuthorized()).thenReturn(Optional.of(asList(csZurich()).stream().collect(toSet())));
		
		Optional<List<ErrorMessage>> errorMessages = tradeServices.validate(invalidOptionsEmptyStyle());

		assertThat(errorMessages.isPresent(), equalTo(true));
		assertThat(errorMessages.get().size(), equalTo(1));
		List<ErrorMessage> errorsList = asList(emptyStyle());
		assertThat(errorMessages.get().equals(errorsList), equalTo(true));
	}
	
	@Test
	public void validateNoErrors() {
		when(customerServices.getSupported()).thenReturn(Optional.of(asList(pluto1(), pluto2()).stream().collect(toSet())));
		when(legalEntityServices.getAuthorized()).thenReturn(Optional.of(asList(csZurich()).stream().collect(toSet())));
		
		Optional<List<ErrorMessage>> errorMessages = tradeServices.validate(validOption());

		assertThat(errorMessages.isPresent(), equalTo(false));
	}
	
	@Test
	public void validateNullBulk() {
		List<Trade> trades = null;
		List<Trade> result = tradeServices.validate(trades);
		
		assertThat(result, notNullValue());
		assertThat(result.size(), equalTo(1));
		assertThat(result.get(0).getErrors(), notNullValue());
		assertThat(result.get(0).getErrors().size(), equalTo(1));
		assertThat(result.get(0).getErrors().equals(asList(emptyTrade())), equalTo(true));
	}
	
	@Test
	public void validateEmptyBulk() {
		List<Trade> result = tradeServices.validate(Collections.emptyList());
		
		assertThat(result, notNullValue());
		assertThat(result.size(), equalTo(1));
		assertThat(result.get(0).getErrors(), notNullValue());
		assertThat(result.get(0).getErrors().size(), equalTo(1));
		assertThat(result.get(0).getErrors().equals(asList(emptyTrade())), equalTo(true));
	}
	
	@Test
	public void validateBulkWithErrors() {
		when(customerServices.getSupported()).thenReturn(Optional.of(asList(pluto1(), pluto2()).stream().collect(toSet())));
		when(dateServices.getCurrentDate()).thenReturn(LocalDate.of(2016, 8, 15));
		when(dateServices.isNotWorkingDay(LocalDate.of(2016, 8, 13))).thenReturn(true);
		when(dateServices.isNotWorkingDay(LocalDate.of(2016, 8, 14))).thenReturn(true);
		when(legalEntityServices.getAuthorized()).thenReturn(Optional.of(asList(csZurich()).stream().collect(toSet())));		
		
		
		List<Trade> tradesValidated = tradeServices.validate(asList(invalidForward(), invalidSpot(), invalidOptionsEmptyStyle()));
		
		assertThat(tradesValidated, notNullValue());
		assertThat(tradesValidated.size(), equalTo(3));
		
		Trade forward = tradesValidated.get(0);

		assertThat(forward.getErrors(), notNullValue());
		assertThat(forward.getErrors().size(), equalTo(1));
		List<ErrorMessage> forwardErrorsList = asList(valueDateForwardNotAfterCurrentDate());
		assertThat(forward.getErrors().equals(forwardErrorsList), equalTo(true));
		
		Trade spot = tradesValidated.get(1);
		
		assertThat(spot.getErrors(), notNullValue());
		assertThat(spot.getErrors().size(), equalTo(1));
		List<ErrorMessage> spotErrorsList = asList(valueDateSpotNotTwoDaysAhead());
		assertThat(spot.getErrors().equals(spotErrorsList), equalTo(true));
		
		Trade options = tradesValidated.get(2);
		
		assertThat(options.getErrors(), notNullValue());
		assertThat(options.getErrors().size(), equalTo(1));
		List<ErrorMessage> optionsErrorsList = asList(emptyStyle());
		assertThat(options.getErrors().equals(optionsErrorsList), equalTo(true));
	}
	
	@Test
	public void validateBulkWithNoErrors() {
		when(customerServices.getSupported()).thenReturn(Optional.of(asList(pluto1(), pluto2()).stream().collect(toSet())));
		when(legalEntityServices.getAuthorized()).thenReturn(Optional.of(asList(csZurich()).stream().collect(toSet())));
		
		List<Trade> tradesValidated = tradeServices.validate(asList(validForward(), validSpot(), validOption()));

		Trade forward = tradesValidated.get(0);

		assertThat(forward.getErrors(), nullValue());
		
		Trade spot = tradesValidated.get(1);
		
		assertThat(spot.getErrors(), nullValue());
		
		Trade options = tradesValidated.get(2);
		
		assertThat(options.getErrors(), nullValue());
	}
	
	

}