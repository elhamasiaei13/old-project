package com.parvanpajooh.identitymanagement2.mvc.base;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GlobalExceptionController extends BaseController {
	
	@RequestMapping("/error.html")
	public String error(HttpServletRequest request, Model model) {
		log.debug("error");
        model.addAttribute("errorCode", request.getAttribute("javax.servlet.error.status_code"));
        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
        
        String errorMessage = null;
        
        if (throwable != null) {
            errorMessage = throwable.getMessage();
        }
        
        model.addAttribute("errorMessage", errorMessage);
        
        return "error/unrecoverable";
    }
	
	@RequestMapping("/login_error.html")
	public String loginError(HttpServletRequest request, Model model) {
		log.debug("login error");
		return "error/login_error";
	}
	
	@RequestMapping("/403.html")
	public String error403(HttpServletRequest request, Model model) {
		log.debug("403");
		return "error/403";
	}
	
	@RequestMapping("/404.html")
	public String error404(HttpServletRequest request, Model model) {
		log.debug("404");
		return "error/404";
	}
	
}
