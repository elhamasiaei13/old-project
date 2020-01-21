package com.parvanpajooh.issuemanager.service;

import java.util.List;

import com.parvanpajooh.commons.platform.ejb.model.UserInfo;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanServiceException;
import com.parvanpajooh.issuemanager.model.Comment;
import com.parvanpajooh.issuemanager.model.vo.CommentVO;

/**
 * 
 * @author
 * 
 */
public interface CommentService extends GenericService<Comment, Long> {

	public List<CommentVO> loadCommentByTaskId(UserInfo userInfo, Long id) throws ParvanServiceException;

	public void editComment(UserInfo userInfo, CommentVO commentVO) throws ParvanServiceException;

	public void delete(UserInfo userInfo, Long id) throws ParvanServiceException;
}
