package com.parvanpajooh.geomanagement.service;

import com.parvanpajooh.commons.platform.ejb.exceptions.ParvanServiceException;
import com.parvanpajooh.commons.platform.ejb.service.GenericLocalService;
import com.parvanpajooh.geomanagement.model.GeoType;

/**
 * @author ali
 *
 */
public interface GeoTypeLocalService extends GenericLocalService<GeoType, Long> {

	public GeoType findByCode(String code) throws ParvanServiceException;

}
