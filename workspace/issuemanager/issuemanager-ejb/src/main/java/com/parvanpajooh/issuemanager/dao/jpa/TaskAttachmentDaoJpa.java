package com.parvanpajooh.issuemanager.dao.jpa;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;

import com.parvanpajooh.commons.platform.ejb.model.CurrentContext;
import com.parvanpajooh.commons.platform.ejb.model.UserInfo;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanDaoException;
import com.parvanpajooh.issuemanager.dao.AggregatedHistoryDao;
import com.parvanpajooh.issuemanager.dao.TaskAttachmentDao;
import com.parvanpajooh.issuemanager.dao.TaskDao;
import com.parvanpajooh.issuemanager.model.AggregatedHistory;
import com.parvanpajooh.issuemanager.model.Task;
import com.parvanpajooh.issuemanager.model.TaskAttachment;
import com.parvanpajooh.issuemanager.model.enums.EmailEnum;
import com.parvanpajooh.issuemanager.model.enums.TableNameEnum;

/**
 * 
 * @author
 * 
 */
@Stateless
public class TaskAttachmentDaoJpa extends GenericDaoJpa<TaskAttachment, Long>implements TaskAttachmentDao {

	public AggregatedHistoryDao aggregatedHistoryDao;

	public TaskDao taskDao;

	@Inject
	public void setAggregatedHistoryDao(AggregatedHistoryDao aggregatedHistoryDao) {
		this.aggregatedHistoryDao = aggregatedHistoryDao;
	}

	public TaskAttachmentDaoJpa() {
		super(TaskAttachment.class);
	}

	@Inject
	public void setTaskDao(TaskDao taskDao) {
		this.taskDao = taskDao;
	}

	@Override
	public List<TaskAttachment> findByTaskId(Long id) throws ParvanDaoException {
		Query query = getEntityManager().createQuery("from TaskAttachment where taskId = ? AND ACTIVE = ?");
		query.setParameter(1, id);
		query.setParameter(2, true);
		List<TaskAttachment> taskAttachments = query.getResultList();

		if (taskAttachments != null && taskAttachments.size() > 0) {
			return taskAttachments;
		} else {
			return null;
		}
	}

	@Override
	public List<TaskAttachment> findByTaskIdAndCreateUserId(Long id, Long creatUserId) throws ParvanDaoException {
		Query query = getEntityManager().createQuery("from TaskAttachment where taskId = ? And createUserId !=?");
		query.setParameter(1, id);
		query.setParameter(2, creatUserId);

		List<TaskAttachment> taskAttachments = query.getResultList();

		if (taskAttachments != null && taskAttachments.size() > 0) {
			return taskAttachments;
		} else {
			return null;
		}
	}

	@Override
	public void refresh(TaskAttachment taskAttachment) throws ParvanDaoException {

		// LOG
		log.debug("Entering save(taskAttachment={})", taskAttachment);

		TaskAttachment refreshedTaskAttachment = null;
		try {

			// refresh taskAttachment object
			refreshedTaskAttachment = get(taskAttachment.getId());
			refreshedTaskAttachment.setComment(taskAttachment.getComment());
			;
			super.refresh(taskAttachment);

			// serialize to JSON
			String serializedStr = TaskAttachment.mapToJson(refreshedTaskAttachment);

			// refresh a new Aggregated record
			AggregatedHistory aggregatedHistory = aggregatedHistoryDao.getByTaskAttachmentId(refreshedTaskAttachment.getId());
			UserInfo userInfo = CurrentContext.getCurrentUserInfo().get();
			LocalDateTime now = LocalDateTime.now();

			aggregatedHistory.setUpdateDate(now);
			aggregatedHistory.setUpdateUserId(userInfo.getUserId());
			aggregatedHistory.setBody(serializedStr);
			aggregatedHistory.setTaskAttachment(refreshedTaskAttachment);
			aggregatedHistory.setActive(false);

			aggregatedHistoryDao.refresh(aggregatedHistory);

			// refresh Task record
			Task task = taskDao.get(refreshedTaskAttachment.getTask().getId());
			task.setUpdateDate(now);
			task.setUpdateUserId(userInfo.getUserId());
			task.setEmailStatus(EmailEnum.NEED);
			taskDao.refresh(task);

		} catch (ParvanDaoException e) {
			throw e;

		} catch (IOException e) {
			log.error("error occurred while serializing to JSON", e);
			throw new ParvanDaoException("error occurred while serializing to JSON", e);
		}

		// LOG
		log.debug("Leaving exists(): {}", refreshedTaskAttachment.getId());

	}

	@Override
	public TaskAttachment save(TaskAttachment taskAttachment) throws ParvanDaoException {

		// LOG
		log.debug("Entering save(taskStatusHistory={})", taskAttachment);

		TaskAttachment savedTaskAttachment = null;
		try {

			// save taskStatsuHistory object
			savedTaskAttachment = super.save(taskAttachment);

			// serialize to JSON
			String serializedStr = savedTaskAttachment.mapToJson(savedTaskAttachment);

			// save a new Aggregated record
			AggregatedHistory aggregatedHistory = new AggregatedHistory();
			UserInfo userInfo = CurrentContext.getCurrentUserInfo().get();
			LocalDateTime now = LocalDateTime.now();

			aggregatedHistory.setCreateDate(now);
			aggregatedHistory.setUpdateDate(now);
			aggregatedHistory.setCreateUserId(userInfo.getUserId());
			aggregatedHistory.setUpdateUserId(userInfo.getUserId());
			aggregatedHistory.setActive(true);
			aggregatedHistory.setBody(serializedStr);
			aggregatedHistory.setTaskAttachment(savedTaskAttachment);
			aggregatedHistory.setType(TableNameEnum.valueOf("TaskAttachment"));
			aggregatedHistory.setTask(savedTaskAttachment.getTask());

			aggregatedHistoryDao.save(aggregatedHistory);
			
			// refresh Task record
			Task task = taskDao.get(savedTaskAttachment.getTask().getId());
			task.setUpdateDate(now);
			task.setUpdateUserId(userInfo.getUserId());
			task.setEmailStatus(EmailEnum.NEED);
			taskDao.refresh(task);

		} catch (ParvanDaoException e) {
			throw e;

		} catch (IOException e) {
			log.error("error occurred while serializing to JSON", e);
			throw new ParvanDaoException("error occurred while serializing to JSON", e);
		}

		// LOG
		log.debug("Leaving exists(): {}", savedTaskAttachment.getId());

		return savedTaskAttachment;
	}

}
