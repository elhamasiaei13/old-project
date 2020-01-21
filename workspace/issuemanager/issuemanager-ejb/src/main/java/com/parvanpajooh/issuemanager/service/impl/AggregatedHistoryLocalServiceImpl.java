package com.parvanpajooh.issuemanager.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.parvanpajooh.commons.util.Validator;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanServiceException;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanUnrecoverableException;
import com.parvanpajooh.issuemanager.dao.AggregatedHistoryDao;
import com.parvanpajooh.issuemanager.dao.TaskDao;
import com.parvanpajooh.issuemanager.model.AggregatedHistory;
import com.parvanpajooh.issuemanager.model.vo.AggregatedHistoryVO;
import com.parvanpajooh.issuemanager.service.AggregatedHistoryLocalService;

/**
 * 
 * @author
 */
@Stateless
public class AggregatedHistoryLocalServiceImpl extends GenericLocalServiceImpl<AggregatedHistory, Long>implements AggregatedHistoryLocalService {
	/**
	 * Log variable for all child classes.
	 */
	static final Logger log = LoggerFactory.getLogger(AggregatedHistoryLocalServiceImpl.class);

	public AggregatedHistoryDao aggregatedHistoryDao;
	public TaskDao taskDao;

	@Inject
	public void setAggregatedHistoryDao(AggregatedHistoryDao aggregatedHistoryDao) {
		this.dao = aggregatedHistoryDao;
		this.aggregatedHistoryDao = aggregatedHistoryDao;
	}
	
	@Inject
	public void setTaskDao(TaskDao taskDao) {
		this.taskDao = taskDao;
	}

	@Override
	public List<AggregatedHistoryVO> loadByTaskId(Long taskId) throws ParvanServiceException {

		List<AggregatedHistoryVO> listVO = null;

		try {

			// find list
			List<AggregatedHistory> aggregatedHistories = aggregatedHistoryDao.findByTaskId(taskId);

			if (Validator.isNotNull(aggregatedHistories)) {
				listVO = new ArrayList<AggregatedHistoryVO>(aggregatedHistories.size());
				for (AggregatedHistory baseObject : aggregatedHistories) {

					AggregatedHistoryVO objectVO = baseObject.toVOLite();

					listVO.add(objectVO);
				}
			}

		} catch (Exception e) {
			log.error("error occurred while loadTaskAssignmentByTaskId taskAssignment", e);
			throw new ParvanUnrecoverableException("error occurred while loadTaskAssignmentByTaskId taskAssignment", e);
		}

		return listVO;
	}

}
