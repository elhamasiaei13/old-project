package com.parvanpajooh.issuemanager.service.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.parvanpajooh.commons.platform.ejb.model.UserInfo;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanServiceException;
import com.parvanpajooh.issuemanager.model.TaskMemberRelation;
import com.parvanpajooh.issuemanager.model.enums.TaskMemberRelationEnum;
import com.parvanpajooh.issuemanager.model.vo.TaskMemberRelationVO;
import com.parvanpajooh.issuemanager.service.TaskMemberRelationLocalService;
import com.parvanpajooh.issuemanager.service.TaskMemberRelationService;

@Stateless
public class TaskMemberRelationServiceImpl extends GenericServiceImpl<TaskMemberRelation, Long>implements TaskMemberRelationService {
	/**
	 * Log variable for all child classes.
	 */
	static final Logger log = LoggerFactory.getLogger(TaskMemberRelationServiceImpl.class);

	private TaskMemberRelationLocalService taskMemberRelationLocalService;

	@Inject
	public void setUserLocalService(TaskMemberRelationLocalService taskMemberRelationLocalService) {
		this.localService = taskMemberRelationLocalService;
		this.taskMemberRelationLocalService = taskMemberRelationLocalService;
	}

	@Override
	public List<TaskMemberRelationVO> loadByTaskId(UserInfo userInfo, Long taskId,TaskMemberRelationEnum type) throws ParvanServiceException {
		log.debug("Entering loadTaskAssignmentByTaskId(userInfo={})", userInfo);
		return taskMemberRelationLocalService.loadByTaskId(taskId,type);
	}

	@Override
	public List<TaskMemberRelationVO> findByTaskIdAndMemberId(UserInfo userInfo, Long taskId, Long memberId, String type) throws ParvanServiceException {
		log.debug("Entering loadTaskAssignmentByTaskId(userInfo={})", userInfo);
		return taskMemberRelationLocalService.findByTaskIdAndMemberId(taskId,memberId,type);		
	}

}
