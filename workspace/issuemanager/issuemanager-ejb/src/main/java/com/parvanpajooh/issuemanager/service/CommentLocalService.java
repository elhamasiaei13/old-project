package com.parvanpajooh.issuemanager.service;

import java.util.List;

import com.parvanpajooh.issuemanager.common.exceptions.ParvanServiceException;
import com.parvanpajooh.issuemanager.model.Comment;
import com.parvanpajooh.issuemanager.model.vo.CommentVO;

/**
 * 
 * @author
 * 
 */
public interface CommentLocalService extends GenericLocalService<Comment, Long> {

	public List<CommentVO> loadCommentByTaskId(Long id) throws ParvanServiceException;

	public void editComment(CommentVO commentVO) throws ParvanServiceException;

	public void delete(Long id) throws ParvanServiceException;
}
