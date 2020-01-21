package com.parvanpajooh.issuemanager.model.enums;

import java.util.ResourceBundle;


public enum EmailEnum{
	

	NEED("need"),
    SENT("sent"),
    FAILD("faild")
	;
	
	/**
	 * 
	 */
	public final String value;

	/**
	 * 
	 * @param value
	 */
	EmailEnum(String value) {
		this.value = value;
		
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	public static EmailEnum fromValue(String value) {
		
		for (EmailEnum thisValue : values()) {
			if (thisValue.value == value) {
				return thisValue;
			}
		}

		// you may return a default value
		return null;
	}
	
	/**
	 * 
	 * @return
	 */
	public String toValue() {
		return value;
	}

}
