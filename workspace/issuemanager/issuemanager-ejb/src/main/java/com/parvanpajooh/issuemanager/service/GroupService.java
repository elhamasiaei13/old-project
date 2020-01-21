package com.parvanpajooh.issuemanager.service;


import com.parvanpajooh.commons.platform.ejb.model.UserInfo;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanServiceException;
import com.parvanpajooh.issuemanager.model.Group;

/**
 * 
 * @author 
 * 
 */
public interface GroupService extends GenericService<Group, Long> {

	public void delete(UserInfo userInfo, Long id) throws ParvanServiceException;

}
