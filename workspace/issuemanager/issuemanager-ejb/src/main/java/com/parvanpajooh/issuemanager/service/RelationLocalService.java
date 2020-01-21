package com.parvanpajooh.issuemanager.service;

import java.util.List;

import com.parvanpajooh.issuemanager.common.exceptions.ParvanServiceException;
import com.parvanpajooh.issuemanager.model.Relation;
import com.parvanpajooh.issuemanager.model.vo.RelationVO;

/**
 * 
 * @author
 * 
 */
public interface RelationLocalService extends GenericLocalService<Relation, Long> {

	public List<RelationVO> loadRelationByTaskId(Long id) throws ParvanServiceException;

	public void delete(Long id) throws ParvanServiceException;
}
