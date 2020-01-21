package com.parvanpajooh.sample.service;

import java.util.List;

import com.parvanpajooh.client.common.PageList;
import com.parvanpajooh.commons.platform.ejb.exceptions.ParvanServiceException;
import com.parvanpajooh.sample.model.vo.ObjectAccessScopeVO;
import com.parvanpajooh.sample.model.vo.UserVO;

public interface UserLocalService {

	/**
	 * 
	 * @param parentAgentId
	 * @return
	 * @throws ParvanServiceException
	 */
	public PageList<UserVO> findUsers(String query, int page, int size) throws ParvanServiceException;

	/**
	 * 
	 * @param username
	 * @return
	 * @throws ParvanServiceException
	 */
	/*public UserVO loadUserByUsername(String username) throws ParvanServiceException;*/

	/**
	 * 
	 * @return
	 * @throws ParvanServiceException
	 */
	public List<ObjectAccessScopeVO> loadScopes() throws ParvanServiceException;
}
