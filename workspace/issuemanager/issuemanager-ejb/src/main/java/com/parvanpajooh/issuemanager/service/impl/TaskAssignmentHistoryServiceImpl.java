package com.parvanpajooh.issuemanager.service.impl;

import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.parvanpajooh.commons.platform.ejb.model.UserInfo;
import com.parvanpajooh.issuemanager.common.exceptions.ErrorCode;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanRecoverableException;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanServiceException;
import com.parvanpajooh.issuemanager.model.TaskAssignmentHistory;
import com.parvanpajooh.issuemanager.model.enums.RoleEnum;
import com.parvanpajooh.issuemanager.model.vo.TaskAssignmentHistoryVO;
import com.parvanpajooh.issuemanager.service.TaskAssignmentHistoryLocalService;
import com.parvanpajooh.issuemanager.service.TaskAssignmentHistoryService;

@Stateless
public class TaskAssignmentHistoryServiceImpl extends GenericServiceImpl<TaskAssignmentHistory, Long> implements TaskAssignmentHistoryService {
	/**
	 * Log variable for all child classes.
	 */
	static final Logger log = LoggerFactory.getLogger(TaskAssignmentHistoryServiceImpl.class);

	private TaskAssignmentHistoryLocalService taskAssignmentLocalService;

	@Inject
	public void setUserLocalService(TaskAssignmentHistoryLocalService taskAssignmentLocalService) {
		this.localService = taskAssignmentLocalService;
		this.taskAssignmentLocalService = taskAssignmentLocalService;
	}

	@Override
	public TaskAssignmentHistoryVO addTaskAssignment(UserInfo userInfo,TaskAssignmentHistoryVO taskAssignmentVO, Long taskId) throws ParvanServiceException{

		log.debug("Entering addTaskAssignment(userInfo={})", userInfo);
		return taskAssignmentLocalService.addTaskAssignment(userInfo, taskAssignmentVO, taskId);

	}

	@Override
	public List<TaskAssignmentHistoryVO> loadTaskAssignmentByTaskId(UserInfo userInfo, Long taskId) throws ParvanServiceException {
		log.debug("Entering loadTaskAssignmentByTaskId(userInfo={})", userInfo);
		return taskAssignmentLocalService.loadTaskAssignmentByTaskId(taskId);
	}

	@Override
	public void edit(UserInfo userInfo, TaskAssignmentHistoryVO taskAssignmentHistoryVO) throws ParvanServiceException {
		log.debug("Entering editTaskAssignment(userInfo={})", userInfo);
		// check access
		Set<String> userRoles = userInfo.getRoleNames();
		if (!(userRoles.contains(RoleEnum.ADMIN.value) && userRoles.contains(RoleEnum.MANAGER.value))) {
			throw new ParvanRecoverableException(ErrorCode.HAS_NOT_ACCESS);
		}
		
		taskAssignmentHistoryVO.setUpdateUserId(userInfo.getUserId());
		taskAssignmentLocalService.edit(taskAssignmentHistoryVO);
		
	}

	@Override
	public List<TaskAssignmentHistoryVO> loadTaskAssignmentByTaskIdAndUserId(UserInfo userInfo, Long taskId, Long createUserId) throws ParvanServiceException {
		log.debug("Entering loadTaskAssignmentByTaskIdAndUserId(userInfo={})", userInfo);
		return taskAssignmentLocalService.loadTaskAssignmentByTaskIdAndUserId(taskId,createUserId);
		
	}

}
