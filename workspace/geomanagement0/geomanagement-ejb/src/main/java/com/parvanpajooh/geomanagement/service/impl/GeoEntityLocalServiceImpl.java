package com.parvanpajooh.geomanagement.service.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.parvanpajooh.commons.constants.StringPool;
import com.parvanpajooh.commons.platform.ejb.exceptions.ErrorCode;
import com.parvanpajooh.commons.platform.ejb.exceptions.ParvanDaoException;
import com.parvanpajooh.commons.platform.ejb.exceptions.ParvanRecoverableException;
import com.parvanpajooh.commons.platform.ejb.exceptions.ParvanServiceException;
import com.parvanpajooh.commons.platform.ejb.exceptions.ParvanUnrecoverableException;
import com.parvanpajooh.commons.platform.ejb.model.UserInfo;
import com.parvanpajooh.commons.platform.ejb.model.vo.BaseVO;
import com.parvanpajooh.commons.platform.ejb.service.impl.GenericLocalServiceImpl;
import com.parvanpajooh.commons.util.Validator;
import com.parvanpajooh.geomanagement.dao.GeoEntityDao;
import com.parvanpajooh.geomanagement.model.GeoEntity;
import com.parvanpajooh.geomanagement.model.GeoType;
import com.parvanpajooh.geomanagement.model.criteria.GeoEntityCriteria;
import com.parvanpajooh.geomanagement.model.vo.GeoEntityVO;
import com.parvanpajooh.geomanagement.model.vo.GeoTypeVO;
import com.parvanpajooh.geomanagement.service.GeoEntityLocalService;
import com.parvanpajooh.geomanagement.service.GeoTypeLocalService;

/**
 * @author ali
 * 
 */
@Stateless
public class GeoEntityLocalServiceImpl extends GenericLocalServiceImpl<GeoEntity, Long> implements GeoEntityLocalService {
	/**
	 * Log variable for all child classes.
	 */
	static final Logger log = LoggerFactory.getLogger(GeoEntityLocalServiceImpl.class);

	private GeoEntityDao geoEntityDao;

	@Inject
	private GeoTypeLocalService geoTypeLocalService;

	@Inject
	public void setGeoEntityDao(GeoEntityDao geoEntityDao) {
		this.dao = geoEntityDao;
		this.geoEntityDao = geoEntityDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.parvanpajooh.ecourier.service.impl.GenericLocalServiceImpl#save(java
	 * .lang.Object, com.parvanpajooh.common.UserInfo)
	 */
	@Override
	public GeoEntity save(GeoEntity geoEntity, UserInfo userInfo) throws ParvanServiceException {
		log.debug("Entering save(geoEntity={})", geoEntity);

		try {
			String sortField = geoEntity.getCode();
	
			if (Validator.isNotNull(geoEntity.getParent()) && Validator.isNotNull(geoEntity.getParent().getId())) {
	
				GeoEntity parent = geoEntity.getParent();
	
				sortField = parent.getSortField() + StringPool.DASH + geoEntity.getCode();
			}
	
			geoEntity.setSortField(sortField);
	
			if (Validator.isNotNull(geoEntity.getId())) {
				_reProccessSubChildSortField(geoEntity.getId(), userInfo);
			}
			
		} catch (ParvanServiceException e) {
			throw e;

		} catch (Exception e) {
			throw new ParvanUnrecoverableException("error in save geoEntity", ErrorCode.SAVE_ERROR);
		}

		return super.save(geoEntity, userInfo);
	}

