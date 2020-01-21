package com.parvanpajooh.identitymanagement2.mvc.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.parvanpajooh.common.UserInfo;
import com.parvanpajooh.common.util.CentralizedThreadLocal;
import com.parvanpajooh.common.util.LocaleThreadLocal;
import com.parvanpajooh.common.util.LocaleUtil;
import com.parvanpajooh.common.util.TimeZoneThreadLocal;
import com.parvanpajooh.common.util.TimeZoneUtil;
import com.parvanpajooh.common.util.UserInfoThreadLocal;
import com.parvanpajooh.common.util.ZoneIdThreadLocal;
import com.parvanpajooh.common.util.ZoneIdUtil;

public class ControllerInterceptors extends ShareController implements AsyncHandlerInterceptor {

	protected transient final Logger log = LoggerFactory.getLogger(getClass());

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		UserInfo userInfo = getUserInfo(request);
		
		LocaleThreadLocal.setDefaultLocale(LocaleUtil.fromLanguageId(userInfo.getLocale()));
		TimeZoneThreadLocal.setDefaultTimeZone(TimeZoneUtil.getTimeZone(userInfo.getZoneId()));
		ZoneIdThreadLocal.setDefaultZoneId(ZoneIdUtil.getZoneId(userInfo.getZoneId()));
		UserInfoThreadLocal.setUserInfo(userInfo);
		
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
