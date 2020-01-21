package com.parvanpajooh.sample.service;

import java.util.List;

import com.parvanpajooh.client.common.PageList;
import com.parvanpajooh.commons.platform.ejb.exceptions.ParvanServiceException;
import com.parvanpajooh.commons.platform.ejb.model.UserInfo;
import com.parvanpajooh.sample.model.vo.ObjectAccessScopeVO;
import com.parvanpajooh.sample.model.vo.UserVO;

public interface UserService {

	/**
	 * 
	 * @param userInfo
	 * @param query
	 * @param page
	 * @param size
	 * @return
	 * @throws ParvanServiceException
	 */
	public PageList<UserVO> findUsers(UserInfo userInfo, String query, int page, int size) throws ParvanServiceException;

	/**
	 * 
	 * @param username
	 * @return
	 * @throws ParvanServiceException
	 */
	/*public UserVO loadUserByUsername(String username) throws ParvanServiceException;*/

	/**
	 * 
	 * @param userInfo
	 * @return
	 * @throws ParvanServiceException
	 */
	public List<ObjectAccessScopeVO> loadScopes(UserInfo userInfo) throws ParvanServiceException;

}
