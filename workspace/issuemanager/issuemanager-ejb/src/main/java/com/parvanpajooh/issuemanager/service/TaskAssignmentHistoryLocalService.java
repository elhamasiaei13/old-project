package com.parvanpajooh.issuemanager.service;

import java.util.List;

import com.parvanpajooh.commons.platform.ejb.model.UserInfo;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanServiceException;
import com.parvanpajooh.issuemanager.model.TaskAssignmentHistory;
import com.parvanpajooh.issuemanager.model.vo.TaskAssignmentHistoryVO;

/**
 * 
 * @author
 * 
 */
public interface TaskAssignmentHistoryLocalService extends GenericLocalService<TaskAssignmentHistory, Long> {

	public TaskAssignmentHistoryVO addTaskAssignment(UserInfo userInfo, TaskAssignmentHistoryVO taskAssignmentVO, Long taskId)
			throws ParvanServiceException;

	public List<TaskAssignmentHistoryVO> loadTaskAssignmentByTaskId(Long taskId) throws ParvanServiceException;
	
	public List<TaskAssignmentHistoryVO> loadTaskAssignmentByTaskIdAndUserId(Long taskId,Long creatUserId) throws ParvanServiceException;

	public void edit(TaskAssignmentHistoryVO taskAssignmentHistoryVO) throws ParvanServiceException;
}
