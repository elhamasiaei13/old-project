package com.parvanpajooh.sample.mvc;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.parvanpajooh.commons.config.Config;

@Controller
public class LogoutController {

	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String showUsers(HttpServletRequest request, ModelMap model) {
		
		model.clear();
		request.getSession().invalidate();
		
	    return "redirect:" + Config.getProperty("uaaLogout") + "?redirect=" + URLEncoder.encode("http://"+request.getServerName()+"/");
	}
}
