package com.parvanpajooh.issuemanager.dao;

import java.util.List;

import com.parvanpajooh.issuemanager.common.exceptions.ParvanDaoException;
import com.parvanpajooh.issuemanager.model.Membership;


/**
 * 
 * @author 
 *
 */
public interface MembershipDao extends GenericDao<Membership, Long> {

	
	public List<Membership> findByGroupID(Long id) throws ParvanDaoException;
	
	public List<Membership> findByMemberId(Long id) throws ParvanDaoException;
	
	public void editMembershipByGroupId(Long groupId, String[] membersId,String[] typesId, String[] allItems) throws ParvanDaoException;
	
}