package com.parvanpajooh.issuemanager.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.parvanpajooh.commons.platform.ejb.model.UserInfo;
import com.parvanpajooh.commons.platform.ejb.model.vo.BaseVO;
import com.parvanpajooh.commons.util.Validator;
import com.parvanpajooh.issuemanager.common.exceptions.ErrorCode;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanDaoException;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanRecoverableException;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanServiceException;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanUnrecoverableException;
import com.parvanpajooh.issuemanager.common.exceptions.SendMail;
import com.parvanpajooh.issuemanager.dao.CommentDao;
import com.parvanpajooh.issuemanager.dao.GroupDao;
import com.parvanpajooh.issuemanager.dao.MemberDao;
import com.parvanpajooh.issuemanager.dao.RelationDao;
import com.parvanpajooh.issuemanager.dao.TaskAssignmentHistoryDao;
import com.parvanpajooh.issuemanager.dao.TaskAttachmentDao;
import com.parvanpajooh.issuemanager.dao.TaskDao;
import com.parvanpajooh.issuemanager.dao.TaskMemberRelationDao;
import com.parvanpajooh.issuemanager.dao.TaskStatusHistoryDao;
import com.parvanpajooh.issuemanager.model.Comment;
import com.parvanpajooh.issuemanager.model.Group;
import com.parvanpajooh.issuemanager.model.Member;
import com.parvanpajooh.issuemanager.model.Relation;
import com.parvanpajooh.issuemanager.model.Task;
import com.parvanpajooh.issuemanager.model.TaskAssignmentHistory;
import com.parvanpajooh.issuemanager.model.TaskAttachment;
import com.parvanpajooh.issuemanager.model.TaskMemberRelation;
import com.parvanpajooh.issuemanager.model.TaskStatusHistory;
import com.parvanpajooh.issuemanager.model.enums.EmailEnum;
import com.parvanpajooh.issuemanager.model.enums.RoleEnum;
import com.parvanpajooh.issuemanager.model.enums.TaskMemberRelationEnum;
import com.parvanpajooh.issuemanager.model.enums.TaskStatusEnum;
import com.parvanpajooh.issuemanager.model.vo.TaskVO;
import com.parvanpajooh.issuemanager.service.TaskLocalService;

/**
 * 
 *
 * 
 */
@Stateless
public class TaskLocalServiceImpl extends GenericLocalServiceImpl<Task, Long>implements TaskLocalService {
	/**
	 * Log variable for all child classes.
	 */
	static final Logger log = LoggerFactory.getLogger(TaskLocalServiceImpl.class);

	public TaskDao taskDao;
	public GroupDao groupDao;
	public MemberDao memberDao;
	public TaskStatusHistoryDao taskStatusHistoryDao;
	public TaskAssignmentHistoryDao taskAssignmentDao;
	public TaskAttachmentDao taskAttachmentDao;
	public RelationDao relationDao;
	public CommentDao commentDao;
	public TaskMemberRelationDao taskMemberRelationDao;

	@Inject
	public void setTaskDao(TaskDao taskDao) {
		this.dao = taskDao;
		this.taskDao = taskDao;
	}

