package com.parvanpajooh.identitymanagement2.common;

import java.util.Set;

public class Idm2UserInfoFactory {
	
	public static UserInfo getUserInfo(
			Long userId, 
			String userName, 
			String firstName, 
			String lastName, 
			String ip,
			String context, 
			String tenantId, 
			Set<String> roleNames,
			String locale, 
			String timeZone, 
			String calendar) {
		
		UserInfo userInfo = new UserInfo(userId, userName, firstName, lastName, ip, context, 
				tenantId, roleNames, locale, timeZone, calendar);
		
		return userInfo;
	}	
}
