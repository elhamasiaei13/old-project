package com.parvanpajooh.issuemanager.service.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.parvanpajooh.commons.platform.ejb.model.UserInfo;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanServiceException;
import com.parvanpajooh.issuemanager.model.Comment;
import com.parvanpajooh.issuemanager.model.vo.CommentVO;
import com.parvanpajooh.issuemanager.service.CommentLocalService;
import com.parvanpajooh.issuemanager.service.CommentService;


@Stateless
public class CommentServiceImpl extends GenericServiceImpl<Comment, Long>
		implements CommentService {
	/**
	 * Log variable for all child classes.
	 */
	static final Logger log = LoggerFactory.getLogger(CommentServiceImpl.class);

	private CommentLocalService commentLocalService;

	@Inject
	public void setUserLocalService(CommentLocalService commentLocalService) {
		this.localService = commentLocalService;
		this.commentLocalService = commentLocalService;
	}


	@Override
	public List<CommentVO> loadCommentByTaskId(UserInfo userInfo, Long id)
			throws ParvanServiceException {
		log.debug("Entering loadCommentByTaskId(userInfo={})", userInfo);
		return commentLocalService.loadCommentByTaskId(id);

	}

	@Override
	public void editComment(UserInfo userInfo, CommentVO commentVO) throws ParvanServiceException{
		log.debug("Entering editComment(userInfo={})", userInfo);
		commentVO.setUpdateUserId(userInfo.getUserId());
		commentLocalService.editComment(commentVO);		
	}
	
	public void delete(UserInfo userInfo, Long id) throws ParvanServiceException {
		log.debug("Entering deleteComment(userInfo={})", userInfo);
		commentLocalService.delete(id);
	}

}
