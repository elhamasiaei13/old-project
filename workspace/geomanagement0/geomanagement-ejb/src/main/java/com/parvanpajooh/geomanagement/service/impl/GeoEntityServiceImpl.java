package com.parvanpajooh.geomanagement.service.impl;

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
import com.parvanpajooh.commons.util.Validator;
import com.parvanpajooh.geomanagement.model.GeoEntity;
import com.parvanpajooh.geomanagement.model.vo.GeoEntityVO;
import com.parvanpajooh.geomanagement.service.GeoEntityLocalService;
import com.parvanpajooh.geomanagement.service.GeoEntityService;

/**
 * @author ali
 * @author Mohammad
 * 
 */
@Stateless
public class GeoEntityServiceImpl extends GenericServiceImpl<GeoEntity, Long> implements GeoEntityService {
    /**
     * Log variable for all child classes.
     */
    static final Logger log = LoggerFactory.getLogger(GeoEntityServiceImpl.class);

    private GeoEntityLocalService geoEntityLocalService;

    @Inject
    public void setGeoEntityLocalService(GeoEntityLocalService geoEntityLocalService) {
		this.localService = geoEntityLocalService;
		this.geoEntityLocalService = geoEntityLocalService;
    }

    @Override
    public GeoEntityVO findByShortCode(UserInfo userInfo, String shortCode) throws ParvanServiceException {
    	log.debug("Entering findByShortCode(shortCode={})", shortCode);
    	
    	try {
    		GeoEntity entity = geoEntityLocalService.findByShortCode(shortCode);
    		
    		log.debug("Leaving findByShortCode(shortCode={})", shortCode);
    		return entity.toVOLite();
    	} catch (ParvanServiceException e) {
    		throw e;
    		
    	} catch (Exception e) {
    		log.error("Error occurred while getting records by its shortCode.", e);
    		throw new ParvanUnrecoverableException("Error occurred while getting records by its shortCode.", e);
    	}
    }

	@Override
	public GeoEntityVO findByName(UserInfo userInfo, String name)
			throws ParvanServiceException {
		log.debug("Entering findByName(userInfo={}, name={})", userInfo, name);
    	
		GeoEntityVO result = null;
		
    	try {
    		GeoEntity entity = geoEntityLocalService.findByName(name);
    		
    		if(entity != null){
    			result = entity.toVOLite();
    		}
    		
    	} catch (ParvanServiceException e) {
    		throw e;
    		
    	} catch (Exception e) {
    		log.error("Error occurred while getting records by its name.", e);
    		throw new ParvanUnrecoverableException("Error occurred while getting records by its name.", e);
    	}
    	
    	log.debug("Leaving findByName(name={})", name);
    	
    	return result;
	}

    @Override
    public GeoEntityVO get(UserInfo userInfo, Long id) throws ParvanServiceException {
		log.debug("Entering get(id={})", id);
	
		try {
		    GeoEntity geoEntity = geoEntityLocalService.get(id);
		    
		    log.debug("Leaving get(id={})", id);
		    return geoEntity.toVO();
		} catch (ParvanServiceException e) {
		    throw e;
	
		} catch (Exception e) {
		    log.error("Error occurred while getting records by its ID.", e);
		    throw new ParvanUnrecoverableException("Error occurred while getting records by its ID.", e);
		}
    }

    @Override
    public List<BaseVO> findByCriteria(UserInfo userInfo, BaseCriteria criteria) throws ParvanServiceException {

		log.debug("Entering findByCriteria(criteria={})", criteria);
	
		List<BaseVO> listVO = null;
	
		try {
	
		    // find by criteria
		    List<GeoEntity> list = localService.findByCriteria(criteria);
	
		    listVO = new ArrayList<BaseVO>(list.size());
		    for (GeoEntity baseObject : list) {
	
			GeoEntityVO entityVO = baseObject.toVOLite();
	
			if (Validator.isNotNull(baseObject.getParent())) {
	
			    GeoEntityVO geoEntityVO = baseObject.getParent().toVOLite();
			    entityVO.setParent(geoEntityVO);
			}
	
			listVO.add(entityVO);
		    }
	
		} catch (Exception e) {
		    throw new ParvanUnrecoverableException("Error occurred while getting list of records by criteria.", e);
		}
	
		log.debug("Leaving findByCriteria(criteria={})", criteria);
		return listVO;
    }

    /* @TransactionAttribute(TransactionAttributeType.SUPPORTS) */
    @Override
    public List<BaseVO> findByCriteria(UserInfo userInfo, BaseCriteria criteria, int firstResult, int maxResults,
	    SortDirectionEnum sortDirection, String sortCriterion) throws ParvanServiceException {

		log.debug("Entering findByCriteria(criteria={}, firstResult={}, criteria={}, firstResult={}, maxResults={}, sortDirection={}, sortCriterion={})", 
			criteria, firstResult, maxResults, sortDirection, sortCriterion);
	
		List<BaseVO> listVO = null;
	
		try {
		    // find by criteria
		    List<GeoEntity> list = localService.findByCriteria(criteria, firstResult, maxResults, sortDirection,
			    sortCriterion);
	
		    listVO = new ArrayList<BaseVO>(list.size());
		    for (GeoEntity baseObject : list) {
			GeoEntityVO entityVO = baseObject.toVOLite();
	
			/*if (Validator.isNotNull(baseObject.getParent())) {
	
			    GeoEntityVO geoEntityVO = baseObject.getParent().toVOLite();
			    entityVO.setParent(geoEntityVO);
			}*/
	
			listVO.add(entityVO);
		    }
	
		} catch (Exception e) {
		    throw new ParvanUnrecoverableException("Error occurred while getting paged-list of records by criteria.", e);
		}
	
		log.debug("Leaving findByCriteria(criteria={}, firstResult={}, criteria={}, firstResult={}, maxResults={}, sortDirection={}, sortCriterion={})", 
			criteria, firstResult, maxResults, sortDirection, sortCriterion);
		return listVO;
    }

}