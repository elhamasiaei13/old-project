package com.parvanpajooh.issuemanager.dao;

import java.util.List;

import com.parvanpajooh.issuemanager.common.exceptions.ParvanDaoException;
import com.parvanpajooh.issuemanager.model.TaskAssignmentHistory;
import com.parvanpajooh.issuemanager.model.TaskAttachment;

/**
 * 
 * @author 
 *
 */
public interface TaskAttachmentDao extends GenericDao<TaskAttachment, Long> {
	
	public List<TaskAttachment> findByTaskId(Long id) throws ParvanDaoException;
	
	public List<TaskAttachment> findByTaskIdAndCreateUserId(Long id,Long createUserId) throws ParvanDaoException;

}