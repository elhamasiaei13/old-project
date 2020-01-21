package com.parvanpajooh.issuemanager.service.impl;

import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.parvanpajooh.commons.platform.ejb.model.UserInfo;
import com.parvanpajooh.issuemanager.common.exceptions.ErrorCode;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanRecoverableException;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanServiceException;
import com.parvanpajooh.issuemanager.model.Group;
import com.parvanpajooh.issuemanager.model.enums.RoleEnum;
import com.parvanpajooh.issuemanager.service.GroupLocalService;
import com.parvanpajooh.issuemanager.service.GroupService;

/**
 * 
 * @author
 * 
 */
@Stateless
public class GroupServiceImpl extends GenericServiceImpl<Group, Long>implements GroupService {
	/**
	 * Log variable for all child classes.
	 */
	static final Logger log = LoggerFactory.getLogger(GroupServiceImpl.class);

	private GroupLocalService groupLocalService;

	@Inject
	public void setUserLocalService(GroupLocalService groupLocalService) {
		this.localService = groupLocalService;
		this.groupLocalService = groupLocalService;
	}
	
	@Override
	public void delete(UserInfo userInfo, Long id) throws ParvanServiceException {
		log.debug("Entering deleteGroup(userInfo={})", userInfo);
		
		// check access
		Set<String> userRoles = userInfo.getRoleNames();
		if (!(userRoles.contains(RoleEnum.ADMIN.value) && userRoles.contains(RoleEnum.MANAGER.value))) {					
			throw new ParvanRecoverableException(ErrorCode.HAS_NOT_ACCESS);
		}
		
		groupLocalService.delete(id);
	}

}
