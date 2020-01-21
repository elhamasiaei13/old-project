package com.parvanpajooh.issuemanager.dao.jpa;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Metamodel;

import com.parvanpajooh.commons.platform.ejb.model.vo.BaseCriteria;
import com.parvanpajooh.commons.util.Validator;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanDaoException;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanException;
import com.parvanpajooh.issuemanager.dao.MemberDao;
import com.parvanpajooh.issuemanager.model.Member;
import com.parvanpajooh.issuemanager.model.Member_;
import com.parvanpajooh.issuemanager.model.criteria.MemberCriteria;

/**
 * 
 * @author ali
 * 
 */
@Stateless
public class MemberDaoJpa extends GenericDaoJpa<Member, Long>implements MemberDao {

	public MemberDaoJpa() {
		super(Member.class);
	}

	@Override
	protected List<Predicate> buildPredicateList(BaseCriteria cri, CriteriaBuilder builder, Metamodel metamodel, Root<Member> root, Map<String, Join> joins)
			throws ParvanException {

		// LOG
		log.debug("Entering buildPredicateList( ... )");

		MemberCriteria memberCriteria = (MemberCriteria) cri;

		List<Predicate> predicateList = new ArrayList<Predicate>();

		String name = memberCriteria.getName();
		
		List<Long> members = memberCriteria.getIds();
		
		Boolean active = memberCriteria.getActive();

		// ----------------------------------------------------------------
		// active
		// ----------------------------------------------------------------
		if (Validator.isNotNull(active)) {
			Predicate predicate = builder.equal(root.<Boolean> get(Member_.active), active);
			predicateList.add(predicate);
		}

		// ----------------------------------------------------------------
		// firstName & lastName & username
		// ----------------------------------------------------------------
		if (Validator.isNotNull(name)) {

			Predicate predicate = builder.or(builder.like(root.<String> get(Member_.firstName), "%" + name + "%"),
					builder.like(root.<String> get(Member_.lastName), "%" + name + "%"), builder.like(root.<String> get(Member_.username), "%" + name + "%"));
			predicateList.add(predicate);
		}
		
		// ----------------------------------------------------------------
		// ids
		// ----------------------------------------------------------------
		if (Validator.isNotNull(members)) {

			List<Predicate> preList = new ArrayList<Predicate>();

			for (Long id : members) {
				Predicate predicate = builder.equal(root.<Long> get(Member_.id), id);
				preList.add(predicate);
			}
			predicateList.add(builder.or(preList.toArray(new Predicate[0])));
		}
		

		basePredicate((BaseCriteria) memberCriteria, builder, root, predicateList);

		// LOG
		log.debug("Leaving buildPredicateList(): {}", predicateList.size());

		return predicateList;
	}

	@Override
	public Member searchByUsernamePass(String username, String pass) throws ParvanDaoException {
		Query query = getEntityManager().createQuery("from Member where username = ? AND password = ?");
		query.setParameter(1, username);
		query.setParameter(2, pass);
		List<Member> members = query.getResultList();

		if (members != null && members.size() > 0) {
			return members.get(0);
		} else {
			return null;
		}
	}
}
