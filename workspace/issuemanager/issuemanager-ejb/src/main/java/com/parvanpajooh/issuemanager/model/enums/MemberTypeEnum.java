package com.parvanpajooh.issuemanager.model.enums;

import java.util.ResourceBundle;


public enum MemberTypeEnum{
	

//	UNKNOWN("0"), 
	/*ALL("all"),*/
	ADMIN("admin"),
	REPORTER("reporter"),
	MANAGER("manager"),
//	SUSPENDEN(ResourceBundle.getBundle("/issuemanager-web/src/main/resources/Messages").getString("task_status_suspended")),
//	FIXED(ResourceBundle.getBundle("/issuemanager-web/src/main/resources/Messages").getString("task_status_fixed")),
//	INPROGRESS(ResourceBundle.getBundle("/issuemanager-web/src/main/resources/Messages").getString("task_status_inprogress")),
	;
	
	
	/**
	 * 
	 */
	public final String value;

	/**
	 * 
	 * @param value
	 */
	MemberTypeEnum(String value) {
		this.value = value;
		
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	public static MemberTypeEnum fromValue(String value) {
		
		for (MemberTypeEnum thisValue : values()) {
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
	public static MemberTypeEnum getDefault() {
		return REPORTER;
	}
}
