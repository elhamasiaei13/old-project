package com.parvanpajooh.identitymanagement2.service.impl;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.parvanpajooh.ecourier.service.impl.GenericLocalServiceImpl;
import com.parvanpajooh.identitymanagement2.dao.UserGroupDao;
import com.parvanpajooh.identitymanagement2.model.UserGroup;
import com.parvanpajooh.identitymanagement2.service.UserGroupLocalService;

/**
 * 
 * @author ali
 * @author mehrdad
 *
 */
@Stateless
public class UserGroupLocalServiceImpl extends GenericLocalServiceImpl<UserGroup, Long> implements UserGroupLocalService {

	
    /**
     * Log variable for all child classes. 
     */
    static final Logger log = LoggerFactory.getLogger( UserLocalServiceImpl.class);
	
    public UserGroupDao userGroupDao;
    
    @Inject
    public void setUserGrouopDao(UserGroupDao userGroupDao) {
        this.dao = userGroupDao;
        this.userGroupDao = userGroupDao;
    }
}
