package com.parvanpajooh.issuemanager.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.parvanpajooh.commons.platform.ejb.model.UserInfo;
import com.parvanpajooh.commons.util.Validator;
import com.parvanpajooh.issuemanager.common.exceptions.ErrorCode;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanRecoverableException;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanServiceException;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanUnrecoverableException;
import com.parvanpajooh.issuemanager.dao.TaskAttachmentDao;
import com.parvanpajooh.issuemanager.dao.TaskDao;
import com.parvanpajooh.issuemanager.model.Task;
import com.parvanpajooh.issuemanager.model.TaskAttachment;
import com.parvanpajooh.issuemanager.model.vo.TaskAttachmentVO;
import com.parvanpajooh.issuemanager.service.TaskAttachmentLocalService;


/**
 * 
 * @author
 */
@Stateless
public class TaskAttachmentLocalServiceImpl extends GenericLocalServiceImpl<TaskAttachment, Long>implements TaskAttachmentLocalService {
	/**
	 * Log variable for all child classes.
	 */
	static final Logger log = LoggerFactory.getLogger(TaskStatusHistoryLocalServiceImpl.class);

	public TaskAttachmentDao taskAttachmentDao;
	public TaskDao taskDao;

	@Inject
	public void setTaskAttachmentDao(TaskAttachmentDao taskAttachmentDao) {
		this.dao = taskAttachmentDao;
		this.taskAttachmentDao = taskAttachmentDao;
	}
	
	@Inject
	public void setTaskADao(TaskDao taskDao) {
		this.taskDao = taskDao;
	}

	public List<TaskAttachmentVO> loadByTaskId(Long taskId) throws ParvanServiceException {

		List<TaskAttachmentVO> listVO = null;

		try {

			// find list
			List<TaskAttachment> taskAttachments = taskAttachmentDao.findByTaskId(taskId);

			if (Validator.isNotNull(taskAttachments)) {
				listVO = new ArrayList<TaskAttachmentVO>(taskAttachments.size());
				for (TaskAttachment baseObject : taskAttachments) {

					TaskAttachmentVO objectVO = baseObject.toVOLite();

					listVO.add(objectVO);
				}
			}

		} catch (Exception e) {
			log.error("error occurred while loadTaskAssignmentByTaskId taskAssignment", e);
			throw new ParvanUnrecoverableException("error occurred while loadTaskAssignmentByTaskId taskAssignment", e);
		}

		return listVO;
	}

	@Override
	public void delete(UserInfo userInfo,Long id) throws ParvanServiceException {
		try {

			TaskAttachment taskAttachment = new TaskAttachment();
			LocalDateTime now = LocalDateTime.now();
			
			taskAttachment = taskAttachmentDao.get(id);
			taskAttachment.setActive(false);
			taskAttachment.setUpdateDate(now);	
			taskAttachment.setUpdateUserId(userInfo.getUserId());
			
			
			// refresh entity
			taskAttachmentDao.refresh(taskAttachment);

		} catch (Exception e) {
			throw new ParvanUnrecoverableException("Error occurred while deleting attachment.", e);
		}
		
	}
	
	@Override
	public TaskAttachmentVO addTaskAttachment(UserInfo userInfo, TaskAttachmentVO taskAttachmentVO, Long taskId) throws ParvanServiceException {
		try {

			if (Validator.isNull(taskAttachmentVO.getName())) {
				log.debug("taskAttachment doesn't have name.");
				throw new ParvanRecoverableException(ErrorCode.FEILDS_IS_EMPTY);
			}

			TaskAttachment taskAttachment = null;

			taskAttachment = new TaskAttachment();
			taskAttachment.fromVO(taskAttachmentVO);

			Task task= taskDao.get(taskId);
			taskAttachment.setTask(task);
			taskAttachment.setActive(true);

			LocalDateTime now = LocalDateTime.now();

			taskAttachment.setCreateDate(now);
			taskAttachment.setUpdateDate(now);
			taskAttachment.setCreateUserId(userInfo.getUserId());
			taskAttachment.setUpdateUserId(userInfo.getUserId());

			// save entity
			taskAttachment = taskAttachmentDao.save(taskAttachment);
			taskAttachmentVO = (TaskAttachmentVO) taskAttachment.toVO();

		} catch (ParvanServiceException e) {
			throw e;

		} catch (Exception e) {
			throw new ParvanUnrecoverableException("Error occurred while saving object.", e);
		}

		return taskAttachmentVO;

	}
}
