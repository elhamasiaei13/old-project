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
import com.parvanpajooh.issuemanager.dao.CommentDao;
import com.parvanpajooh.issuemanager.dao.TaskDao;
import com.parvanpajooh.issuemanager.model.AggregatedHistory;
import com.parvanpajooh.issuemanager.model.Comment;
import com.parvanpajooh.issuemanager.model.Task;
import com.parvanpajooh.issuemanager.model.enums.EmailEnum;
import com.parvanpajooh.issuemanager.model.enums.TableNameEnum;

/**
 * 
 * @author
 * 
 */
@Stateless
public class CommentDaoJpa extends GenericDaoJpa<Comment, Long>implements CommentDao {

	public AggregatedHistoryDao aggregatedHistoryDao;

	public TaskDao taskDao;

	public CommentDaoJpa() {
		super(Comment.class);
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
	public List<Comment> findBytaskId(Long id) throws ParvanDaoException {

		Query query = getEntityManager().createQuery("from Comment where task.id = ? AND active=?");
		query.setParameter(1, id);
		query.setParameter(2, true);

		List<Comment> CommentList = query.getResultList();

		return CommentList;
	}

	@Override
	public List<Comment> findByTaskIdAndCreateUserId(Long id, Long creatUserId) throws ParvanDaoException {
		Query query = getEntityManager().createQuery("from Comment where taskId = ? And createUserId !=?");
		query.setParameter(1, id);
		query.setParameter(2, creatUserId);

		List<Comment> CommentList = query.getResultList();

		if (CommentList != null && CommentList.size() > 0) {
			return CommentList;
		} else {
			return null;
		}
	}

	@Override
	public Comment save(Comment comment) throws ParvanDaoException {

		// LOG
		log.debug("Entering save(comment={})", comment);

		Comment savedComment = null;
		try {

			// save comment object
			savedComment = super.save(comment);

			// serialize to JSON
			String serializedStr = Comment.mapToJson(savedComment);

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
			aggregatedHistory.setComment(savedComment);
			aggregatedHistory.setType(TableNameEnum.valueOf("Comment"));
			aggregatedHistory.setTask(savedComment.getTask());

			aggregatedHistoryDao.save(aggregatedHistory);

			// refresh Task record
			Task task = taskDao.get(savedComment.getTask().getId());
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
		log.debug("Leaving exists(): {}", savedComment.getId());

		return savedComment;
	}

	@Override
	public void refresh(Comment comment) throws ParvanDaoException {

		// LOG
		log.debug("Entering save(comment={})", comment);
		Comment com = null;

		try {

			// refresh comment object
			com = get(comment.getId());
			com.setDescription(comment.getDescription());
			super.refresh(com);

			// serialize to JSON
			String serializedStr = Comment.mapToJson(com);

			// refresh Aggregated record
			AggregatedHistory aggregatedHistory = aggregatedHistoryDao.getByCommentId(com.getId());
			UserInfo userInfo = CurrentContext.getCurrentUserInfo().get();
			LocalDateTime now = LocalDateTime.now();

			aggregatedHistory.setUpdateDate(now);
			aggregatedHistory.setUpdateUserId(userInfo.getUserId());
			aggregatedHistory.setActive(true);
			aggregatedHistory.setBody(serializedStr);
			aggregatedHistory.setComment(com);

			aggregatedHistoryDao.refresh(aggregatedHistory);

			// refresh Task record
			Task task = taskDao.get(com.getTask().getId());
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
		log.debug("Leaving exists(): {}", com.getId());

	}

	@Override
	public void delete(Comment comment) throws ParvanDaoException {

		// LOG
		log.debug("Entering save(comment={})", comment);
		Comment com = null;

		try {

			// refresh comment object
			com = get(comment.getId());
			com.setActive(false);
			super.refresh(com);

			// serialize to JSON
			String serializedStr = Comment.mapToJson(com);

			// refresh Aggregated record
			AggregatedHistory aggregatedHistory = aggregatedHistoryDao.getByCommentId(com.getId());
			UserInfo userInfo = CurrentContext.getCurrentUserInfo().get();
			LocalDateTime now = LocalDateTime.now();

			aggregatedHistory.setUpdateDate(now);
			aggregatedHistory.setUpdateUserId(userInfo.getUserId());
			aggregatedHistory.setActive(false);
			aggregatedHistory.setBody(serializedStr);
			aggregatedHistory.setComment(com);

			aggregatedHistoryDao.refresh(aggregatedHistory);

			// refresh Task record
			Task task = taskDao.get(com.getTask().getId());
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
		log.debug("Leaving exists(): {}", com.getId());

	}

}
