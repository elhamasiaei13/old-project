package com.parvanpajooh.issuemanager.model.enums;


public enum RelationTypeEnum{
	



	RELATESTO("relates to"),
	ISDUPLICATEDBY("is duplicated by"),
	DUPLICATES("duplicates"),
	ISBLOCKEDBY("is blocked by"),
	BLOCKS("blocks"),
	ISCLONEDBY("is cloned by"),
	CLONES("clones"),
	;
	
	/**
	 * 
	 */
	public final String value;

	/**
	 * 
	 * @param value
	 */
	RelationTypeEnum(String value) {
		this.value = value;
		
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	public static RelationTypeEnum fromValue(String value) {
		
		for (RelationTypeEnum thisValue : values()) {
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
	public static RelationTypeEnum getDefault() {
		return RELATESTO;
	}
}
