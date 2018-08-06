package com.luxsoft.tradingvalidatorapi.commontests.trade;

import java.time.LocalDate;

import org.junit.Ignore;

import com.luxsoft.tradingvalidatorapi.trade.model.Trade;

@Ignore
public class TradesForTests {
	
	private static final LocalDate DATE_2016_8_17 = LocalDate.of(2016, 8, 17);
	private static final LocalDate DATE_2016_8_12 = LocalDate.of(2016, 8, 12);
	private static final LocalDate DATE_2016_8_16 = LocalDate.of(2016, 8, 16);
	private static final String AMERICAN = "AMERICAN";
	private static final String BRAZILIAN = "BRAZILIAN";
	private static final LocalDate DATE_2016_8_15 = LocalDate.of(2016, 8, 15);
	private static final LocalDate DATE_2016_8_11 = LocalDate.of(2016, 8, 11);
	private static final String CS_ZURICH = "CS Zurich";
	private static final String EURUSD = "EURUSD";
	private static final String PLUTO1 = "PLUTO1";

	public static Trade empty() {
		return new Trade();
	}
	
	public static Trade tradeWithValueDateBeforeTradeDate() {
		return create(PLUTO1, EURUSD, CS_ZURICH, DATE_2016_8_15, DATE_2016_8_11);
	}
	
	public static Trade tradeWithValueDateInANonWorkingDay() {
		return create(PLUTO1, EURUSD, CS_ZURICH, DATE_2016_8_15, LocalDate.of(2016, 10, 9));
	}
	
	public static Trade tradeWithNonSupportedLegalEntity() {
		return create(PLUTO1, EURUSD, "Invalid", DATE_2016_8_11, DATE_2016_8_15);
	}
	
	public static Trade tradeWithNonSupportedCustomer() {
		return create("PLUTO3", EURUSD, CS_ZURICH, DATE_2016_8_11, DATE_2016_8_15);
	}
	
	public static Trade tradeWithCcyPairWrongLength() {
		return create(PLUTO1, "EURUSDABC", CS_ZURICH, DATE_2016_8_11, DATE_2016_8_15);
	}
	
	public static Trade tradeWithCcyPairNonISO4217() {
		return create(PLUTO1, "ZZZYYY", CS_ZURICH, DATE_2016_8_11, DATE_2016_8_15);
	}
	
	public static Trade tradeWithCcyPairFirstNonISO4217() {
		return create(PLUTO1, "ZZZUSD", CS_ZURICH, DATE_2016_8_11, DATE_2016_8_15);
	}
	
	public static Trade tradeWithCcyPairSecondNonISO4217() {
		return create(PLUTO1, "EURYYY", CS_ZURICH, DATE_2016_8_11, DATE_2016_8_15);
	}
	
	public static Trade invalidSpot() {
		Trade spot = create(PLUTO1, EURUSD, CS_ZURICH, DATE_2016_8_11, DATE_2016_8_16);
		spot.setType("Spot");
		return spot;
	}

	public static Trade validSpot() {
		return create(PLUTO1, EURUSD, CS_ZURICH, DATE_2016_8_11, DATE_2016_8_15);
	}
	
	public static Trade invalidForward() {
		Trade forward = create(PLUTO1, EURUSD, CS_ZURICH, DATE_2016_8_11, DATE_2016_8_15);
		forward.setType("Forward");
		return forward;
	}

	public static Trade validForward() {
		return create(PLUTO1, EURUSD, CS_ZURICH, DATE_2016_8_11, DATE_2016_8_15);
	}
	
	public static Trade invalidOptionsEmptyStyle() {
		Trade trade = create(PLUTO1, EURUSD, CS_ZURICH, DATE_2016_8_11, DATE_2016_8_15);
		trade.setType("VanillaOption");
		trade.setExpiryDate(DATE_2016_8_16);
		trade.setPremiumDate(DATE_2016_8_16);
		trade.setDeliveryDate(DATE_2016_8_17);
		return trade;
	}
	
	public static Trade invalidOptionsStyleNotSupported() {
		Trade trade = create(PLUTO1, EURUSD, CS_ZURICH, DATE_2016_8_11, DATE_2016_8_15);
		trade.setStyle(BRAZILIAN);
		trade.setExpiryDate(DATE_2016_8_16);
		trade.setPremiumDate(DATE_2016_8_16);
		trade.setDeliveryDate(DATE_2016_8_17);
		return trade;
	}
	
	public static Trade invalidOptionsEmptyExcerciseStartDate() {
		Trade trade = create(PLUTO1, EURUSD, CS_ZURICH, DATE_2016_8_11, DATE_2016_8_15);
		trade.setStyle(AMERICAN);
		trade.setExpiryDate(DATE_2016_8_16);
		trade.setPremiumDate(DATE_2016_8_16);
		trade.setDeliveryDate(DATE_2016_8_17);
		return trade;
	}
	
