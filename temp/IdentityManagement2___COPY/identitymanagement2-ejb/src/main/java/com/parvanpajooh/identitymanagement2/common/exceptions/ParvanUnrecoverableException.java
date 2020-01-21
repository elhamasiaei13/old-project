package com.parvanpajooh.identitymanagement2.common.exceptions;

/**
 * @author mehrdad
 * @author ali
 */
public class ParvanUnrecoverableException extends ParvanServiceException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

	/**
	 * @param errorCode
	 */
	public ParvanUnrecoverableException(ErrorCode errorCode) {
		super(errorCode);
	}

	/**
	 * @param message
	 * @param errorCode
	 */
	public ParvanUnrecoverableException(String message, ErrorCode errorCode) {
		super(message, errorCode);
	}

	/**
	 * @param message
	 * @param cause
	 * @param errorCode
	 */
	public ParvanUnrecoverableException(String message, Throwable cause,
			ErrorCode errorCode) {
		super(message, cause, errorCode);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ParvanUnrecoverableException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public ParvanUnrecoverableException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 * @param errorCode
	 */
	public ParvanUnrecoverableException(Throwable cause, ErrorCode errorCode) {
		super(cause, errorCode);
	}

	/**
	 * @param cause
	 */
	public ParvanUnrecoverableException(Throwable cause) {
		super(cause);
	}

    

}
