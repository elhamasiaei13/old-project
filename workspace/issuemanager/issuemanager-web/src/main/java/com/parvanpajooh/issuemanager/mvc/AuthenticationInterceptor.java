package com.parvanpajooh.issuemanager.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.parvanpajooh.commons.platform.ejb.model.UserInfo;

@Component
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

	protected transient final Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		log.info("Interceptor: Pre-handle");
		
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");

		// Avoid a redirect loop for some urls
		if (userInfo == null) {
			response.sendRedirect("/issuemanager-web/login");
			return false;
		}
		return true;
	}
	
	@Override
	public void postHandle(	HttpServletRequest request, HttpServletResponse response,
			Object handler, ModelAndView modelAndView) throws Exception {
		System.out.println("---method executed---");
	}
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
			Object handler, Exception ex) throws Exception {
		System.out.println("---Request Completed---");
	}
	
}
