package com.parvanpajooh.identitymanagement2.dao.jpa;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Metamodel;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.poi.ss.formula.functions.T;
import org.hibernate.StaleObjectStateException;

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
import com.parvanpajooh.identitymanagement2.model.User;
import com.parvanpajooh.identitymanagement2.model.User_;
import com.parvanpajooh.identitymanagement2.model.criteria.UserCriteria;

/**
 * 
 * @author moosa
 *
 */
@Stateless
public class UserDaoJpa extends GenericDaoJpa<User, Long>implements UserDao {

	public UserDaoJpa() {
		super(User.class);
	}

	@Override
	protected List<Predicate> buildPredicateList(BaseCriteria cri, CriteriaBuilder builder, Metamodel metamodel, Root<User> root, Map<String, Join> joins) {

		// LOG
		log.debug("Entering buildPredicateList( ... )");

		UserCriteria userCriteria = (UserCriteria) cri;

		List<Predicate> predicateList = new ArrayList<Predicate>();

		// userCriteria.setActive(true);
		Boolean active = userCriteria.getActive();

		String searchName = userCriteria.getSearchName();

		String canonicalSearchName = userCriteria.getCanonicalSearchName();

		// ----------------------------------------------------------------
		// Active
		// ----------------------------------------------------------------
		if (Validator.isNotNull(active)) {
			Predicate predicate = builder.equal(root.<Boolean> get(User_.active), active);
			predicateList.add(predicate);
		}

		// ----------------------------------------------------------------
		// lastName
		// ----------------------------------------------------------------
		if (Validator.isNotNull(searchName)) {
			Predicate predicate = builder.or(builder.like(root.<String> get(User_.firstName), "%" + searchName + "%"),
					builder.like(root.<String> get(User_.lastName), "%" + searchName + "%"),
					builder.like(root.<String> get(User_.userName), "%" + searchName + "%"));
			predicateList.add(predicate);
		}

		// ----------------------------------------------------------------
		// canonicalSearchName
		// ----------------------------------------------------------------
		if (Validator.isNotNull(canonicalSearchName)) {
			List<Predicate> preList = new ArrayList<Predicate>();
			
			
			String[] splited = canonicalSearchName.trim().split("\\s+");
			
			for(String str:splited){
				
				Predicate predicate = builder.or(builder.like(root.<String> get(User_.firstName), "%" + str + "%"),
						builder.like(root.<String> get(User_.lastName), "%" + str + "%"),
						builder.like(root.<String> get(User_.userName), "%" + str + "%"));
				preList.add(predicate);
				
			}
				predicateList.add(builder.and(preList.toArray(new Predicate[0])));			
		}

		// ----------------------------------------------------------------
		// Create Date
		// ----------------------------------------------------------------
		Date createDateFrom = getDate(userCriteria.getCreateDateFrom());
		Date createDateTo = getDate(userCriteria.getCreateDateTo());

		if (Validator.isNotNull(createDateFrom) || Validator.isNotNull(createDateTo)) {

			if (Validator.isNotNull(createDateFrom)) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(createDateFrom);
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);

				Date calDate = cal.getTime();
				Instant instant = Instant.ofEpochMilli(calDate.getTime());
				LocalDateTime calLocalDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

				Predicate predicate = builder.greaterThanOrEqualTo(root.<LocalDateTime> get(User_.createDate), calLocalDate);
				predicateList.add(predicate);

			}

			if (Validator.isNotNull(createDateTo)) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(createDateTo);
				cal.set(Calendar.HOUR_OF_DAY, 23);
				cal.set(Calendar.MINUTE, 59);
				cal.set(Calendar.SECOND, 59);

				Date calDate = cal.getTime();
				Instant instant = Instant.ofEpochMilli(calDate.getTime());
				LocalDateTime calLocalDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

				Predicate predicate = builder.lessThanOrEqualTo(root.<LocalDateTime> get(User_.createDate), calLocalDate);
				predicateList.add(predicate);
			}

		}

		// ----------------------------------------------------------------
		// Update Date
		// ----------------------------------------------------------------
		Date updateDateFrom = getDate(userCriteria.getUpdateDateFrom());
		Date updateDateTo = getDate(userCriteria.getUpdateDateTo());

		if (Validator.isNotNull(updateDateFrom) || Validator.isNotNull(updateDateTo)) {

			if (Validator.isNotNull(updateDateFrom)) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(updateDateFrom);
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);

				Date calDate = cal.getTime();
				Instant instant = Instant.ofEpochMilli(calDate.getTime());
				LocalDateTime calLocalDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

				Predicate predicate = builder.greaterThanOrEqualTo(root.<LocalDateTime> get(User_.updateDate), calLocalDate);
				predicateList.add(predicate);
			}

			if (Validator.isNotNull(updateDateTo)) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(updateDateTo);
				cal.set(Calendar.HOUR_OF_DAY, 23);
				cal.set(Calendar.MINUTE, 59);
				cal.set(Calendar.SECOND, 59);

				Date calDate = cal.getTime();
				Instant instant = Instant.ofEpochMilli(calDate.getTime());
				LocalDateTime calLocalDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

				Predicate predicate = builder.lessThanOrEqualTo(root.<LocalDateTime> get(User_.updateDate), calLocalDate);
				predicateList.add(predicate);
			}
		}

		basePredicate((BaseCriteria) userCriteria, builder, root, predicateList);

		// LOG
		log.debug("Leaving buildPredicateList(): {}", predicateList.size());

		return predicateList;
	}

	private Date getDate(LocalDateTime ldt) {

		Date date = null;
		if (Validator.isNotNull(ldt)) {
			Instant instant = ldt.atZone(ZoneId.systemDefault()).toInstant();
			date = Date.from(instant);
		}

		return date;
	}

	protected void basePredicate(BaseCriteria cri, CriteriaBuilder builder, Root<User> root, List<Predicate> predicateList) {

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
