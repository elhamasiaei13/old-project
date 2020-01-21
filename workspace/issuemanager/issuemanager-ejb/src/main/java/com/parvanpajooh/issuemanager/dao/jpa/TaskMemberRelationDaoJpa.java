package com.parvanpajooh.issuemanager.dao.jpa;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.StaleObjectStateException;

import com.parvanpajooh.commons.platform.ejb.model.CurrentContext;
import com.parvanpajooh.commons.platform.ejb.model.UserInfo;
import com.parvanpajooh.commons.util.Validator;
import com.parvanpajooh.issuemanager.common.exceptions.ObjectExistsException;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanDaoException;
import com.parvanpajooh.issuemanager.dao.AggregatedHistoryDao;
import com.parvanpajooh.issuemanager.dao.MemberDao;
import com.parvanpajooh.issuemanager.dao.TaskDao;
import com.parvanpajooh.issuemanager.dao.TaskMemberRelationDao;
import com.parvanpajooh.issuemanager.model.TaskMemberRelation;
import com.parvanpajooh.issuemanager.model.enums.TaskMemberRelationEnum;

/**
 * 
 * @author
 * 
 */
@Stateless
public class TaskMemberRelationDaoJpa extends GenericDaoJpa<TaskMemberRelation, Long>implements TaskMemberRelationDao {

	public AggregatedHistoryDao aggregatedHistoryDao;

	public TaskDao taskDao;
	public MemberDao memberDao;

	@Inject
	public void setAggregatedHistoryDao(AggregatedHistoryDao aggregatedHistoryDao) {
		this.aggregatedHistoryDao = aggregatedHistoryDao;
	}

	public TaskMemberRelationDaoJpa() {
		super(TaskMemberRelation.class);
	}

	@Inject
	public void setTaskDao(TaskDao taskDao) {
		this.taskDao = taskDao;
	}

	@Inject
	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}

	@Override
	public List<TaskMemberRelation> findByTaskIdAndType(Long taskId, TaskMemberRelationEnum type) throws ParvanDaoException {
		Query query = getEntityManager().createQuery("from TaskMemberRelation where taskId = ? and type= ? ");
		query.setParameter(1, taskId);
		query.setParameter(2, type);
		List<TaskMemberRelation> taskMemberRelations = query.getResultList();

		if (taskMemberRelations != null && taskMemberRelations.size() > 0) {
			return taskMemberRelations;
		} else {
			return null;
		}
	}

	@Override
	public List<TaskMemberRelation> findByTaskIdAndMemberId(Long taskId, Long memberId, String type) throws ParvanDaoException {
		Query query = getEntityManager().createQuery("from TaskMemberRelation where taskId = ? AND memberId = ? and type= ? ");
		query.setParameter(1, taskId);
		query.setParameter(2, memberId);
		query.setParameter(3, TaskMemberRelationEnum.fromValue(type));
		List<TaskMemberRelation> taskMemberRelations = query.getResultList();

		if (taskMemberRelations != null && taskMemberRelations.size() > 0) {
			return taskMemberRelations;
		} else {
			return null;
		}
	}

	/**
	 * Entity manager, injected by Spring using @PersistenceContext annotation
	 * on setEntityManager()
	 */
	@PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
	private EntityManager entityManager;

	private Class<TaskMemberRelation> persistentClass;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.parvanpajooh.platform.parvan.entityconfig.dao.GenericDao#save(java.
	 * lang.Object)
	 */
	@Override
	public TaskMemberRelation save(TaskMemberRelation object) throws ParvanDaoException {

		// LOG
		log.debug("Entering save( object={})", object);

		TaskMemberRelation entity = null;
		List<TaskMemberRelation> list = null;

		try {

			UserInfo userInfo = CurrentContext.getCurrentUserInfo().get();
			object.setMember(memberDao.get(userInfo.getUserId()));
			object.setTask(taskDao.get(object.getTask().getId()));
			list = findByTaskIdAndMemberId(object.getTask().getId(), object.getMember().getId(), object.getType().toString());
			entity = object;

			if (Validator.isNull(list)) {
				// save
				entity = this.entityManager.merge(object);
				this.entityManager.flush();

			} else {
				delete(list.get(0));

			}

		} catch (ConstraintViolationException e) {
			throw e;

		} catch (OptimisticLockException e) {
			if (e.getCause() != null && e.getCause() instanceof StaleObjectStateException) {
				throw (StaleObjectStateException) e.getCause();
			}

			throw e;

		} catch (Exception e) {

			Throwable cause = ExceptionUtils.getRootCause(e);

			if (cause != null && cause.getMessage() != null && cause.getMessage().indexOf("_UNIQUE") > -1) {
				throw new ObjectExistsException();
			}

			throw new ParvanDaoException("Error occurred while saving record.", e);
		}

		// LOG
		log.debug("Leaving exists(): {}", entity);

		return entity;
	}
	
	
}
