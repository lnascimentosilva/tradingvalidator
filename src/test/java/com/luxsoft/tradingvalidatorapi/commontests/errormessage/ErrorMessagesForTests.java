package com.luxsoft.tradingvalidatorapi.commontests.errormessage;

import org.junit.Ignore;

import com.luxsoft.tradingvalidatorapi.errormessage.model.ErrorMessage;

@Ignore
public class ErrorMessagesForTests {
	
	private static final String MUST_BE_BEFORE_PREMIUM_DATE = "must be before premiumDate";
	private static final String MUST_BE_BEFORE_EXPIRY_DATE = "must be before expiryDate";
	private static final String PREMIUM_DATE = "premiumDate";
	private static final String DELIVERY_DATE = "deliveryDate";
	private static final String Expiry_DATE = "expiryDate";
	private static final String EXCERCISE_START_DATE = "excerciseStartDate";
	private static final String STYLE = "style";
	private static final String IS_NOT_BETWEEN_Expiry_DATE_AND_TRADE_DATE = "is not between expiryDate and tradeDate";
	private static final String IS_NOT_AFTER_CURRENT_DATE = "is not after current date";
	private static final String IS_NOT_TWO_DAYS_FROM_TRADE_DATE = "is not two days from tradeDate";
	private static final String LEGAL_ENTITY = "legalEntity";
	private static final String VALUE_DATE = "valueDate";
	private static final String TRADE_DATE = "tradeDate";
	private static final String CCY_PAIR = "ccyPair";
	private static final String CUSTOMER = "customer";
	private static final String TRADE = "trade";
	private static final String YYY_IS_NOT_A_VALID_ISO_4217_CODE = "YYY is not a valid ISO 4217 code";
	private static final String ZZZ_IS_NOT_A_VALID_ISO_4217_CODE = "ZZZ is not a valid ISO 4217 code";
	private static final String IS_AT_THE_WRONG_LENGTH = "is at the wrong length";
	private static final String IS_NOT_SUPPORTED = "is not supported";
	private static final String CANNOT_FALL_ON_WEEKEND_OR_NON_WORKING_DAY_FOR_CURRENCY = "cannot fall on weekend or non-working day for currency";
	private static final String MUST_NOT_BE_BEFORE_TRADE_DATE = "must not be before tradeDate";
	private static final String MUST_NOT_BE_EMPTY = "must not be empty";

	public static ErrorMessage emptyTrade() {
		return new ErrorMessage(TRADE, MUST_NOT_BE_EMPTY);
	}
	
	public static ErrorMessage emptyCustomer() {
		return new ErrorMessage(CUSTOMER, MUST_NOT_BE_EMPTY);		
	} 
	
	public static ErrorMessage emptyCurrency() {
		return new ErrorMessage(CCY_PAIR, MUST_NOT_BE_EMPTY);		
	} 
	
	public static ErrorMessage emptyTradeDate() {
		return new ErrorMessage(TRADE_DATE, MUST_NOT_BE_EMPTY);		
	} 
	
	public static ErrorMessage emptyValueDate() {
		return new ErrorMessage(VALUE_DATE, MUST_NOT_BE_EMPTY);		
	} 
	
	public static ErrorMessage emptyLegalEntity() {
		return new ErrorMessage(LEGAL_ENTITY, MUST_NOT_BE_EMPTY);
	}
	
	public static ErrorMessage emptyStyle() {
		return new ErrorMessage(STYLE, MUST_NOT_BE_EMPTY);		
	}
	
	public static ErrorMessage emptyExcerciseStartDate() {
		return new ErrorMessage(EXCERCISE_START_DATE, MUST_NOT_BE_EMPTY);		
	}
	
	public static ErrorMessage emptyExpiryDate() {
		return new ErrorMessage(Expiry_DATE, MUST_NOT_BE_EMPTY);		
	}
	
	public static ErrorMessage emptyDeliveryDate() {
		return new ErrorMessage(DELIVERY_DATE, MUST_NOT_BE_EMPTY);		
	}
	
	public static ErrorMessage emptyPremiumDate() {
		return new ErrorMessage(PREMIUM_DATE, MUST_NOT_BE_EMPTY);		
	}
	
	public static ErrorMessage valueDateBeforeTradeDate() {
		return new ErrorMessage(VALUE_DATE, MUST_NOT_BE_BEFORE_TRADE_DATE);
	}
	
	public static ErrorMessage valueDateNonWorkingDay() {
		return new ErrorMessage(VALUE_DATE, CANNOT_FALL_ON_WEEKEND_OR_NON_WORKING_DAY_FOR_CURRENCY);
	}
	
	public static ErrorMessage legalEntityNotSupported() {
		return new ErrorMessage(LEGAL_ENTITY, IS_NOT_SUPPORTED);
	}
	
	public static ErrorMessage customerNotSupported() {
		return new ErrorMessage(CUSTOMER, IS_NOT_SUPPORTED);
	}
	
	public static ErrorMessage ccyPairWrongLength() {
		return new ErrorMessage(CCY_PAIR, IS_AT_THE_WRONG_LENGTH);
	}
	
	public static ErrorMessage ccyPairNonISO4217ZZZ() {
		return new ErrorMessage(CCY_PAIR, ZZZ_IS_NOT_A_VALID_ISO_4217_CODE);
	}
	
	public static ErrorMessage ccyPairNonISO4217YYY() {
		return new ErrorMessage(CCY_PAIR, YYY_IS_NOT_A_VALID_ISO_4217_CODE);
	}
	
	public static ErrorMessage valueDateSpotNotTwoDaysAhead() {
		return new ErrorMessage(VALUE_DATE, IS_NOT_TWO_DAYS_FROM_TRADE_DATE);
	}
	
	public static ErrorMessage valueDateForwardNotAfterCurrentDate() {
		return new ErrorMessage(VALUE_DATE, IS_NOT_AFTER_CURRENT_DATE);
	}
	
	public static ErrorMessage excerciseStartDateNotBetweenExpiryDateAndTradeDate() {
		return new ErrorMessage(EXCERCISE_START_DATE, IS_NOT_BETWEEN_Expiry_DATE_AND_TRADE_DATE);
	}
	
	public static ErrorMessage styleNotSupported() {
		return new ErrorMessage(STYLE, IS_NOT_SUPPORTED);
	}
	
	public static ErrorMessage deliveryDateMustBeBeforeExpiryDate() {
		return new ErrorMessage(DELIVERY_DATE, MUST_BE_BEFORE_EXPIRY_DATE);
	}
	
	public static ErrorMessage deliveryDateMustBeBoforePremiumDate() {
		return new ErrorMessage(DELIVERY_DATE, MUST_BE_BEFORE_PREMIUM_DATE);
	}

}
