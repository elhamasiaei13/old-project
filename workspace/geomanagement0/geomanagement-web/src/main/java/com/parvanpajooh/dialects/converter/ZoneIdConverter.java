package com.parvanpajooh.dialects.converter;

import org.thymeleaf.Arguments;

public class ZoneIdConverter extends Converter {

	public ZoneIdConverter(Arguments arguments) {
		super(arguments);
	}

	@Override
	public String getText(Object data) {
		return (String) data;
	}

	@Override
	public String getValue(Object data) {
		return String.valueOf( data );
	}

}
