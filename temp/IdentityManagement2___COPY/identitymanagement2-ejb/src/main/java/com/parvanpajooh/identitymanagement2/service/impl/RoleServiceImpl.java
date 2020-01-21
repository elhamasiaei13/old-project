package com.parvanpajooh.identitymanagement2.service.impl;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.parvanpajooh.ecourier.service.impl.GenericServiceImpl;
import com.parvanpajooh.identitymanagement2.model.Role;
import com.parvanpajooh.identitymanagement2.service.RoleLocalService;
import com.parvanpajooh.identitymanagement2.service.RoleService;

/**
 * 
 * @author ali
 *
 */
@Stateless
public class RoleServiceImpl extends GenericServiceImpl<Role, Long> implements RoleService {

    /**
     * Log variable for all child classes. 
     */
	static final Logger log = LoggerFactory.getLogger( RoleServiceImpl.class);
    
	private RoleLocalService roleLocalService;
    
    @Inject
    public void setRoleLocalService(RoleLocalService roleLocalService) {
        this.localService = roleLocalService;
        this.roleLocalService = roleLocalService;
    }
}
