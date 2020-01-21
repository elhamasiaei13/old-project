package com.parvanpajooh.issuemanager.dao.jpa;

import java.time.LocalDateTime;
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

import com.parvanpajooh.commons.platform.ejb.model.CurrentContext;
import com.parvanpajooh.commons.platform.ejb.model.UserInfo;
import com.parvanpajooh.commons.platform.ejb.model.vo.BaseCriteria;
import com.parvanpajooh.commons.util.Validator;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanDaoException;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanException;
import com.parvanpajooh.issuemanager.dao.MembershipDao;
import com.parvanpajooh.issuemanager.model.Group;
import com.parvanpajooh.issuemanager.model.Group_;
import com.parvanpajooh.issuemanager.model.Member;
import com.parvanpajooh.issuemanager.model.Membership;
import com.parvanpajooh.issuemanager.model.Membership_;
import com.parvanpajooh.issuemanager.model.Task;
import com.parvanpajooh.issuemanager.model.criteria.MembershipCriteria;
import com.parvanpajooh.issuemanager.model.enums.MemberTypeEnum;
import com.parvanpajooh.issuemanager.model.vo.GroupVO;

@Stateless
public class MembershipDaoJpa extends GenericDaoJpa<Membership, Long>implements MembershipDao {

	public MembershipDaoJpa() {
		super(Membership.class);
	}

	@Override
	public List<Membership> findByGroupID(Long id) throws ParvanDaoException {
		Query query = getEntityManager().createQuery("from Membership m where m.group.id =? and active =?");
		query.setParameter(1, id);
		query.setParameter(2, true);
		List<Membership> membership = query.getResultList();

		return membership;

	}
	
	@Override
	public List<Membership> findByMemberId(Long id) throws ParvanDaoException {
		Query query = getEntityManager().createQuery("from Membership m where m.member.id =? and active =?");
		query.setParameter(1, id);
		query.setParameter(2, true);
		List<Membership> membership = query.getResultList();

		return membership;

	}

	@Override
	protected List<Predicate> buildPredicateList(BaseCriteria cri, CriteriaBuilder builder, Metamodel metamodel, Root<Membership> root, Map<String, Join> joins)
			throws ParvanException {

		// LOG
		log.debug("Entering buildPredicateList( ... )");

		MembershipCriteria membershipCriteria = (MembershipCriteria) cri;
		Boolean active = membershipCriteria.getActive();

		List<Predicate> predicateList = new ArrayList<Predicate>();
		GroupVO group = membershipCriteria.getGroup();

		// ----------------------------------------------------------------
		// active
		// ----------------------------------------------------------------
		if (Validator.isNotNull(active)) {
			Predicate predicate = builder.equal(root.<Boolean> get(Membership_.active), active);
			predicateList.add(predicate);
		}
		
		// ----------------------------------------------------------------
		// group
		// ----------------------------------------------------------------
		if (Validator.isNotNull(group) && Validator.isNotNull(group.getId())) {

			Join<Task, Group> groupJoin = root.join("group");
			if (Validator.isNotNull(group.getId())) {

				Predicate predicate = builder.equal(groupJoin.<Long> get(Group_.id), group.getId());
				predicateList.add(predicate);
			}

		}

		basePredicate((BaseCriteria) membershipCriteria, builder, root, predicateList);

		// LOG
		log.debug("Leaving buildPredicateList(): {}", predicateList.size());

		return predicateList;
	}

	@Override
	public void editMembershipByGroupId(Long groupId, String[] membersId, String[] typesId, String[] allItems) throws ParvanDaoException {

		UserInfo userInfo = CurrentContext.getCurrentUserInfo().get();

		Query queryGroup = getEntityManager().createQuery("from Group where id=?");
		queryGroup.setParameter(1, groupId);
		List<Group> groupList = queryGroup.getResultList();

		for (String id : membersId) {
			Membership membership = new Membership();
			LocalDateTime now = LocalDateTime.now();
			int index = 0;
			for (String id2 : allItems) {
				if (id2.equals(id)) {
					for (MemberTypeEnum t : MemberTypeEnum.values()) {
						if (t.name().equals(typesId[index]))
							membership.setType(t);
					}
					break;
				}
				index++;
			}
			membership.setCreateDate(now);
			membership.setUpdateDate(now);
			membership.setCreateUserId(userInfo.getUserId());
			membership.setUpdateUserId(userInfo.getUserId());

			Query queryMember = getEntityManager().createQuery("from Member where id=?");
			queryMember.setParameter(1, Long.parseLong(id));
			List<Member> memberList = queryMember.getResultList();
			membership.setMember(memberList.get(0));
			membership.setGroup(groupList.get(0));
			membership.setActive(true);

			Query queryMembership = getEntityManager().createQuery("from Membership where memberId=? and groupId=?");
			queryMembership.setParameter(1, Long.parseLong(id));
			queryMembership.setParameter(2, groupId);
			List<Membership> membershipList = queryMembership.getResultList();
			if (Validator.isNotNull(membershipList)) {
				Membership savedMembership = get(membershipList.get(0).getId());
				membership.setId(savedMembership.getId());
				membership.setVersion(savedMembership.getVersion());
				
			} 
			getEntityManager().merge(membership);
		}
	}
}
