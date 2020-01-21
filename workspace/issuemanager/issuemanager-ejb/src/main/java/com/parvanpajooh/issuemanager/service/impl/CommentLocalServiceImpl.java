package com.parvanpajooh.issuemanager.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.parvanpajooh.commons.platform.ejb.model.UserInfo;
import com.parvanpajooh.commons.platform.ejb.model.vo.BaseVO;
import com.parvanpajooh.commons.util.Validator;
import com.parvanpajooh.issuemanager.common.exceptions.ErrorCode;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanRecoverableException;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanServiceException;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanUnrecoverableException;
import com.parvanpajooh.issuemanager.dao.CommentDao;
import com.parvanpajooh.issuemanager.dao.TaskDao;
import com.parvanpajooh.issuemanager.model.Comment;
import com.parvanpajooh.issuemanager.model.Task;
import com.parvanpajooh.issuemanager.model.vo.CommentVO;
import com.parvanpajooh.issuemanager.service.CommentLocalService;


@Stateless
public class CommentLocalServiceImpl extends GenericLocalServiceImpl<Comment, Long> implements CommentLocalService {
	/**
	 * Log variable for all child classes.
	 */
	static final Logger log = LoggerFactory.getLogger(CommentLocalServiceImpl.class);

	public CommentDao commentDao;
	public TaskDao taskDao;

	@Inject
	public void setCommentDao(CommentDao commentDao) {
		this.dao = commentDao;
		this.commentDao = commentDao;
	}

	@Inject
	public void setTaskDao(TaskDao taskDao) {
		this.taskDao = taskDao;
	}

	@Override
	public List<CommentVO> loadCommentByTaskId(Long id) throws ParvanServiceException {
		List<CommentVO> result = null;

		try {
			List<Comment> comments = commentDao.findBytaskId(id);
			result = convertToTree(comments);
		} catch (Exception e) {
			log.error("error occurred while loadCommentByTaskId", e);
			throw new ParvanUnrecoverableException("error occurred while loadCommentByTaskId", e);
		}

		return result;

	}

	@Override
	public BaseVO save(BaseVO baseVO, UserInfo userInfo) throws ParvanServiceException {
		log.info("Entering save(baseVO={}, userInfo={})", baseVO, userInfo);

		CommentVO commentVO = (CommentVO) baseVO;

		try {
			String description = commentVO.getDescription();

			if (Validator.isNull(description) || Validator.isNull(commentVO.getTask())) {
				log.debug("feild is empty.");
				throw new ParvanRecoverableException(ErrorCode.FEILDS_IS_EMPTY);
			}

			Long taskId = commentVO.getTask().getId();
			Comment comment = null;

			if (Validator.isNull(commentVO.getId())) {
				comment = new Comment();
				comment.fromVO(commentVO);
			} else {
				comment = commentDao.get(commentVO.getId());
				comment.fromVO(commentVO);
			}
			Task task = taskDao.get(taskId);
			comment.setTask(task);
			comment.setActive(true);

			LocalDateTime now = LocalDateTime.now();

			comment.setCreateDate(now);
			comment.setUpdateDate(now);
			comment.setTenantId(userInfo.getTenantId());
			comment.setCreateUserId(userInfo.getUserId());
			comment.setUpdateUserId(userInfo.getUserId());

			// save entity
			comment = commentDao.save(comment);
			commentVO = (CommentVO) comment.toVO();

		} catch (ParvanServiceException e) {
			throw e;

		} catch (Exception e) {
			throw new ParvanUnrecoverableException("Error occurred while saving object.", e);
		}

		return commentVO;
	}

	/**
	 * 
	 * @param members
	 * @return
	 */
	private List<CommentVO> convertToTree(List<Comment> comments) {

		List<CommentVO> result = new ArrayList<CommentVO>();
		Map<Long, CommentVO> map = new HashMap<Long, CommentVO>();

		Iterator<Comment> iterator = comments.iterator();
		while (iterator.hasNext()) {
			Comment thisComment = iterator.next();
			CommentVO commentVO = thisComment.toVOLite();
			result.add(commentVO);
			map.put(thisComment.getId(), commentVO);
			iterator.remove();
		}

		return result;
	}

	@Override
	public void editComment(CommentVO commentVO) throws ParvanServiceException {
		try {

			String description = commentVO.getDescription();

			if (Validator.isNull(description)) {
				log.debug("feild is empty.");
				throw new ParvanRecoverableException(ErrorCode.FEILDS_IS_EMPTY);
			}

			Comment comment = new Comment();
			LocalDateTime now = LocalDateTime.now();

			comment = commentDao.get(commentVO.getId());
			comment.setUpdateDate(now);		
			
			comment.fromVO(commentVO);

			// refresh entity
			commentDao.refresh(comment);

		} catch (ParvanServiceException e) {
			throw e;

		} catch (Exception e) {
			throw new ParvanUnrecoverableException("Error occurred while refreshing comment.", e);
		}
		
	}

	public void delete(Long id) throws ParvanServiceException {
		try {

			Comment comment = null;
			LocalDateTime now = LocalDateTime.now();
			
			comment = commentDao.get(id);
			comment.setActive(false);
			comment.setUpdateDate(now);	
			
			

			// refresh entity
			commentDao.delete(comment);

		} catch (Exception e) {
			throw new ParvanUnrecoverableException("Error occurred while deleting comment.", e);
		}
		
	}

}
