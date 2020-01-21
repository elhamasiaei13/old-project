package com.parvanpajooh.issuemanager.model.enums;


public enum RoleEnum{
	


	ADMIN("admin"),
	MANAGER("manager"),
	WORKER("worker"),
	REPORTER("reporter"),
	;
	
	
	/**
	 * 
	 */
	public final String value;

	/**
	 * 
	 * @param value
	 */
	RoleEnum(String value) {
		this.value = value;
		
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	public static RoleEnum fromValue(String value) {
		
		for (RoleEnum thisValue : values()) {
			if (thisValue.value == value) {
				return thisValue;
			}
		}

		// you may return a default value
		return getDefault();
		// or throw an exception
		// throw new IllegalArgumentException("Invalid LanguageIso6391: " + value);
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
	public static RoleEnum getDefault() {
		return REPORTER;
	}
}
