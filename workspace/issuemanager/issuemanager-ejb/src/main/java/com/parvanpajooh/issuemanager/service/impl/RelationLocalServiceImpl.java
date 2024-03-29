package com.parvanpajooh.issuemanager.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.parvanpajooh.commons.platform.ejb.model.UserInfo;
import com.parvanpajooh.commons.platform.ejb.model.vo.BaseVO;
import com.parvanpajooh.commons.util.Validator;
import com.parvanpajooh.issuemanager.common.exceptions.ErrorCode;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanRecoverableException;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanServiceException;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanUnrecoverableException;
import com.parvanpajooh.issuemanager.dao.RelationDao;
import com.parvanpajooh.issuemanager.dao.TaskDao;
import com.parvanpajooh.issuemanager.model.Relation;
import com.parvanpajooh.issuemanager.model.Task;
import com.parvanpajooh.issuemanager.model.enums.RoleEnum;
import com.parvanpajooh.issuemanager.model.vo.RelationVO;
import com.parvanpajooh.issuemanager.service.RelationLocalService;

@Stateless
public class RelationLocalServiceImpl extends GenericLocalServiceImpl<Relation, Long>implements RelationLocalService {
	/**
	 * Log variable for all child classes.
	 */
	static final Logger log = LoggerFactory.getLogger(RelationLocalServiceImpl.class);

	public RelationDao relationDao;
	public TaskDao taskDao;

	@Inject
	public void setRelationDao(RelationDao relationDao) {
		this.dao = relationDao;
		this.relationDao = relationDao;
	}

	@Inject
	public void setTaskDao(TaskDao taskDao) {
		this.taskDao = taskDao;
	}

	@Override
	public List<RelationVO> loadRelationByTaskId(Long id) throws ParvanServiceException {
		List<RelationVO> result = null;

		try {
			List<Relation> relation = relationDao.findBytaskId(id);
			result = convertToTree(relation);
		} catch (Exception e) {
			log.error("error occurred while loadRelationByTaskId", e);
			throw new ParvanUnrecoverableException("error occurred while loadRelationByTaskId", e);
		}

		return result;

	}

	@Override
	public BaseVO save(BaseVO baseVO, UserInfo userInfo) throws ParvanServiceException {
		log.info("Entering save(baseVO={}, userInfo={})", baseVO, userInfo);

		RelationVO relationVO = (RelationVO) baseVO;

		try {
			boolean isNew = Validator.isNull(relationVO.getId());
			String description = relationVO.getDescription();

			// check access
			Set<String> userRoles = userInfo.getRoleNames();
			if (!(userRoles.contains(RoleEnum.ADMIN.value) && userRoles.contains(RoleEnum.MANAGER.value))) {
				throw new ParvanRecoverableException(ErrorCode.HAS_NOT_ACCESS);
			}


			if (Validator.isNull(description) || Validator.isNull(relationVO.getToTask()) || Validator.isNull(relationVO.getType())) {
				log.debug("feild is empty.");
				throw new ParvanRecoverableException(ErrorCode.FEILDS_IS_EMPTY);
			}

			Long fromTaskId = relationVO.getFromTask().getId();
			Long toTaskId = relationVO.getToTask().getId();

			Relation relation = null;
			Task task = new Task();
			LocalDateTime now = LocalDateTime.now();

			if (isNew) {
				relation = new Relation();
				relation.fromVO(relationVO);
				relation.setCreateDate(now);
				relation.setCreateUserId(userInfo.getUserId());

			} else {

				relation = relationDao.get(relationVO.getId());
				relation.fromVO(relationVO);
			}

			Task fromTask = taskDao.get(fromTaskId);
			Task toTask = taskDao.get(toTaskId);
			relation.setFromTask(fromTask);
			relation.setToTask(toTask);
			relation.setActive(true);

			relation.setUpdateDate(now);
			relation.setUpdateUserId(userInfo.getUserId());

			// save entity
			relation = relationDao.save(relation);
			relationVO = (RelationVO) relation.toVO();

		} catch (ParvanServiceException e) {
			throw e;

		} catch (Exception e) {
			throw new ParvanUnrecoverableException("Error occurred while saving object.", e);
		}

		return relationVO;
	}

	/**
	 * 
	 * @param members
	 * @return
	 */
	private List<RelationVO> convertToTree(List<Relation> relations) {

		List<RelationVO> result = new ArrayList<RelationVO>();
		Map<Long, RelationVO> map = new HashMap<Long, RelationVO>();

		Iterator<Relation> iterator = relations.iterator();
		while (iterator.hasNext()) {
			Relation thisRelation = iterator.next();
			RelationVO relationVO = (RelationVO) thisRelation.toVOLite();
			result.add(relationVO);
			map.put(thisRelation.getId(), relationVO);
			iterator.remove();
		}

		return result;
	}

	// @Override
	// public void editRelation(RelationVO relationVO) throws
	// ParvanServiceException {
	// try {
	//
	// String description = relationVO.getDescription();
	//
	// if (Validator.isNull(description) ||
	// Validator.isNull(relationVO.getToTask()) ||
	// Validator.isNull(relationVO.getType())) {
	// log.debug("feild is empty.");
	// throw new ParvanRecoverableException(ErrorCode.FEILDS_IS_EMPTY);
	// }
	//
	// Relation relation = new Relation();
	// Task task = new Task();
	// LocalDateTime now = LocalDateTime.now();
	//
	// relation = relationDao.get(relationVO.getId());
	// relation.setUpdateDate(now);
	// task.fromVO(relationVO.getToTask());
	// relation.setToTask(task);
	//
	// relation.fromVO(relationVO);
	//
	// // refresh entity
	// relationDao.refresh(relation);
	//
	// } catch (ParvanServiceException e) {
	// throw e;
	//
	// } catch (Exception e) {
	// throw new ParvanUnrecoverableException("Error occurred while refreshing
	// relation.", e);
	// }
	//
	// }

	public void delete(Long id) throws ParvanServiceException {
		try {

			Relation relation = null;
			LocalDateTime now = LocalDateTime.now();

			relation = relationDao.get(id);
			relation.setActive(false);
			relation.setUpdateDate(now);

			// refresh entity
			relationDao.delete(relation);

		} catch (Exception e) {
			throw new ParvanUnrecoverableException("Error occurred while deleting relation.", e);
		}
	}

}
