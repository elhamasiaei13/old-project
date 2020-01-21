package com.parvanpajooh.issuemanager.service.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.parvanpajooh.commons.platform.ejb.model.UserInfo;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanServiceException;
import com.parvanpajooh.issuemanager.model.AggregatedHistory;
import com.parvanpajooh.issuemanager.model.vo.AggregatedHistoryVO;
import com.parvanpajooh.issuemanager.service.AggregatedHistoryLocalService;
import com.parvanpajooh.issuemanager.service.AggregatedHistoryService;

@Stateless
public class AggregatedHistoryServiceImpl extends GenericServiceImpl<AggregatedHistory, Long>implements AggregatedHistoryService {
	/**
	 * Log variable for all child classes.
	 */
	static final Logger log = LoggerFactory.getLogger(AggregatedHistoryServiceImpl.class);

	private AggregatedHistoryLocalService aggregatedHistoryLocalService;

	@Inject
	public void setUserLocalService(AggregatedHistoryLocalService aggregatedHistoryLocalService) {
		this.localService = aggregatedHistoryLocalService;
		this.aggregatedHistoryLocalService = aggregatedHistoryLocalService;
	}

	@Override
	public List<AggregatedHistoryVO> loadByTaskId(UserInfo userInfo, Long taskId) throws ParvanServiceException {
		log.debug("Entering loadTaskAssignmentByTaskId(userInfo={})", userInfo);
		return aggregatedHistoryLocalService.loadByTaskId(taskId);
	}

}
