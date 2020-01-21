package com.parvanpajooh.issuemanager.service;

import java.util.List;

import com.parvanpajooh.commons.platform.ejb.model.UserInfo;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanServiceException;
import com.parvanpajooh.issuemanager.model.Membership;
import com.parvanpajooh.issuemanager.model.vo.MembershipVO;

/**
 * 
 * @author
 * 
 */
public interface MembershipService extends GenericService<Membership, Long> {

	public List<MembershipVO> loadMemberships(UserInfo userInfo) throws ParvanServiceException;

	public List<MembershipVO> loadMembershipByGroupId(UserInfo userInfo, Long id) throws ParvanServiceException;
	
	public List<MembershipVO> findByMemberId(UserInfo userInfo) throws ParvanServiceException;

	public void editMembership(UserInfo userInfo, Long groupId, String[] membersId, String[] typesId, String[] allItems) throws ParvanServiceException;
}
