package com.parvanpajooh.identitymanagement2.model.enums;

import com.parvanpajooh.common.util.Validator;

public enum RoleTypeEnum{
	


	OMNIADMIN("omniAdmin"),
	GUEST("guest"),
	NORMAL("normal"),
	;
		
	/**
	 * 
	 */
	public final String value;

	/**
	 * 
	 * @param value
	 */
	RoleTypeEnum(String value) {
		this.value = value;
		
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	public static RoleTypeEnum fromValue(String value) {
		
		for (RoleTypeEnum thisValue : values()) {
			if (Validator.equals(thisValue.value, value)) {
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
	public static RoleTypeEnum getDefault() {
		return NORMAL;
	}
}
