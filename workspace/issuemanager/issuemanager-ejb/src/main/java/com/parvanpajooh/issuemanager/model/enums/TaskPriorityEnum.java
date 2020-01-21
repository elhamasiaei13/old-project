package com.parvanpajooh.issuemanager.model.enums;


public enum TaskPriorityEnum{
	

	BLOCKER("Blocker"),
	CRITICAL("Critical"),
	MAJOR("Major"),
	MINOR("Minor"),
	TRIVIAL("Trivial"),
	;
	
	
	/**
	 * 
	 */
	public final String value;

	/**
	 * 
	 * @param value
	 */
	TaskPriorityEnum(String value) {
		this.value = value;
		
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	public static TaskPriorityEnum fromValue(String value) {
		
		for (TaskPriorityEnum thisValue : values()) {
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
	public static TaskPriorityEnum getDefault() {
		return CRITICAL;
	}
}
