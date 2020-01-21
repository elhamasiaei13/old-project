package com.parvanpajooh.sample.dao.jpa;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Metamodel;

import com.parvanpajooh.commons.platform.ejb.dao.jpa.GenericDaoJpa;
import com.parvanpajooh.commons.platform.ejb.exceptions.ParvanException;
import com.parvanpajooh.commons.platform.ejb.model.vo.BaseCriteria;
import com.parvanpajooh.commons.util.Validator;
import com.parvanpajooh.sample.dao.SampleDao;
import com.parvanpajooh.sample.model.Sample;
import com.parvanpajooh.sample.model.criteria.SampleCriteria;
import com.parvanpajooh.sample.model.vo.SampleVO;
import com.parvanpajooh.sample.model.Sample_;

/**
 * 
 * @author
 *
 */
@Stateless
public class SampleDaoJpa extends GenericDaoJpa<Sample, Long>implements SampleDao {

	public SampleDaoJpa() {
		super(Sample.class);
	}

	@Override
	protected List<Predicate> buildPredicateList(
			BaseCriteria cri, 
			CriteriaBuilder builder, 
			Metamodel metamodel, 
			Root<Sample> root,
			@SuppressWarnings("rawtypes") Map<String, Join> joins) throws ParvanException {

		// LOG
		log.debug("Entering buildPredicateList( ... )");

		List<Predicate> predicateList = new ArrayList<Predicate>();

		SampleCriteria generalAgentCriteria = (SampleCriteria) cri;
		String canonicalSearchName = generalAgentCriteria.getCanonicalSearchName();
		Long id = generalAgentCriteria.getId();
		SampleVO parent = generalAgentCriteria.getParent();
		String iataCode = generalAgentCriteria.getIataCode();
		String tel = generalAgentCriteria.getTel();
		String address = generalAgentCriteria.getAddress();
		Boolean branch = generalAgentCriteria.getBranch();
		Boolean active = generalAgentCriteria.getActive();
		String nodeCode = generalAgentCriteria.getNodeCode();
		String nodeId = generalAgentCriteria.getNodeId();
		String currentAccountNumber = generalAgentCriteria.getCurrentAccountNumber();
		String name = generalAgentCriteria.getName();
		Set<Long> allowedAgentIds = generalAgentCriteria.getAllowedAgentIds();

		// ----------------------------------------------------------------
		// parent
		// ----------------------------------------------------------------
		if (Validator.isNotNull(parent) && Validator.isNotNull(parent.getId())) {

			Join<Sample, Sample> generalAgentJoin = root.join(Sample_.parent);
			if (Validator.isNotNull(parent.getId())) {

				Predicate predicate = builder.equal(generalAgentJoin.<Long> get(Sample_.id), parent.getId());
				predicateList.add(predicate);
			}
		}

		// ----------------------------------------------------------------
		// Id
		// ----------------------------------------------------------------
		if (Validator.isNotNull(id)) {
			Predicate predicate = builder.equal(root.<Long> get(Sample_.id), id);
			predicateList.add(predicate);
		}
		
		// ----------------------------------------------------------------
		// Name
		// ----------------------------------------------------------------
		if (Validator.isNotNull(name)) {
			Predicate predicate = builder.like(root.<String> get(Sample_.name), "%" + name + "%");
			predicateList.add(predicate);
		}

		// ----------------------------------------------------------------
		// IATA Code
		// ----------------------------------------------------------------
		if (Validator.isNotNull(iataCode)) {
			Predicate predicate = builder.like(root.<String> get(Sample_.iataCode), iataCode);
			predicateList.add(predicate);
		}

		// ----------------------------------------------------------------
		// Address
		// ----------------------------------------------------------------
		if (Validator.isNotNull(address)) {
			Predicate predicate = builder.like(root.<String> get(Sample_.address), "%" + address + "%");
			predicateList.add(predicate);
		}

		// ----------------------------------------------------------------
		// Tel
		// ----------------------------------------------------------------
		if (Validator.isNotNull(tel)) {
			Predicate predicate = builder.like(root.<String> get(Sample_.tel), "%" + tel + "%");
			predicateList.add(predicate);
		}

		// ----------------------------------------------------------------
		// Active
		// ----------------------------------------------------------------
		if (Validator.isNotNull(active)) {
			Predicate predicate = builder.equal(root.<Boolean> get(Sample_.active), active);
			predicateList.add(predicate);
		}

		// ----------------------------------------------------------------
		// Branch
		// ----------------------------------------------------------------
		if (Validator.isNotNull(branch)) {
			Predicate predicate = builder.equal(root.<Boolean> get(Sample_.branch), branch);
			predicateList.add(predicate);
		}

		// ----------------------------------------------------------------
		// NodeId
		// ----------------------------------------------------------------
		if (Validator.isNotNull(nodeId)) {
			Predicate predicate = builder.equal(root.<Long> get(Sample_.nodeId), nodeId);
			predicateList.add(predicate);
		}
		
		// ----------------------------------------------------------------
		// NodeCode
		// ----------------------------------------------------------------
		if (Validator.isNotNull(nodeCode)) {
			Predicate predicate = builder.equal(root.<String> get(Sample_.nodeCode), nodeCode);
			predicateList.add(predicate);
		}

		// ----------------------------------------------------------------
		// CanonicalSearchName
		// ----------------------------------------------------------------
		if (Validator.isNotNull(canonicalSearchName)) {
			List<Predicate> preList = new ArrayList<Predicate>();

			String[] splited = canonicalSearchName.trim().split("\\s+");

			for (String str : splited) {

				Predicate predicate = builder.like(root.<String> get(Sample_.canonicalSearchName), "%" + str + "%");
				preList.add(predicate);

			}
			predicateList.add(builder.and(preList.toArray(new Predicate[0])));
		}

		// ----------------------------------------------------------------
		// allowedAgentIds
		// ----------------------------------------------------------------
		if (Validator.isNull(allowedAgentIds)) {
			if (allowedAgentIds == null) {
				allowedAgentIds = new HashSet<>();
			}
			allowedAgentIds.add(0L);
		}
		
		List<Predicate> orPredicates = new ArrayList<Predicate>();
		
		Join<Sample, Sample> generalAgentJoin = root.join(Sample_.parent);		
		
		orPredicates.add(generalAgentJoin.<Long> get(Sample_.id).in(allowedAgentIds));		
		orPredicates.add(root.<Long> get(Sample_.id).in(allowedAgentIds));

		predicateList.add(builder.or(orPredicates.toArray(new Predicate[0])));
		
		// LOG
		log.debug("Leaving buildPredicateList(): {}", predicateList.size());

		return predicateList;
	}

