package com.luxsoft.tradingvalidatorapi.date.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class DateServicesImpl implements DateServices {

	private static final EnumSet<DayOfWeek> WEEKEND = EnumSet.of(
				DayOfWeek.SATURDAY, 
				DayOfWeek.SUNDAY
			);

	@Override
	public LocalDate getCurrentDate() {		
		return LocalDate.of(2016, 10, 9);
	}
	
	@Override
	public boolean isNotWorkingDay(LocalDate date) {		
		return WEEKEND.contains(date.getDayOfWeek())
				
				|| this.getNonWorkingDays().contains(date);
		
	}
	
	private Set<LocalDate> getNonWorkingDays() {
		Set<LocalDate> nonWorkingDays = new HashSet<>();
		nonWorkingDays.add(LocalDate.of(2016, 12, 25));
		nonWorkingDays.add(LocalDate.of(2016, 12, 31));
		return nonWorkingDays;
	}
}