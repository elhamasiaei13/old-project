package com.parvanpajooh.geomanagement.mvc;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.parvanpajooh.geomanagement.mvc.base.GeoManagementBaseController;

@Controller
@RequestMapping("/")
public class HomeController extends GeoManagementBaseController {
	
	@RequestMapping(method = RequestMethod.GET)
	public String showUsers(HttpServletRequest request, Model model) {
		
	    return "home";
	}
}
