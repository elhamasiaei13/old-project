package com.parvanpajooh.issuemanager.model.enums;

import java.util.ResourceBundle;


public enum TaskStatusEnum{
	


	NEW("new"),
	SUSPENDED("suspended"),
	SUSPENDEDINFO("suspended-info"),
	INFORMATIONOK("information-ok"),
	FIXED("fixed"),
	INPROGRESS("inprogress"),
	CLOSED("closed");
	
	
	/**
	 * 
	 */
	public final String value;

	/**
	 * 
	 * @param value
	 */
	TaskStatusEnum(String value) {
		this.value = value;
		
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	public static TaskStatusEnum fromValue(String value) {
		
		for (TaskStatusEnum thisValue : values()) {
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
	public static TaskStatusEnum getDefault() {
		return NEW;
	}
}
