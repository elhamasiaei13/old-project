package com.parvanpajooh.identitymanagement2.dao.jpa;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Metamodel;

import com.parvanpajooh.common.util.DateUtil;
import com.parvanpajooh.common.util.Validator;
import com.parvanpajooh.common.vo.BaseCriteria;
import com.parvanpajooh.ecourier.dao.jpa.GenericDaoJpa;
import com.parvanpajooh.identitymanagement2.dao.RoleDao;
import com.parvanpajooh.identitymanagement2.model.Role;
import com.parvanpajooh.identitymanagement2.model.Role_;
import com.parvanpajooh.identitymanagement2.model.User;
import com.parvanpajooh.identitymanagement2.model.criteria.RoleCriteria;
import com.parvanpajooh.identitymanagement2.model.enums.RoleTypeEnum;

/**
 * 
 * @author moosa
 *
 */
@Stateless
public class RoleDaoJpa extends GenericDaoJpa<Role, Long>implements RoleDao {

	public RoleDaoJpa() {
		super(Role.class);
	}

	@Override
	protected List<Predicate> buildPredicateList(BaseCriteria cri, CriteriaBuilder builder, Metamodel metamodel, Root<Role> root, Map<String, Join> joins) {

		// LOG
		log.debug("Entering buildPredicateList( ... )");

		RoleCriteria roleCriteria = (RoleCriteria) cri;
		List<Predicate> predicateList = new ArrayList<Predicate>();
		String canonicalSearchName = roleCriteria.getCanonicalSearchRoleName();
		String type = roleCriteria.getType();
		Boolean active = roleCriteria.getActive();
		
		// ----------------------------------------------------------------
		// Active
		// ----------------------------------------------------------------
		if (Validator.isNotNull(active)) {
			Predicate predicate = builder.equal(root.<Boolean> get(Role_.active), active);
			predicateList.add(predicate);
		}
		
		// ----------------------------------------------------------------
		// Type
		// ----------------------------------------------------------------
		if (Validator.isNotNull(type)) {
			Predicate predicate = builder.equal(root.<RoleTypeEnum> get(Role_.type), RoleTypeEnum.fromValue(type));
			predicateList.add(predicate);
		}

		// ----------------------------------------------------------------
		// canonicalSearchName
		// ----------------------------------------------------------------
		if (Validator.isNotNull(canonicalSearchName)) {
			List<Predicate> preList = new ArrayList<Predicate>();

			String[] splited = canonicalSearchName.trim().split("\\s+");

			for (String str : splited) {

				Predicate predicate = builder.or(builder.like(root.<String> get(Role_.roleName), "%" + str + "%"),
						builder.like(root.<String> get(Role_.description), "%" + str + "%"));
				preList.add(predicate);

			}
			predicateList.add(builder.and(preList.toArray(new Predicate[0])));
		}


		basePredicate((BaseCriteria) roleCriteria, builder, root, predicateList);

		// LOG
		log.debug("Leaving buildPredicateList(): {}", predicateList.size());

		return predicateList;
	}	
	protected void basePredicate(BaseCriteria cri, CriteriaBuilder builder, Root<Role> root, List<Predicate> predicateList) {

		if (Validator.isNotNull(cri.getCreateUserId())) {
			Predicate predicate = builder.equal(root.<String> get("createUserId"), cri.getCreateUserId());
			predicateList.add(predicate);
		}

		if (Validator.isNotNull(cri.getUpdateUserId())) {
			Predicate predicate = builder.equal(root.<String> get("updateUserId"), cri.getUpdateUserId());
			predicateList.add(predicate);
		}

		if (Validator.isNotNull(cri.getCreateDate())) {
			Predicate predicate = builder.equal(root.<LocalDateTime> get("createDate"), cri.getCreateDate());
			predicateList.add(predicate);
		}

		if (Validator.isNotNull(cri.getUpdateDate())) {
			Predicate predicate = builder.equal(root.<LocalDateTime> get("updateDate"), cri.getCreateDate());
			predicateList.add(predicate);
		}

		if (Validator.isNotNull(cri.getCreateDateFrom())) {
			LocalDateTime localDateTime = DateUtil.getFirstTimeOfDate(cri.getCreateDateFrom());

			Predicate predicate = builder.greaterThanOrEqualTo(root.<LocalDateTime> get("createDate"), localDateTime);
			predicateList.add(predicate);
		}

		if (Validator.isNotNull(cri.getCreateDateTo())) {
			LocalDateTime localDateTime = DateUtil.getLastTimeOfDate(cri.getCreateDateTo());

			Predicate predicate = builder.lessThanOrEqualTo(root.<LocalDateTime> get("createDate"), localDateTime);
			predicateList.add(predicate);
		}

		if (Validator.isNotNull(cri.getUpdateDateFrom())) {
			LocalDateTime localDateTime = DateUtil.getFirstTimeOfDate(cri.getUpdateDateFrom());

			Predicate predicate = builder.greaterThanOrEqualTo(root.<LocalDateTime> get("updateDate"), localDateTime);
			predicateList.add(predicate);
		}

		if (Validator.isNotNull(cri.getUpdateDateTo())) {
			LocalDateTime localDateTime = DateUtil.getLastTimeOfDate(cri.getUpdateDateTo());

			Predicate predicate = builder.lessThanOrEqualTo(root.<LocalDateTime> get("updateDate"), localDateTime);
			predicateList.add(predicate);
		}
	}
}
