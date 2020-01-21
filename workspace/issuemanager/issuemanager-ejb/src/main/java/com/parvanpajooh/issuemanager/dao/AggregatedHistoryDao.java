package com.parvanpajooh.issuemanager.dao;

import java.util.List;

import com.parvanpajooh.issuemanager.common.exceptions.ParvanDaoException;
import com.parvanpajooh.issuemanager.dao.GenericDao;
import com.parvanpajooh.issuemanager.model.AggregatedHistory;
import com.parvanpajooh.issuemanager.model.Relation;
import com.parvanpajooh.issuemanager.model.TaskAssignmentHistory;

/**
 * 
 * 
 * 
 */
public interface AggregatedHistoryDao extends GenericDao<AggregatedHistory, Long> {

	public List<AggregatedHistory> findByTaskId(Long id) throws ParvanDaoException;

	public AggregatedHistory getByTaskStatusId(Long id) throws ParvanDaoException;

	public AggregatedHistory getByCommentId(Long id) throws ParvanDaoException;

	public AggregatedHistory getByTaskAssignmentId(Long id) throws ParvanDaoException;

	public AggregatedHistory getByTaskAttachmentId(Long id) throws ParvanDaoException;
	
	public AggregatedHistory getByRelationId(Long id) throws ParvanDaoException;
}