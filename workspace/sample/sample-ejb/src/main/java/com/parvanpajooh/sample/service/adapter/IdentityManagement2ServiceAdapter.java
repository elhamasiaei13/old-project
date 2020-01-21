package com.parvanpajooh.sample.service.adapter;

import java.util.List;

import com.parvanpajooh.client.common.PageList;
import com.parvanpajooh.commons.platform.ejb.exceptions.ParvanServiceException;
import com.parvanpajooh.commons.platform.ejb.exceptions.ParvanUnrecoverableException;
import com.parvanpajooh.sample.model.vo.ObjectAccessScopeVO;
import com.parvanpajooh.sample.model.vo.UserVO;

public interface IdentityManagement2ServiceAdapter {

	/**
	 * 
	 * @param query
	 * @param page
	 * @param size
	 * @return
	 * @throws ParvanUnrecoverableException
	 */
	public PageList<UserVO> findUsers(String query, int page, int size) throws ParvanUnrecoverableException;

	/**
	 * 
	 * @return
	 * @throws ParvanUnrecoverableException
	 */
	public List<ObjectAccessScopeVO> loadScopes() throws ParvanUnrecoverableException;

	/**
	 * 
	 * @param username
	 * @return
	 */
	/*public UserVO loadUserByUsername(String username) throws ParvanServiceException;*/
	
	/**
	 * 
	 * @param uuid
	 * @return
	 * @throws ParvanServiceException
	 */
	public UserVO loadUserByUuid(String uuid) throws ParvanServiceException;

}
