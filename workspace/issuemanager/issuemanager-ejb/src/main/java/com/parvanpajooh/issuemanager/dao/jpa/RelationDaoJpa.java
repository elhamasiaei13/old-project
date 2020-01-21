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
import com.parvanpajooh.issuemanager.dao.RelationDao;
import com.parvanpajooh.issuemanager.dao.TaskDao;
import com.parvanpajooh.issuemanager.model.AggregatedHistory;
import com.parvanpajooh.issuemanager.model.Relation;
import com.parvanpajooh.issuemanager.model.Task;
import com.parvanpajooh.issuemanager.model.enums.EmailEnum;
import com.parvanpajooh.issuemanager.model.enums.TableNameEnum;

/**
 * 
 * @author
 * 
 */
@Stateless
public class RelationDaoJpa extends GenericDaoJpa<Relation, Long>implements RelationDao {

	public AggregatedHistoryDao aggregatedHistoryDao;

	public TaskDao taskDao;

	public RelationDaoJpa() {
		super(Relation.class);
	}

	@Inject
	public void setAggregatedHistoryDao(AggregatedHistoryDao aggregatedHistoryDao) {
		this.aggregatedHistoryDao = aggregatedHistoryDao;
	}

	@Inject
	public void setTaskDao(TaskDao taskDao) {
		this.taskDao = taskDao;
	}

	@Override
	public List<Relation> findBytaskId(Long id) throws ParvanDaoException {

		Query query = getEntityManager().createQuery("from Relation where fromTask.id = ? AND active=?");
		query.setParameter(1, id);
		query.setParameter(2, true);

		List<Relation> relationList = query.getResultList();

		return relationList;
	}

	@Override
	public List<Relation> findByTaskIdAndCreateUserId(Long id, Long creatUserId) throws ParvanDaoException {
		Query query = getEntityManager().createQuery("from Relation where fromTask.id = ? And createUserId !=?");
		query.setParameter(1, id);
		query.setParameter(2, creatUserId);

		List<Relation> relations = query.getResultList();

		if (relations != null && relations.size() > 0) {
			return relations;
		} else {
			return null;
		}
	}

	@Override
	public Relation save(Relation relation) throws ParvanDaoException {

		// LOG
		log.debug("Entering save(relation={})", relation);

		Relation savedRelation = null;
		try {

			// save comment object
			savedRelation = super.save(relation);

			// serialize to JSON
			String serializedStr = Relation.mapToJson(savedRelation);

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
			aggregatedHistory.setRelation(savedRelation);
			aggregatedHistory.setType(TableNameEnum.valueOf("Relation"));
			aggregatedHistory.setTask(savedRelation.getFromTask());

			aggregatedHistoryDao.save(aggregatedHistory);

			// refresh Task record
			Task task = taskDao.get(savedRelation.getFromTask().getId());
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
		log.debug("Leaving exists(): {}", savedRelation.getId());

		return savedRelation;
	}

	@Override
	public void refresh(Relation relation) throws ParvanDaoException {

		// LOG
		log.debug("Entering refresh(relation={})", relation);
		Relation rel = null;

		try {

			// refresh comment object
			rel = get(relation.getId());
			rel.setDescription(relation.getDescription());
			super.refresh(rel);

			// serialize to JSON
			String serializedStr = Relation.mapToJson(rel);

			// refresh Aggregated record
			AggregatedHistory aggregatedHistory = aggregatedHistoryDao.getByRelationId(rel.getId());
			UserInfo userInfo = CurrentContext.getCurrentUserInfo().get();
			LocalDateTime now = LocalDateTime.now();

			aggregatedHistory.setUpdateDate(now);
			aggregatedHistory.setUpdateUserId(userInfo.getUserId());
			aggregatedHistory.setActive(true);
			aggregatedHistory.setBody(serializedStr);
			aggregatedHistory.setRelation(rel);

			aggregatedHistoryDao.refresh(aggregatedHistory);

			// refresh Task record
			Task task = taskDao.get(rel.getFromTask().getId());
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
		log.debug("Leaving exists(): {}", rel.getId());

	}

	@Override
	public void delete(Relation relation) throws ParvanDaoException {

		// LOG
		log.debug("Entering delete(relation={})", relation);
		Relation rel = null;

		try {

			// refresh relation object
			rel = get(relation.getId());
			rel.setActive(false);
			super.refresh(rel);

			// serialize to JSON
			String serializedStr = Relation.mapToJson(rel);

			// refresh Aggregated record
			AggregatedHistory aggregatedHistory = aggregatedHistoryDao.getByRelationId(rel.getId());
			UserInfo userInfo = CurrentContext.getCurrentUserInfo().get();
			LocalDateTime now = LocalDateTime.now();

			aggregatedHistory.setUpdateDate(now);
			aggregatedHistory.setUpdateUserId(userInfo.getUserId());
			aggregatedHistory.setActive(false);
			aggregatedHistory.setBody(serializedStr);
			aggregatedHistory.setRelation(rel);

			aggregatedHistoryDao.refresh(aggregatedHistory);
			
			// refresh Task record
			Task task = taskDao.get(rel.getFromTask().getId());
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
		log.debug("Leaving exists(): {}", rel.getId());

	}

}
