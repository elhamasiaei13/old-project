package com.parvanpajooh.common.convertor;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

@Component
public class CustomJacksonObjectMapper extends ObjectMapper {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7989025780038599881L;

	public CustomJacksonObjectMapper(){
    	SimpleModule module = new SimpleModule("MyModule", new Version(1, 0, 0, null, null, null));
//    	module.addSerializer(LocalDate.class, new DateJsonSerializer());
    	module.addSerializer(LocalDateTime.class, new DateJsonSerializer());
//    	module.addSerializer(JalaliLocalDateTime.class, new JalaliDateJsonSerializer());
//    	module.addDeserializer(LocalDate.class, new LocalDateJsonDeserializer());
    	module.addDeserializer(LocalDateTime.class, new LocalDateTimeJsonDeserializer());
    	registerModule(module);
    }

}
