package com.luxsoft.tradingvalidatorapi.date.service;

import java.time.LocalDate;

public interface DateServices {
	
	public LocalDate getCurrentDate();

	boolean isNotWorkingDay(LocalDate date);

}
