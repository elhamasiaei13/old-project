package com.parvanpajooh.issuemanager.dao.jpa;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Metamodel;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.StaleObjectStateException;

import com.parvanpajooh.commons.platform.ejb.model.BaseModel;
import com.parvanpajooh.commons.platform.ejb.model.CurrentContext;
import com.parvanpajooh.commons.platform.ejb.model.UserInfo;
import com.parvanpajooh.commons.platform.ejb.model.vo.BaseCriteria;
import com.parvanpajooh.commons.util.DateUtil;
import com.parvanpajooh.commons.util.Validator;
import com.parvanpajooh.issuemanager.common.exceptions.ObjectExistsException;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanDaoException;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanException;
import com.parvanpajooh.issuemanager.dao.TaskDao;
import com.parvanpajooh.issuemanager.dao.TaskMemberRelationDao;
import com.parvanpajooh.issuemanager.model.Group;
import com.parvanpajooh.issuemanager.model.Group_;
import com.parvanpajooh.issuemanager.model.Member;
import com.parvanpajooh.issuemanager.model.Member_;
import com.parvanpajooh.issuemanager.model.Task;
import com.parvanpajooh.issuemanager.model.TaskMemberRelation;
import com.parvanpajooh.issuemanager.model.Task_;
import com.parvanpajooh.issuemanager.model.criteria.TaskCriteria;
import com.parvanpajooh.issuemanager.model.enums.EmailEnum;
import com.parvanpajooh.issuemanager.model.enums.TaskMemberRelationEnum;
import com.parvanpajooh.issuemanager.model.enums.TaskStatusEnum;
import com.parvanpajooh.issuemanager.model.vo.GroupVO;
import com.parvanpajooh.issuemanager.model.vo.MemberVO;

/**
 * 
 * @author
 * 
 */
@Stateless
public class TaskDaoJpa extends GenericDaoJpa<Task, Long>implements TaskDao {

	public TaskDaoJpa() {
		super(Task.class);
	}

	public TaskMemberRelationDao taskMemberRelationDao;

	@Inject
	public void setTaskMemberRelationDao(TaskMemberRelationDao taskMemberRelationDao) {
		this.taskMemberRelationDao = taskMemberRelationDao;
	}

	/**
	 * Entity manager, injected by Spring using @PersistenceContext annotation
	 * on setEntityManager()
	 */
	@PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
	private EntityManager entityManager;

	private Date getDate(LocalDateTime ldt) {

		Date date = null;
		if (Validator.isNotNull(ldt)) {
			Instant instant = ldt.atZone(ZoneId.systemDefault()).toInstant();
			date = Date.from(instant);
		}

		return date;
	}

	@Override
	protected List<Predicate> buildPredicateList(BaseCriteria cri, CriteriaBuilder builder, Metamodel metamodel, Root<Task> root, Map<String, Join> joins)
			throws ParvanException {

		// LOG
		log.debug("Entering buildPredicateList( ... )");

		TaskCriteria taskCriteria = (TaskCriteria) cri;

		List<Predicate> predicateList = new ArrayList<Predicate>();

		String subject = taskCriteria.getSubject();
		Boolean active = taskCriteria.getActive();
		Long taskId = taskCriteria.getTaskSearchId();
		MemberVO currentMember = taskCriteria.getCurrentMember();
		Long creatMember = taskCriteria.getCreateUserId();
		Long updateMember = taskCriteria.getUpdateUserId();
		List<Long> groups = taskCriteria.getGroupIds();
		GroupVO group = taskCriteria.getGroup();
		TaskStatusEnum currentTaskStatus = taskCriteria.getCurrentTaskStatus();

		// ----------------------------------------------------------------
		// Due Date
		// ----------------------------------------------------------------
		Date dueDateFrom = getDate(taskCriteria.getDueDateFrom());
		Date dueDateTo = getDate(taskCriteria.getDueDateTo());
		if (Validator.isNotNull(dueDateFrom) || Validator.isNotNull(dueDateTo)) {

			if (Validator.isNotNull(dueDateFrom)) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(dueDateFrom);
				cal.getTime();
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);

				Predicate predicate = builder.greaterThanOrEqualTo(root.<LocalDateTime> get(Task_.dueDate), DateUtil.getFirstTimeOfDate(dueDateFrom));
				predicateList.add(predicate);
			}

			if (Validator.isNotNull(dueDateTo)) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(dueDateTo);
				cal.set(Calendar.HOUR_OF_DAY, 23);
				cal.set(Calendar.MINUTE, 59);
				cal.set(Calendar.SECOND, 59);

