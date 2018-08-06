package com.luxsoft.tradingvalidatorapi.date.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DateServicesUTest {

	@Autowired
	private DateServices dateServices;

	@Test
	public void getCurrentDate() {
			
		assertThat(dateServices.getCurrentDate(), notNullValue());
		assertThat(dateServices.getCurrentDate(), equalTo(LocalDate.of(2016, 10, 9)));
	}
	
	@Test
	public void checkIfDateIsSaturday() {			
		assertThat(dateServices.isNotWorkingDay(LocalDate.of(2016, 10, 8)), equalTo(true));
	}
	
	@Test
	public void checkIfDateIsSunday() {			
		assertThat(dateServices.isNotWorkingDay(LocalDate.of(2016, 10, 9)), equalTo(true));
	}
	
	@Test
	public void checkIfDateIsANonWorkingDay() {			
		assertThat(dateServices.isNotWorkingDay(LocalDate.of(2016, 12, 25)), equalTo(true));
	}
	
	@Test
	public void checkIfDateIsABusinessDay() {			
		assertThat(dateServices.isNotWorkingDay(LocalDate.of(2016, 10, 10)), equalTo(false));
	}

}