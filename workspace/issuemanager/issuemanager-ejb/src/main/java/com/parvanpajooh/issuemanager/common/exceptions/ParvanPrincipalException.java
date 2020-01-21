package com.parvanpajooh.issuemanager.common.exceptions;

import javax.ejb.ApplicationException;

/**
 * @author ali
 */
@ApplicationException(rollback=true)
public class ParvanPrincipalException extends ParvanRecoverableException {

	
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public ParvanPrincipalException() {
    	super(ErrorCode.ACCESS_DENIDE);
    }
    
	/**
	 * @param errorCode
	 */
	public ParvanPrincipalException(ErrorCode errorCode) {
		super(errorCode);
	}

	/**
	 * @param message
	 * @param errorCode
	 */
	public ParvanPrincipalException(String message, ErrorCode errorCode) {
		super(message, errorCode);
	}

	/**
	 * @param message
	 * @param cause
	 * @param errorCode
	 */
	public ParvanPrincipalException(String message, Throwable cause,
			ErrorCode errorCode) {
		super(message, cause, errorCode);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ParvanPrincipalException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public ParvanPrincipalException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 * @param errorCode
	 */
	public ParvanPrincipalException(Throwable cause, ErrorCode errorCode) {
		super(cause, errorCode);
	}

	/**
	 * @param cause
	 */
	public ParvanPrincipalException(Throwable cause) {
		super(cause);
	}

    

}
