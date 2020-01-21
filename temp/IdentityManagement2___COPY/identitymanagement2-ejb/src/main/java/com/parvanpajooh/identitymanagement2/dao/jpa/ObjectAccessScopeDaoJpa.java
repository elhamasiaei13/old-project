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
import com.parvanpajooh.identitymanagement2.dao.PermissionDao;
import com.parvanpajooh.identitymanagement2.dao.RoleDao;
import com.parvanpajooh.identitymanagement2.model.Permission;
import com.parvanpajooh.identitymanagement2.model.Role;
import com.parvanpajooh.identitymanagement2.model.UserGroup;

/**
 * 
 * @author moosa
 *
 */
@Stateless
public class ObjectAccessScopeDaoJpa extends GenericDaoJpa<Permission, Long>implements PermissionDao {

	public ObjectAccessScopeDaoJpa() {
		super(Permission.class);
	}

	@Override
	protected List<Predicate> buildPredicateList(BaseCriteria cri, CriteriaBuilder builder, Metamodel metamodel, Root<Permission> root, Map<String, Join> joins) {

		// LOG
		log.debug("Entering buildPredicateList( ... )");

		List<Predicate> predicateList = new ArrayList<Predicate>();

		// LOG
		log.debug("Leaving buildPredicateList(): {}", predicateList.size());

		return predicateList;
	}

}
