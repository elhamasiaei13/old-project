package com.parvanpajooh.issuemanager.service;

import java.util.List;

import com.parvanpajooh.commons.platform.ejb.model.UserInfo;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanServiceException;
import com.parvanpajooh.issuemanager.model.TaskMemberRelation;
import com.parvanpajooh.issuemanager.model.enums.TaskMemberRelationEnum;
import com.parvanpajooh.issuemanager.model.vo.TaskMemberRelationVO;

/**
 * 
 * @author
 * 
 */
public interface TaskMemberRelationService extends GenericService<TaskMemberRelation, Long> {

	public List<TaskMemberRelationVO> loadByTaskId(UserInfo userInfo, Long taskId,TaskMemberRelationEnum type) throws ParvanServiceException;
	
	public List<TaskMemberRelationVO> findByTaskIdAndMemberId(UserInfo userInfo, Long taskId,Long memberId,String type) throws ParvanServiceException;

}
