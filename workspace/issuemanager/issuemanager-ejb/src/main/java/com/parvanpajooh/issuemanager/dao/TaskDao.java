package com.parvanpajooh.issuemanager.dao;

import java.util.List;

import com.parvanpajooh.issuemanager.common.exceptions.ParvanDaoException;
import com.parvanpajooh.issuemanager.model.Task;
import com.parvanpajooh.issuemanager.model.enums.EmailEnum;

/**
 * 
 * @author
 *
 */
public interface TaskDao extends GenericDao<Task, Long> {

	public Task saveTask(Task object, Long id) throws ParvanDaoException;
	
	public List<Task> findByEmailStatus(EmailEnum status) throws ParvanDaoException;

}