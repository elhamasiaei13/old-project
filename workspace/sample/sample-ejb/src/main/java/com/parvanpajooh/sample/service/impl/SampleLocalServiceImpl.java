package com.parvanpajooh.sample.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.parvanpajooh.commons.enums.SortDirectionEnum;
import com.parvanpajooh.commons.platform.ejb.exceptions.ParvanServiceException;
import com.parvanpajooh.commons.platform.ejb.model.vo.BaseCriteria;
import com.parvanpajooh.commons.platform.ejb.service.impl.GenericLocalServiceImpl;
import com.parvanpajooh.sample.dao.SampleDao;
import com.parvanpajooh.sample.model.Sample;
import com.parvanpajooh.sample.model.criteria.SampleCriteria;
import com.parvanpajooh.sample.model.vo.AgentTreeVO;
import com.parvanpajooh.sample.service.SampleLocalService;
import com.parvanpajooh.sample.service.adapter.IdentityManagement2ServiceAdapter;

/**
 * 
 * @author
 *
 */
@Stateless
public class SampleLocalServiceImpl extends GenericLocalServiceImpl<Sample, Long>implements SampleLocalService {
	/**
	 * Log variable for all child classes.
	 */
	static final Logger log = LoggerFactory.getLogger(SampleLocalServiceImpl.class);
	
	private static Map<String, AgentTreeVO> rootAgentTreeVoMap = new HashMap<String, AgentTreeVO>();

	public SampleDao sampleDao;

	@Inject
	private IdentityManagement2ServiceAdapter identityManagement2ServiceAdapter;

	@Inject
	public void setGeneralAgentDao(SampleDao generalAgentDao) {
		this.dao = generalAgentDao;
		this.sampleDao = generalAgentDao;
	}
	
	@Override
	public List<Sample> findByCriteria(BaseCriteria criteria, int firstResult, int maxResults, SortDirectionEnum sortDirection, String sortCriterion)
			throws ParvanServiceException {

		// LOG
		log.debug("Entering findByCriteria( criteria={}, firstResult={}, maxResults={}, sortDirection={}, sortCriterion={})", criteria, firstResult, maxResults,
				sortDirection, sortCriterion);

		List<Sample> list = null;
		SampleCriteria generalAgentCriteria = (SampleCriteria) criteria;

		try {

			// find by criteria
			list = super.findByCriteria(generalAgentCriteria, firstResult, maxResults, sortDirection, sortCriterion);

		} catch (ParvanServiceException e) {
			throw new ParvanServiceException(e.getMessage());
		}

		return list;
	}

	/*@Override
	public List<GeneralAgent> findByParentId(Long id) throws ParvanServiceException {

		// LOG
		log.debug("Entering findByParentId( parentId={})", id);

		List<GeneralAgent> generalAgents;

		try {
			generalAgents = generalAgentDao.findByParentId(id);

		} catch (Exception e) {
			log.error("Error occurred while finding agents by parentId [" + id + "]", e);
			throw new ParvanServiceException(e.getMessage());
		}

		return generalAgents;

	}*/


}