	protected void basePredicate(BaseCriteria cri, CriteriaBuilder builder, Root<Sample> root, List<Predicate> predicateList) {

		if (Validator.isNotNull(cri.getCreateUserId())) {
			Predicate predicate = builder.equal(root.<String> get("createUserId"), cri.getCreateUserId());
			predicateList.add(predicate);
		}

		if (Validator.isNotNull(cri.getUpdateUserId())) {
			Predicate predicate = builder.equal(root.<String> get("updateUserId"), cri.getUpdateUserId());
			predicateList.add(predicate);
		}

		if (Validator.isNotNull(cri.getCreateDateFrom())) {
			Predicate predicate = builder.greaterThanOrEqualTo(root.<LocalDateTime> get("createDate"), cri.getCreateDateFrom());
			predicateList.add(predicate);
		}

		if (Validator.isNotNull(cri.getCreateDateTo())) {
			Predicate predicate = builder.lessThanOrEqualTo(root.<LocalDateTime> get("createDate"), cri.getCreateDateTo());
			predicateList.add(predicate);
		}

		if (Validator.isNotNull(cri.getUpdateDateFrom())) {
			Predicate predicate = builder.greaterThanOrEqualTo(root.<LocalDateTime> get("updateDate"), cri.getUpdateDateFrom());
			predicateList.add(predicate);
		}

		if (Validator.isNotNull(cri.getUpdateDateTo())) {
			Predicate predicate = builder.lessThanOrEqualTo(root.<LocalDateTime> get("updateDate"), cri.getUpdateDateTo());
			predicateList.add(predicate);
		}
	}


}
