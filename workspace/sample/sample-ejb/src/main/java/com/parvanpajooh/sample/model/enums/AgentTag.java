package com.parvanpajooh.sample.model.enums;

import com.parvanpajooh.commons.util.Validator;

public enum AgentTag {

	AIRPORT("Airport"), 
	TRANSIT("Transit");

	/**
	 * 
	 */
	public final String value;

	/**
	 * 
	 * @param value
	 */
	AgentTag(String value) {
		this.value = value;
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	public static AgentTag fromValue(String value) {

		for (AgentTag thisValue : values()) {
			if (Validator.equals(thisValue.toString(), value)) {
				return thisValue;
			}
		}

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
