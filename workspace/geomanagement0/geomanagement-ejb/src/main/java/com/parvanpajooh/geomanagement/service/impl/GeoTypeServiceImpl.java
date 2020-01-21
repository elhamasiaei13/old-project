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
import com.parvanpajooh.geomanagement.model.GeoType;
import com.parvanpajooh.geomanagement.model.vo.GeoTypeVO;
import com.parvanpajooh.geomanagement.service.GeoTypeLocalService;
import com.parvanpajooh.geomanagement.service.GeoTypeService;

/**
 * @author ali
 *
 */
@Stateless
public class GeoTypeServiceImpl extends GenericServiceImpl<GeoType, Long> implements GeoTypeService {
	/**
     * Log variable for all child classes. 
     */
	static final Logger log = LoggerFactory.getLogger( GeoTypeServiceImpl.class);
    
    private GeoTypeLocalService geoTypeLocalService;
	
    @Inject
    public void setGeoTypeLocalService(GeoTypeLocalService geoTypeLocalService) {
        this.localService = geoTypeLocalService;
        this.geoTypeLocalService = geoTypeLocalService;
    }
    
    @Override
    public GeoTypeVO findByCode(UserInfo userInfo, String code) throws ParvanServiceException {
    	log.debug("findByCode");
    	
    	try {
    		GeoType geoType = geoTypeLocalService.findByCode(code);
    		
			return geoType.toVO();
    	} catch (ParvanServiceException e) {
    		throw e;
    		
		} catch (Exception e) {
    		log.error("Error occurred while getting records by its code.", e);
    		throw new ParvanUnrecoverableException("Error occurred while getting records by its code.", e);
		}
    }
    
    @Override
    public GeoTypeVO get(UserInfo userInfo, Long id) throws ParvanServiceException {
    	log.debug("get by id");
    	
    	try {
    		GeoType geoType = geoTypeLocalService.get(id);
    		
    		return geoType.toVO();
    	} catch (ParvanServiceException e) {
    		throw e;
    		
    	} catch (Exception e) {
    		log.error("Error occurred while getting records by its ID.", e);
    		throw new ParvanUnrecoverableException("Error occurred while getting records by its ID.", e);
    	}
    }
    
    @Override
	public List<BaseVO> findByCriteria(UserInfo userInfo, BaseCriteria criteria) throws ParvanServiceException {
    	
    	log.info("findByCriteria");
    	
    	List<BaseVO> listVO = null;
    	
    	try {
    		
    		// find by criteria
    		List<GeoType> list = localService.findByCriteria(criteria);
    		
    		listVO = new ArrayList<BaseVO>(list.size());
        	for (GeoType baseObject : list) {
        		
        		GeoTypeVO entityVO = baseObject.toVOLite();
        		
        		if( Validator.isNotNull( baseObject.getParentType() ) ) {
        			
        			GeoTypeVO geoTypeVO = baseObject.getParentType().toVOLite();
        			entityVO.setParentType(geoTypeVO);
        		}
        		
        		listVO.add( entityVO );
    		}
    		
    	} catch (Exception e) {
    		throw new ParvanUnrecoverableException("Error occurred while getting list of records by criteria.", e);
		}
    	
    	return listVO;
	}
	
    /*@TransactionAttribute(TransactionAttributeType.SUPPORTS)*/
    @Override
	public List<BaseVO> findByCriteria(
			UserInfo userInfo, 
			BaseCriteria criteria,
			int firstResult, 
			int maxResults,
			SortDirectionEnum sortDirection,
			String sortCriterion) throws ParvanServiceException {
		
		log.info("find pagination T by " + criteria );
    	
		List<BaseVO> listVO = null;
		
    	try {
    		// find by criteria
    		List<GeoType> list = localService.findByCriteria(
    				criteria, 
    				firstResult, 
    				maxResults,
    				sortDirection,
    				sortCriterion);
    		
    		listVO = new ArrayList<BaseVO>(list.size());
        	for (GeoType baseObject : list) {
        		GeoTypeVO entityVO = baseObject.toVOLite();
        		
        		if( Validator.isNotNull( baseObject.getParentType() ) ) {
        			
        			GeoTypeVO geoTypeVO = baseObject.getParentType().toVOLite();
        			entityVO.setParentType(geoTypeVO);
        		}
        		
        		listVO.add( entityVO );
    		}
    	
    	} catch (Exception e) {
    		throw new ParvanUnrecoverableException("Error occurred while getting paged-list of records by criteria.", e);
		}
		
		return listVO;
	}

}