package com.parvanpajooh.issuemanager.service;

import java.util.List;

import com.parvanpajooh.commons.platform.ejb.model.UserInfo;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanServiceException;
import com.parvanpajooh.issuemanager.model.AggregatedHistory;
import com.parvanpajooh.issuemanager.model.vo.AggregatedHistoryVO;

/**
 * 
 * @author
 * 
 */
public interface AggregatedHistoryService extends GenericService<AggregatedHistory, Long> {

	public List<AggregatedHistoryVO> loadByTaskId(UserInfo userInfo, Long taskId) throws ParvanServiceException;

}
