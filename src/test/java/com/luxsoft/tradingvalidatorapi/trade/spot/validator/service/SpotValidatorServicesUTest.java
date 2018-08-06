package com.luxsoft.tradingvalidatorapi.trade.spot.validator.service;

import static com.luxsoft.tradingvalidatorapi.commontests.customer.CustomersForTests.pluto1;
import static com.luxsoft.tradingvalidatorapi.commontests.customer.CustomersForTests.pluto2;
import static com.luxsoft.tradingvalidatorapi.commontests.errormessage.ErrorMessagesForTests.valueDateSpotNotTwoDaysAhead;
import static com.luxsoft.tradingvalidatorapi.commontests.legalentity.LegalEntitiesForTests.csZurich;
import static com.luxsoft.tradingvalidatorapi.commontests.trade.TradesForTests.invalidSpot;
import static com.luxsoft.tradingvalidatorapi.commontests.trade.TradesForTests.validSpot;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toSet;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
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
import com.luxsoft.tradingvalidatorapi.date.service.DateServices;
import com.luxsoft.tradingvalidatorapi.errormessage.model.ErrorMessage;
import com.luxsoft.tradingvalidatorapi.legalentity.service.LegalEntityServices;
import com.luxsoft.tradingvalidatorapi.trade.validator.service.TradingValidatorServices;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpotValidatorServicesUTest {
	
	@Autowired
	@Qualifier(value = "spotValidator")
	private TradingValidatorServices spotValidatorServices;
	
	@MockBean
	private DateServices dateServices;
	
	@MockBean
	private CustomerServices customerServices;
	
	@MockBean
	private LegalEntityServices legalEntityServices;

	@Test
	public void validateSpotWrongValueDate() {
		when(customerServices.getSupported()).thenReturn(Optional.of(asList(pluto1(), pluto2()).stream().collect(toSet())));
		when(dateServices.isNotWorkingDay(LocalDate.of(2016, 8, 13))).thenReturn(true);
		when(dateServices.isNotWorkingDay(LocalDate.of(2016, 8, 14))).thenReturn(true);
		when(legalEntityServices.getAuthorized()).thenReturn(Optional.of(asList(csZurich()).stream().collect(toSet())));
		
		Optional<List<ErrorMessage>> errorMessages = spotValidatorServices.validate(invalidSpot());

		assertThat(errorMessages.isPresent(), equalTo(true));
		assertThat(errorMessages.get().size(), equalTo(1));
		List<ErrorMessage> errorsList = asList(valueDateSpotNotTwoDaysAhead());
		assertThat(errorMessages.get().equals(errorsList), equalTo(true));
	}
	
	@Test
	public void validateValidSpot() {
		when(customerServices.getSupported()).thenReturn(Optional.of(asList(pluto1(), pluto2()).stream().collect(toSet())));
		when(dateServices.isNotWorkingDay(LocalDate.of(2016, 8, 13))).thenReturn(true);
		when(dateServices.isNotWorkingDay(LocalDate.of(2016, 8, 14))).thenReturn(true);
		when(legalEntityServices.getAuthorized()).thenReturn(Optional.of(asList(csZurich()).stream().collect(toSet())));
		
		Optional<List<ErrorMessage>> errorMessages = spotValidatorServices.validate(validSpot());

		assertThat(errorMessages.isPresent(), equalTo(false));
	}
	
	@Test
	public void checkType() {
		assertThat(spotValidatorServices.getType(), equalTo("Spot"));
	}

}