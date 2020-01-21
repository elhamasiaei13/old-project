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
import com.parvanpajooh.issuemanager.model.TaskAttachment;
import com.parvanpajooh.issuemanager.model.enums.RoleEnum;
import com.parvanpajooh.issuemanager.model.vo.TaskAttachmentVO;
import com.parvanpajooh.issuemanager.service.TaskAttachmentLocalService;
import com.parvanpajooh.issuemanager.service.TaskAttachmentService;

@Stateless
public class TaskAttachmentServiceImpl extends GenericServiceImpl<TaskAttachment, Long>implements TaskAttachmentService {
	/**
	 * Log variable for all child classes.
	 */
	static final Logger log = LoggerFactory.getLogger(TaskAttachmentServiceImpl.class);

	private TaskAttachmentLocalService taskAttachmentLocalService;

	@Inject
	public void setUserLocalService(TaskAttachmentLocalService taskAttachmentLocalService) {
		this.localService = taskAttachmentLocalService;
		this.taskAttachmentLocalService = taskAttachmentLocalService;
	}

	@Override
	public List<TaskAttachmentVO> loadByTaskId(UserInfo userInfo, Long taskId) throws ParvanServiceException {
		log.debug("Entering loadTaskAssignmentByTaskId(userInfo={})", userInfo);
		return taskAttachmentLocalService.loadByTaskId(taskId);
	}
	
	@Override
	public TaskAttachmentVO addTaskAttachment(UserInfo userInfo,TaskAttachmentVO taskAttachmentVO, Long taskId) throws ParvanServiceException{

		log.debug("Entering addTaskAttachment(userInfo={})", userInfo);
		return taskAttachmentLocalService.addTaskAttachment(userInfo, taskAttachmentVO, taskId);

	}
	
	@Override
	public void delete(UserInfo userInfo, Long id) throws ParvanServiceException {
		log.debug("Entering deleteAttachment(userInfo={})", userInfo);	
		// check access
		Set<String> userRoles = userInfo.getRoleNames();
		if (!(userRoles.contains(RoleEnum.ADMIN.value) && userRoles.contains(RoleEnum.MANAGER.value))) {					
			throw new ParvanRecoverableException(ErrorCode.HAS_NOT_ACCESS);
		}
		taskAttachmentLocalService.delete(userInfo,id);
	}

}
