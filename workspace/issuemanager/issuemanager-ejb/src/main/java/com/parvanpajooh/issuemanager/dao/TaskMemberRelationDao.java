package com.parvanpajooh.issuemanager.dao;

import java.util.List;

import com.parvanpajooh.issuemanager.common.exceptions.ParvanDaoException;
import com.parvanpajooh.issuemanager.model.TaskMemberRelation;
import com.parvanpajooh.issuemanager.model.enums.TaskMemberRelationEnum;

/**
 * 
 * @author 
 *
 */
public interface TaskMemberRelationDao extends GenericDao<TaskMemberRelation, Long> {
	
	public List<TaskMemberRelation> findByTaskIdAndType(Long taskId,TaskMemberRelationEnum type) throws ParvanDaoException;
	
	public List<TaskMemberRelation> findByTaskIdAndMemberId(Long taskId,Long memberId,String type) throws ParvanDaoException;

}