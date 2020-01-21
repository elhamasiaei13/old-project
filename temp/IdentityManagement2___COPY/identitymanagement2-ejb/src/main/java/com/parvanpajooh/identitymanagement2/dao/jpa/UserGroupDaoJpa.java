package com.parvanpajooh.identitymanagement2.dao.jpa;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Metamodel;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.OptimisticLockException;
import org.hibernate.StaleObjectStateException;
import org.hibernate.exception.ConstraintViolationException;

import com.parvanpajooh.common.CurrentContext;
import com.parvanpajooh.common.UserInfo;
import com.parvanpajooh.common.exceptions.ObjectExistsException;
import com.parvanpajooh.common.exceptions.ParvanDaoException;
import com.parvanpajooh.common.util.DateUtil;
import com.parvanpajooh.common.util.Validator;
import com.parvanpajooh.common.vo.BaseCriteria;
import com.parvanpajooh.ecourier.dao.jpa.GenericDaoJpa;
import com.parvanpajooh.ecourier.model.BaseModel;
import com.parvanpajooh.identitymanagement2.dao.UserDao;
import com.parvanpajooh.identitymanagement2.dao.UserGroupDao;
import com.parvanpajooh.identitymanagement2.model.User;
import com.parvanpajooh.identitymanagement2.model.UserGroup;
import com.parvanpajooh.identitymanagement2.model.UserGroup_;
import com.parvanpajooh.identitymanagement2.model.criteria.UserGroupCriteria;

/**
 * 
 * @author moosa
 *
 */
@Stateless
public class UserGroupDaoJpa extends GenericDaoJpa<UserGroup, Long>implements UserGroupDao {

	public UserGroupDaoJpa() {
		super(UserGroup.class);
	}
	
	@Inject
	public UserDao userDao;

	/**
	 * Entity manager, injected by Spring using @PersistenceContext annotation
	 * on setEntityManager()
	 */
	@PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
	private EntityManager entityManager;



	@Override
	public UserGroup save(UserGroup object) throws ParvanDaoException {

		// LOG
		log.debug("Entering save( object={})", object);

		UserGroup entity = null;

		try {
			UserInfo userInfo = CurrentContext.getCurrentUserInfo().get();

			BaseModel model = (BaseModel) object;
			if (Validator.isNotNull(model.getTenantId()) && Validator.isNotNull(userInfo) && !model.getTenantId().equals(userInfo.getTenantId())) {
				throw new ParvanDaoException("Error occurred while saving record. (mismatched tenantId)");
			}

			// save
			entity = this.entityManager.merge(object);
			this.entityManager.flush();

		} catch (ConstraintViolationException e) {
			throw e;

		} catch (OptimisticLockException e) {
			if (e.getCause() != null && e.getCause() instanceof StaleObjectStateException) {
				throw (StaleObjectStateException) e.getCause();
			}

			throw e;

		} catch (Exception e) {

			Throwable cause = ExceptionUtils.getRootCause(e);

			if (cause != null && cause.getMessage() != null && cause.getMessage().indexOf("_UNIQUE") > -1) {
				throw new ObjectExistsException();
			}

			throw new ParvanDaoException("Error occurred while saving record.", e);
		}

		// LOG
		log.debug("Leaving exists(): {}", entity);

		return entity;
	}

	@Override
	protected List<Predicate> buildPredicateList(BaseCriteria cri, CriteriaBuilder builder, Metamodel metamodel, Root<UserGroup> root, Map<String, Join> joins) {

		// LOG
		log.debug("Entering buildPredicateList( ... )");

		UserGroupCriteria userGroupCriteria= (UserGroupCriteria) cri;
		List<Predicate> predicateList = new ArrayList<Predicate>();
		String canonicalSearchName = userGroupCriteria.getCanonicalSearchGroupName();
		Boolean active = userGroupCriteria.getActive();
		
		// ----------------------------------------------------------------
		// Active
		// ----------------------------------------------------------------
		if (Validator.isNotNull(active)) {
			Predicate predicate = builder.equal(root.<Boolean> get(UserGroup_.active), active);
			predicateList.add(predicate);
		}

		// ----------------------------------------------------------------
		// canonicalSearchName
		// ----------------------------------------------------------------
		if (Validator.isNotNull(canonicalSearchName)) {
			List<Predicate> preList = new ArrayList<Predicate>();

			String[] splited = canonicalSearchName.trim().split("\\s+");

			for (String str : splited) {

				Predicate predicate = builder.or(builder.like(root.<String> get(UserGroup_.name), "%" + str + "%"),
						builder.like(root.<String> get(UserGroup_.description), "%" + str + "%"));
				preList.add(predicate);
			}
			predicateList.add(builder.and(preList.toArray(new Predicate[0])));
		}

		basePredicate((BaseCriteria) userGroupCriteria, builder, root, predicateList);

		// LOG
		log.debug("Leaving buildPredicateList(): {}", predicateList.size());

		return predicateList;
	}
	
	protected void basePredicate(BaseCriteria cri, CriteriaBuilder builder, Root<UserGroup> root, List<Predicate> predicateList) {

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
