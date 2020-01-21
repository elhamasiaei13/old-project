package com.parvanpajooh.issuemanager.service.impl;

import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.parvanpajooh.commons.platform.ejb.model.UserInfo;
import com.parvanpajooh.issuemanager.common.exceptions.ErrorCode;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanRecoverableException;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanServiceException;
import com.parvanpajooh.issuemanager.model.Member;
import com.parvanpajooh.issuemanager.model.enums.RoleEnum;
import com.parvanpajooh.issuemanager.model.vo.MemberVO;
import com.parvanpajooh.issuemanager.service.MemberLocalService;
import com.parvanpajooh.issuemanager.service.MemberService;

/**
 * 
 * @author
 * 
 */
@Stateless
public class MemberServiceImpl extends GenericServiceImpl<Member, Long>implements MemberService {
	/**
	 * Log variable for all child classes.
	 */
	static final Logger log = LoggerFactory.getLogger(MemberServiceImpl.class);

	private MemberLocalService memberLocalService;

	@Inject
	public void setUserLocalService(MemberLocalService memberLocalService) {
		this.localService = memberLocalService;
		this.memberLocalService = memberLocalService;
	}
	
	@Override
	public MemberVO searchMemberByUsernamePass(String username, String pass) throws ParvanServiceException {
		log.debug("Entering searchMemberByUsernamPass(userInfo={})");
		return memberLocalService.searchMemberByUsernamePass(username, pass);

	}

	@Override
	public void changePass(UserInfo userInfo, MemberVO member) throws ParvanServiceException {
		log.debug("Entering editMember(userInfo={})", userInfo);
		memberLocalService.chanePass(userInfo, member);
		
	}
	
	@Override
	public void delete(UserInfo userInfo, Long id) throws ParvanServiceException {
		log.debug("Entering deleteMember(userInfo={})", userInfo);
		
		// check access
		Set<String> userRoles = userInfo.getRoleNames();
		if (!(userRoles.contains(RoleEnum.ADMIN.value) && userRoles.contains(RoleEnum.MANAGER.value))) {					
			throw new ParvanRecoverableException(ErrorCode.HAS_NOT_ACCESS);
		}
		
		memberLocalService.delete(id);
	}
}
