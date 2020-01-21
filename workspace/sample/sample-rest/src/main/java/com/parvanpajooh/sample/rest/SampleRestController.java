package com.parvanpajooh.sample.rest;

import java.util.Map;

import javax.ejb.EJB;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parvanpajooh.common.auth.BaseRestController;
import com.parvanpajooh.common.convertor.JacksonObjectMapperProvider;
import com.parvanpajooh.commons.platform.ejb.exceptions.ErrorCode;
import com.parvanpajooh.commons.platform.ejb.exceptions.ParvanRecoverableException;
import com.parvanpajooh.commons.util.StringUtil;
import com.parvanpajooh.sample.service.SampleService;
import com.parvanpajooh.sample.service.UserService;

@RestController
@RequestMapping("/v1")
public class SampleRestController extends BaseRestController {

	protected transient final Logger log = LoggerFactory.getLogger(getClass());

	@EJB(mappedName = "java:global/sample-ear/sample-ejb/SampleServiceImpl")
	private SampleService generalAgentService;

	@EJB(mappedName = "java:global/sample-ear/sample-ejb/UserServiceImpl")
	private UserService userService;

	@Autowired
	protected JacksonObjectMapperProvider mapperProvider;


	/*private UserInfo loadUserInfo(HttpServletRequest request, String userName, String tenantId) throws Exception {
		UserVO user = userService.loadUserByUsername(userName);
		if (user == null) {
			throw new ObjectNotFoundException("user is not valid");
		}
		Set<String> roleNames = new HashSet<String>();
		Set<RoleVO> roles = user.getRoles();
		if (roles != null) {
			for (RoleVO role : roles) {
				roleNames.add(role.getName());
			}
		}
		String ip = IpUtils.getIpFromRequest(request);
		String contextPath = request.getContextPath();
		UserInfo userInfo = AmUserInfo.getDefaultUserInfo(
				user.getId(), 
				user.getUserName(),
				user.getFirstName(), 
				user.getLastName(), 
				ip, 
				contextPath, 
				tenantId, 
				roleNames, 
				user.getLocale(),
				user.getZoneId(),
				user.getCalendar());
		return userInfo;
	}*/

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
				result.put("invalidField", invalidField);
				result.put("status", "ValidationException");
			} else if (recoverableException.getErrorCode().equals(ErrorCode.OBJECT_EXIST)) {
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
			} else if (recoverableException.getErrorCode().equals(ErrorCode.SHIPMENT_INVALID_STATE_TRANSITION)) {
				result.put("status", "ErrorCode");
				result.put("errorCode", recoverableException.getErrorCode().toValue());
				result.put("errorField", recoverableException.getMessage());
				result.put("errorMessage", getMessage("error-invalid-shipment-state",
						getMessage("trackstate." + StringUtil.upperCase(recoverableException.getMessage()), (Object) null)));
			} else {
				result.put("status", "ErrorCode");
				result.put("errorCode", recoverableException.getErrorCode().toValue());
				result.put("errorMessage", recoverableException.getMessage());
			}
		} else {
			log.error("error in proccessException", e);
			result.put("status", "fail");
		}
	}
}
