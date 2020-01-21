package com.parvanpajooh.sample.service.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.parvanpajooh.client.common.PageList;
import com.parvanpajooh.commons.platform.ejb.exceptions.ParvanServiceException;
import com.parvanpajooh.commons.platform.ejb.exceptions.ParvanUnrecoverableException;
import com.parvanpajooh.sample.model.vo.ObjectAccessScopeVO;
import com.parvanpajooh.sample.model.vo.UserVO;
import com.parvanpajooh.sample.service.UserLocalService;
import com.parvanpajooh.sample.service.adapter.IdentityManagement2ServiceAdapter;

/**
 * @author dev-03
 *
 */
@Stateless
public class UserLocalServiceImpl implements UserLocalService {

	/**
	 * Log variable for all child classes.
	 */
	static final Logger log = LoggerFactory.getLogger(UserLocalServiceImpl.class);

	@Inject
	private IdentityManagement2ServiceAdapter idm2ServiceAdapter;

	@Override
	public PageList<UserVO> findUsers(String query, int page, int size) throws ParvanServiceException {
		log.debug("Entering findUsers( query={} , page={} , size={})", query, page, size);
		PageList<UserVO> userListVO = null;
		try {
			userListVO = idm2ServiceAdapter.findUsers(query, page, size);
		} catch (Exception e) {
			throw new ParvanUnrecoverableException("Error occurred while finding users:", e);
		}
		log.debug("Leaving findUsers(): ret={}", (userListVO != null) ? userListVO.getTotal() : "NULL");
		return userListVO;
	}

	/*@Override
	public UserVO loadUserByUsername(String username) throws ParvanServiceException {
		log.debug("Entering loadUserByUsername( username={})", username);
		UserVO user = null;
		try {
			user = idm2ServiceAdapter.loadUserByUsername(username);
		} catch (Exception e) {
			throw new ParvanUnrecoverableException("Error occurred while loadUserByUsername :", e);
		}
		log.debug("Leaving loadUserByUsername(): ret={}", user);
		return user;
	}*/

	@Override
	public List<ObjectAccessScopeVO> loadScopes() throws ParvanServiceException {

		// LOG
		log.debug("Entering loadScops()");

		List<ObjectAccessScopeVO> list = null;

		try {

			list = idm2ServiceAdapter.loadScopes();

		} catch (Exception e) {
			throw new ParvanUnrecoverableException("Error occurred while loadScopes", e);
		}

		// LOG
		log.debug("Leaving loadScopes(): ret={}", (list != null) ? list.size() : "NULL");

		return list;

	}
}
