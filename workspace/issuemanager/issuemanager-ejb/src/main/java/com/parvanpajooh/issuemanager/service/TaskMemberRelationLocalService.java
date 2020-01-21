package com.parvanpajooh.issuemanager.service;

import java.util.List;

import com.parvanpajooh.issuemanager.common.exceptions.ParvanDaoException;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanServiceException;
import com.parvanpajooh.issuemanager.model.TaskMemberRelation;
import com.parvanpajooh.issuemanager.model.enums.TaskMemberRelationEnum;
import com.parvanpajooh.issuemanager.model.vo.TaskMemberRelationVO;

/**
 * 
 * @author
 * 
 */
public interface TaskMemberRelationLocalService extends GenericLocalService<TaskMemberRelation, Long> {

	public List<TaskMemberRelationVO> loadByTaskId(Long taskId,TaskMemberRelationEnum type) throws ParvanServiceException;
	
	public List<TaskMemberRelationVO> findByTaskIdAndMemberId(Long taskId,Long memberId,String type) throws ParvanServiceException;
	
	public List<TaskMemberRelation> findByTaskIdAndType(Long taskId,TaskMemberRelationEnum type) throws ParvanServiceException;

}
