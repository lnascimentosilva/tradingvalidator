package com.luxsoft.tradingvalidatorapi.trade.options.validator.service;

import static com.luxsoft.tradingvalidatorapi.commontests.customer.CustomersForTests.pluto1;
import static com.luxsoft.tradingvalidatorapi.commontests.customer.CustomersForTests.pluto2;
import static com.luxsoft.tradingvalidatorapi.commontests.errormessage.ErrorMessagesForTests.deliveryDateMustBeBeforeExpiryDate;
import static com.luxsoft.tradingvalidatorapi.commontests.errormessage.ErrorMessagesForTests.deliveryDateMustBeBoforePremiumDate;
import static com.luxsoft.tradingvalidatorapi.commontests.errormessage.ErrorMessagesForTests.emptyDeliveryDate;
import static com.luxsoft.tradingvalidatorapi.commontests.errormessage.ErrorMessagesForTests.emptyExcerciseStartDate;
import static com.luxsoft.tradingvalidatorapi.commontests.errormessage.ErrorMessagesForTests.emptyExpiryDate;
import static com.luxsoft.tradingvalidatorapi.commontests.errormessage.ErrorMessagesForTests.emptyPremiumDate;
import static com.luxsoft.tradingvalidatorapi.commontests.errormessage.ErrorMessagesForTests.emptyStyle;
import static com.luxsoft.tradingvalidatorapi.commontests.errormessage.ErrorMessagesForTests.excerciseStartDateNotBetweenExpiryDateAndTradeDate;
import static com.luxsoft.tradingvalidatorapi.commontests.errormessage.ErrorMessagesForTests.styleNotSupported;
import static com.luxsoft.tradingvalidatorapi.commontests.legalentity.LegalEntitiesForTests.csZurich;
import static com.luxsoft.tradingvalidatorapi.commontests.trade.TradesForTests.invalidOptionsDeliveryDateAfterExpiryDate;
import static com.luxsoft.tradingvalidatorapi.commontests.trade.TradesForTests.invalidOptionsDeliveryDateAfterPremiumDate;
import static com.luxsoft.tradingvalidatorapi.commontests.trade.TradesForTests.invalidOptionsEmptyDeliveryDate;
import static com.luxsoft.tradingvalidatorapi.commontests.trade.TradesForTests.invalidOptionsEmptyExcerciseStartDate;
import static com.luxsoft.tradingvalidatorapi.commontests.trade.TradesForTests.invalidOptionsEmptyExpiryDate;
import static com.luxsoft.tradingvalidatorapi.commontests.trade.TradesForTests.invalidOptionsEmptyPremiumDate;
import static com.luxsoft.tradingvalidatorapi.commontests.trade.TradesForTests.invalidOptionsEmptyStyle;
import static com.luxsoft.tradingvalidatorapi.commontests.trade.TradesForTests.invalidOptionsExcerciseStartDateAfterExpiryDate;
import static com.luxsoft.tradingvalidatorapi.commontests.trade.TradesForTests.invalidOptionsExcerciseStartDateBeforeTradeDate;
import static com.luxsoft.tradingvalidatorapi.commontests.trade.TradesForTests.invalidOptionsStyleNotSupported;
import static com.luxsoft.tradingvalidatorapi.commontests.trade.TradesForTests.validOption;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toSet;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.luxsoft.tradingvalidatorapi.customer.service.CustomerServices;
import com.luxsoft.tradingvalidatorapi.errormessage.model.ErrorMessage;
import com.luxsoft.tradingvalidatorapi.legalentity.service.LegalEntityServices;
import com.luxsoft.tradingvalidatorapi.trade.validator.service.TradingValidatorServices;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OptionsValidatorServicesUTest {
	
	@Autowired
	@Qualifier(value = "optionsValidator")
	private TradingValidatorServices optionsValidatorServices;
	
	@MockBean
	private CustomerServices customerServices;
	
	@MockBean
	private LegalEntityServices legalEntityServices;

	@Test
	public void validateOptionsEmptyStyle() {
		when(customerServices.getSupported()).thenReturn(Optional.of(asList(pluto1(), pluto2()).stream().collect(toSet())));
		when(legalEntityServices.getAuthorized()).thenReturn(Optional.of(asList(csZurich()).stream().collect(toSet())));
		
		Optional<List<ErrorMessage>> errorMessages = optionsValidatorServices.validate(invalidOptionsEmptyStyle());

		assertThat(errorMessages.isPresent(), equalTo(true));
		assertThat(errorMessages.get().size(), equalTo(1));
		List<ErrorMessage> errorsList = asList(emptyStyle());
		assertThat(errorMessages.get().equals(errorsList), equalTo(true));
	}
	
	@Test
	public void validateOptionsNotSupportedStyle() {
		when(customerServices.getSupported()).thenReturn(Optional.of(asList(pluto1(), pluto2()).stream().collect(toSet())));
		when(legalEntityServices.getAuthorized()).thenReturn(Optional.of(asList(csZurich()).stream().collect(toSet())));
		
		Optional<List<ErrorMessage>> errorMessages = optionsValidatorServices.validate(invalidOptionsStyleNotSupported());

		assertThat(errorMessages.isPresent(), equalTo(true));
		assertThat(errorMessages.get().size(), equalTo(1));
		List<ErrorMessage> errorsList = asList(styleNotSupported());
		assertThat(errorMessages.get().equals(errorsList), equalTo(true));
	}
	
	@Test
	public void validateOptionsEmptyExcerciseStartDate() {
		when(customerServices.getSupported()).thenReturn(Optional.of(asList(pluto1(), pluto2()).stream().collect(toSet())));
		when(legalEntityServices.getAuthorized()).thenReturn(Optional.of(asList(csZurich()).stream().collect(toSet())));
		
		Optional<List<ErrorMessage>> errorMessages = optionsValidatorServices.validate(invalidOptionsEmptyExcerciseStartDate());

		assertThat(errorMessages.isPresent(), equalTo(true));
		assertThat(errorMessages.get().size(), equalTo(1));
		List<ErrorMessage> errorsList = asList(emptyExcerciseStartDate());
		assertThat(errorMessages.get().equals(errorsList), equalTo(true));
	}
	
	@Test
	public void validateOptionsEmptyExpiryDate() {
		when(customerServices.getSupported()).thenReturn(Optional.of(asList(pluto1(), pluto2()).stream().collect(toSet())));
		when(legalEntityServices.getAuthorized()).thenReturn(Optional.of(asList(csZurich()).stream().collect(toSet())));
		
		Optional<List<ErrorMessage>> errorMessages = optionsValidatorServices.validate(invalidOptionsEmptyExpiryDate());

		assertThat(errorMessages.isPresent(), equalTo(true));
		assertThat(errorMessages.get().size(), equalTo(1));
		List<ErrorMessage> errorsList = asList(emptyExpiryDate());
		assertThat(errorMessages.get().equals(errorsList), equalTo(true));
	}
	
	@Test
	public void validateOptionsExcerciseStartDateBeforeTradeDate() {
		when(customerServices.getSupported()).thenReturn(Optional.of(asList(pluto1(), pluto2()).stream().collect(toSet())));
		when(legalEntityServices.getAuthorized()).thenReturn(Optional.of(asList(csZurich()).stream().collect(toSet())));
		
		Optional<List<ErrorMessage>> errorMessages = optionsValidatorServices.validate(invalidOptionsExcerciseStartDateBeforeTradeDate());

		assertThat(errorMessages.isPresent(), equalTo(true));
		assertThat(errorMessages.get().size(), equalTo(1));
		List<ErrorMessage> errorsList = asList(excerciseStartDateNotBetweenExpiryDateAndTradeDate());
		assertThat(errorMessages.get().equals(errorsList), equalTo(true));
	}
	
	@Test
	public void validateOptionsExcerciseStartDateAfterExpiryDate() {
		when(customerServices.getSupported()).thenReturn(Optional.of(asList(pluto1(), pluto2()).stream().collect(toSet())));
		when(legalEntityServices.getAuthorized()).thenReturn(Optional.of(asList(csZurich()).stream().collect(toSet())));
		
		Optional<List<ErrorMessage>> errorMessages = optionsValidatorServices.validate(invalidOptionsExcerciseStartDateAfterExpiryDate());

		assertThat(errorMessages.isPresent(), equalTo(true));
		assertThat(errorMessages.get().size(), equalTo(1));
		List<ErrorMessage> errorsList = asList(excerciseStartDateNotBetweenExpiryDateAndTradeDate());
		assertThat(errorMessages.get().equals(errorsList), equalTo(true));
	}
	
	@Test
	public void validateOptionsEmptyDeliveryDate() {
		when(customerServices.getSupported()).thenReturn(Optional.of(asList(pluto1(), pluto2()).stream().collect(toSet())));
		when(legalEntityServices.getAuthorized()).thenReturn(Optional.of(asList(csZurich()).stream().collect(toSet())));
		
		Optional<List<ErrorMessage>> errorMessages = optionsValidatorServices.validate(invalidOptionsEmptyDeliveryDate());

		assertThat(errorMessages.isPresent(), equalTo(true));
		assertThat(errorMessages.get().size(), equalTo(1));
		List<ErrorMessage> errorsList = asList(emptyDeliveryDate());
		assertThat(errorMessages.get().equals(errorsList), equalTo(true));
	}
	
	@Test
	public void validateOptionsEmptyPremiumDate() {
		when(customerServices.getSupported()).thenReturn(Optional.of(asList(pluto1(), pluto2()).stream().collect(toSet())));
		when(legalEntityServices.getAuthorized()).thenReturn(Optional.of(asList(csZurich()).stream().collect(toSet())));
		
		Optional<List<ErrorMessage>> errorMessages = optionsValidatorServices.validate(invalidOptionsEmptyPremiumDate());

		assertThat(errorMessages.isPresent(), equalTo(true));
		assertThat(errorMessages.get().size(), equalTo(1));
		List<ErrorMessage> errorsList = asList(emptyPremiumDate());
		assertThat(errorMessages.get().equals(errorsList), equalTo(true));
	}
	
	@Test
	public void validateOptionsDeliveryDateAfterExpiryDate() {
		when(customerServices.getSupported()).thenReturn(Optional.of(asList(pluto1(), pluto2()).stream().collect(toSet())));
		when(legalEntityServices.getAuthorized()).thenReturn(Optional.of(asList(csZurich()).stream().collect(toSet())));
		
		Optional<List<ErrorMessage>> errorMessages = optionsValidatorServices.validate(invalidOptionsDeliveryDateAfterExpiryDate());

		assertThat(errorMessages.isPresent(), equalTo(true));
		assertThat(errorMessages.get().size(), equalTo(1));
		List<ErrorMessage> errorsList = asList(deliveryDateMustBeBeforeExpiryDate());
		assertThat(errorMessages.get().equals(errorsList), equalTo(true));
	}
	
	@Test
	public void validateOptionsDeliveryDateAfterPremiumDate() {
		when(customerServices.getSupported()).thenReturn(Optional.of(asList(pluto1(), pluto2()).stream().collect(toSet())));
		when(legalEntityServices.getAuthorized()).thenReturn(Optional.of(asList(csZurich()).stream().collect(toSet())));
		
		Optional<List<ErrorMessage>> errorMessages = optionsValidatorServices.validate(invalidOptionsDeliveryDateAfterPremiumDate());

		assertThat(errorMessages.isPresent(), equalTo(true));
		assertThat(errorMessages.get().size(), equalTo(1));
		List<ErrorMessage> errorsList = asList(deliveryDateMustBeBoforePremiumDate());
		assertThat(errorMessages.get().equals(errorsList), equalTo(true));
	}
	
	@Test
	public void validateValidOption() {
		when(customerServices.getSupported()).thenReturn(Optional.of(asList(pluto1(), pluto2()).stream().collect(toSet())));
		when(legalEntityServices.getAuthorized()).thenReturn(Optional.of(asList(csZurich()).stream().collect(toSet())));
		
		Optional<List<ErrorMessage>> errorMessages = optionsValidatorServices.validate(validOption());

		assertThat(errorMessages.isPresent(), equalTo(false));
	}
	
	@Test
	public void checkType() {
		assertThat(optionsValidatorServices.getType(), equalTo("VanillaOption"));
	}

}