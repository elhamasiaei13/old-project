package com.parvanpajooh.identitymanagement2.common.exceptions;

/**
 * @author ali
 */
public class ObjectExistsException extends ParvanDaoException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

	/**
	 * @param errorCode
	 */
	public ObjectExistsException() {
		super(ErrorCode.OBJECT_EXIST);
	}
}
