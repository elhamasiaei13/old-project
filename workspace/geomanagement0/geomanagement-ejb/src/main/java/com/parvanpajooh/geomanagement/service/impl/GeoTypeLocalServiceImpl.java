package com.parvanpajooh.geomanagement.service.impl;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.parvanpajooh.commons.platform.ejb.exceptions.ErrorCode;
import com.parvanpajooh.commons.platform.ejb.exceptions.ParvanDaoException;
import com.parvanpajooh.commons.platform.ejb.exceptions.ParvanRecoverableException;
import com.parvanpajooh.commons.platform.ejb.exceptions.ParvanServiceException;
import com.parvanpajooh.commons.platform.ejb.exceptions.ParvanUnrecoverableException;
import com.parvanpajooh.commons.platform.ejb.model.UserInfo;
import com.parvanpajooh.commons.platform.ejb.model.vo.BaseVO;
import com.parvanpajooh.commons.platform.ejb.service.impl.GenericLocalServiceImpl;
import com.parvanpajooh.commons.util.Validator;
import com.parvanpajooh.geomanagement.dao.GeoTypeDao;
import com.parvanpajooh.geomanagement.model.GeoType;
import com.parvanpajooh.geomanagement.model.vo.GeoTypeVO;
import com.parvanpajooh.geomanagement.service.GeoTypeLocalService;

/**
 * @author ali
 *
 */
@Stateless
public class GeoTypeLocalServiceImpl extends GenericLocalServiceImpl<GeoType, Long> implements GeoTypeLocalService{
	/**
     * Log variable for all child classes. 
     */
    static final Logger log = LoggerFactory.getLogger( GeoTypeLocalServiceImpl.class);
    
    private GeoTypeDao geoTypeDao;
    
    @Inject 
    public void setGeoTypeDao(GeoTypeDao geoTypeDao) {
    	this.dao = geoTypeDao;
    	this.geoTypeDao = geoTypeDao;
    }
    
    @Override
    public GeoType findByCode(String code) throws ParvanServiceException {
    	GeoType type = null;
		try {
			type = geoTypeDao.findGeoTypeByCode(code);
			
		} catch (ParvanDaoException e) {
			throw new ParvanUnrecoverableException(	"error in load of geoType with code: " + code, ErrorCode.GET_ERROR);
		}
    	
    	return type;
    }

    @Override
    public GeoTypeVO save(BaseVO baseVO, UserInfo userInfo) throws ParvanServiceException {
    	log.info("save GeoTypeVO");
		
		GeoTypeVO geoTypeVO = (GeoTypeVO) baseVO;
		
		try {

			GeoType geoType = null;
			// code of geoType must be unique
			geoType = geoTypeDao.findGeoTypeByCode(geoTypeVO.getCode());
			if( Validator.isNotNull(geoType)) {
				if( ! geoType.getId().equals(geoTypeVO.getId())) {
					throw new ParvanRecoverableException("GeoType.code" , ErrorCode.DATA_DUPLICATE);
				}
			}
			// nameFa of geoType must be unique
			geoType = geoTypeDao.findGeoTypeByNameFa(geoTypeVO.getNameFa());
			if( Validator.isNotNull(geoType)) {
				if( ! geoType.getId().equals(geoTypeVO.getId())) {
					throw new ParvanRecoverableException("GeoType.nameFa" , ErrorCode.DATA_DUPLICATE);
				}
			}
			// nameEn of geoType must be unique
			geoType = geoTypeDao.findGeoTypeByNameEn(geoTypeVO.getNameEn());
			if( Validator.isNotNull(geoType)) {
				if( ! geoType.getId().equals(geoTypeVO.getId())) {
					throw new ParvanRecoverableException("GeoType.nameEn" , ErrorCode.DATA_DUPLICATE);
				}
			}
			
			boolean isNew = Validator.isNull(geoTypeVO.getId());
			
			if(isNew){
				
				GeoType type = geoTypeDao.findGeoTypeByCode(geoTypeVO.getCode());
    			
    			if(Validator.isNotNull(type)){
    				throw new ParvanRecoverableException(ErrorCode.KEY_NOT_UNIQUE);
    			}
    			
				geoType = new GeoType();
				geoType.fromVO(geoTypeVO);
				
			}
			else {
				geoType = get(geoTypeVO.getId());
				geoType.fromVO(geoTypeVO);
			}
			
			if (Validator.isNotNull(geoTypeVO.getParentType())) {
				
				GeoType parent = null;
				
				if (Validator.isNotNull(geoTypeVO.getParentType().getId())) {
					
					parent = get(geoTypeVO.getParentType().getId());
					
					if(!isParentValid(geoType, parent)){
						throw new ParvanRecoverableException(ErrorCode.INVALID_PARENT);
					}
					
				}
				else if (Validator.isNotNull(geoTypeVO.getParentType().getCode())) {
					
					parent = geoTypeDao.findGeoTypeByCode(geoTypeVO.getParentType().getCode());
					
					if(!isParentValid(geoType, parent)){
						throw new ParvanRecoverableException(ErrorCode.INVALID_PARENT);
					}
					
					if(parent == null){
						throw new ParvanRecoverableException(ErrorCode.INVALID_PARENT);
					}
				}
				
				geoType.setParentType(parent);
			}
			else {
				geoType.setParentType(null);
			}
			
			geoType = super.save(geoType, userInfo);
			
			geoTypeVO = geoType.toVO();
			
		} catch (ParvanServiceException e) {
			throw e;
			
		} catch (Exception e) {
			throw new ParvanUnrecoverableException(	"error in save geoType", ErrorCode.SAVE_ERROR);
		}
		
		return geoTypeVO;
    }
	
	private boolean isParentValid(GeoType geoType, GeoType parent){
		if(geoType.equals(parent)){
			return false;
		}
		
		GeoType parentOfParent = parent.getParentType();
		
		if(Validator.isNotNull(parentOfParent)){
			if(!isParentValid(geoType, parentOfParent)){
				return false;
			}
		}
		
		return true;
	}
}
