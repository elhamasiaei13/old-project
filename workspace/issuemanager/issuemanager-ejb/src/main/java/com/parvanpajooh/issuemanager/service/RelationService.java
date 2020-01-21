package com.parvanpajooh.issuemanager.service;

import java.util.List;

import com.parvanpajooh.commons.platform.ejb.model.UserInfo;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanServiceException;
import com.parvanpajooh.issuemanager.model.Relation;
import com.parvanpajooh.issuemanager.model.vo.RelationVO;

/**
 * 
 * @author
 * 
 */
public interface RelationService extends GenericService<Relation, Long> {

	public List<RelationVO> loadRelationByTaskId(UserInfo userInfo, Long id) throws ParvanServiceException;

	public void delete(UserInfo userInfo, Long id) throws ParvanServiceException;
}
