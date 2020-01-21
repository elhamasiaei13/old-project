package com.parvanpajooh.issuemanager.dao;

import java.util.List;

import com.parvanpajooh.issuemanager.common.exceptions.ParvanDaoException;
import com.parvanpajooh.issuemanager.dao.GenericDao;
import com.parvanpajooh.issuemanager.model.Comment;
import com.parvanpajooh.issuemanager.model.Relation;
import com.parvanpajooh.issuemanager.model.TaskStatusHistory;

/**
 * 
 * @author
 * 
 */
public interface RelationDao extends GenericDao<Relation, Long> {

	public List<Relation> findBytaskId(Long id) throws ParvanDaoException;

	public List<Relation> findByTaskIdAndCreateUserId(Long id, Long createUserId) throws ParvanDaoException;

}