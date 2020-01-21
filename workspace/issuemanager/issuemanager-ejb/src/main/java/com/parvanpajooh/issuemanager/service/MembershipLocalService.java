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
public interface MembershipLocalService extends GenericLocalService<Membership, Long> {

	public List<MembershipVO> loadMemberships() throws ParvanServiceException;

	public List<MembershipVO> loadMembershipByGroupId(Long id) throws ParvanServiceException;

	public List<MembershipVO> findByMemberId(Long id) throws ParvanServiceException;

	public void editMembership(UserInfo userInfo, Long groupId, String[] membersId, String[] typesId, String[] allItems) throws ParvanServiceException;

}
