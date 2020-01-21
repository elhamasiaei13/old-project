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
import com.parvanpajooh.issuemanager.dao.TaskAssignmentHistoryDao;
import com.parvanpajooh.issuemanager.dao.TaskDao;
import com.parvanpajooh.issuemanager.model.AggregatedHistory;
import com.parvanpajooh.issuemanager.model.Task;
import com.parvanpajooh.issuemanager.model.TaskAssignmentHistory;
import com.parvanpajooh.issuemanager.model.enums.EmailEnum;
import com.parvanpajooh.issuemanager.model.enums.TableNameEnum;

/**
 * 
 * @author 
 * 
 */
@Stateless
public class TaskAssignmentHistoryDaoJpa extends GenericDaoJpa<TaskAssignmentHistory, Long> implements TaskAssignmentHistoryDao {

	public TaskAssignmentHistoryDaoJpa() {
		super(TaskAssignmentHistory.class);
	}
	
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
	
	/**
	 * Entity manager, injected by Spring using @PersistenceContext annotation
	 * on setEntityManager()
	 */
	@PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
	private EntityManager entityManager;

	@Override
	public List<TaskAssignmentHistory> findByTaskId(Long id) throws ParvanDaoException {
		Query query = getEntityManager().createQuery("from TaskAssignmentHistory where taskId = ?");
		query.setParameter(1, id);
		List<TaskAssignmentHistory> taskAssignment = query.getResultList();

		if (taskAssignment != null && taskAssignment.size() > 0) {
			return taskAssignment;
		} else {
			return null;
		}
	}
	
	@Override
	public List<TaskAssignmentHistory> findByTaskIdAndCreateUserId(Long id,Long creatUserId) throws ParvanDaoException {
		Query query = getEntityManager().createQuery("from TaskAssignmentHistory where taskId = ? and createUserId != ? ");
		query.setParameter(1, id);
		query.setParameter(2, id);
		List<TaskAssignmentHistory> taskAssignment = query.getResultList();

		if (taskAssignment != null && taskAssignment.size() > 0) {
			return taskAssignment;
		} else {
			return null;
		}
	}
	
	@Override
	public TaskAssignmentHistory save(TaskAssignmentHistory taskAssignmentHistory) throws ParvanDaoException {

		// LOG
		log.debug("Entering save(taskStatusHistory={})", taskAssignmentHistory);
		
		TaskAssignmentHistory savedTaskAssignmentHistory = null;
		try {

			// save taskAssignmentHistory object
			savedTaskAssignmentHistory = super.save(taskAssignmentHistory);
			
			// serialize to JSON
			String serializedStr = TaskAssignmentHistory.mapToJson(savedTaskAssignmentHistory);
		
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
			aggregatedHistory.setTaskAssignmentHistory(savedTaskAssignmentHistory);
			aggregatedHistory.setType(TableNameEnum.valueOf("TaskAssignmentHistory"));
			aggregatedHistory.setTask(savedTaskAssignmentHistory.getTask());
			
			aggregatedHistoryDao.save(aggregatedHistory);
			
			// refresh Task record
			Task task = taskDao.get(savedTaskAssignmentHistory.getTask().getId());
			task.setCurrentMember(savedTaskAssignmentHistory.getMemberTo());
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
		log.debug("Leaving exists(): {}", savedTaskAssignmentHistory.getId());

		return savedTaskAssignmentHistory;
	}
	
	@Override
	public void refresh(TaskAssignmentHistory taskAssignmentHistory) throws ParvanDaoException {

		// LOG
		log.debug("Entering save(taskStatusHistory={})", taskAssignmentHistory);
		
		TaskAssignmentHistory refreshedTaskAssignmentHistory = null;
		
		try {
			
			UserInfo userInfo = CurrentContext.getCurrentUserInfo().get();
			LocalDateTime now = LocalDateTime.now();

			// refresh taskAssignmentHistory object
			refreshedTaskAssignmentHistory = get(taskAssignmentHistory.getId());
			refreshedTaskAssignmentHistory.setComment(taskAssignmentHistory.getComment());
			refreshedTaskAssignmentHistory.setUpdateDate(now);
			refreshedTaskAssignmentHistory.setUpdateUserId(userInfo.getUserId());
			super.refresh(refreshedTaskAssignmentHistory);
			
			// serialize to JSON
			String serializedStr = TaskAssignmentHistory.mapToJson(refreshedTaskAssignmentHistory);
		
			// refresh Aggregated record
			AggregatedHistory aggregatedHistory = aggregatedHistoryDao.getByTaskAssignmentId(refreshedTaskAssignmentHistory.getId());
						

			aggregatedHistory.setUpdateDate(now);
			aggregatedHistory.setUpdateUserId(userInfo.getUserId());
			aggregatedHistory.setActive(true);
			aggregatedHistory.setBody(serializedStr);
			aggregatedHistory.setTaskAssignmentHistory(refreshedTaskAssignmentHistory);
			
			aggregatedHistoryDao.refresh(aggregatedHistory);
			
			// refresh Task record
			Task task = taskDao.get(refreshedTaskAssignmentHistory.getTask().getId());
			task.setCurrentMember(refreshedTaskAssignmentHistory.getMemberTo());
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
		log.debug("Leaving exists(): {}", refreshedTaskAssignmentHistory.getId());	
	}


}
