package com.parvanpajooh.issuemanager.dao.jpa;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Metamodel;

import com.parvanpajooh.commons.platform.ejb.model.vo.BaseCriteria;
import com.parvanpajooh.commons.util.Validator;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanException;
import com.parvanpajooh.issuemanager.dao.GroupDao;
import com.parvanpajooh.issuemanager.model.Group;
import com.parvanpajooh.issuemanager.model.Group_;
import com.parvanpajooh.issuemanager.model.criteria.GroupCriteria;

/**
 * 
 * @author
 * 
 */
@Stateless
public class GroupDaoJpa extends GenericDaoJpa<Group, Long>implements GroupDao {

	public GroupDaoJpa() {
		super(Group.class);
	}

	@Override
	protected List<Predicate> buildPredicateList(BaseCriteria cri, CriteriaBuilder builder, Metamodel metamodel, Root<Group> root, Map<String, Join> joins)
			throws ParvanException {

		// LOG
		log.debug("Entering buildPredicateList( ... )");

		GroupCriteria groupCriteria = (GroupCriteria) cri;

		List<Predicate> predicateList = new ArrayList<Predicate>();

		String name = groupCriteria.getName();

		List<Long> groups = groupCriteria.getIds();
		
		Boolean active = groupCriteria.getActive();

		// ----------------------------------------------------------------
		// active
		// ----------------------------------------------------------------
		if (Validator.isNotNull(active)) {
			Predicate predicate = builder.equal(root.<Boolean> get(Group_.active), active);
			predicateList.add(predicate);
		}
		
		// ----------------------------------------------------------------
		// firstName & lastName & username
		// ----------------------------------------------------------------
		if (Validator.isNotNull(name)) {

			Predicate predicate = builder.or(builder.like(root.<String> get(Group_.name), "%" + name + "%"),
					builder.like(root.<String> get(Group_.description), "%" + name + "%"));
			predicateList.add(predicate);
		}

		// ----------------------------------------------------------------
		// ids
		// ----------------------------------------------------------------
		if (Validator.isNotNull(groups)) {

			List<Predicate> preList = new ArrayList<Predicate>();

			for (Long id : groups) {
				Predicate predicate = builder.equal(root.<Long> get(Group_.id), id);
				preList.add(predicate);
			}
			predicateList.add(builder.or(preList.toArray(new Predicate[0])));
		}

		basePredicate((BaseCriteria) groupCriteria, builder, root, predicateList);

		// LOG
		log.debug("Leaving buildPredicateList(): {}", predicateList.size());

		return predicateList;
	}
}
