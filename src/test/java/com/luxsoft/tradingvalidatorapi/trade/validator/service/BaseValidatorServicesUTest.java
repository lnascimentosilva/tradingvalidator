package com.luxsoft.tradingvalidatorapi.trade.validator.service;

import static com.luxsoft.tradingvalidatorapi.commontests.customer.CustomersForTests.pluto1;
import static com.luxsoft.tradingvalidatorapi.commontests.customer.CustomersForTests.pluto2;
import static com.luxsoft.tradingvalidatorapi.commontests.errormessage.ErrorMessagesForTests.ccyPairNonISO4217YYY;
import static com.luxsoft.tradingvalidatorapi.commontests.errormessage.ErrorMessagesForTests.ccyPairNonISO4217ZZZ;
import static com.luxsoft.tradingvalidatorapi.commontests.errormessage.ErrorMessagesForTests.ccyPairWrongLength;
import static com.luxsoft.tradingvalidatorapi.commontests.errormessage.ErrorMessagesForTests.customerNotSupported;
import static com.luxsoft.tradingvalidatorapi.commontests.errormessage.ErrorMessagesForTests.emptyCurrency;
import static com.luxsoft.tradingvalidatorapi.commontests.errormessage.ErrorMessagesForTests.emptyCustomer;
import static com.luxsoft.tradingvalidatorapi.commontests.errormessage.ErrorMessagesForTests.emptyLegalEntity;
import static com.luxsoft.tradingvalidatorapi.commontests.errormessage.ErrorMessagesForTests.emptyTrade;
import static com.luxsoft.tradingvalidatorapi.commontests.errormessage.ErrorMessagesForTests.emptyTradeDate;
import static com.luxsoft.tradingvalidatorapi.commontests.errormessage.ErrorMessagesForTests.emptyValueDate;
import static com.luxsoft.tradingvalidatorapi.commontests.errormessage.ErrorMessagesForTests.legalEntityNotSupported;
import static com.luxsoft.tradingvalidatorapi.commontests.errormessage.ErrorMessagesForTests.valueDateBeforeTradeDate;
import static com.luxsoft.tradingvalidatorapi.commontests.errormessage.ErrorMessagesForTests.valueDateNonWorkingDay;
import static com.luxsoft.tradingvalidatorapi.commontests.legalentity.LegalEntitiesForTests.csZurich;
import static com.luxsoft.tradingvalidatorapi.commontests.trade.TradesForTests.empty;
import static com.luxsoft.tradingvalidatorapi.commontests.trade.TradesForTests.tradeWithCcyPairFirstNonISO4217;
import static com.luxsoft.tradingvalidatorapi.commontests.trade.TradesForTests.tradeWithCcyPairNonISO4217;
import static com.luxsoft.tradingvalidatorapi.commontests.trade.TradesForTests.tradeWithCcyPairSecondNonISO4217;
import static com.luxsoft.tradingvalidatorapi.commontests.trade.TradesForTests.tradeWithCcyPairWrongLength;
import static com.luxsoft.tradingvalidatorapi.commontests.trade.TradesForTests.tradeWithNonSupportedCustomer;
import static com.luxsoft.tradingvalidatorapi.commontests.trade.TradesForTests.tradeWithNonSupportedLegalEntity;
import static com.luxsoft.tradingvalidatorapi.commontests.trade.TradesForTests.tradeWithValueDateBeforeTradeDate;
import static com.luxsoft.tradingvalidatorapi.commontests.trade.TradesForTests.tradeWithValueDateInANonWorkingDay;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toSet;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.luxsoft.tradingvalidatorapi.common.exception.IntegrationException;
import com.luxsoft.tradingvalidatorapi.customer.service.CustomerServices;
import com.luxsoft.tradingvalidatorapi.date.service.DateServices;
import com.luxsoft.tradingvalidatorapi.errormessage.model.ErrorMessage;
import com.luxsoft.tradingvalidatorapi.legalentity.service.LegalEntityServices;
import com.luxsoft.tradingvalidatorapi.trade.model.Trade;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BaseValidatorServicesUTest {

	@Autowired
	@Qualifier(value = "baseValidator")
	private TradingValidatorServices baseValidatorServices;
	
	@MockBean
	private DateServices dateServices;
	
	@MockBean
	private CustomerServices customerServices;
	
	@MockBean
	private LegalEntityServices legalEntityServices;
	
	@Value("${error.withoutSupportedCustomers}")
	private String withoutSupportedCustomers;
	
	@Value("${error.withoutAuthorizedLegalEntities}")
	private String withoutAuthorizedLegalEntities;


	@Test
	public void validateNullTrade() {
		Optional<List<ErrorMessage>> errorMessages = baseValidatorServices.validate(null);

		assertThat(errorMessages.isPresent(), equalTo(true));
		assertThat(errorMessages.get().size(), equalTo(1));
		assertThat(errorMessages.get().equals(asList(emptyTrade())), equalTo(true));
	}

	@Test
	public void validateNullAndEmptyValues() {
		Optional<List<ErrorMessage>> errorMessages = baseValidatorServices.validate(empty());

		assertThat(errorMessages.isPresent(), equalTo(true));
		assertThat(errorMessages.get().size(), equalTo(5));
		List<ErrorMessage> errorsList = asList(emptyCustomer(), emptyCurrency(), emptyTradeDate(), emptyValueDate(), emptyLegalEntity());
		assertThat(errorMessages.get().equals(errorsList), equalTo(true));
	}
	
	@Test
	public void validateValueDateBeforeTradeDate() {
		when(customerServices.getSupported()).thenReturn(Optional.of(asList(pluto1(), pluto2()).stream().collect(toSet())));
		when(legalEntityServices.getAuthorized()).thenReturn(Optional.of(asList(csZurich()).stream().collect(toSet())));
		
		Optional<List<ErrorMessage>> errorMessages = baseValidatorServices.validate(tradeWithValueDateBeforeTradeDate());

		assertThat(errorMessages.isPresent(), equalTo(true));
		assertThat(errorMessages.get().size(), equalTo(1));
		List<ErrorMessage> errorsList = asList(valueDateBeforeTradeDate());
		assertThat(errorMessages.get().equals(errorsList), equalTo(true));
	}
	
	@Test
	public void validateValueDateInANonWorkingDay() {
		
		Trade tradeWithValueDateInANonWorkingDay = tradeWithValueDateInANonWorkingDay();
		
		when(dateServices.isNotWorkingDay(tradeWithValueDateInANonWorkingDay.getValueDate())).thenReturn(true);
		when(customerServices.getSupported()).thenReturn(Optional.of(asList(pluto1(), pluto2()).stream().collect(toSet())));
		when(legalEntityServices.getAuthorized()).thenReturn(Optional.of(asList(csZurich()).stream().collect(toSet())));
		
		Optional<List<ErrorMessage>> errorMessages = baseValidatorServices.validate(tradeWithValueDateInANonWorkingDay);

		assertThat(errorMessages.isPresent(), equalTo(true));
		assertThat(errorMessages.get().size(), equalTo(1));
		List<ErrorMessage> errorsList = asList(valueDateNonWorkingDay());
		assertThat(errorMessages.get().equals(errorsList), equalTo(true));
	}
	
	@Test
	public void validateNotSupportedLegalEntities() {
		
		when(customerServices.getSupported()).thenReturn(Optional.of(asList(pluto1(), pluto2()).stream().collect(toSet())));
		when(legalEntityServices.getAuthorized()).thenReturn(Optional.of(asList(csZurich()).stream().collect(toSet())));
		
		
		Optional<List<ErrorMessage>> errorMessages = baseValidatorServices.validate(tradeWithNonSupportedLegalEntity());

		assertThat(errorMessages.isPresent(), equalTo(true));
		assertThat(errorMessages.get().size(), equalTo(1));
		List<ErrorMessage> errorsList = asList(legalEntityNotSupported());
		assertThat(errorMessages.get().equals(errorsList), equalTo(true));
	}
	
	@Test(expected=IntegrationException.class)
	public void validateNoSupportedLegalEntitiesAvailable() {
		
		try {
			baseValidatorServices.validate(tradeWithNonSupportedLegalEntity());
		} catch (IntegrationException e) {
			assertThat(e.getMessage(), equalTo(withoutAuthorizedLegalEntities));
			
			throw e;
		}
		
		fail("Shouldn't have reached here!");
	}
	
	@Test
	public void validateNotSupportedCustomer() {
		
		when(customerServices.getSupported()).thenReturn(Optional.of(asList(pluto1(), pluto2()).stream().collect(toSet())));
		when(legalEntityServices.getAuthorized()).thenReturn(Optional.of(asList(csZurich()).stream().collect(toSet())));
		
		Optional<List<ErrorMessage>> errorMessages = baseValidatorServices.validate(tradeWithNonSupportedCustomer());

		assertThat(errorMessages.isPresent(), equalTo(true));
		assertThat(errorMessages.get().size(), equalTo(1));
		List<ErrorMessage> errorsList = asList(customerNotSupported());
		assertThat(errorMessages.get().equals(errorsList), equalTo(true));
	}
	
	@Test(expected=IntegrationException.class)
	public void validateNoSupportedCustomersAvailable() {
		when(legalEntityServices.getAuthorized()).thenReturn(Optional.of(asList(csZurich()).stream().collect(toSet())));
		
		try {
			baseValidatorServices.validate(tradeWithNonSupportedCustomer());
		} catch (IntegrationException e) {
			assertThat(e.getMessage(), equalTo(withoutSupportedCustomers));
			
			throw e;
		}
		
		fail("Shouldn't have reached here!");
	}
	
	@Test
	public void validateCcyPairWrongLength() {
		when(customerServices.getSupported()).thenReturn(Optional.of(asList(pluto1(), pluto2()).stream().collect(toSet())));
		when(legalEntityServices.getAuthorized()).thenReturn(Optional.of(asList(csZurich()).stream().collect(toSet())));
		
		Optional<List<ErrorMessage>> errorMessages = baseValidatorServices.validate(tradeWithCcyPairWrongLength());

		assertThat(errorMessages.isPresent(), equalTo(true));
		assertThat(errorMessages.get().size(), equalTo(1));
		List<ErrorMessage> errorsList = asList(ccyPairWrongLength());
		assertThat(errorMessages.get().equals(errorsList), equalTo(true));
	}
	
	@Test
	public void validateCcyPairNonISO4217() {
		when(customerServices.getSupported()).thenReturn(Optional.of(asList(pluto1(), pluto2()).stream().collect(toSet())));
		when(legalEntityServices.getAuthorized()).thenReturn(Optional.of(asList(csZurich()).stream().collect(toSet())));
		
		Optional<List<ErrorMessage>> errorMessages = baseValidatorServices.validate(tradeWithCcyPairNonISO4217());

		assertThat(errorMessages.isPresent(), equalTo(true));
		assertThat(errorMessages.get().size(), equalTo(2));
		List<ErrorMessage> errorsList = asList(ccyPairNonISO4217ZZZ(), ccyPairNonISO4217YYY());
		assertThat(errorMessages.get().equals(errorsList), equalTo(true));
	}
	
	@Test
	public void validateCcyPairFirstNonISO4217() {
		when(customerServices.getSupported()).thenReturn(Optional.of(asList(pluto1(), pluto2()).stream().collect(toSet())));
		when(legalEntityServices.getAuthorized()).thenReturn(Optional.of(asList(csZurich()).stream().collect(toSet())));
		
		Optional<List<ErrorMessage>> errorMessages = baseValidatorServices.validate(tradeWithCcyPairFirstNonISO4217());

		assertThat(errorMessages.isPresent(), equalTo(true));
		assertThat(errorMessages.get().size(), equalTo(1));
		List<ErrorMessage> errorsList = asList(ccyPairNonISO4217ZZZ());
		assertThat(errorMessages.get().equals(errorsList), equalTo(true));
	}
	
	@Test
	public void validateCcyPairSecondNonISO4217() {
		when(customerServices.getSupported()).thenReturn(Optional.of(asList(pluto1(), pluto2()).stream().collect(toSet())));
		when(legalEntityServices.getAuthorized()).thenReturn(Optional.of(asList(csZurich()).stream().collect(toSet())));
		
		Optional<List<ErrorMessage>> errorMessages = baseValidatorServices.validate(tradeWithCcyPairSecondNonISO4217());

		assertThat(errorMessages.isPresent(), equalTo(true));
		assertThat(errorMessages.get().size(), equalTo(1));
		List<ErrorMessage> errorsList = asList(ccyPairNonISO4217YYY());
		assertThat(errorMessages.get().equals(errorsList), equalTo(true));
	}
	
	@Test
	public void checkType() {
		assertThat(baseValidatorServices.getType(), equalTo("BASE"));
	}

}