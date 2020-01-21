/**
 * 
 */
package com.parvanpajooh.geomanagement.rest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.parvanpajooh.client.common.ErrorInfo;
import com.parvanpajooh.common.auth.BaseRestController;
import com.parvanpajooh.commons.constants.StringPool;
import com.parvanpajooh.commons.platform.ejb.exceptions.ErrorCode;
import com.parvanpajooh.commons.platform.ejb.exceptions.ObjectNotFoundException;
import com.parvanpajooh.commons.platform.ejb.exceptions.ParvanRecoverableException;
import com.parvanpajooh.commons.platform.ejb.exceptions.ParvanServiceException;
import com.parvanpajooh.commons.util.Validator;

@ControllerAdvice
public class GlobalRestControllerAdvice extends BaseRestController{
	
	@ExceptionHandler(ObjectNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public @ResponseBody ErrorInfo handleNotFound(HttpServletRequest req, Exception ex) {
	    return new ErrorInfo(String.valueOf(HttpStatus.NOT_FOUND), ex.getMessage(), null);
	}
	
	@ExceptionHandler(ParvanServiceException.class)
	@ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
	public @ResponseBody ErrorInfo handleParvanService(HttpServletRequest req, Exception ex) {
		
		if(ex instanceof ParvanRecoverableException){
			
			ParvanRecoverableException e = (ParvanRecoverableException) ex;
			
			ErrorCode errorCode = e.getErrorCode();
			Exception cause = (Exception) e.getCause();
			
			String code = null;
			String message = null;
			String invalidField = null;
			
			if (errorCode.equals(ErrorCode.DATA_IS_INVALID)) {
				if (cause != null && cause instanceof ConstraintViolationException) {
					invalidField = ((ConstraintViolationException) cause).getConstraintViolations().iterator().next().getPropertyPath().toString();

				} else {
					invalidField = e.getMessage();
				}

				code = String.valueOf(errorCode.toValue());
				message = getMessage(invalidField);
			} else if (errorCode.equals(ErrorCode.OBJECT_EXIST)) {
				invalidField = e.getMessage();
				code = String.valueOf(errorCode.toValue());
				message = invalidField;
			} else if (errorCode.equals(ErrorCode.DATA_DUPLICATE)) {
				code = String.valueOf(errorCode.toValue());
				invalidField = e.getMessage();
				message = getMessage("error.data-duplicate", getMessage(e.getMessage(), (Object) null));
			} else if (errorCode.equals(ErrorCode.FIELD_IS_EMPTY)) {
				code = String.valueOf(errorCode.toValue());
				invalidField = e.getMessage();
				message = getMessage("error-required", getMessage(e.getMessage(), (Object) null));
			} else {
				code = String.valueOf(errorCode.toValue());
				if (Validator.isNotNull(e.getMessage())) {
					message = getMessage("errorCode." + errorCode.toValue(), (Object) null);
					message = message + StringPool.SPACE + e.getMessage();
				} else {
					message = getMessage("errorCode." + errorCode.toValue(), (Object) null);
				}
			}
			
			return new ErrorInfo(code, message, invalidField);
			
			
		} else {

		    return new ErrorInfo("0", ex.getMessage(), null);
		}
		
	}
}
