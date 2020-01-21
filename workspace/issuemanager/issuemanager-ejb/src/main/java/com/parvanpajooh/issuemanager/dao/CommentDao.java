package com.parvanpajooh.issuemanager.dao;

import java.util.List;

import com.parvanpajooh.issuemanager.common.exceptions.ParvanDaoException;
import com.parvanpajooh.issuemanager.dao.GenericDao;
import com.parvanpajooh.issuemanager.model.Comment;
import com.parvanpajooh.issuemanager.model.Relation;

/**
 * 
 * @author ali
 * 
 */
public interface CommentDao extends GenericDao<Comment, Long> {

	public List<Comment> findBytaskId(Long id) throws ParvanDaoException;
	
	public List<Comment> findByTaskIdAndCreateUserId(Long id, Long createUserId) throws ParvanDaoException;

	
}