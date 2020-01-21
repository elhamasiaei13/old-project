package com.parvanpajooh.issuemanager.service.impl;

import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.parvanpajooh.commons.platform.ejb.model.UserInfo;
import com.parvanpajooh.issuemanager.common.exceptions.ErrorCode;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanRecoverableException;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanServiceException;
import com.parvanpajooh.issuemanager.model.Relation;
import com.parvanpajooh.issuemanager.model.enums.RoleEnum;
import com.parvanpajooh.issuemanager.model.vo.RelationVO;
import com.parvanpajooh.issuemanager.service.RelationLocalService;
import com.parvanpajooh.issuemanager.service.RelationService;


@Stateless
public class RelationServiceImpl extends GenericServiceImpl<Relation, Long>
		implements RelationService {
	/**
	 * Log variable for all child classes.
	 */
	static final Logger log = LoggerFactory.getLogger(RelationServiceImpl.class);

	private RelationLocalService relationLocalService;

	@Inject
	public void setUserLocalService(RelationLocalService relationLocalService) {
		this.localService = relationLocalService;
		this.relationLocalService = relationLocalService;
	}

	@Override
	public List<RelationVO> loadRelationByTaskId(UserInfo userInfo, Long id)
			throws ParvanServiceException {
		log.debug("Entering loadRelationByTaskId(userInfo={})", userInfo);
		return relationLocalService.loadRelationByTaskId(id);

	}
	
	public void delete(UserInfo userInfo, Long id) throws ParvanServiceException {
		log.debug("Entering deleteRelation(userInfo={})", userInfo);
		
		// check access
		Set<String> userRoles = userInfo.getRoleNames();
		if (!(userRoles.contains(RoleEnum.ADMIN.value) && userRoles.contains(RoleEnum.MANAGER.value))) {					
			throw new ParvanRecoverableException(ErrorCode.HAS_NOT_ACCESS);
		}
		
		relationLocalService.delete(id);
	}

}
