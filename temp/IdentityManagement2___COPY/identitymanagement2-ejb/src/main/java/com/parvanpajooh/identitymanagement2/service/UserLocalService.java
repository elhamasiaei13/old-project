package com.parvanpajooh.identitymanagement2.service;

import java.util.List;

import com.parvanpajooh.common.UserInfo;
import com.parvanpajooh.common.exceptions.ParvanServiceException;
import com.parvanpajooh.common.vo.BaseVO;
import com.parvanpajooh.ecourier.service.GenericLocalService;
import com.parvanpajooh.identitymanagement2.model.User;
import com.parvanpajooh.identitymanagement2.model.vo.UserVO;


/**
 * 
 * @author ali
 *
 */
public interface UserLocalService extends GenericLocalService<User, Long> {

	public UserVO save(BaseVO baseVO, UserInfo userInfo,String[] ids) throws ParvanServiceException ;

	BaseVO changePass(BaseVO baseVO, UserInfo userInfo) throws ParvanServiceException;
	
}