				Predicate predicate = builder.lessThanOrEqualTo(root.<LocalDateTime> get(Task_.dueDate), DateUtil.getFirstTimeOfDate(dueDateTo));
				predicateList.add(predicate);
			}

		}

		// ----------------------------------------------------------------
		// Create Date
		// ----------------------------------------------------------------
		Date createDateFrom = getDate(taskCriteria.getCreateDateFrom());
		Date createDateTo = getDate(taskCriteria.getCreateDateTo());
		if (Validator.isNotNull(createDateFrom) || Validator.isNotNull(createDateTo)) {

			if (Validator.isNotNull(createDateFrom)) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(createDateFrom);
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);

				Predicate predicate = builder.greaterThanOrEqualTo(root.<LocalDateTime> get(Task_.createDate), DateUtil.getFirstTimeOfDate(createDateFrom));
				predicateList.add(predicate);
			}

			if (Validator.isNotNull(createDateTo)) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(createDateTo);
				cal.set(Calendar.HOUR_OF_DAY, 23);
				cal.set(Calendar.MINUTE, 59);
				cal.set(Calendar.SECOND, 59);

				Predicate predicate = builder.lessThanOrEqualTo(root.<LocalDateTime> get(Task_.createDate), DateUtil.getFirstTimeOfDate(createDateTo));
				predicateList.add(predicate);
			}

		}

		// ----------------------------------------------------------------
		// Update Date
		// ----------------------------------------------------------------
		Date updateDateFrom = getDate(taskCriteria.getUpdateDateFrom());
		Date updateDateTo = getDate(taskCriteria.getUpdateDateTo());
		if (Validator.isNotNull(updateDateFrom) || Validator.isNotNull(updateDateTo)) {

			if (Validator.isNotNull(updateDateFrom)) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(updateDateFrom);
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);

				Predicate predicate = builder.greaterThanOrEqualTo(root.<LocalDateTime> get(Task_.updateDate), DateUtil.getFirstTimeOfDate(updateDateFrom));
				predicateList.add(predicate);
			}

			if (Validator.isNotNull(updateDateTo)) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(updateDateTo);
				cal.set(Calendar.HOUR_OF_DAY, 23);
				cal.set(Calendar.MINUTE, 59);
				cal.set(Calendar.SECOND, 59);

				Predicate predicate = builder.lessThanOrEqualTo(root.<LocalDateTime> get(Task_.updateDate), DateUtil.getFirstTimeOfDate(updateDateTo));
				predicateList.add(predicate);
			}
		}

		// ----------------------------------------------------------------
		// subject & description
		// ----------------------------------------------------------------
		if (Validator.isNotNull(subject)) {

			Predicate predicate = builder.or(builder.like(root.<String> get(Task_.subject), "%" + subject + "%"),
					builder.like(root.<String> get(Task_.description), "%" + subject + "%"));
			predicateList.add(predicate);
		}

		// ----------------------------------------------------------------
		// active
		// ----------------------------------------------------------------
		if (Validator.isNotNull(active)) {
			Predicate predicate = builder.equal(root.<Boolean> get(Task_.active), active);
			predicateList.add(predicate);
		}
		
		// ----------------------------------------------------------------
		// id
		// ----------------------------------------------------------------
		if (Validator.isNotNull(taskId)) {
			Predicate predicate = builder.equal(root.<Long> get(Task_.id), taskId);
			predicateList.add(predicate);
		}

		// ----------------------------------------------------------------
		// currentTaskStatus
		// ----------------------------------------------------------------
		if (Validator.isNotNull(currentTaskStatus)) {
			Predicate predicate = builder.equal(root.<TaskStatusEnum> get(Task_.currentTaskStatus), currentTaskStatus);
			predicateList.add(predicate);
		}

		// // ----------------------------------------------------------------
		// // group
		// // ----------------------------------------------------------------
		// if (Validator.isNotNull(group) && Validator.isNotNull(group.getId()))
		// {
		//
		// Join<Task, Group> groupJoin = root.join("group");
		// if (Validator.isNotNull(group.getId())) {
		//
		// Predicate predicate = builder.equal(groupJoin.<Long> get(Group_.id),
		// group.getId());
		// predicateList.add(predicate);
		// }
		//
		// }

		// ----------------------------------------------------------------
		// groups
		// ----------------------------------------------------------------
		if (Validator.isNotNull(groups)) {

			List<Long> ids = new ArrayList<Long>();
			Join<Task, Group> groupJoin = root.join("group");
			List<Predicate> preList = new ArrayList<Predicate>();

			if (Validator.isNotNull(group) && Validator.isNotNull(group.getId())) {
				for (Long item : groups) {

					if (Validator.equals(group.getId(), item)) {
						ids.add(group.getId());
					}
				}
			} else {
				ids = groups;
			}

			for (Long id : ids) {

				Predicate predicate = builder.equal(groupJoin.<Long> get(Group_.id), id);
				preList.add(predicate);
			}
			predicateList.add(builder.or(preList.toArray(new Predicate[0])));
		}

		// ----------------------------------------------------------------
		// currentMember
		// ----------------------------------------------------------------
		if (Validator.isNotNull(currentMember) && Validator.isNotNull(currentMember.getId())) {

			Join<Task, Member> memberJoin = root.join("currentMember");
			if (Validator.isNotNull(currentMember.getId())) {

				Predicate predicate = builder.equal(memberJoin.<Long> get(Member_.id), currentMember.getId());
				predicateList.add(predicate);
			}

		}

		// ----------------------------------------------------------------
		// creatMember
		// ----------------------------------------------------------------
		if (Validator.isNotNull(creatMember)) {

			Predicate predicate = builder.equal(root.<Long> get(Task_.createUserId), creatMember);
			predicateList.add(predicate);

		}

		// ----------------------------------------------------------------
		// updateMember
		// ----------------------------------------------------------------
		if (Validator.isNotNull(updateMember)) {

			Predicate predicate = builder.equal(root.<Long> get(Task_.updateUserId), updateMember);
			predicateList.add(predicate);

		}

		/*
		 * // ----------------------------------------------------------------
		 * // member //
		 * ---------------------------------------------------------------- if
		 * (Validator.isNotNull(currentMember)){
		 * 
		 * 
		 * // TaskStatusEnum taskStatusEnum = //
		 * taskAssignment.getTaskStatusEnum();
		 * 
		 * // if (Validator.isNotNull(taskStatusEnum)) { // //
		 * Join<TaskAssignment,TaskStatusEnum> taskStatusEnumJoin = //
		 * taskAssignmentJoin.join("taskStatusEnum"); // // Predicate predicate
		 * = builder.equal(taskStatusEnumJoin.<String> //
		 * get(TaskStatusEnum_.name), taskStatusEnum.name()); // //
		 * predicateList.add(predicate); // }
		 * 
		 * MemberVO member = currentMember;
		 * 
		 * if (Validator.isNotNull(member) &&
		 * Validator.isNotNull(member.getId())) {
		 * 
		 * Join<TaskAssignment, Member> memberJoin = root.join("member");
		 * Predicate predicate = builder.equal(memberJoin.<Long>
		 * get(Member_.id), member.getId()); predicateList.add(predicate); } }
		 */

		basePredicate((BaseCriteria) taskCriteria, builder, root, predicateList);

		// LOG
		log.debug("Leaving buildPredicateList(): {}", predicateList.size());

		return predicateList;
	}

	@Override
	public Task saveTask(Task object, Long groupId) throws ParvanDaoException {

		// LOG
		log.debug("Entering save( object={})", object);

		Task entity = null;
		boolean isNew = Validator.isNull(object.getId());
		LocalDateTime now = LocalDateTime.now();

		try {
			UserInfo userInfo = CurrentContext.getCurrentUserInfo().get();

			BaseModel model = (BaseModel) object;
			if (Validator.isNotNull(model.getTenantId()) && Validator.isNotNull(userInfo) && !model.getTenantId().equals(userInfo.getTenantId())) {
				throw new ParvanDaoException("Error occurred while saving record. (mismatched tenantId)");
			}

			Query queryGroup = getEntityManager().createQuery("from Group where id=?");
			queryGroup.setParameter(1, groupId);
			List<Group> groupList = queryGroup.getResultList();
			object.setGroup(groupList.get(0));
			// save Task
			entity = this.entityManager.merge(object);
			this.entityManager.flush();

			if (isNew) {
				// save TaskMemberRelation
				List<TaskMemberRelation> taskMemberRelations = null;

				taskMemberRelations = taskMemberRelationDao.findByTaskIdAndMemberId(entity.getId(), entity.getCreateUserId(),
						TaskMemberRelationEnum.WATCHING.toString());
				if (Validator.isNull(taskMemberRelations)) {
					TaskMemberRelation taskMemberRelation = new TaskMemberRelation();
					taskMemberRelation.setTask(entity);
					taskMemberRelation.setType(TaskMemberRelationEnum.WATCHING);
					taskMemberRelation.setCreateDate(now);
					taskMemberRelation.setUpdateDate(now);
					taskMemberRelation.setCreateUserId(userInfo.getUserId());
					taskMemberRelation.setUpdateUserId(userInfo.getUserId());
					taskMemberRelationDao.save(taskMemberRelation);
				}
			}

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
	public List<Task> findByEmailStatus(EmailEnum status) throws ParvanDaoException {
		Query query = getEntityManager().createQuery("from Task where emailStatus = ? or emailStatus = ?");
		query.setParameter(1, status);
		query.setParameter(2, EmailEnum.FAILD);
		List<Task> taskList = query.getResultList();

		return taskList;

	}
}