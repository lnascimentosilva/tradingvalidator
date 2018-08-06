package com.luxsoft.tradingvalidatorapi.legalentity.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.luxsoft.tradingvalidatorapi.legalentity.model.LegalEntity;

@Service
public class LegalEntityServicesImpl implements LegalEntityServices {

	@Override
	public Optional<Set<LegalEntity>> getAuthorized() {
		Set<LegalEntity> legalEntities = new HashSet<>();
		legalEntities.add(new LegalEntity("CS Zurich"));
		
		return Optional.of(legalEntities);
	}
}