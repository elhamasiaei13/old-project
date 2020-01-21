package com.parvanpajooh.issuemanager.model.enums;

import java.util.ResourceBundle;


public enum TableNameEnum{
	

	TaskStatusHistory("TaskStatusHistory"),
    TaskAssignmentHistory("TaskAssignmentHistory"),
    TaskAttachment("TaskAttachment"),
    Comment("Comment"),
    Relation("Relation")
	;
	
	/**
	 * 
	 */
	public final String value;

	/**
	 * 
	 * @param value
	 */
	TableNameEnum(String value) {
		this.value = value;
		
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	public static TableNameEnum fromValue(String value) {
		
		for (TableNameEnum thisValue : values()) {
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
