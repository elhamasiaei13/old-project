package com.parvanpajooh.identitymanagement2.common.exceptions;

/**
 */
public enum ErrorCode{

	UNKNOWN(0), 
	INTERNAL_ERROR(10001), 
	INTERNAL_IO_ERROR(10002), 
	VERSION_CHANGED(10003), 
	LOCK_ERROR(10004), 
	DATA_IS_INVALID(10005), 
	SAVE_ERROR(10006),
	REMOVE_ERROR(10007), 
	GET_ERROR(10008), 
	OBJECT_NOT_FOUND(10009),
	OBJECT_EXIST(10010),
	DATA_DUPLICATE(10011),
	ACCESS_DENIDE(10012),
	OBJECT_USED(10013),
	FIELD_IS_EMPTY(10014),
	INVALID_DATE_FORMAT(10015),
	NOT_ENOUGH_PARAMETERS(10016),
	CALCULATION_ERROR(10017),
	TODATE_BEFORE_FROMDATE(10018),
	ATLEAST_ONE_TRACK_NEEDED(10100),
	HOUSEWAY_HAS_NO_TRACK(10101),
	KEY_NOT_UNIQUE(10102),
	INVALID_INSTANCE_CLASS (10103),
	INVALID_DATA_FORMAT (10104),
	
	INVALID_PARENT(20000),
	GROUP__GROUP_IN_USE(20001),
	FEILDS_IS_EMPTY(20002),
	IMAGE_IS_NOT_SUITABLE(20003),
	MEMBER_IS_EMPTY(20004),	
	LOGIN_USER_PASS_NOT_CORRECT(20005),
	FILE_EXIST(20006),
	NOT_EQUAL_PASSWORDS(20007),
	;
	
	
	/**
	 * 
	 */
	private final int value;

	/**
	 * 
	 * @param value
	 */
	ErrorCode(int value) {
		this.value = value;
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	public static ErrorCode fromValue(int value) {
		
		for (ErrorCode thisValue : values()) {
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
	public int toValue() {
		return value;
	}

	/**
	 * 
	 * @return
	 */
	public static ErrorCode getDefault() {
		return UNKNOWN;
	}
}
