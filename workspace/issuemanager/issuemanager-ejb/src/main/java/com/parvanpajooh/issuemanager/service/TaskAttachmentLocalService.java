package com.parvanpajooh.issuemanager.service;

import java.util.List;

import com.parvanpajooh.commons.platform.ejb.model.UserInfo;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanServiceException;
import com.parvanpajooh.issuemanager.model.TaskAttachment;
import com.parvanpajooh.issuemanager.model.vo.TaskAttachmentVO;

/**
 * 
 * @author
 * 
 */
public interface TaskAttachmentLocalService extends GenericLocalService<TaskAttachment, Long> {

	public List<TaskAttachmentVO> loadByTaskId(Long taskId) throws ParvanServiceException;

	public void delete(UserInfo userInfo,Long id) throws ParvanServiceException;
	
	public TaskAttachmentVO addTaskAttachment(UserInfo userInfo, TaskAttachmentVO taskAttachmentVO, Long taskId) throws ParvanServiceException;
}
