package com.parvanpajooh.issuemanager.service;

import java.util.List;

import com.parvanpajooh.issuemanager.common.exceptions.ParvanDaoException;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanServiceException;
import com.parvanpajooh.issuemanager.service.GenericLocalService;
import com.parvanpajooh.issuemanager.model.Group;
import com.parvanpajooh.issuemanager.model.vo.GroupVO;
import com.parvanpajooh.issuemanager.model.vo.MemberVO;

/**
 * 
 * @author
 * 
 */
public interface GroupLocalService extends GenericLocalService<Group, Long> {

	public void delete(Long id) throws ParvanServiceException;}
