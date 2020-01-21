package com.parvanpajooh.common.convertor;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class LocalDateJsonDeserializer extends JsonDeserializer<LocalDate> {

    //private static Logger logger = LoggerFactory.getLogger(JodaDateTimeJsonSerializer.class);
	
	DateTimeFormatter _formatter = null;
	
    @Override
    public LocalDate deserialize(JsonParser parser, DeserializationContext context)
            throws IOException{
    	
    	System.out.println("");
    	
    	switch(parser.getCurrentToken())
        {
            case START_ARRAY:
                if(parser.nextToken() == JsonToken.END_ARRAY)
                    return null;
                int year = parser.getIntValue();

                parser.nextToken();
                int month = parser.getIntValue();

                parser.nextToken();
                int day = parser.getIntValue();

                if(parser.nextToken() != JsonToken.END_ARRAY)
                    throw context.wrongTokenException(parser, JsonToken.END_ARRAY, "Expected array to end.");
                return LocalDate.of(year, month, day);

            case VALUE_STRING:
                String string = parser.getText().trim();
                if(string.length() == 0) {
                    return null;
                }
                return LocalDate.parse(string, _formatter);
        }

        throw context.wrongTokenException(parser, JsonToken.START_ARRAY, "Expected array or string.");
    	
    }
}