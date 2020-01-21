package com.parvanpajooh.identitymanagement2.common.exceptions;

/**
 * @author ali
 */
public class SaveObjectException extends ParvanDaoException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

	/**
	 * @param errorCode
	 */
	public SaveObjectException() {
		super(ErrorCode.SAVE_ERROR);
	}
}
