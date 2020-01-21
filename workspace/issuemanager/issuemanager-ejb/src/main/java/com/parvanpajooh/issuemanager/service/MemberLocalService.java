package com.parvanpajooh.issuemanager.service;

import com.parvanpajooh.commons.platform.ejb.model.UserInfo;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanServiceException;
import com.parvanpajooh.issuemanager.model.Member;
import com.parvanpajooh.issuemanager.model.vo.MemberVO;

/**
 * 
 * @author
 * 
 */
public interface MemberLocalService extends GenericLocalService<Member, Long> {

	public MemberVO searchMemberByUsernamePass(String username, String pass) throws ParvanServiceException;

	public void chanePass(UserInfo userInfo, MemberVO member) throws ParvanServiceException;

	public void delete(Long id) throws ParvanServiceException;
}
