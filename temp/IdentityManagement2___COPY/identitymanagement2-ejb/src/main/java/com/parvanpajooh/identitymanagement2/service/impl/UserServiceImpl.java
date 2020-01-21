package com.parvanpajooh.identitymanagement2.service.impl;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.parvanpajooh.common.UserInfo;
import com.parvanpajooh.common.exceptions.ParvanServiceException;
import com.parvanpajooh.common.vo.BaseVO;
import com.parvanpajooh.ecourier.service.impl.GenericServiceImpl;
import com.parvanpajooh.identitymanagement2.model.User;
import com.parvanpajooh.identitymanagement2.model.vo.UserVO;
import com.parvanpajooh.identitymanagement2.service.UserLocalService;
import com.parvanpajooh.identitymanagement2.service.UserService;

/**
 * 
 * @author ali
 *
 */
@Stateless
public class UserServiceImpl extends GenericServiceImpl<User, Long>implements UserService {

	/**
	 * Log variable for all child classes.
	 */
	static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	private UserLocalService userLocalService;

	@Inject
	public void setUserLocalService(UserLocalService userLocalService) {
		this.localService = userLocalService;
		this.userLocalService = userLocalService;
	}

	@Override
	public UserVO save(UserInfo userInfo, UserVO userVO, String[] ids) throws ParvanServiceException {
		return userLocalService.save(userVO, userInfo, ids);
	}

	@Override
	public BaseVO changePass(BaseVO baseVO, UserInfo userInfo) throws ParvanServiceException {
		return userLocalService.changePass(baseVO, userInfo);

	}
}
