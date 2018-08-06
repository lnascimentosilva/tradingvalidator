package com.luxsoft.tradingvalidatorapi.customer.service;

import static com.luxsoft.tradingvalidatorapi.commontests.customer.CustomersForTests.pluto1;
import static com.luxsoft.tradingvalidatorapi.commontests.customer.CustomersForTests.pluto2;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Optional;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.luxsoft.tradingvalidatorapi.customer.model.Customer;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerServicesUTest {

	@Autowired
	private CustomerServices customerServices;

	@Test
	public void getSupported() {
		Optional<Set<Customer>> supportedCustomers = customerServices.getSupported();
		
		assertThat(supportedCustomers.isPresent(), equalTo(true));
		assertThat(supportedCustomers.get().size(), equalTo(2));
		assertThat(supportedCustomers.get().contains(pluto1()), equalTo(true));
		assertThat(supportedCustomers.get().contains(pluto2()), equalTo(true));
	}

}