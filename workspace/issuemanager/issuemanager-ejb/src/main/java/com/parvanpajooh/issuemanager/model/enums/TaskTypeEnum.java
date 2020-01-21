package com.parvanpajooh.issuemanager.model.enums;


public enum TaskTypeEnum{
	


	REQUEST("request"),
	REQUIREMENT("requirement"),
	TASK("task"),
	;
	
	
	/**
	 * 
	 */
	public final String value;

	/**
	 * 
	 * @param value
	 */
	TaskTypeEnum(String value) {
		this.value = value;
		
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	public static TaskTypeEnum fromValue(String value) {
		
		for (TaskTypeEnum thisValue : values()) {
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
	public static TaskTypeEnum getDefault() {
		return TASK;
	}
}
