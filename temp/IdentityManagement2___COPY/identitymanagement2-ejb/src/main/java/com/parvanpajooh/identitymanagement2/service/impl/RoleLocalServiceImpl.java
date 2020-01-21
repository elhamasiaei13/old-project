package com.parvanpajooh.identitymanagement2.service.impl;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.parvanpajooh.ecourier.service.impl.GenericLocalServiceImpl;
import com.parvanpajooh.identitymanagement2.dao.RoleDao;
import com.parvanpajooh.identitymanagement2.model.Role;
import com.parvanpajooh.identitymanagement2.service.RoleLocalService;

/**
 * 
 * @author ali
 * @author mehrdad
 *
 */
@Stateless
public class RoleLocalServiceImpl extends GenericLocalServiceImpl<Role, Long> implements RoleLocalService {


    /**
     * Log variable for all child classes. 
     */
    static final Logger log = LoggerFactory.getLogger( RoleLocalServiceImpl.class);
	
    public RoleDao roleDao;
    
    @Inject
    public void setRoleDao(RoleDao roleDao) {
        this.dao = roleDao;
        this.roleDao = roleDao;
    }
	
   
}
