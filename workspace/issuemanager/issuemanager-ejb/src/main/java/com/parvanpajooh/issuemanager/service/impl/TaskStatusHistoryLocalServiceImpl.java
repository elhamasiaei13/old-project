package com.parvanpajooh.issuemanager.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
import com.parvanpajooh.issuemanager.dao.AggregatedHistoryDao;
import com.parvanpajooh.issuemanager.dao.TaskDao;
import com.parvanpajooh.issuemanager.dao.TaskStatusHistoryDao;
import com.parvanpajooh.issuemanager.model.Task;
import com.parvanpajooh.issuemanager.model.TaskStatusHistory;
import com.parvanpajooh.issuemanager.model.enums.RoleEnum;
import com.parvanpajooh.issuemanager.model.vo.TaskStatusHistoryVO;
import com.parvanpajooh.issuemanager.service.TaskStatusHistoryLocalService;

/**
 * 
 * @author
 */
@Stateless
public class TaskStatusHistoryLocalServiceImpl extends GenericLocalServiceImpl<TaskStatusHistory, Long>implements TaskStatusHistoryLocalService {
	/**
	 * Log variable for all child classes.
	 */
	static final Logger log = LoggerFactory.getLogger(TaskStatusHistoryLocalServiceImpl.class);

	public TaskStatusHistoryDao taskStatusHistoryDao;
	public AggregatedHistoryDao aggregatedHistoryDao;
	public TaskDao taskDao;

	@Inject
	public void setTaskStatusHistoryDao(TaskStatusHistoryDao taskStatusHistoryDao) {
		this.dao = taskStatusHistoryDao;
		this.taskStatusHistoryDao = taskStatusHistoryDao;
	}

	@Inject
	public void setTaskDao(TaskDao taskDao) {
		this.taskDao = taskDao;
	}

	@Inject
	public void setAggregatedHistoryDao(AggregatedHistoryDao aggregatedHistoryDao) {
		this.aggregatedHistoryDao = aggregatedHistoryDao;
	}

	@Override
	public List<TaskStatusHistoryVO> loadByTaskId(Long taskId) throws ParvanServiceException {

		List<TaskStatusHistoryVO> listVO = null;

		try {

			// find list
			List<TaskStatusHistory> taskStatusHistory = taskStatusHistoryDao.findByTaskId(taskId);

			if (Validator.isNotNull(taskStatusHistory)) {
				listVO = new ArrayList<TaskStatusHistoryVO>(taskStatusHistory.size());
				for (TaskStatusHistory baseObject : taskStatusHistory) {

					TaskStatusHistoryVO objectVO = baseObject.toVOLite();

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
	public void edit(TaskStatusHistoryVO taskStatusHistoryVO) throws ParvanServiceException {
		try {


			TaskStatusHistory taskStatusHistory = new TaskStatusHistory();
			LocalDateTime now = LocalDateTime.now();

			taskStatusHistory = taskStatusHistoryDao.get(taskStatusHistoryVO.getId());
			taskStatusHistory.setUpdateDate(now);
			taskStatusHistory.setComment(taskStatusHistoryVO.getComment());

			// refresh entity
			taskStatusHistoryDao.refresh(taskStatusHistory);

		} catch (Exception e) {
			throw new ParvanUnrecoverableException("Error occurred while refreshing comment.", e);
		}

	}

	@Override
	public TaskStatusHistoryVO addTaskStatus(UserInfo userInfo, TaskStatusHistoryVO taskStatusHistoryVO, Long taskId) throws ParvanServiceException {

		try {
			// check access
			Set<String> userRoles = userInfo.getRoleNames();
			if (!(userRoles.contains(RoleEnum.ADMIN.value) && userRoles.contains(RoleEnum.MANAGER.value))) {
				throw new ParvanRecoverableException(ErrorCode.HAS_NOT_ACCESS);
			}

			if (Validator.isNull(taskStatusHistoryVO.getToStatus())) {
				log.debug("taskStatusHistory doesn't have status.");
				throw new ParvanRecoverableException(ErrorCode.FEILDS_IS_EMPTY);
			}

			TaskStatusHistory taskStatusHistory = null;

			if (Validator.isNull(taskStatusHistoryVO.getId())) {
				taskStatusHistory = new TaskStatusHistory();
				taskStatusHistory.fromVO(taskStatusHistoryVO);
			} else {
				taskStatusHistory = taskStatusHistoryDao.get(taskStatusHistoryVO.getId());
				taskStatusHistory.fromVO(taskStatusHistoryVO);
			}

			Task task = taskDao.get(taskId);
			taskStatusHistory.setTask(task);

			LocalDateTime now = LocalDateTime.now();

			taskStatusHistory.setCreateDate(now);
			taskStatusHistory.setUpdateDate(now);
			taskStatusHistory.setCreateUserId(userInfo.getUserId());
			taskStatusHistory.setUpdateUserId(userInfo.getUserId());

			// save entity
			taskStatusHistory = taskStatusHistoryDao.save(taskStatusHistory);

			taskStatusHistoryVO = (TaskStatusHistoryVO) taskStatusHistory.toVO();

		} catch (ParvanServiceException e) {
			throw e;

		} catch (Exception e) {
			throw new ParvanUnrecoverableException("Error occurred while saving object.", e);
		}

		return taskStatusHistoryVO;
	}

}