	@Override
	public GeoEntityVO save(BaseVO baseVO, UserInfo userInfo) throws ParvanServiceException {
		log.debug("Entering save(baseVO={})", baseVO);

		GeoEntityVO geoEntityVO = (GeoEntityVO) baseVO;

		try {

			GeoEntity geoEntity = null;

			// shortCode of GeoEntity must be empty or unique
			String shortCode = geoEntityVO.getShortCode();
			if (Validator.isNotNull(shortCode)) {
				// find by shortCode
				geoEntity = geoEntityDao.findGeoEntityByShortCode(shortCode);
				if (Validator.isNotNull(geoEntity)) {
					if (!geoEntity.getId().equals(geoEntityVO.getId())) {
						throw new ParvanRecoverableException("geoEntity.shortCode", ErrorCode.DATA_DUPLICATE);
					}
				}
			}

			boolean isNew = Validator.isNull(geoEntityVO.getId());

			if (isNew) {
				geoEntity = new GeoEntity();
				geoEntity.fromVO(geoEntityVO);

			} else {
				geoEntity = get(geoEntityVO.getId());
				geoEntity.fromVO(geoEntityVO);
			}

			if (Validator.isNotNull(geoEntityVO.getParent()) && Validator.isNotNull(geoEntityVO.getParent().getId())) {

				GeoEntity parent = get(geoEntityVO.getParent().getId());

				if (!_isParentValid(geoEntity, parent)) {
					throw new ParvanRecoverableException(ErrorCode.INVALID_PARENT);
				}

				geoEntity.setParent(parent);
			} else {
				geoEntity.setParent(null);
			}

			GeoTypeVO geoTypeVO = geoEntityVO.getType();

			GeoType geoType = null;

			if (Validator.isNotNull(geoTypeVO.getId())) {
				geoType = geoTypeLocalService.get(geoEntityVO.getType().getId());
			} else if (Validator.isNotNull(geoTypeVO.getCode())) {
				geoType = geoTypeLocalService.findByCode(geoEntityVO.getType().getCode());
			} else {
				throw new ParvanRecoverableException(ErrorCode.OBJECT_NOT_FOUND);
			}

			geoEntity.setType(geoType);

			geoEntity = save(geoEntity, userInfo);

			geoEntityVO = geoEntity.toVO();

		} catch (ParvanServiceException e) {
			throw e;

		} catch (Exception e) {
			throw new ParvanUnrecoverableException("error in save geoEntity", ErrorCode.SAVE_ERROR);
		}

		log.debug("Leaving save(baseVO={})", baseVO);
		return geoEntityVO;
	}

	@Override
	public GeoEntity findByShortCode(String shortCode) throws ParvanServiceException {
		log.debug("Entering findByShortCode(shortCode={})", shortCode);
		GeoEntity geoEntity = null;
		try {
			geoEntity = geoEntityDao.findGeoEntityByShortCode(shortCode);
			
		} catch (ParvanDaoException e) {
			throw new ParvanUnrecoverableException("error in load of geoEntity with shortCode: " + shortCode, ErrorCode.GET_ERROR);
		}
		log.debug("Leaving findByShortCode(shortCode={})", shortCode);
		return geoEntity;
	}
	
	@Override
	public GeoEntity findByName(String name) throws ParvanServiceException {
		log.debug("Entering findByName(name={})", name);
		
		GeoEntity geoEntity = null;
		try {
			geoEntity = geoEntityDao.findGeoEntityByName(name);
			
		} catch (ParvanDaoException e) {
			throw new ParvanUnrecoverableException("error in load of geoEntity with name: " + name, ErrorCode.GET_ERROR);
		}
		log.debug("Leaving findByName(name={})", name);
		return geoEntity;
	}

	/**
	 * 
	 * @param geoEntity
	 * @param parent
	 * @return
	 */
	private boolean _isParentValid(GeoEntity geoEntity, GeoEntity parent) {
		if (geoEntity.equals(parent)) {
			return false;
		}

		GeoEntity parentOfParent = parent.getParent();

		if (Validator.isNotNull(parentOfParent)) {
			if (!_isParentValid(geoEntity, parentOfParent)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 
	 * @param parentId
	 * @param userInfo
	 * @throws ParvanServiceException
	 */
	private void _reProccessSubChildSortField(Long parentId, UserInfo userInfo) throws ParvanServiceException {

		try {
			GeoEntityVO parent = new GeoEntityVO();
			parent.setId(parentId);

			GeoEntityCriteria criteria = new GeoEntityCriteria();
			criteria.setParent(parent);

			List<GeoEntity> childGeoEntity = geoEntityDao.findByCriteria(criteria);

			for (GeoEntity geoEntity : childGeoEntity) {
				save(geoEntity, userInfo);
			}

		} catch (ParvanDaoException e) {
			throw new ParvanUnrecoverableException("error in reProccessSubChildSortField", ErrorCode.SAVE_ERROR);
		}
	}
}
