package com.parvanpajooh.identitymanagement2.common.exceptions;

/**
 * @author mehrdad
 * @author ali
 */
public class ParvanClientException extends ParvanException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

	/**
	 * @param errorCode
	 */
	public ParvanClientException(ErrorCode errorCode) {
		super(errorCode);
	}

	/**
	 * @param message
	 * @param errorCode
	 */
	public ParvanClientException(String message, ErrorCode errorCode) {
		super(message, errorCode);
	}

	/**
	 * @param message
	 * @param cause
	 * @param errorCode
	 */
	public ParvanClientException(String message, Throwable cause,
			ErrorCode errorCode) {
		super(message, cause, errorCode);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ParvanClientException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public ParvanClientException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 * @param errorCode
	 */
	public ParvanClientException(Throwable cause, ErrorCode errorCode) {
		super(cause, errorCode);
	}

	/**
	 * @param cause
	 */
	public ParvanClientException(Throwable cause) {
		super(cause);
	}

    

}
