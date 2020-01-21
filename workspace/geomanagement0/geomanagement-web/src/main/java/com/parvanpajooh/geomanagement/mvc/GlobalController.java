package com.parvanpajooh.geomanagement.mvc;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.parvanpajooh.common.auth.UserInfoLoader;
import com.parvanpajooh.common.util.ParamUtil;
import com.parvanpajooh.commons.platform.ejb.exceptions.ObjectNotFoundException;
import com.parvanpajooh.commons.platform.ejb.exceptions.ParvanServiceException;
import com.parvanpajooh.commons.platform.ejb.model.UserInfo;
import com.parvanpajooh.commons.util.Validator;
import com.parvanpajooh.geomanagement.mvc.base.GeoManagementBaseController;

@ControllerAdvice
public class GlobalController extends GeoManagementBaseController {
	
	@ModelAttribute("userInfo")
	public UserInfo populateUserInfo(HttpServletRequest request) throws ObjectNotFoundException, ParvanServiceException {

		UserInfo userInfo = UserInfoLoader.getInstance().getUserInfo();
		
		return userInfo;
	}
	
	@ModelAttribute("timeZones")
	public List<String> loadTimeZones(HttpServletRequest request) throws ObjectNotFoundException, ParvanServiceException {
		@SuppressWarnings("unchecked")
		List<String> timeZones = (List<String>) request.getSession().getAttribute("timeZones");
		
		boolean doRefresh = ParamUtil.getBoolean(request, "doRefresh", false);
		
		if(Validator.isNull(timeZones) || doRefresh){
			
			Set<String> zoneIds = ZoneId.getAvailableZoneIds();
			
			request.getSession().setAttribute("timeZones", new ArrayList<String>(zoneIds));
		}
		
		return timeZones;
	}

}
