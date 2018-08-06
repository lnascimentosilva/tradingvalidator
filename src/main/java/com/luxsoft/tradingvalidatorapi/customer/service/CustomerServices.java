package com.luxsoft.tradingvalidatorapi.customer.service;

import java.util.Optional;
import java.util.Set;

import com.luxsoft.tradingvalidatorapi.customer.model.Customer;

public interface CustomerServices {
	
	public Optional<Set<Customer>> getSupported();

}
