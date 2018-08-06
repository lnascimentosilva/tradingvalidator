package com.luxsoft.tradingvalidatorapi.legalentity.service;

import java.util.Optional;
import java.util.Set;

import com.luxsoft.tradingvalidatorapi.legalentity.model.LegalEntity;

public interface LegalEntityServices {
	
	public Optional<Set<LegalEntity>> getAuthorized();

}
