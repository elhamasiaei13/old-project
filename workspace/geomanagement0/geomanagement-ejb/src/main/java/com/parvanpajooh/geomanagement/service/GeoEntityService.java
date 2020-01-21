package com.parvanpajooh.geomanagement.service;

import com.parvanpajooh.commons.platform.ejb.exceptions.ParvanServiceException;
import com.parvanpajooh.commons.platform.ejb.model.UserInfo;
import com.parvanpajooh.commons.platform.ejb.service.GenericService;
import com.parvanpajooh.geomanagement.model.GeoEntity;
import com.parvanpajooh.geomanagement.model.vo.GeoEntityVO;

/**
 * @author ali
 * @author Mohammad
 *
 */
public interface GeoEntityService extends GenericService<GeoEntity, Long> {

	/**
	 * 
	 * @param userInfo
	 * @param shortCode
	 * @return
	 * @throws ParvanServiceException
	 */
	GeoEntityVO findByShortCode(UserInfo userInfo, String shortCode) throws ParvanServiceException;

	/**
	 * 
	 * @param userInfo
	 * @param name
	 * @return
	 * @throws ParvanServiceException
	 */
	GeoEntityVO findByName(UserInfo userInfo, String name) throws ParvanServiceException;

}
