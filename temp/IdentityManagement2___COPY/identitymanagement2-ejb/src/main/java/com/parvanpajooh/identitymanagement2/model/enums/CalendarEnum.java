package com.parvanpajooh.identitymanagement2.model.enums;


public enum CalendarEnum{
	


	JALALI("jalali"),
	GREGORIAN("gregorian"),
	;
		
	/**
	 * 
	 */
	public final String value;

	/**
	 * 
	 * @param value
	 */
	CalendarEnum(String value) {
		this.value = value;
		
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	public static CalendarEnum fromValue(String value) {
		
		for (CalendarEnum thisValue : values()) {
			if (thisValue.value == value) {
				return thisValue;
			}
		}

		// you may return a default value
		return getDefault();
		// or throw an exception

	}
	
	/**
	 * 
	 * @return
	 */
	public String toValue() {
		return value;
	}

	/**
	 * 
	 * @return
	 */
	public static CalendarEnum getDefault() {
		return JALALI;
	}
}
