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
public interface TaskStatusHistoryService extends GenericService<TaskStatusHistory, Long> {

	public List<TaskStatusHistoryVO> loadByTaskId(UserInfo userInfo, Long taskId) throws ParvanServiceException;

	public void edit(UserInfo userInfo, TaskStatusHistoryVO taskStatusHistoryVO) throws ParvanServiceException;

	public TaskStatusHistoryVO addTaskStatus(UserInfo userInfo, TaskStatusHistoryVO taskStatusHistoryVO, Long taskId) throws ParvanServiceException;

}
