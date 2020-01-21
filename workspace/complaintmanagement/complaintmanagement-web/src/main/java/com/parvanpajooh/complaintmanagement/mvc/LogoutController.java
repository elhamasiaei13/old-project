package com.parvanpajooh.complaintmanagement.mvc;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.parvanpajooh.commons.config.Config;
import com.parvanpajooh.complaintmanagement.mvc.base.ComplaintManagementBaseController;

@Controller
public class LogoutController extends ComplaintManagementBaseController {

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logOut(HttpServletRequest request, ModelMap model) {

		// LOG
		log.debug("Entering logOut()");

		String redirectUrl = null;
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			model.clear();
			request.getSession().invalidate();

			redirectUrl = "redirect:" + Config.getProperty("uaaLogout") + "?redirect=" + URLEncoder.encode(
					"http://" + request.getServerName() + "/", java.nio.charset.StandardCharsets.UTF_8.toString());

		} catch (UnsupportedEncodingException e) {
			proccessException(e, result);
		}

		// LOG
		log.debug("Leaving logOut()");
		return redirectUrl;
	}
}
