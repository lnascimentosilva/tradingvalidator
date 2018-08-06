package com.luxsoft.tradingvalidatorapi.legalentity.service;

import static com.luxsoft.tradingvalidatorapi.commontests.legalentity.LegalEntitiesForTests.csZurich;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Optional;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.luxsoft.tradingvalidatorapi.legalentity.model.LegalEntity;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LegalEntityServicesUTest {

	@Autowired
	private LegalEntityServices legalEntityServices;

	@Test
	public void getAuthorized() {
		Optional<Set<LegalEntity>> authorizedLegalEntities = legalEntityServices.getAuthorized();
		
		assertThat(authorizedLegalEntities.isPresent(), equalTo(true));
		assertThat(authorizedLegalEntities.get().size(), equalTo(1));
		assertThat(authorizedLegalEntities.get().contains(csZurich()), equalTo(true));
	}

}