package com.parvanpajooh.issuemanager.dao;

import java.util.List;

import com.parvanpajooh.issuemanager.common.exceptions.ParvanDaoException;
import com.parvanpajooh.issuemanager.model.AggregatedHistory;
import com.parvanpajooh.issuemanager.model.TaskAttachment;
import com.parvanpajooh.issuemanager.model.TaskStatusHistory;

/**
 * 
 * @author 
 *
 */
public interface TaskStatusHistoryDao extends GenericDao<TaskStatusHistory, Long> {
	
	public List<TaskStatusHistory> findByTaskId(Long id) throws ParvanDaoException;
	
	public List<TaskStatusHistory> findByTaskIdAndCreateUserId(Long id,Long createUserId) throws ParvanDaoException;


}