package com.parvanpajooh.issuemanager.common.exceptions;

/**
 * @author mehrdad
 * @author ali
 */
public class ParvanRecoverableException extends ParvanServiceException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

	/**
	 * @param errorCode
	 */
	public ParvanRecoverableException(ErrorCode errorCode) {
		super(errorCode);
	}

	/**
	 * @param message
	 * @param errorCode
	 */
	public ParvanRecoverableException(String message, ErrorCode errorCode) {
		super(message, errorCode);
	}

	/**
	 * @param message
	 * @param cause
	 * @param errorCode
	 */
	public ParvanRecoverableException(String message, Throwable cause,
			ErrorCode errorCode) {
		super(message, cause, errorCode);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ParvanRecoverableException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public ParvanRecoverableException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 * @param errorCode
	 */
	public ParvanRecoverableException(Throwable cause, ErrorCode errorCode) {
		super(cause, errorCode);
	}

	/**
	 * @param cause
	 */
	public ParvanRecoverableException(Throwable cause) {
		super(cause);
	}

    

}
