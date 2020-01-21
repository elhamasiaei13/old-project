package com.parvanpajooh.issuemanager.service;

import com.parvanpajooh.commons.platform.ejb.model.UserInfo;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanServiceException;
import com.parvanpajooh.issuemanager.model.Task;

/**
 * 
 * @author
 * 
 */
public interface TaskService extends GenericService<Task, Long> {


	public void delete(UserInfo userInfo, Long id) throws ParvanServiceException;
	
	public void sendEmail(String mailServerUserName, String mailServerPassword, String mailServerHost,String mailServerPort ,
			String mailIpAddress,String mailStarttls,String mailAuth,String mailRealm,String mailDefaultAssignee) throws ParvanServiceException;
	
}
