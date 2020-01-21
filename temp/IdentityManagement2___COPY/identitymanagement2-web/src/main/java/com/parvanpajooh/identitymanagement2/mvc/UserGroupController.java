package com.parvanpajooh.identitymanagement2.mvc;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.parvanpajooh.ecourier.service.GenericService;
import com.parvanpajooh.identitymanagement2.model.UserGroup;
import com.parvanpajooh.identitymanagement2.model.criteria.UserGroupCriteria;
import com.parvanpajooh.identitymanagement2.model.vo.UserGroupVO;
import com.parvanpajooh.identitymanagement2.mvc.base.AbstractController;
import com.parvanpajooh.identitymanagement2.service.UserGroupService;

/**
 * 
 * @author ali
 *
 */
@Controller
@RequestMapping("/userGroup")
public class UserGroupController extends AbstractController<UserGroup, UserGroupVO, UserGroupCriteria> {

	@EJB(mappedName="java:global/identitymanagement2-ear/identitymanagement2-ejb/UserGroupServiceImpl")
	private UserGroupService userGroupService;
	
	@Override
	protected GenericService<UserGroup, Long> getService() {
		return userGroupService;
	}

	@Override
	protected String getName() {
		return "userGroups";
	}


}
