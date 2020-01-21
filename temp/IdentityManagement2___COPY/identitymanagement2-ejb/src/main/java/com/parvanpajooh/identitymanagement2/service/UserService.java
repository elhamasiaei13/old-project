package com.parvanpajooh.identitymanagement2.service;

import com.parvanpajooh.common.UserInfo;
import com.parvanpajooh.common.exceptions.ParvanServiceException;
import com.parvanpajooh.common.vo.BaseVO;
import com.parvanpajooh.ecourier.service.GenericService;
import com.parvanpajooh.identitymanagement2.model.User;
import com.parvanpajooh.identitymanagement2.model.vo.UserVO;

/**
 * 
 * @author ali
 *
 */
public interface UserService extends GenericService<User, Long> {

	UserVO save(UserInfo userInfo, UserVO userVO, String[] ids) throws ParvanServiceException;

	BaseVO changePass(BaseVO baseVO, UserInfo userInfo) throws ParvanServiceException;

}