	@Inject
	public void setGroupDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}

	@Inject
	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}

	@Inject
	public void setTaskStatusHistoryDao(TaskStatusHistoryDao taskStatusHistoryDao) {
		this.taskStatusHistoryDao = taskStatusHistoryDao;
	}

	@Inject
	public void setTaskAssignmentDao(TaskAssignmentHistoryDao taskAssignmentDao) {
		this.taskAssignmentDao = taskAssignmentDao;
	}

	@Inject
	public void setTaskAssignmentDao(TaskMemberRelationDao taskMemberRelationDao) {
		this.taskMemberRelationDao = taskMemberRelationDao;
	}

	@Inject
	public void setRelationDaoDao(RelationDao relationDao) {
		this.relationDao = relationDao;
	}

	@Inject
	public void setTaskAttachmentDao(TaskAttachmentDao taskAttachmentDao) {
		this.taskAttachmentDao = taskAttachmentDao;
	}

	@Inject
	public void setCommentDao(CommentDao commentDao) {
		this.commentDao = commentDao;
	}

	@Override
	public BaseVO save(BaseVO baseVO, UserInfo userInfo) throws ParvanServiceException {
		log.info("Entering save(baseVO={}, userInfo={})", baseVO, userInfo);

		TaskVO taskVO = (TaskVO) baseVO;

		try {
			boolean isNew = Validator.isNull(taskVO.getId());
			Task task = null;
			Group group = null;
			Member member = null;
			LocalDateTime now = LocalDateTime.now();

			if (!isNew) {

				if (Validator.isNotNull(taskVO.getCurrentMember())) {
					member = memberDao.get(taskVO.getCurrentMember().getId());
				}
				task = taskDao.get(taskVO.getId());
				group = groupDao.get(taskVO.getGroup().getId());

				List<TaskStatusHistory> taskStatusHistorys = taskStatusHistoryDao.findByTaskIdAndCreateUserId(taskVO.getId(), taskVO.getCreateUserId());
				List<TaskAssignmentHistory> taskAssignmentHistorys = taskAssignmentDao.findByTaskIdAndCreateUserId(taskVO.getId(), taskVO.getCreateUserId());
				List<Relation> relations = relationDao.findByTaskIdAndCreateUserId(taskVO.getId(), taskVO.getCreateUserId());
				List<TaskAttachment> taskAttachments = taskAttachmentDao.findByTaskIdAndCreateUserId(taskVO.getId(), taskVO.getCreateUserId());
				List<Comment> comments = commentDao.findByTaskIdAndCreateUserId(taskVO.getId(), taskVO.getCreateUserId());

				if (!Validator.equals(userInfo.getUserId(), task.getCreateUserId()) || Validator.isNotNull(taskStatusHistorys)
						|| Validator.isNotNull(taskAssignmentHistorys) || Validator.isNotNull(relations) || Validator.isNotNull(taskAttachments)
						|| Validator.isNotNull(comments)) {

					// check access
					Set<String> userRoles = userInfo.getRoleNames();
					if (!(userRoles.contains(RoleEnum.ADMIN.value) && userRoles.contains(RoleEnum.MANAGER.value))) {
						throw new ParvanRecoverableException(ErrorCode.HAS_NOT_ACCESS);
					}

				}

				task.fromVO(taskVO);
				task.setGroup(group);
				if (Validator.isNotNull(member)) {
					task.setCurrentMember(member);
				}
			}

			String subject = taskVO.getSubject();

			if (Validator.isNull(subject) || Validator.isNull(taskVO.getGroup()) || Validator.isNull(taskVO.getTaskType())
					|| Validator.isNull(taskVO.getPriority())) {
				log.debug("feild is empty.");
				throw new ParvanRecoverableException(ErrorCode.FEILDS_IS_EMPTY);
			}

			Long groupId = taskVO.getGroup().getId();

			if (isNew) {
				task = new Task();
				task.fromVO(taskVO);
				task.setCreateDate(now);
				task.setCreateUserId(userInfo.getUserId());
				task.setEmailStatus(EmailEnum.NEED);
				task.setCurrentTaskStatus(TaskStatusEnum.NEW);

			}

			task.setUpdateDate(now);
			task.setUpdateUserId(userInfo.getUserId());

			task.setActive(true);			

			// save entity
			task = taskDao.saveTask(task, groupId);
			taskVO = (TaskVO) task.toVO();

		} catch (ParvanServiceException e) {
			throw e;

		} catch (Exception e) {
			throw new ParvanUnrecoverableException("Error occurred while saving object.", e);
		}

		return taskVO;
	}

	@Override
	public void delete(Long id) throws ParvanServiceException {

		try {

			Task task = null;
			LocalDateTime now = LocalDateTime.now();

			task = taskDao.get(id);
			task.setActive(false);
			task.setUpdateDate(now);

			// refresh entity
			taskDao.refresh(task);

		} catch (Exception e) {
			throw new ParvanUnrecoverableException("Error occurred while deleting object.", e);
		}
	}

	@Override
	public void sendEmail(String[] mailList, String subject, String body, String mailServerUserName, String mailServerPassword, String mailServerHost,
			String mailServerPort, String mailIpAddress,String mailStarttls,String mailAuth,String mailRealm) throws ParvanServiceException, MessagingException {

		// LOG
		log.debug("Entering sendEmail in TaskLocalServiceImpl(mailList={}, )", mailList, "(subject=)",subject,"body=", body);

		SendMail mail = new SendMail();

		if (Validator.isNotNull(mailList)) {

			mail.send(mailList, body, subject, mailServerUserName, mailServerPassword, mailServerHost, mailServerPort, mailStarttls, mailAuth, mailRealm);
		}
		
		// LOG
		log.debug("Leaving method sendEmail() in TaskLocalServiceImpl");
	}

	@Override
	public List<Task> findByEmailStatus(EmailEnum status) throws ParvanServiceException {
		// LOG
		log.debug("Entering findByEmailStatus()");

		List<Task> list = null;

		try {

			// find by criteria
			list = taskDao.findByEmailStatus(status);

			// LOG
			log.info("found {} ", (list != null) ? list.size() : "NULL");

		} catch (Exception e) {
			throw new ParvanUnrecoverableException("Error occurred while getting list of records by findByEmailStatus.", e);
		}

		// LOG
		log.debug("Leaving method findByEmailStatus(): ret={}", (list != null) ? list.size() : "NULL");

		return list;

	}

}
