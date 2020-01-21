package com.parvanpajooh.sample.mvc;

import java.util.List;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.parvanpajooh.common.auth.UserInfoLoader;
import com.parvanpajooh.common.util.ParamUtil;
import com.parvanpajooh.commons.platform.ejb.exceptions.ObjectNotFoundException;
import com.parvanpajooh.commons.platform.ejb.exceptions.ParvanServiceException;
import com.parvanpajooh.commons.platform.ejb.model.UserInfo;
import com.parvanpajooh.commons.util.Validator;
import com.parvanpajooh.sample.model.vo.ObjectAccessScopeVO;
import com.parvanpajooh.sample.mvc.base.SampleBaseController;
import com.parvanpajooh.sample.service.SampleService;
import com.parvanpajooh.sample.service.UserService;

@ControllerAdvice
public class GlobalController extends SampleBaseController {

	@EJB(mappedName = "java:global/sample-ear/sample-ejb/SampleServiceImpl")
	private SampleService sampleService;

	@EJB(mappedName = "java:global/sample-ear/sample-ejb/UserServiceImpl")
	private UserService userService;

	@ModelAttribute("userInfo")
	public UserInfo populateUserInfo(HttpServletRequest request) throws ObjectNotFoundException, ParvanServiceException {

		return UserInfoLoader.getInstance().getUserInfo();
	}
	
	@ModelAttribute("objectAccessScopes")
	public List<ObjectAccessScopeVO> loadObjectAccessScopes(HttpServletRequest request) throws ObjectNotFoundException, ParvanServiceException {
		@SuppressWarnings("unchecked")
		List<ObjectAccessScopeVO> objectAccessScopes = (List<ObjectAccessScopeVO>) request.getSession().getAttribute("objectAccessScopes");

		boolean doRefresh = ParamUtil.getBoolean(request, "doRefresh", false);

		if (Validator.isNull(objectAccessScopes) || doRefresh) {

			UserInfo userInfo = UserInfoLoader.getInstance().getUserInfo();
			
			if (userInfo != null) {
				objectAccessScopes = userService.loadScopes(userInfo);
			}

			request.getSession().setAttribute("objectAccessScopes", objectAccessScopes);
		}

		return objectAccessScopes;
	}

}
