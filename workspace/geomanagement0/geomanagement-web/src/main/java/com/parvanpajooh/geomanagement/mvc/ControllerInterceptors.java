package com.parvanpajooh.geomanagement.mvc;

import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import com.parvanpajooh.common.auth.UserInfoLoader;
import com.parvanpajooh.common.util.UserInfoThreadLocal;
import com.parvanpajooh.commons.platform.ejb.model.UserInfo;
import com.parvanpajooh.commons.platform.ejb.util.logging.MdcHelper;
import com.parvanpajooh.commons.util.CentralizedThreadLocal;
import com.parvanpajooh.commons.util.LocaleThreadLocal;
import com.parvanpajooh.commons.util.LocaleUtil;
import com.parvanpajooh.commons.util.TimeZoneThreadLocal;
import com.parvanpajooh.commons.util.TimeZoneUtil;
import com.parvanpajooh.commons.util.ZoneIdThreadLocal;
import com.parvanpajooh.commons.util.ZoneIdUtil;
import com.parvanpajooh.geomanagement.mvc.base.GeoManagementBaseController;

public class ControllerInterceptors extends GeoManagementBaseController implements AsyncHandlerInterceptor {

	protected transient final Logger log = LoggerFactory.getLogger(getClass());

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		if(handler instanceof ResourceHttpRequestHandler){
			return true;
		}
		
		UserInfo userInfo = UserInfoLoader.getInstance().getUserInfo();
		
		LocaleThreadLocal.setDefaultLocale(LocaleUtil.fromLanguageId(userInfo.getLocale()));
		TimeZoneThreadLocal.setDefaultTimeZone(TimeZoneUtil.getTimeZone(userInfo.getZoneId()));
		ZoneIdThreadLocal.setDefaultZoneId(ZoneIdUtil.getZoneId(userInfo.getZoneId()));
		UserInfoThreadLocal.setUserInfo(userInfo);
		
    	if (userInfo != null) {
        	String sessionId = request.getSession().getId();
    		UUID uuid = UUID.randomUUID();
    		String requestId = uuid.toString();
        	String requestServletPath = request.getServletPath();
        	String requestPathInfo = request.getPathInfo();
        	userInfo.setSessionId(sessionId);
        	userInfo.setRequestId(requestId);
        	userInfo.setRequestServletPath(requestServletPath);
        	userInfo.setRequestPathInfo(requestPathInfo);
        	MdcHelper.fillMdc(userInfo);
        	
        	//XXX MO: Temp Code
        	Set<String> roleNames = userInfo.getRoleNames();
        	if ( !roleNames.isEmpty() ) {
        		if ( !(roleNames.contains("ROLE_MANAGER") || roleNames.contains("ROLE_ADMIN")) ) {
        			log.debug("access denied!!!");
        			return false;
        		}
        	}
        	//XXX END
    	} 
		
		return true;
	}

	/**
	 * This implementation is empty.
	 */
	@Override
	public void postHandle(
			HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {
		
	}

	/**
	 * This implementation is empty.
	 */
	@Override
	public void afterCompletion(
			HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
		CentralizedThreadLocal.clearShortLivedThreadLocals();
		
	}

	/**
	 * This implementation is empty.
	 */
	@Override
	public void afterConcurrentHandlingStarted(
			HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
	}
	
}
