package com.parvanpajooh.complaintmanagement.mvc;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.parvanpajooh.common.auth.UserInfoLoader;
import com.parvanpajooh.commons.platform.ejb.exceptions.ObjectNotFoundException;
import com.parvanpajooh.commons.platform.ejb.exceptions.ParvanServiceException;
import com.parvanpajooh.commons.platform.ejb.model.UserInfo;
import com.parvanpajooh.complaintmanagement.mvc.base.ComplaintManagementBaseController;

@ControllerAdvice
public class GlobalController extends ComplaintManagementBaseController{


	@ModelAttribute("userInfo")
	public UserInfo populateUserInfo(HttpServletRequest request) throws ObjectNotFoundException, ParvanServiceException {

		return UserInfoLoader.getInstance().getUserInfo();
	}

}
