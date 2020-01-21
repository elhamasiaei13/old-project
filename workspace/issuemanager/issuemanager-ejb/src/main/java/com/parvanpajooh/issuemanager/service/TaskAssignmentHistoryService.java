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
public interface TaskAssignmentHistoryService extends GenericService<TaskAssignmentHistory, Long> {

	public TaskAssignmentHistoryVO addTaskAssignment(UserInfo userInfo, TaskAssignmentHistoryVO taskAssignmentVO, Long taskId)
			throws ParvanServiceException;

	public List<TaskAssignmentHistoryVO> loadTaskAssignmentByTaskId(UserInfo userInfo, Long taskId) throws ParvanServiceException;
	
	public List<TaskAssignmentHistoryVO> loadTaskAssignmentByTaskIdAndUserId(UserInfo userInfo, Long taskId,Long createUserId) throws ParvanServiceException;

	public void edit(UserInfo userInfo ,TaskAssignmentHistoryVO taskAssignmentHistoryVO) throws ParvanServiceException;

}
