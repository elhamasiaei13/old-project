package com.parvanpajooh.dialects.converter;

import org.thymeleaf.Arguments;

import com.parvanpajooh.geomanagement.model.vo.GeoTypeVO;

public class GeoTypeConverter extends Converter {

	public GeoTypeConverter(Arguments arguments) {
		super(arguments);
	}

	@Override
	public String getText(Object data) {
		GeoTypeVO vo = (GeoTypeVO) data;
		return vo.getNameFa();// TODO use locale
	}

	@Override
	public String getValue(Object data) {
		GeoTypeVO vo = (GeoTypeVO) data;
		return vo.getCode();
	}

}
