package com.parvanpajooh.geomanagement.service;

import com.parvanpajooh.commons.platform.ejb.exceptions.ParvanServiceException;
import com.parvanpajooh.commons.platform.ejb.service.GenericLocalService;
import com.parvanpajooh.geomanagement.model.GeoEntity;

/**
 * @author ali
 * @author Mohammad
 *
 */
public interface GeoEntityLocalService extends GenericLocalService<GeoEntity, Long> {

	/**
	 * 
	 * @param shortCode
	 * @return
	 * @throws ParvanServiceException
	 */
	GeoEntity findByShortCode(String shortCode) throws ParvanServiceException;

	/**
	 * 
	 * @param name
	 * @return
	 * @throws ParvanServiceException
	 */
	GeoEntity findByName(String name) throws ParvanServiceException;

}
