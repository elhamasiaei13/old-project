package com.parvanpajooh.issuemanager.mvc;

import java.time.ZoneId;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.parvanpajooh.commons.platform.ejb.model.UserInfo;
import com.parvanpajooh.commons.util.LocaleUtil;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanServiceException;
import com.parvanpajooh.issuemanager.model.vo.MemberVO;
import com.parvanpajooh.issuemanager.service.MemberService;

@Controller
@RequestMapping("/")
public class LoginController extends BaseController {

	@EJB(mappedName = "java:global/issuemanager-ear/issuemanager-ejb/MemberServiceImpl")
	private MemberService memberService;

	@RequestMapping(value = "/*", method = RequestMethod.GET)
	public String enter(HttpServletRequest request, Model model) throws ParvanServiceException {
		model.addAttribute("member", new MemberVO());
		return "redirect:/login";
	}

	@RequestMapping(value = "/login", method = { RequestMethod.POST, RequestMethod.GET })
	public String login(HttpServletRequest request, Model model) throws ParvanServiceException {
		boolean isPosted = request.getMethod().toLowerCase().equals("post");

		if (isPosted) {

			String username = request.getParameter("username");
			String password = request.getParameter("password");
			ZoneId timeZone = ZoneId.systemDefault();
			String defaultCalendar = "gregorian";
			Map<String, Object> result = new HashMap<String, Object>();
			
			try {
				MemberVO memberVO = memberService.searchMemberByUsernamePass(username, password);
				model.addAttribute("member", memberVO);
				Set<String> roleNamesSet = new HashSet<String>(memberVO.getRoles());


				UserInfo userInfo = new UserInfo(memberVO.getId(), memberVO.getUsername(), memberVO.getFirstName(), memberVO.getLastName(), request.getRemoteAddr(), request.getContextPath(),null , roleNamesSet, LocaleUtil.fromLanguageId("fa").toString(), timeZone.getId(), defaultCalendar) {
				};

				request.getSession().setAttribute("userInfo", userInfo);
				model.addAttribute("userInfo", userInfo);

			} catch (ParvanServiceException e) {
				proccessException(e, result);
				model.addAttribute("result", result);

				return "login";
			}

			return "redirect:/mainPage?success=true";

		} else {
			model.addAttribute("member", new MemberVO());
			return "login";
		}
	}

	@RequestMapping(value = "/mainPage", method = RequestMethod.GET)
	public String mainPageShow(HttpServletRequest request, Model model) throws ParvanServiceException {
		model.addAttribute("userInfo", request.getSession().getAttribute("userInfo"));
		return "mainPage";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request, Model model) throws ParvanServiceException {
		request.getSession().invalidate();
		model.addAttribute("userInfo", request.getSession().getAttribute("userInfo"));
		return "redirect:/login";
	}

}
