package com.parvanpajooh.identitymanagement2.common.exceptions;

/**
 * @author mehrdad
 * @author ali
 */
public class ParvanDaoException extends ParvanException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

	/**
	 * @param errorCode
	 */
	public ParvanDaoException(ErrorCode errorCode) {
		super(errorCode);
	}

	/**
	 * @param message
	 * @param errorCode
	 */
	public ParvanDaoException(String message, ErrorCode errorCode) {
		super(message, errorCode);
	}

	/**
	 * @param message
	 * @param cause
	 * @param errorCode
	 */
	public ParvanDaoException(String message, Throwable cause,
			ErrorCode errorCode) {
		super(message, cause, errorCode);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ParvanDaoException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public ParvanDaoException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 * @param errorCode
	 */
	public ParvanDaoException(Throwable cause, ErrorCode errorCode) {
		super(cause, errorCode);
	}

	/**
	 * @param cause
	 */
	public ParvanDaoException(Throwable cause) {
		super(cause);
	}

    

}
