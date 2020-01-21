package com.parvanpajooh.issuemanager.dao.jpa;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.parvanpajooh.commons.platform.ejb.model.CurrentContext;
import com.parvanpajooh.commons.platform.ejb.model.UserInfo;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanDaoException;
import com.parvanpajooh.issuemanager.dao.AggregatedHistoryDao;
import com.parvanpajooh.issuemanager.dao.TaskDao;
import com.parvanpajooh.issuemanager.dao.TaskStatusHistoryDao;
import com.parvanpajooh.issuemanager.model.AggregatedHistory;
import com.parvanpajooh.issuemanager.model.Task;
import com.parvanpajooh.issuemanager.model.TaskStatusHistory;
import com.parvanpajooh.issuemanager.model.enums.EmailEnum;
import com.parvanpajooh.issuemanager.model.enums.TableNameEnum;
import com.parvanpajooh.issuemanager.model.enums.TaskStatusEnum;

/**
 * 
 * @author
 * 
 */
@Stateless
public class TaskStatusHistoryDaoJpa extends GenericDaoJpa<TaskStatusHistory, Long>implements TaskStatusHistoryDao {

	@PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
	private EntityManager entityManager;

	public AggregatedHistoryDao aggregatedHistoryDao;
	public TaskDao taskDao;

	@Inject
	public void setAggregatedHistoryDao(AggregatedHistoryDao aggregatedHistoryDao) {
		this.aggregatedHistoryDao = aggregatedHistoryDao;
	}

	@Inject
	public void setTaskDao(TaskDao taskDao) {
		this.taskDao = taskDao;
	}

	public TaskStatusHistoryDaoJpa() {
		super(TaskStatusHistory.class);
	}

	@Override
	public List<TaskStatusHistory> findByTaskId(Long id) throws ParvanDaoException {
		Query query = getEntityManager().createQuery("from TaskStatusHistory where taskId = ?");
		query.setParameter(1, id);
		List<TaskStatusHistory> taskStatusHistory = query.getResultList();

		if (taskStatusHistory != null && taskStatusHistory.size() > 0) {
			return taskStatusHistory;
		} else {
			return null;
		}
	}
	
	@Override
	public List<TaskStatusHistory> findByTaskIdAndCreateUserId(Long id,Long creatUserId) throws ParvanDaoException {
		Query query = getEntityManager().createQuery("from TaskStatusHistory where taskId = ? And createUserId !=?");
		query.setParameter(1, id);
		query.setParameter(2, creatUserId);

		List<TaskStatusHistory> taskStatusHistory = query.getResultList();

		if (taskStatusHistory != null && taskStatusHistory.size() > 0) {
			return taskStatusHistory;
		} else {
			return null;
		}
	}

	@Override
	public TaskStatusHistory save(TaskStatusHistory taskStatusHistory) throws ParvanDaoException {

		// LOG
		log.debug("Entering save(taskStatusHistory={})", taskStatusHistory);

		TaskStatusHistory savedTaskStatusHistory = null;
		try {

			// save taskStatsuHistory object
			savedTaskStatusHistory = super.save(taskStatusHistory);

			// serialize to JSON
			String serializedStr = TaskStatusHistory.mapToJson(savedTaskStatusHistory);

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
			aggregatedHistory.setTaskStatusHistory(savedTaskStatusHistory);
			aggregatedHistory.setType(TableNameEnum.valueOf("TaskStatusHistory"));
			aggregatedHistory.setTask(savedTaskStatusHistory.getTask());

			aggregatedHistoryDao.save(aggregatedHistory);

			// refresh Task record
			Task task = taskDao.get(savedTaskStatusHistory.getTask().getId());
			task.setCurrentTaskStatus(TaskStatusEnum.valueOf(savedTaskStatusHistory.getToStatus()));
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
		log.debug("Leaving exists(): {}", savedTaskStatusHistory.getId());

		return savedTaskStatusHistory;
	}
	
	@Override
	public void refresh(TaskStatusHistory taskStatusHistory) throws ParvanDaoException {

		// LOG
		log.debug("Entering save(taskStatusHistory={})", taskStatusHistory);

		TaskStatusHistory refreshedTaskStatusHistory = null;
		try {

			// refresh taskStatsuHistory object
			refreshedTaskStatusHistory = get(taskStatusHistory.getId());
			refreshedTaskStatusHistory.setComment(taskStatusHistory.getComment());
			super.refresh(refreshedTaskStatusHistory);

			// serialize to JSON
			String serializedStr = TaskStatusHistory.mapToJson(refreshedTaskStatusHistory);

			// refresh Aggregated record
			AggregatedHistory aggregatedHistory = aggregatedHistoryDao.getByTaskStatusId(refreshedTaskStatusHistory.getId());
			UserInfo userInfo = CurrentContext.getCurrentUserInfo().get();
			LocalDateTime now = LocalDateTime.now();

			aggregatedHistory.setUpdateDate(now);
			aggregatedHistory.setUpdateUserId(userInfo.getUserId());
			aggregatedHistory.setActive(true);
			aggregatedHistory.setBody(serializedStr);
			aggregatedHistory.setTaskStatusHistory(refreshedTaskStatusHistory);

			aggregatedHistoryDao.refresh(aggregatedHistory);

			// refresh Task record
			Task task = taskDao.get(refreshedTaskStatusHistory.getTask().getId());
			task.setCurrentTaskStatus(TaskStatusEnum.valueOf(refreshedTaskStatusHistory.getToStatus()));
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
		log.debug("Leaving exists(): {}", refreshedTaskStatusHistory.getId());

	}


}
