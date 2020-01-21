package com.parvanpajooh.issuemanager.model.enums;

public enum TaskMemberRelationEnum{
	


	VOTING("voting"),
	WATCHING("watching")
	;
	
	
	/**
	 * 
	 */
	public final String value;

	/**
	 * 
	 * @param value
	 */
	TaskMemberRelationEnum(String value) {
		this.value = value;
		
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	public static TaskMemberRelationEnum fromValue(String value) {
		

		for (TaskMemberRelationEnum thisValue : values()) {
			if (thisValue.toString().equalsIgnoreCase(value)) {			
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
	public static TaskMemberRelationEnum getDefault() {
		return VOTING;
	}
}
