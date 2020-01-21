package com.parvanpajooh.identitymanagement2.service.impl;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.parvanpajooh.ecourier.service.impl.GenericServiceImpl;
import com.parvanpajooh.identitymanagement2.model.UserGroup;
import com.parvanpajooh.identitymanagement2.service.UserGroupLocalService;
import com.parvanpajooh.identitymanagement2.service.UserGroupService;

/**
 * 
 * @author ali
 *
 */
@Stateless
public class UserGroupServiceImpl extends GenericServiceImpl<UserGroup, Long> implements UserGroupService {

    /**
     * Log variable for all child classes. 
     */
	static final Logger log = LoggerFactory.getLogger( UserGroupServiceImpl.class);
    
	private UserGroupLocalService userGroupLocalService;
    
    @Inject
    public void setUserGroupLocalService(UserGroupLocalService userGroupLocalService) {
        this.localService = userGroupLocalService;
        this.userGroupLocalService = userGroupLocalService;
    }

}
