package com.parvanpajooh.geomanagement.service;

import com.parvanpajooh.commons.platform.ejb.exceptions.ParvanServiceException;
import com.parvanpajooh.commons.platform.ejb.model.UserInfo;
import com.parvanpajooh.commons.platform.ejb.service.GenericService;
import com.parvanpajooh.geomanagement.model.GeoType;
import com.parvanpajooh.geomanagement.model.vo.GeoTypeVO;

/**
 * @author ali
 *
 */
public interface GeoTypeService extends GenericService<GeoType, Long> {

	public GeoTypeVO findByCode(UserInfo userInfo, String code) throws ParvanServiceException;
}
