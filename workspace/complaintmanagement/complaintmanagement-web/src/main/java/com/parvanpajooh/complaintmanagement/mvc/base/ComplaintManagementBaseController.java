package com.parvanpajooh.complaintmanagement.mvc.base;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.thymeleaf.extras.springsecurity4.auth.AuthUtils;

import com.parvanpajooh.common.formatter.DateProperyEditor;
import com.parvanpajooh.common.formatter.JalaliDateProperyEditor;
import com.parvanpajooh.commons.config.Config;
import com.parvanpajooh.commons.constants.StringPool;
import com.parvanpajooh.commons.customtype.JalaliLocalDateTime;
import com.parvanpajooh.commons.platform.ejb.exceptions.ErrorCode;
import com.parvanpajooh.commons.platform.ejb.exceptions.ObjectNotFoundException;
import com.parvanpajooh.commons.platform.ejb.exceptions.ParvanRecoverableException;
import com.parvanpajooh.commons.platform.ejb.exceptions.ParvanServiceException;
import com.parvanpajooh.commons.platform.ejb.model.UserInfo;
import com.parvanpajooh.commons.platform.ejb.util.permission.exception.PrincipalException;
import com.parvanpajooh.commons.util.DateUtil;
import com.parvanpajooh.commons.util.LocaleUtil;
import com.parvanpajooh.commons.util.StringUtil;
import com.parvanpajooh.commons.util.Validator;
import com.parvanpajooh.commons.util.ZoneIdUtil;


