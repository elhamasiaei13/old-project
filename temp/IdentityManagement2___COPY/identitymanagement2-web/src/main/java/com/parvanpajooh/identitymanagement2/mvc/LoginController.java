package com.parvanpajooh.identitymanagement2.mvc;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.parvanpajooh.identitymanagement2.common.exceptions.ParvanServiceException;
import com.parvanpajooh.identitymanagement2.mvc.base.BaseController;


@Controller
@RequestMapping("/")
public class LoginController extends BaseController{


	
	@RequestMapping(value = "/mainPage", method=RequestMethod.GET)
	public String mainPageShow(HttpServletRequest request, Model model) throws ParvanServiceException {	
		
		return "mainPage";
	}
	
	@RequestMapping(value = "/logout", method=RequestMethod.GET)
	public String logout(HttpServletRequest request, Model model) throws ParvanServiceException {
		request.getSession().invalidate();	
		
		return "redirect:/login";
	}
	
}
