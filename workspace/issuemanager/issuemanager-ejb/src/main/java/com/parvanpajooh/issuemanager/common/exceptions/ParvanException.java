package com.parvanpajooh.issuemanager.common.exceptions;


/**
 * 
 * @author mehrdad
 * @author ali
 *
 */
public class ParvanException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private ErrorCode errorCode ;

    /**
     * @param message
     */
    public ParvanException(String message) {
        super(message);
    }
    
    /**
     * @param message
     */
    public ParvanException(ErrorCode errorCode) {
        super();
        this.errorCode = errorCode;
    }

    /**
     * @param cause
     */
    public ParvanException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public ParvanException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
    
    /**
     * @param message
     * @param cause
     */
    public ParvanException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * @param cause
     */
    public ParvanException(Throwable cause, ErrorCode errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }
    
    /**
     * @param message
     * @param cause
     */
    public ParvanException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

    
}
