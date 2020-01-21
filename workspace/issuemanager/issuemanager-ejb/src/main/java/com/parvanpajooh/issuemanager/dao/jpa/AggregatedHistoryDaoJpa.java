package com.parvanpajooh.issuemanager.dao.jpa;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.parvanpajooh.issuemanager.dao.jpa.GenericDaoJpa;
import com.parvanpajooh.issuemanager.common.exceptions.ObjectNotFoundException;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanDaoException;
import com.parvanpajooh.issuemanager.dao.AggregatedHistoryDao;
import com.parvanpajooh.issuemanager.model.AggregatedHistory;
import com.parvanpajooh.issuemanager.model.Relation;
import com.parvanpajooh.issuemanager.model.TaskAttachment;

/**
 * 
 * @author
 * 
 */
@Stateless
public class AggregatedHistoryDaoJpa extends GenericDaoJpa<AggregatedHistory, Long>implements AggregatedHistoryDao {

	/**
	 * Entity manager, injected by Spring using @PersistenceContext annotation
	 * on setEntityManager()
	 */
	@PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
	private EntityManager entityManager;
	private Class<AggregatedHistory> persistentClass;

	public AggregatedHistoryDaoJpa() {
		super(AggregatedHistory.class);
	}

	@Override
	public List<AggregatedHistory> findByTaskId(Long id) throws ParvanDaoException {
		Query query = getEntityManager().createQuery("from AggregatedHistory where taskId = ? AND ACTIVE = ?");
		query.setParameter(1, id);
		query.setParameter(2, true);
		List<AggregatedHistory> aggregatedHistories = query.getResultList();

		if (aggregatedHistories != null && aggregatedHistories.size() > 0) {
			return aggregatedHistories;
		} else {
			return null;
		}

	}

	@Override
	public AggregatedHistory getByTaskStatusId(Long id) throws ParvanDaoException {
		log.debug("Entering get( id={})", id);

		Query query = getEntityManager().createQuery("from AggregatedHistory where taskStatusHistoryId = ? AND ACTIVE = ?");
		query.setParameter(1, id);
		query.setParameter(2, true);
		List<AggregatedHistory> aggregatedHistories = query.getResultList();

		if (aggregatedHistories != null && aggregatedHistories.size() > 0) {
			return aggregatedHistories.get(0);
		} else {
			return null;
		}

	}

	@Override
	public AggregatedHistory getByCommentId(Long id) throws ParvanDaoException {
		log.debug("Entering get( id={})", id);

		Query query = getEntityManager().createQuery("from AggregatedHistory where commentId = ? AND ACTIVE = ?");
		query.setParameter(1, id);
		query.setParameter(2, true);
		List<AggregatedHistory> aggregatedHistories = query.getResultList();

		if (aggregatedHistories != null && aggregatedHistories.size() > 0) {
			return aggregatedHistories.get(0);
		} else {
			return null;
		}

	}

	@Override
	public AggregatedHistory getByTaskAssignmentId(Long id) throws ParvanDaoException {
		log.debug("Entering get( id={})", id);

		Query query = getEntityManager().createQuery("from AggregatedHistory where taskAssignmentHistoryId = ? AND ACTIVE = ?");
		query.setParameter(1, id);
		query.setParameter(2, true);
		List<AggregatedHistory> aggregatedHistories = query.getResultList();

		if (aggregatedHistories != null && aggregatedHistories.size() > 0) {
			return aggregatedHistories.get(0);
		} else {
			return null;
		}

	}

	@Override
	public AggregatedHistory getByTaskAttachmentId(Long id) throws ParvanDaoException {
		log.debug("Entering get( id={})", id);

		Query query = getEntityManager().createQuery("from AggregatedHistory where taskAttachmentId = ? AND ACTIVE = ?");
		query.setParameter(1, id);
		query.setParameter(2, true);
		List<AggregatedHistory> aggregatedHistories = query.getResultList();

		if (aggregatedHistories != null && aggregatedHistories.size() > 0) {
			return aggregatedHistories.get(0);
		} else {
			return null;
		}

	}

	@Override
	public AggregatedHistory getByRelationId(Long id) throws ParvanDaoException {
		log.debug("Entering get( id={})", id);

		Query query = getEntityManager().createQuery("from AggregatedHistory where relationId = ? AND ACTIVE = ?");
		query.setParameter(1, id);
		query.setParameter(2, true);
		List<AggregatedHistory> aggregatedHistories = query.getResultList();

		if (aggregatedHistories != null && aggregatedHistories.size() > 0) {
			return aggregatedHistories.get(0);
		} else {
			return null;
		}
		
	}

}
