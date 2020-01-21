package com.parvanpajooh.issuemanager.service;

import java.util.List;

import com.parvanpajooh.issuemanager.common.exceptions.ParvanServiceException;
import com.parvanpajooh.issuemanager.service.GenericLocalService;
import com.parvanpajooh.issuemanager.model.AggregatedHistory;
import com.parvanpajooh.issuemanager.model.TaskStatusHistory;
import com.parvanpajooh.issuemanager.model.vo.AggregatedHistoryVO;
import com.parvanpajooh.issuemanager.model.vo.TaskStatusHistoryVO;

/**
 * 
 * @author
 * 
 */
public interface AggregatedHistoryLocalService extends GenericLocalService<AggregatedHistory, Long> {

	public List<AggregatedHistoryVO> loadByTaskId(Long taskId) throws ParvanServiceException;
	
}
