package com.parvanpajooh.geomanagement.dao;

import com.parvanpajooh.commons.platform.ejb.dao.GenericDao;
import com.parvanpajooh.commons.platform.ejb.exceptions.ParvanDaoException;
import com.parvanpajooh.geomanagement.model.GeoType;

/**
 * @author ali
 *
 */
public interface GeoTypeDao extends GenericDao<GeoType, Long> {

	public GeoType findGeoTypeByCode(String code) throws ParvanDaoException;

	public GeoType findGeoTypeByNameFa(String nameFa) throws ParvanDaoException;
	
	public GeoType findGeoTypeByNameEn(String nameEn) throws ParvanDaoException;
}
