package com.parvanpajooh.issuemanager.service;

import java.util.List;

import javax.mail.MessagingException;

import com.parvanpajooh.issuemanager.common.exceptions.ParvanDaoException;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanServiceException;
import com.parvanpajooh.issuemanager.model.Task;
import com.parvanpajooh.issuemanager.model.enums.EmailEnum;

/**
 * 
 * @author
 * 
 */
public interface TaskLocalService extends GenericLocalService<Task, Long> {


	public void delete(Long id) throws ParvanServiceException;
	
	public void sendEmail(String[] mailList,String subject,String body,String mailServerUserName, String mailServerPassword, String mailServerHost,String mailServerPort ,
			String mailIpAddress,String mailStarttls,String mailAuth,String mailRealm) throws ParvanServiceException,MessagingException;
	
	public List<Task> findByEmailStatus(EmailEnum status) throws ParvanServiceException;

}
