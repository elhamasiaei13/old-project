package com.parvanpajooh.issuemanager.service;

import java.util.List;

import com.parvanpajooh.issuemanager.common.exceptions.ParvanServiceException;
import com.parvanpajooh.issuemanager.model.vo.CommentVO;

/**
 * 
 * @author 
 * 
 */
public interface LoginLocalService {

	public List<CommentVO> getErrorList(String errorMessage)
			throws ParvanServiceException;

}
