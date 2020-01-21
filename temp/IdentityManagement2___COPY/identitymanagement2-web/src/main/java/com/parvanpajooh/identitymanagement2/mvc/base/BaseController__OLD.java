package com.parvanpajooh.identitymanagement2.mvc.base;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;

import com.parvanpajooh.identitymanagement2.common.exceptions.ErrorCode;
import com.parvanpajooh.identitymanagement2.common.exceptions.ParvanRecoverableException;
import com.parvanpajooh.common.constants.StringPool;
import com.parvanpajooh.common.util.StringUtil;
import com.parvanpajooh.common.util.Validator;

public class BaseController__OLD {

	protected transient final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private MessageSource messageSource;
	ResourceBundle resourceBundle = ResourceBundle.getBundle("Messages");

	protected void proccessException(Exception e, Map<String, Object> result) {

		if (e instanceof ParvanRecoverableException) {
			ParvanRecoverableException recoverableException = (ParvanRecoverableException) e;

			Exception cause = (Exception) recoverableException.getCause();

			if (recoverableException.getErrorCode().equals(ErrorCode.DATA_IS_INVALID)) {
				String invalidField = null;
				if (cause != null && cause instanceof ConstraintViolationException) {
					invalidField = ((ConstraintViolationException) cause).getConstraintViolations().iterator().next().getPropertyPath().toString();

				} else {
					invalidField = recoverableException.getMessage();
				}

				result.put("invalidField", getMessage(invalidField));
				result.put("status", "ValidationException");
			} else if (recoverableException.getErrorCode().equals(ErrorCode.OBJECT_EXIST)) {
				String invalidField = recoverableException.getMessage();
				result.put("invalidField", invalidField);
				result.put("status", "ObjectExistsException");

			} else if (recoverableException.getErrorCode().equals(ErrorCode.DATA_DUPLICATE)) {
				result.put("status", "ErrorCode");
				result.put("errorCode", recoverableException.getErrorCode().toValue());
				result.put("errorField", recoverableException.getMessage());
				result.put("errorMessage", getMessage("error.data-duplicate", getMessage(recoverableException.getMessage(), (Object) null)));

			} else if (recoverableException.getErrorCode().equals(ErrorCode.FIELD_IS_EMPTY)) {
				result.put("status", "ErrorCode");
				result.put("errorCode", recoverableException.getErrorCode().toValue());
				result.put("errorField", recoverableException.getMessage());
				result.put("errorMessage", getMessage("error-required", getMessage(recoverableException.getMessage(), (Object) null)));

			} else if (recoverableException.getErrorCode().equals(ErrorCode.FEILDS_IS_EMPTY)) {
				result.put("status", "ErrorCode");
				result.put("errorCode", recoverableException.getErrorCode().toValue());
				result.put("errorField", recoverableException.getMessage());
				ResourceBundle resourceBundle = ResourceBundle.getBundle("Messages");
				result.put("errorMessage", getMessage(resourceBundle.getString("ERROR_FEILDS_IS_EMPTY"),
						getMessage("trackstate." + StringUtil.upperCase(recoverableException.getMessage()), (Object) null)));

			} else {
				result.put("status", "ErrorCode");
				result.put("errorCode", recoverableException.getErrorCode().toValue());
				if (Validator.isNotNull(recoverableException.getMessage())) {
					String message = getMessage("errorCode." + recoverableException.getErrorCode().toValue(), (Object) null);
					result.put("errorMessage", message + StringPool.SPACE + recoverableException.getMessage());
				} else {
					result.put("errorMessage", getMessage("errorCode." + recoverableException.getErrorCode().toValue(), (Object) null));
				}
			}
		} else {
			log.error("", e);
			result.put("status", "fail");
			result.put("message", e.getMessage());
		}
	}

	protected String getMessage(final String messageKey, final Object... messageParameters) {

		Locale locale = LocaleContextHolder.getLocale();

		String result = null;
		try {
			result = messageSource.getMessage(messageKey, messageParameters, locale);
		} catch (NoSuchMessageException e) {
			result = messageKey;
		}

		return result;

	}
}
