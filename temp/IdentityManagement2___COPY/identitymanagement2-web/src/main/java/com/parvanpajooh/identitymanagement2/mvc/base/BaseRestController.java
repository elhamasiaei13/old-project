package com.parvanpajooh.identitymanagement2.mvc.base;

import java.nio.charset.Charset;
import java.util.Collections;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.parvanpajooh.common.util.Validator;

public class BaseRestController {
	
	
	
	protected HttpHeaders getHeaders(String accessToken){
		HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    	headers.setAcceptCharset(Collections.singletonList(Charset.forName("UTF-8")));
    	//headers.set("Connection", "Close");
    	//headers.set("Accept-Encoding", "gzip");
    	
    	if(Validator.isNotNull(accessToken)){
    		headers.set("Authorization", "Bearer " + accessToken);
    	}
        
        return headers;
	}
}
