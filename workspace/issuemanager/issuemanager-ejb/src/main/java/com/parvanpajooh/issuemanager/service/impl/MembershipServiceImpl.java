package com.parvanpajooh.issuemanager.service.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.parvanpajooh.commons.platform.ejb.model.UserInfo;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanServiceException;
import com.parvanpajooh.issuemanager.model.Membership;
import com.parvanpajooh.issuemanager.model.vo.MembershipVO;
import com.parvanpajooh.issuemanager.service.MembershipLocalService;
import com.parvanpajooh.issuemanager.service.MembershipService;

/**
 * 
 * @author ali
 * 
 */
@Stateless
public class MembershipServiceImpl extends GenericServiceImpl<Membership, Long>
		implements MembershipService {
	/**
	 * Log variable for all child classes.
	 */
	static final Logger log = LoggerFactory
			.getLogger(MembershipServiceImpl.class);

	private MembershipLocalService membershipLocalService;

	@Inject
	public void setUserLocalService(
			MembershipLocalService membershipLocalService) {
		this.localService = membershipLocalService;
		this.membershipLocalService = membershipLocalService;
	}

	@Override
	public List<MembershipVO> loadMemberships(UserInfo userInfo)
			throws ParvanServiceException {
		log.debug("Entering loadMemberships(userInfo={})", userInfo);
		return membershipLocalService.loadMemberships();
	}

	@Override
	public List<MembershipVO> loadMembershipByGroupId(UserInfo userInfo, Long id)
			throws ParvanServiceException {
		return membershipLocalService.loadMembershipByGroupId(id);
	}

	@Override
	public void editMembership(UserInfo userInfo, Long groupId,
			String[] membersId, String[] typeIds, String[] allItems) throws ParvanServiceException{
		membershipLocalService.editMembership(userInfo, groupId, membersId, typeIds, allItems);

	}
	
	@Override
	public void delete(UserInfo userInfo, Long id) throws ParvanServiceException {
		log.debug("Entering deleteTask(userInfo={})", userInfo);
		membershipLocalService.delete(id,userInfo);
	}

	@Override
	public List<MembershipVO> findByMemberId(UserInfo userInfo) throws ParvanServiceException {
		log.debug("Entering findByMemberId(userInfo={})", userInfo);
		return membershipLocalService.findByMemberId(userInfo.getUserId());
		
	}

}
