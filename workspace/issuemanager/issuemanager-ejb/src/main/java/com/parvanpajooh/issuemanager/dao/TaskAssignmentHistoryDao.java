package com.parvanpajooh.issuemanager.dao;

import java.util.List;

import com.parvanpajooh.issuemanager.common.exceptions.ParvanDaoException;
import com.parvanpajooh.issuemanager.dao.GenericDao;
import com.parvanpajooh.issuemanager.model.TaskAssignmentHistory;


/**
 * 
 * @author 
 *
 */
public interface TaskAssignmentHistoryDao extends GenericDao<TaskAssignmentHistory, Long> {

	
	public List<TaskAssignmentHistory> findByTaskId(Long id) throws ParvanDaoException;
	
	public List<TaskAssignmentHistory> findByTaskIdAndCreateUserId(Long id,Long createUserId) throws ParvanDaoException;

}