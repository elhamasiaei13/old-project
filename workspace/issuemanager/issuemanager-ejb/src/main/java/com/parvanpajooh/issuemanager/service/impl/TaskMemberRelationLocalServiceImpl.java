package com.parvanpajooh.issuemanager.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.parvanpajooh.commons.util.Validator;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanDaoException;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanServiceException;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanUnrecoverableException;
import com.parvanpajooh.issuemanager.dao.MemberDao;
import com.parvanpajooh.issuemanager.dao.TaskDao;
import com.parvanpajooh.issuemanager.dao.TaskMemberRelationDao;
import com.parvanpajooh.issuemanager.model.TaskMemberRelation;
import com.parvanpajooh.issuemanager.model.enums.TaskMemberRelationEnum;
import com.parvanpajooh.issuemanager.model.vo.TaskMemberRelationVO;
import com.parvanpajooh.issuemanager.service.TaskMemberRelationLocalService;


/**
 * 
 * @author
 */
@Stateless
public class TaskMemberRelationLocalServiceImpl extends GenericLocalServiceImpl<TaskMemberRelation, Long>implements TaskMemberRelationLocalService {
	/**
	 * Log variable for all child classes.
	 */
	static final Logger log = LoggerFactory.getLogger(TaskStatusHistoryLocalServiceImpl.class);

	public TaskMemberRelationDao taskMemberRelationDao;
	public TaskDao taskDao;
	public MemberDao memberDao;

	@Inject
	public void setTaskMemberRelationDao(TaskMemberRelationDao taskMemberRelationDao) {
		this.dao = taskMemberRelationDao;
		this.taskMemberRelationDao = taskMemberRelationDao;
	}
	
	@Inject
	public void setTaskDao(TaskDao taskDao) {
		this.taskDao = taskDao;
	}
	
	@Inject
	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}

	public List<TaskMemberRelationVO> loadByTaskId(Long taskId,TaskMemberRelationEnum type) throws ParvanServiceException {

		List<TaskMemberRelationVO> listVO = null;

		try {

			// find list
			List<TaskMemberRelation> taskMemberRelations = taskMemberRelationDao.findByTaskIdAndType(taskId,type);

			if (Validator.isNotNull(taskMemberRelations)) {
				listVO = new ArrayList<TaskMemberRelationVO>(taskMemberRelations.size());
				for (TaskMemberRelation baseObject : taskMemberRelations) {

					TaskMemberRelationVO objectVO = baseObject.toVOLite();

					listVO.add(objectVO);
				}
			}

		} catch (Exception e) {
			log.error("error occurred while loadTaskAssignmentByTaskId taskAssignment", e);
			throw new ParvanUnrecoverableException("error occurred while loadTaskAssignmentByTaskId taskAssignment", e);
		}

		return listVO;
	}

	@Override
	public List<TaskMemberRelationVO> findByTaskIdAndMemberId(Long taskId, Long memberId, String type) throws ParvanServiceException {

		List<TaskMemberRelationVO> listVO = null;

		try {

			// find list
			List<TaskMemberRelation> taskMemberRelations = taskMemberRelationDao.findByTaskIdAndMemberId(taskId,memberId,type);

			if (Validator.isNotNull(taskMemberRelations)) {
				listVO = new ArrayList<TaskMemberRelationVO>(taskMemberRelations.size());
				for (TaskMemberRelation baseObject : taskMemberRelations) {

					TaskMemberRelationVO objectVO = baseObject.toVOLite();

					listVO.add(objectVO);
				}
			}

		} catch (Exception e) {
			log.error("error occurred while loadTaskAssignmentByTaskId taskAssignment", e);
			throw new ParvanUnrecoverableException("error occurred while loadTaskAssignmentByTaskId taskAssignment", e);
		}

		return listVO;
		
	}

	@Override
	public List<TaskMemberRelation> findByTaskIdAndType(Long taskId, TaskMemberRelationEnum type) throws ParvanServiceException {
		// LOG
		log.debug("Entering findByTaskIdAndType ()");

		List<TaskMemberRelation> list = null;

		try {

			// find by criteria
			list = taskMemberRelationDao.findByTaskIdAndType(taskId, type);

			// LOG
			log.info("found {} ", (list != null) ? list.size() : "NULL");

		} catch (Exception e) {
			throw new ParvanUnrecoverableException(
					"Error occurred while getting list of records by findByTaskIdAndType.",
					e);
		}

		// LOG
		log.debug("Leaving method findByTaskIdAndType(): ret={}",
				(list != null) ? list.size() : "NULL");

		return list;
	}
			

}