public abstract class ComplaintManagementBaseController 
{
	static 
	{
	    HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
			public boolean verify(String hostname, SSLSession session) {
				// ip address of the service URL(like.23.28.244.244)
				System.out.println("----------------------------------------------------------------------");
				System.out.println(hostname);
				System.out.println("----------------------------------------------------------------------");
				// if (hostname.startsWith("192.168.0") ||
				// hostname.startsWith("83.147.193."))
				return true;
				// return false;
			}
		});
	}

	protected transient final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private MessageSource messageSource;
	
	//@Autowired
	//protected JacksonObjectMapperProvider mapperProvider;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(LocalDate.class, new DateProperyEditor(false, true));
		binder.registerCustomEditor(LocalDateTime.class, new DateProperyEditor(true, true));
		binder.registerCustomEditor(JalaliLocalDateTime.class, new JalaliDateProperyEditor(true, true));
	}

	@ModelAttribute("default")
	public void setUserLocale(HttpServletRequest request, Model model, Locale locale) {
		model.addAttribute("config", Config.getInstance());
	}

	@ExceptionHandler(ParvanServiceException.class)
	public String handleException(Exception e, HttpServletResponse response) throws Exception {

		ErrorCode errorCode = ((ParvanServiceException) e).getErrorCode();

		if (errorCode != null && errorCode.equals(ErrorCode.OBJECT_NOT_FOUND)) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			log.error(errorCode.name(), e);

		} else if (errorCode != null && errorCode.equals(ErrorCode.ACCESS_DENIDE)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
			log.error(errorCode.name(), e);

		} else {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			log.error("Parvan Service Exception", e);
		}

		return null;
	}

	@ModelAttribute("errorCodes")
	public String errorCodes(HttpServletRequest request) throws ObjectNotFoundException, ParvanServiceException {
		String errorCodes = (String) request.getSession().getAttribute("errorCodes");

		if (Validator.isNull(errorCodes)) {
			StringBuilder builder = new StringBuilder();

			@SuppressWarnings("rawtypes")
			Class clazz = ErrorCode.class;
			Object[] constants = clazz.getEnumConstants();
			for (Object object : constants) {
				int value = ErrorCode.valueOf(String.valueOf(object)).toValue();

				if (builder.length() > 0)
					builder.append(",");
				builder.append("'" + ErrorCode.valueOf(String.valueOf(object)).toValue() + "':'" + getMessage("errorCode." + value, (Object) null) + "'");
			}

			request.getSession().setAttribute("errorCodes", builder.toString());
		}

		return errorCodes;
	}

	protected void writeMessage(Map<String, Object> result, String msg) {
		try {
			result.put("message", messageSource.getMessage(msg, new Object[0], LocaleContextHolder.getLocale()));
		} catch (Exception e) {
			result.put("message", msg);
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

	protected void proccessException(Exception e, Map<String, Object> result) {
		log.error("Server Exception: ", e);
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
		} else if (e instanceof PrincipalException) {
			PrincipalException principalException = (PrincipalException) e;

			result.put("status", "ErrorCode");
			result.put("errorCode", ErrorCode.ACCESS_DENIDE);
			if (Validator.isNotNull(principalException.getMessage())) {
				result.put("errorMessage", principalException.getMessage());
			} else {
				result.put("errorMessage", getMessage("errorCode." + ErrorCode.ACCESS_DENIDE.toValue(), (Object) null));
			}
		} else {
			log.error("", e);
			result.put("status", "fail");
			result.put("message", e.getMessage());
		}
	}

	protected void removeSelected(HttpServletRequest request, String entity) {
		HttpSession session = request.getSession();
		@SuppressWarnings("unchecked")
		Map<String, List<Long>> selectPool = (Map<String, List<Long>>) session.getAttribute("entity-selectPool");
		if (selectPool == null) {
			selectPool = new HashMap<>();
		}

		selectPool.remove(StringUtil.upperCaseFirstLetter(entity) + "-selected");
		session.setAttribute("entity-selectPool", selectPool);
	}

	protected void changeSelected(HttpServletRequest request, String entity, String action, Long id) {
		HttpSession session = request.getSession();
		@SuppressWarnings("unchecked")
		Map<String, List<Long>> selectPool = (Map<String, List<Long>>) session.getAttribute("entity-selectPool");
		if (selectPool == null) {
			selectPool = new HashMap<>();
		}

		List<Long> ids = selectPool.get(StringUtil.upperCaseFirstLetter(entity) + "-selected");
		if (ids == null) {
			ids = new ArrayList<>();
		}

		if ("select".equals(action)) {
			ids.add(id);
		} else {
			ids.remove(id);
		}

		selectPool.put(StringUtil.upperCaseFirstLetter(entity) + "-selected", ids);
		session.setAttribute("entity-selectPool", selectPool);
	}



	/**
	 * 
	 * @param role
	 * @return
	 */
	protected boolean hasRole(String role) {
		Authentication authentication = AuthUtils.getAuthenticationObject();
		@SuppressWarnings("unchecked")
		List<GrantedAuthority> authorities = (List<GrantedAuthority>) authentication.getAuthorities();

		boolean hasRole = false;

		for (GrantedAuthority grantedAuthority : authorities) {
			if (grantedAuthority.getAuthority().equals(role)) {
				hasRole = true;
				break;
			}
		}

		return hasRole;
	}

	protected String getLocalDate(LocalDateTime utcDate, UserInfo ecUserInfo) {
		return DateUtil.getDate(utcDate, LocaleUtil.fromLanguageId(ecUserInfo.getLocale()), ecUserInfo.getCalendar(),
				ZoneIdUtil.getZoneId(ecUserInfo.getZoneId()));
	}

	protected String getLocalDate(LocalDate utcDate, UserInfo ecUserInfo) {
		return DateUtil.getDate(utcDate, LocaleUtil.fromLanguageId(ecUserInfo.getLocale()), ecUserInfo.getCalendar(),
				ZoneIdUtil.getZoneId(ecUserInfo.getZoneId()));
	}

	protected String getLocalDate(Date utcDate, UserInfo ecUserInfo) {
		return DateUtil.getDate(utcDate, LocaleUtil.fromLanguageId(ecUserInfo.getLocale()), ecUserInfo.getCalendar(),
				ZoneIdUtil.getZoneId(ecUserInfo.getZoneId()));
	}

	protected String getLocalDateTime(LocalDateTime utcDate, UserInfo ecUserInfo) {
		return DateUtil.getDateTime(utcDate, LocaleUtil.fromLanguageId(ecUserInfo.getLocale()), ecUserInfo.getCalendar(),
				ZoneIdUtil.getZoneId(ecUserInfo.getZoneId()));
	}

	protected String getLocalDateTime(Date utcDate, UserInfo ecUserInfo) {
		return DateUtil.getDateTime(utcDate, LocaleUtil.fromLanguageId(ecUserInfo.getLocale()), ecUserInfo.getCalendar(),
				ZoneIdUtil.getZoneId(ecUserInfo.getZoneId()));
	}
	
	protected HttpHeaders getHeaders(String accessToken){
		HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    	headers.setAcceptCharset(Collections.singletonList(Charset.forName("UTF-8")));
    	//headers.set("Connection", "Close");
    	//headers.set("Accept-Encoding", "gzip");
    	
    	if(Validator.isNotNull(accessToken)){
    		headers.set("Authorization", "Bearer " + accessToken);
    	}
        
        return headers;
	}	
}
