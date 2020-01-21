package com.parvanpajooh.sample.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.parvanpajooh.commons.enums.SortDirectionEnum;
import com.parvanpajooh.commons.platform.ejb.exceptions.ParvanServiceException;
import com.parvanpajooh.commons.platform.ejb.exceptions.ParvanUnrecoverableException;
import com.parvanpajooh.commons.platform.ejb.model.UserInfo;
import com.parvanpajooh.commons.platform.ejb.model.vo.BaseCriteria;
import com.parvanpajooh.commons.platform.ejb.model.vo.BaseVO;
import com.parvanpajooh.commons.platform.ejb.service.impl.GenericServiceImpl;
import com.parvanpajooh.sample.model.Sample;
import com.parvanpajooh.sample.service.SampleLocalService;
import com.parvanpajooh.sample.service.SampleService;

/**
 * 
 * @author
 *
 */
@Stateless
public class SampleServiceImpl extends GenericServiceImpl<Sample, Long>implements SampleService {
	/**
	 * Log variable for all child classes.
	 */
	static final Logger log = LoggerFactory.getLogger(SampleServiceImpl.class);

	private SampleLocalService generalAgentLocalService;

	@Inject
	public void setGeneralAgentLocalService(SampleLocalService generalAgentLocalService) {
		this.localService = generalAgentLocalService;
		this.generalAgentLocalService = generalAgentLocalService;
	}
	
	@Override
	public List<BaseVO> findByCriteria(UserInfo UserInfo,
			BaseCriteria criteria, int firstResult, int maxResults,
			SortDirectionEnum sortDirection, String sortCriterion)
			throws ParvanServiceException {

		log.debug("find pagination T by " + criteria);

		List<BaseVO> listVO = null;

		try {
			
			// find by criteria
			List<Sample> list = localService.findByCriteria(criteria, firstResult,
					maxResults, sortDirection, sortCriterion);

			listVO = new ArrayList<BaseVO>(list.size());
			for (Sample baseObject : list) {

				BaseVO baseVO = toVO(baseObject, false, UserInfo);

				listVO.add(baseVO);
			}

		} catch (ParvanServiceException e) {
			throw e;

		} catch (Exception e) {
			throw new ParvanUnrecoverableException(
					"Error occurred while getting paged-list of records by criteria.",
					e);
		}

		return listVO;
	}


}
