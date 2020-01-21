package com.parvanpajooh.identitymanagement2.dao.jpa;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Metamodel;

import com.parvanpajooh.common.vo.BaseCriteria;
import com.parvanpajooh.ecourier.dao.jpa.GenericDaoJpa;
import com.parvanpajooh.identitymanagement2.dao.ObjectAccessScopeDao;
import com.parvanpajooh.identitymanagement2.model.ObjectAccessScope;
import com.parvanpajooh.identitymanagement2.model.Permission;

/**
 * 
 * @author moosa
 *
 */
@Stateless
public class PermissionDaoJpa extends GenericDaoJpa<ObjectAccessScope, Long>implements ObjectAccessScopeDao {

	public PermissionDaoJpa() {
		super(ObjectAccessScope.class);
	}

	@Override
	protected List<Predicate> buildPredicateList(BaseCriteria cri, CriteriaBuilder builder, Metamodel metamodel, Root<ObjectAccessScope> root, Map<String, Join> joins) {

		// LOG
		log.debug("Entering buildPredicateList( ... )");

		List<Predicate> predicateList = new ArrayList<Predicate>();

		// LOG
		log.debug("Leaving buildPredicateList(): {}", predicateList.size());

		return predicateList;
	}

}
