package com.parvanpajooh.issuemanager.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.parvanpajooh.commons.platform.ejb.model.UserInfo;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanDaoException;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanServiceException;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanUnrecoverableException;
import com.parvanpajooh.issuemanager.dao.MembershipDao;
import com.parvanpajooh.issuemanager.model.Membership;
import com.parvanpajooh.issuemanager.model.vo.MembershipVO;
import com.parvanpajooh.issuemanager.service.MembershipLocalService;

/**
 * 
 * @author
 */
@Stateless
public class MembershipLocalServiceImpl extends GenericLocalServiceImpl<Membership, Long>implements MembershipLocalService {
	/**
	 * Log variable for all child classes.
	 */
	static final Logger log = LoggerFactory.getLogger(MembershipLocalServiceImpl.class);

	public MembershipDao membershipDao;

	@Inject
	public void setMembershipDao(MembershipDao membershipDao) {
		this.dao = membershipDao;
		this.membershipDao = membershipDao;
	}

	@Override
	public List<MembershipVO> loadMemberships() throws ParvanServiceException {
		log.info("Entering loadMemberships()");

		List<Membership> memberships = null;
		try {
			memberships = membershipDao.findAll();
		} catch (ParvanDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<MembershipVO> result = new ArrayList<MembershipVO>();
		Map<Long, MembershipVO> map = new HashMap<Long, MembershipVO>();

		Iterator<Membership> iterator = memberships.iterator();
		while (iterator.hasNext()) {
			Membership thisMember = iterator.next();
			MembershipVO membershipVO = thisMember.toVOLite();
			result.add(membershipVO);

			map.put(thisMember.getId(), membershipVO);
			iterator.remove();
		}

		return result;
	}

	@Override
	public List<MembershipVO> loadMembershipByGroupId(Long id) throws ParvanServiceException {

		log.info("Entering loadMembership()");

		List<Membership> memberships = null;

		try {
			memberships = membershipDao.findByGroupID(id);
		} catch (Exception e) {
			throw new ParvanUnrecoverableException("Error occurred while load membership by groupId.", e);
		}

		List<MembershipVO> result = new ArrayList<MembershipVO>();
		Map<Long, MembershipVO> map = new HashMap<Long, MembershipVO>();

		Iterator<Membership> iterator = memberships.iterator();
		while (iterator.hasNext()) {
			Membership thisMember = iterator.next();
			MembershipVO membershipVO = thisMember.toVOLite();
			result.add(membershipVO);

			map.put(thisMember.getId(), membershipVO);
			iterator.remove();
		}

		return result;
	}

	@Override
	public void editMembership(UserInfo userInfo, Long groupId, String[] memberIds, String[] typeIds, String[] allItems) throws ParvanServiceException {
		try {
			membershipDao.editMembershipByGroupId(groupId, memberIds, typeIds, allItems);
		} catch (Exception e) {
			throw new ParvanUnrecoverableException("Error occurred while edit membership by Id.", e);
		}
	}

	@Override
	public void delete(Long id,UserInfo userInfo) throws ParvanServiceException {
		try {

			Membership membership = null;
			LocalDateTime now = LocalDateTime.now();

			membership = membershipDao.get(id);
			membership.setActive(false);
			membership.setUpdateDate(now);

			// refresh entity
			membershipDao.refresh(membership);

		} catch (Exception e) {
			throw new ParvanUnrecoverableException("Error occurred while deleting membership.", e);
		}
	}

	@Override
	public List<MembershipVO> findByMemberId(Long id) throws ParvanServiceException {
		log.info("Entering loadMembership()");

		List<Membership> memberships = null;

		try {
			memberships = membershipDao.findByMemberId(id);
		} catch (Exception e) {
			throw new ParvanUnrecoverableException("Error occurred while load membership by memberId.", e);
		}

		List<MembershipVO> result = new ArrayList<MembershipVO>();
		Map<Long, MembershipVO> map = new HashMap<Long, MembershipVO>();

		Iterator<Membership> iterator = memberships.iterator();
		while (iterator.hasNext()) {
			Membership thisMember = iterator.next();
			MembershipVO membershipVO = thisMember.toVOLite();
			result.add(membershipVO);

			map.put(thisMember.getId(), membershipVO);
			iterator.remove();
		}
		return result;		
	}
}
