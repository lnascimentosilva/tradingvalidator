package com.luxsoft.tradingvalidatorapi.customer.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.luxsoft.tradingvalidatorapi.customer.model.Customer;

@Service
public class CustomerServicesImpl implements CustomerServices {

	@Override
	public Optional<Set<Customer>> getSupported() {
		Set<Customer> customers = new HashSet<>();

		customers.add(new Customer("PLUTO1"));
		customers.add(new Customer("PLUTO2"));
		
		return Optional.of(customers);
	}
}