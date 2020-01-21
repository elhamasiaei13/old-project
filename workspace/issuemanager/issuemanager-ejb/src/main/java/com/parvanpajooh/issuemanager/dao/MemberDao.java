package com.parvanpajooh.issuemanager.dao;

import com.parvanpajooh.issuemanager.common.exceptions.ParvanDaoException;
import com.parvanpajooh.issuemanager.dao.GenericDao;
import com.parvanpajooh.issuemanager.model.Member;

/**
 * 
 * @author 
 * 
 */
public interface MemberDao extends GenericDao<Member, Long> {

	
	public Member searchByUsernamePass(String username, String pass) throws ParvanDaoException;

}