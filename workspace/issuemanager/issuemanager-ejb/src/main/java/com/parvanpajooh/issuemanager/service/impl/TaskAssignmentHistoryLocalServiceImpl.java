package com.parvanpajooh.issuemanager.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
import com.parvanpajooh.issuemanager.dao.MemberDao;
import com.parvanpajooh.issuemanager.dao.TaskAssignmentHistoryDao;
import com.parvanpajooh.issuemanager.dao.TaskDao;
import com.parvanpajooh.issuemanager.model.Member;
import com.parvanpajooh.issuemanager.model.Task;
import com.parvanpajooh.issuemanager.model.TaskAssignmentHistory;
import com.parvanpajooh.issuemanager.model.enums.RoleEnum;
import com.parvanpajooh.issuemanager.model.vo.TaskAssignmentHistoryVO;
import com.parvanpajooh.issuemanager.service.TaskAssignmentHistoryLocalService;

/**
 * 
 * @author
 */
@Stateless
public class TaskAssignmentHistoryLocalServiceImpl extends GenericLocalServiceImpl<TaskAssignmentHistory, Long>implements TaskAssignmentHistoryLocalService {
	/**
	 * Log variable for all child classes.
	 */
	static final Logger log = LoggerFactory.getLogger(TaskAssignmentHistoryLocalServiceImpl.class);

	public TaskAssignmentHistoryDao taskAssignmentDao;
	public MemberDao memberDao;
	public TaskDao taskDao;

	@Inject
	public void setTaskAssignmentHistoryDao(TaskAssignmentHistoryDao taskAssignmentDao) {
		this.dao = taskAssignmentDao;
		this.taskAssignmentDao = taskAssignmentDao;
	}

	@Inject
	public void setMemberDao(MemberDao memberDao) {

		this.memberDao = memberDao;
	}

	@Inject
	public void setTypeDao(TaskDao taskDao) {

		this.taskDao = taskDao;
	}

	@Override
	public TaskAssignmentHistoryVO addTaskAssignment(UserInfo userInfo, TaskAssignmentHistoryVO taskAssignmentVO, Long taskId) throws ParvanServiceException {

		try {

			// check access
			Set<String> userRoles = userInfo.getRoleNames();
			if (!(userRoles.contains(RoleEnum.ADMIN.value) && userRoles.contains(RoleEnum.MANAGER.value))) {
				throw new ParvanRecoverableException(ErrorCode.HAS_NOT_ACCESS);
			}

//			if (Validator.isNull(taskAssignmentVO.getMemberTo())) {
//				log.debug("taskAssignment doesn't have member.");
//				throw new ParvanRecoverableException(ErrorCode.FEILDS_IS_EMPTY);
//			}

			
			TaskAssignmentHistory taskAssignment = null;
			Task task = new Task();
			Member member = new Member();

			if (Validator.isNull(taskAssignmentVO.getId())) {
				taskAssignment = new TaskAssignmentHistory();
				taskAssignment.fromVO(taskAssignmentVO);
			} else {
				taskAssignment = taskAssignmentDao.get(taskAssignmentVO.getId());
				taskAssignment.fromVO(taskAssignmentVO);
			}
			
			if(Validator.isNotNull(taskAssignmentVO.getMemberTo())){
				Long memberId = taskAssignmentVO.getMemberTo().getId();
				member = memberDao.get(memberId);
				taskAssignment.setMemberTo(member);
			}
			
			task = taskDao.get(taskId);
			taskAssignment.setTask(task);

			LocalDateTime now = LocalDateTime.now();

			taskAssignment.setCreateDate(now);
			taskAssignment.setUpdateDate(now);
			taskAssignment.setCreateUserId(userInfo.getUserId());
			taskAssignment.setUpdateUserId(userInfo.getUserId());
			taskAssignment.setMemberFrom(task.getCurrentMember());

			// save TaskAssignment
			taskAssignment = taskAssignmentDao.save(taskAssignment);

			taskAssignmentVO = (TaskAssignmentHistoryVO) taskAssignment.toVO();

		} catch (ParvanServiceException e) {
			throw e;

		} catch (Exception e) {
			throw new ParvanUnrecoverableException("Error occurred while saving object.", e);
		}

		return taskAssignmentVO;
	}

	/**
	 * 
	 * @param tasks
	 * @return
	 */
	private List<TaskAssignmentHistoryVO> convertToTree(List<TaskAssignmentHistory> tasks) {

		List<TaskAssignmentHistoryVO> result = new ArrayList<TaskAssignmentHistoryVO>();
		Map<Long, TaskAssignmentHistoryVO> map = new HashMap<Long, TaskAssignmentHistoryVO>();

		Iterator<TaskAssignmentHistory> iterator = tasks.iterator();
		while (iterator.hasNext()) {
			TaskAssignmentHistory thisTaskAssignment = iterator.next();

			TaskAssignmentHistoryVO taskAssignmentVO = thisTaskAssignment.toVOLite();

			result.add(taskAssignmentVO);

			map.put(thisTaskAssignment.getId(), taskAssignmentVO);
			iterator.remove();
		}

		return result;
	}

	@Override
	public List<TaskAssignmentHistoryVO> loadTaskAssignmentByTaskId(Long taskId) throws ParvanServiceException {

		List<TaskAssignmentHistoryVO> listVO = null;

		try {

			// find list
			List<TaskAssignmentHistory> taskAssignment = taskAssignmentDao.findByTaskId(taskId);
			if (Validator.isNotNull(taskAssignment)) {
				listVO = new ArrayList<TaskAssignmentHistoryVO>(taskAssignment.size());
				for (TaskAssignmentHistory baseObject : taskAssignment) {

					TaskAssignmentHistoryVO objectVO = baseObject.toVOLite();

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
	public void edit(TaskAssignmentHistoryVO taskAssignmentHistoryVO) throws ParvanServiceException {
		try {

			TaskAssignmentHistory taskAssignmentHistory = new TaskAssignmentHistory();
			LocalDateTime now = LocalDateTime.now();

			taskAssignmentHistory = taskAssignmentDao.get(taskAssignmentHistoryVO.getId());
			taskAssignmentHistory.setUpdateDate(now);
			taskAssignmentHistory.setComment(taskAssignmentHistoryVO.getComment());

			// refresh entity
			taskAssignmentDao.refresh(taskAssignmentHistory);

		} catch (Exception e) {
			throw new ParvanUnrecoverableException("Error occurred while refreshing comment.", e);
		}

	}

	@Override
	public List<TaskAssignmentHistoryVO> loadTaskAssignmentByTaskIdAndUserId(Long taskId, Long creatUserId) throws ParvanServiceException {
		List<TaskAssignmentHistoryVO> listVO = null;

		try {

			// find list
			List<TaskAssignmentHistory> taskAssignment = taskAssignmentDao.findByTaskIdAndCreateUserId(taskId, creatUserId);
			if (Validator.isNotNull(taskAssignment)) {
				listVO = new ArrayList<TaskAssignmentHistoryVO>(taskAssignment.size());
				for (TaskAssignmentHistory baseObject : taskAssignment) {

					TaskAssignmentHistoryVO objectVO = baseObject.toVOLite();

					listVO.add(objectVO);
				}
			}
		} catch (Exception e) {
			log.error("error occurred while loadTaskAssignmentByTaskIdAndUserId taskAssignment", e);
			throw new ParvanUnrecoverableException("error occurred while loadTaskAssignmentByTaskIdAndUserId taskAssignment", e);
		}

		return listVO;

	}
}
