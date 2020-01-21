package com.parvanpajooh.issuemanager.service;

import java.util.List;

import com.parvanpajooh.commons.platform.ejb.model.UserInfo;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanServiceException;
import com.parvanpajooh.issuemanager.model.TaskStatusHistory;
import com.parvanpajooh.issuemanager.model.vo.TaskStatusHistoryVO;

/**
 * 
 * @author
 * 
 */
public interface TaskStatusHistoryLocalService extends GenericLocalService<TaskStatusHistory, Long> {

	public List<TaskStatusHistoryVO> loadByTaskId(Long taskId) throws ParvanServiceException;
	
	public void edit(TaskStatusHistoryVO taskStatusHistoryVO) throws ParvanServiceException;
	
	public TaskStatusHistoryVO addTaskStatus(UserInfo userInfo, TaskStatusHistoryVO taskStatusHistoryVO, Long taskId) throws ParvanServiceException;

}
