package com.parvanpajooh.geomanagement.dao;

import com.parvanpajooh.commons.platform.ejb.dao.GenericDao;
import com.parvanpajooh.commons.platform.ejb.exceptions.ParvanDaoException;
import com.parvanpajooh.geomanagement.model.GeoEntity;

/**
 * @author ali
 * @author Mohammad
 * @author mehrdad
 *
 */
public interface GeoEntityDao extends GenericDao<GeoEntity, Long> {

	/**
	 * 
	 * @param shortCode
	 * @return
	 * @throws ParvanDaoException
	 */
	public GeoEntity findGeoEntityByShortCode(String shortCode) throws ParvanDaoException;

	/**
	 * 
	 * @param name
	 * @return
	 * @throws ParvanDaoException
	 */
	public GeoEntity findGeoEntityByName(String name) throws ParvanDaoException;

}