	public static Trade invalidOptionsEmptyExpiryDate() {
		Trade trade = create(PLUTO1, EURUSD, CS_ZURICH, DATE_2016_8_11, DATE_2016_8_15);
		trade.setStyle(AMERICAN);
		trade.setExcerciseStartDate(LocalDate.of(2016, 8, 10));
		trade.setPremiumDate(DATE_2016_8_16);
		trade.setDeliveryDate(DATE_2016_8_17);
		return trade;
	}
	
	public static Trade invalidOptionsEmptyDeliveryDate() {
		Trade trade = create(PLUTO1, EURUSD, CS_ZURICH, DATE_2016_8_11, DATE_2016_8_15);
		trade.setStyle(AMERICAN);
		trade.setExcerciseStartDate(DATE_2016_8_12);
		trade.setExpiryDate(DATE_2016_8_16);
		trade.setPremiumDate(DATE_2016_8_16);
		return trade;
	}
	
	public static Trade invalidOptionsEmptyPremiumDate() {
		Trade trade = create(PLUTO1, EURUSD, CS_ZURICH, DATE_2016_8_11, DATE_2016_8_15);
		trade.setStyle(AMERICAN);
		trade.setExcerciseStartDate(DATE_2016_8_12);
		trade.setExpiryDate(DATE_2016_8_16);
		trade.setDeliveryDate(DATE_2016_8_17);
		return trade;
	}
	
	public static Trade invalidOptionsExcerciseStartDateBeforeTradeDate() {
		Trade trade = create(PLUTO1, EURUSD, CS_ZURICH, DATE_2016_8_11, DATE_2016_8_15);
		trade.setStyle(AMERICAN);
		trade.setExcerciseStartDate(LocalDate.of(2016, 8, 10));
		trade.setExpiryDate(DATE_2016_8_16);
		trade.setPremiumDate(DATE_2016_8_16);
		trade.setDeliveryDate(DATE_2016_8_17);
		return trade;
	}
	
	public static Trade invalidOptionsExcerciseStartDateAfterExpiryDate() {
		Trade trade = create(PLUTO1, EURUSD, CS_ZURICH, DATE_2016_8_11, DATE_2016_8_15);
		trade.setStyle(AMERICAN);
		trade.setExcerciseStartDate(DATE_2016_8_12);
		trade.setExpiryDate(LocalDate.of(2016, 8, 10));
		trade.setPremiumDate(DATE_2016_8_16);
		trade.setDeliveryDate(DATE_2016_8_17);
		return trade;
	}
	
	public static Trade invalidOptionsDeliveryDateAfterExpiryDate() {
		Trade trade = create(PLUTO1, EURUSD, CS_ZURICH, DATE_2016_8_11, DATE_2016_8_15);
		trade.setStyle(AMERICAN);
		trade.setExcerciseStartDate(DATE_2016_8_12);
		trade.setExpiryDate(DATE_2016_8_17);
		trade.setPremiumDate(DATE_2016_8_15);
		trade.setDeliveryDate(DATE_2016_8_16);
		return trade;
	}
	
	public static Trade invalidOptionsDeliveryDateAfterPremiumDate() {
		Trade trade = create(PLUTO1, EURUSD, CS_ZURICH, DATE_2016_8_11, DATE_2016_8_15);
		trade.setStyle(AMERICAN);
		trade.setExcerciseStartDate(DATE_2016_8_12);
		trade.setExpiryDate(DATE_2016_8_15);
		trade.setPremiumDate(DATE_2016_8_17);
		trade.setDeliveryDate(DATE_2016_8_16);
		return trade;
	}
	
	public static Trade validOption() {
		Trade trade = create(PLUTO1, EURUSD, CS_ZURICH, DATE_2016_8_11, DATE_2016_8_15);
		trade.setStyle(AMERICAN);
		trade.setExcerciseStartDate(DATE_2016_8_12);
		trade.setExpiryDate(DATE_2016_8_15);
		trade.setPremiumDate(DATE_2016_8_15);
		trade.setDeliveryDate(DATE_2016_8_16);
		return trade;
	}

	private static Trade create(String customer, String currency, String legalEntity, LocalDate tradeDate, LocalDate valueDate) {
		Trade spot = new Trade();
		spot.setCustomer(customer);
		spot.setCcyPair(currency);
		spot.setLegalEntity(legalEntity);
		spot.setTradeDate(tradeDate);
		spot.setValueDate(valueDate);
		return spot;
	}
}
