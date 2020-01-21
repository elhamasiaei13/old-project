package com.parvanpajooh.identitymanagement2.mvc.base;

import java.awt.Menu;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.thymeleaf.extras.springsecurity4.auth.AuthUtils;

import com.parvanpajooh.common.Idm2UserInfoFactory;
import com.parvanpajooh.common.UserInfo;
import com.parvanpajooh.common.exceptions.ObjectNotFoundException;
import com.parvanpajooh.common.exceptions.ParvanServiceException;
import com.parvanpajooh.common.util.DateUtil;
import com.parvanpajooh.common.util.IpUtils;
import com.parvanpajooh.common.util.LocaleUtil;
import com.parvanpajooh.common.util.TenantUtil;
import com.parvanpajooh.common.util.TimeZoneUtil;
import com.parvanpajooh.common.util.Validator;
import com.parvanpajooh.common.util.ZoneIdUtil;

/**
 * 
 * @author ali
 *
 */
public abstract class ShareController extends BaseRestController {

	protected transient final Logger log = LoggerFactory.getLogger(getClass());

	protected UserInfo getUserInfo(HttpServletRequest request) throws ObjectNotFoundException, ParvanServiceException {

		HttpSession session = request.getSession();

		UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");

		if (userInfo == null) {
			Authentication authentication = AuthUtils.getAuthenticationObject();

			String userName = null;
			if (authentication == null) {
				userName = "anonymousUser";
			} else {
				userName = authentication.getName();

			}

			String ip = IpUtils.getIpFromRequest(request);

			String tenantId = TenantUtil.getTenantId(request);

			// TimeZone timeZone = TimeZoneUtil.getDefault();
			ZoneId timeZone = ZoneId.systemDefault();
			String defaultCalendar = "gregorian";

			userInfo = Idm2UserInfoFactory.getUserInfo(0l, "anonymousUser", "guest", "guest", ip, request.getContextPath(), tenantId, new HashSet<String>(),
					LocaleUtil.fromLanguageId("fa").toString(), timeZone.getId(), defaultCalendar);
		}

		return userInfo;
	}

//	protected Menu getMenus(HttpServletRequest request) {
//		HttpSession session = request.getSession();
//		Menu menus = (Menu) session.getAttribute("menus");
//
//		if (Validator.isNull(menus)) {
//
//		}
//	}

	protected boolean hasRole(String role) {
		Authentication authentication = AuthUtils.getAuthenticationObject();
		@SuppressWarnings("unchecked")
		List<GrantedAuthority> authorities = (List<GrantedAuthority>) authentication.getAuthorities();

		boolean hasRole = false;

		for (GrantedAuthority grantedAuthority : authorities) {
			if (grantedAuthority.getAuthority().equals(role)) {
				hasRole = true;
				break;
			}
		}

		return hasRole;
	}

	protected String getLocalDate(LocalDateTime utcDate, UserInfo userInfo) {
		return DateUtil.getDate(utcDate, LocaleUtil.fromLanguageId(userInfo.getLocale()), userInfo.getCalendar(), ZoneIdUtil.getZoneId(userInfo.getZoneId()));
	}

	protected String getLocalDate(LocalDate utcDate, UserInfo userInfo) {
		return DateUtil.getDate(utcDate, LocaleUtil.fromLanguageId(userInfo.getLocale()), userInfo.getCalendar(), ZoneIdUtil.getZoneId(userInfo.getZoneId()));
	}

	protected String getLocalDate(Date utcDate, UserInfo userInfo) {
		return DateUtil.getDate(utcDate, LocaleUtil.fromLanguageId(userInfo.getLocale()), userInfo.getCalendar(), ZoneIdUtil.getZoneId(userInfo.getZoneId()));
	}

	protected String getLocalDateTime(LocalDateTime utcDate, UserInfo userInfo) {
		return DateUtil.getDateTime(utcDate, LocaleUtil.fromLanguageId(userInfo.getLocale()), userInfo.getCalendar(),
				ZoneIdUtil.getZoneId(userInfo.getZoneId()));
	}

	protected String getLocalDateTime(Date utcDate, UserInfo userInfo) {
		return DateUtil.getDateTime(utcDate, LocaleUtil.fromLanguageId(userInfo.getLocale()), userInfo.getCalendar(),
				ZoneIdUtil.getZoneId(userInfo.getZoneId()));
	}
}
