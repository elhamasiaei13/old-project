package com.parvanpajooh.issuemanager.common.exceptions;

/**
 * @author ali
 */
public class ObjectNotFoundException extends ParvanDaoException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

	/**
	 * @param errorCode
	 */
	public ObjectNotFoundException() {
		super(ErrorCode.OBJECT_NOT_FOUND);
	}

	/**
	 * @param message
	 */
	public ObjectNotFoundException(String message) {
		super(message, ErrorCode.OBJECT_NOT_FOUND);
	}
}
