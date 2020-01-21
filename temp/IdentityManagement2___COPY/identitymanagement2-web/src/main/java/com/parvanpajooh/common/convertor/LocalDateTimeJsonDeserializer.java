package com.parvanpajooh.common.convertor;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class LocalDateTimeJsonDeserializer extends JsonDeserializer<LocalDateTime> {

    //private static Logger logger = LoggerFactory.getLogger(JodaDateTimeJsonSerializer.class);
	
	DateTimeFormatter _formatter = null;
	
    @Override
    public LocalDateTime deserialize(JsonParser parser, DeserializationContext context)
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

                parser.nextToken();
                int hour = parser.getIntValue();

                parser.nextToken();
                int minute = parser.getIntValue();

                if(parser.nextToken() != JsonToken.END_ARRAY)
                {
                    int second = parser.getIntValue();

                    if(parser.nextToken() != JsonToken.END_ARRAY)
                    {
                        int partialSecond = parser.getIntValue();
                        if(partialSecond < 1_000 &&
                                !context.isEnabled(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS))
                            partialSecond *= 1_000_000; // value is milliseconds, convert it to nanoseconds

                        if(parser.nextToken() != JsonToken.END_ARRAY)
                            throw context.wrongTokenException(parser, JsonToken.END_ARRAY, "Expected array to end.");

                        return LocalDateTime.of(year, month, day, hour, minute, second, partialSecond);
                    }

                    return LocalDateTime.of(year, month, day, hour, minute, second);
                }

                return LocalDateTime.of(year, month, day, hour, minute);

            case VALUE_STRING:
                String string = parser.getText().trim();
                if (string.length() == 0) {
                    return null;
                }
                return LocalDateTime.parse(string, _formatter);
        }

        throw context.wrongTokenException(parser, JsonToken.START_ARRAY, "Expected array or string.");
    	
    }
}