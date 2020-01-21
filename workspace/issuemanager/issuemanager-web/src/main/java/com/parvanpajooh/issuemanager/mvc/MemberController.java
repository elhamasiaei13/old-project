package com.parvanpajooh.issuemanager.mvc;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.dandelion.datatables.core.ajax.ColumnDef;
import com.github.dandelion.datatables.core.ajax.ColumnDef.SortDirection;
import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import com.github.dandelion.datatables.core.ajax.DatatablesResponse;
import com.github.dandelion.datatables.extras.spring4.ajax.DatatablesParams;
import com.parvanpajooh.commons.enums.SortDirectionEnum;
import com.parvanpajooh.commons.platform.ejb.model.UserInfo;
import com.parvanpajooh.commons.platform.ejb.model.vo.BaseVO;
import com.parvanpajooh.commons.util.Validator;
import com.parvanpajooh.issuemanager.common.exceptions.ErrorCode;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanDaoException;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanRecoverableException;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanServiceException;
import com.parvanpajooh.issuemanager.model.criteria.MemberCriteria;
import com.parvanpajooh.issuemanager.model.enums.RoleEnum;
import com.parvanpajooh.issuemanager.model.vo.MemberVO;
import com.parvanpajooh.issuemanager.model.vo.MembershipVO;
import com.parvanpajooh.issuemanager.service.MemberService;
import com.parvanpajooh.issuemanager.service.MembershipService;

@Controller
@RequestMapping("/")
public class MemberController extends BaseController {

	private static final String REPO_PATH = "/usr/local/issuemanager/";

	@EJB(mappedName = "java:global/issuemanager-ear/issuemanager-ejb/MemberServiceImpl")
	private MemberService memberService;

	@EJB(mappedName = "java:global/issuemanager-ear/issuemanager-ejb/MembershipServiceImpl")
	private MembershipService membershipService;

	@RequestMapping(value = "/showMembers", method = RequestMethod.GET)
	public String showMembers(HttpServletRequest request, Model model) throws ParvanServiceException {
		Map<String, Object> result = new HashMap<String, Object>();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
		List<BaseVO> list = null;
		try {
			// check access
			Set<String> userRoles = userInfo.getRoleNames();
			if (!(userRoles.contains(RoleEnum.ADMIN.value) && userRoles.contains(RoleEnum.MANAGER.value))) {
				throw new ParvanRecoverableException(ErrorCode.HAS_NOT_ACCESS);
			}

		} catch (ParvanServiceException e) {
			proccessException(e, result);
			model.addAttribute("result", result);
			model.addAttribute("allMembers", list);
			model.addAttribute("userInfo", userInfo);
			return "mainPage";
		}
		model.addAttribute("allMembers", list);
		model.addAttribute("userInfo", userInfo);
		return "showMembers";
	}

	@RequestMapping(value = "/changePass", method = RequestMethod.GET)
	public String showChangePassPage(HttpServletRequest request, Model model) throws ParvanServiceException {

		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
		model.addAttribute("userInfo", userInfo);
		model.addAttribute("member", new MemberVO());
		return "changePass";
	}

	@RequestMapping(value = "/changePass/save", method = { RequestMethod.POST, RequestMethod.GET })
	public String changePass(HttpServletRequest request, Model model, @ModelAttribute MemberVO memberVO) throws ParvanServiceException {

		Map<String, Object> result = new HashMap<String, Object>();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
		MemberVO member;
		try {

			member = (MemberVO) memberService.get(userInfo, userInfo.getUserId());

			member.setPassword(memberVO.getPassword());
			member.setPasswordConfirm(memberVO.getPasswordConfirm());

			memberService.changePass(userInfo, member);
		} catch (ParvanServiceException e) {
			proccessException(e, result);
			model.addAttribute("result", result);
			model.addAttribute("member", new MemberVO());
			model.addAttribute("userInfo", userInfo);
			return "changePass";
		}

		model.addAttribute("userInfo", request.getSession().getAttribute("userInfo"));
		return "redirect:/mainPage?success=true";
	}

	@RequestMapping(value = "/changePassByAdmin/save", method = { RequestMethod.POST, RequestMethod.GET })
	public String changePassByAdmin(HttpServletRequest request, Model model, @ModelAttribute MemberVO memberVO) throws ParvanServiceException {

		Map<String, Object> result = new HashMap<String, Object>();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
		MemberVO member;
		try {

			member = (MemberVO) memberService.get(userInfo, memberVO.getId());

			member.setPassword(memberVO.getPassword());
			member.setPasswordConfirm(memberVO.getPasswordConfirm());

			memberService.changePass(userInfo, member);
		} catch (ParvanServiceException e) {
			proccessException(e, result);
			model.addAttribute("result", result);
			model.addAttribute("member", memberVO);
			model.addAttribute("userInfo", userInfo);
			return "changePassByAdmin";
		}

		model.addAttribute("userInfo", request.getSession().getAttribute("userInfo"));
		return "redirect:/mainPage?success=true";
	}

	@RequestMapping(value = "/changePassByAdmin/{id}", method = RequestMethod.GET)
	public String showChangePassPageByAdmin(HttpServletRequest request, Model model, @PathVariable Long id) throws ParvanServiceException {

		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
		MemberVO member = (MemberVO) memberService.get(userInfo, id);
		member.setPassword(null);
		model.addAttribute("userInfo", userInfo);
		model.addAttribute("member", member);
		return "changePassByAdmin";
	}

	@RequestMapping(value = "/addMember", method = RequestMethod.GET)
	public String showAddPage(HttpServletRequest request, Model model) throws ParvanServiceException {
		model.addAttribute("member", new MemberVO());
		model.addAttribute("userInfo", request.getSession().getAttribute("userInfo"));
		return "addMember";
	}

	@RequestMapping(value = "/addMember/save", method = { RequestMethod.POST, RequestMethod.GET })
	public String addMember(HttpServletRequest request, @ModelAttribute MemberVO memberVO, Model model, @RequestParam("file") MultipartFile file)
			throws ParvanServiceException, ParvanDaoException {
		Map<String, Object> result = new HashMap<String, Object>();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");

		try {
			InputStream in = new ByteArrayInputStream(file.getBytes());
			BufferedImage image = ImageIO.read(in);
			String mimeType = file.getContentType();
			byte[] imageFile = file.getBytes();
			memberVO.setFileImage(imageFile);

			// save member
			memberService.save(userInfo, memberVO);

		} catch (ParvanServiceException e) {
			proccessException(e, result);
			model.addAttribute("result", result);
			model.addAttribute("member", new MemberVO());
			model.addAttribute("userInfo", userInfo);
			return "addMember";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "redirect:/showMembers?success=true";

	}

	@RequestMapping(value = "/removeMember", method = RequestMethod.POST)
	public String removeMember(final HttpServletRequest req) throws ParvanServiceException, ParvanDaoException {
		UserInfo userInfo = (UserInfo) req.getSession().getAttribute("userInfo");

		Long rowId = Long.valueOf(req.getParameter("id"));

		// delete member
		memberService.delete(userInfo, rowId);
		return "redirect:/showMembers";
	}

	@RequestMapping(value = "/editMember/{id}", method = RequestMethod.GET)
	public String showEditPage(@PathVariable Long id, final HttpServletRequest req, Model model) throws ParvanServiceException {

		UserInfo userInfo = (UserInfo) req.getSession().getAttribute("userInfo");
		MemberVO member = (MemberVO) memberService.get(userInfo, id);
		model.addAttribute("member", member);
		model.addAttribute("userInfo", userInfo);
		return "editMember";
	}

	@RequestMapping(value = "/editMember/save", method = { RequestMethod.POST, RequestMethod.GET })
	public String editMember(HttpServletRequest request, @ModelAttribute MemberVO memberVO, Model model, @RequestParam("file") MultipartFile file)
			throws ParvanServiceException, ParvanDaoException {

		Map<String, Object> result = new HashMap<String, Object>();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
		try {

			byte[] imageFile = file.getBytes();
			memberVO.setFileImage(imageFile);
			memberVO.setActive(true);

			// edit member
			memberService.save(userInfo, memberVO);

		} catch (ParvanServiceException e) {
			proccessException(e, result);
			model.addAttribute("result", result);
			model.addAttribute("member", memberVO);
			model.addAttribute("userInfo", userInfo);
			return "editMember";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "redirect:/showMembers";
	}

	@RequestMapping(value = "/pagingAllMembers", method = RequestMethod.GET)
	public @ResponseBody DatatablesResponse<BaseVO> findAll(@DatatablesParams DatatablesCriterias criterias, MemberCriteria memberCriteria,
			HttpServletRequest request) {

		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
		int firstResult = criterias.getDisplayStart();
		int maxResults = criterias.getDisplaySize();
		ColumnDef columnDef = criterias.getSortingColumnDefs().get(0);
		memberCriteria.setActive(true);

		SortDirectionEnum sortDirection = SortDirectionEnum.Ascending;
		String sortCriterion = columnDef.getName();

		if ("0".equals(sortCriterion)) {
			sortCriterion = "indexInParent";
		}

		if (columnDef.getSortDirection().equals(SortDirection.DESC)) {
			sortDirection = SortDirectionEnum.Descending;
		}

		DataSet<BaseVO> dataSet;

		try {

			memberCriteria.setTenantId(null);

			List<BaseVO> rows = memberService.findByCriteria(userInfo, memberCriteria, firstResult, maxResults, sortDirection, sortCriterion);
			long totalRecords = memberService.countByCriteria(userInfo, memberCriteria);
			dataSet = new DataSet<BaseVO>(rows, (long) rows.size(), totalRecords);

		} catch (Exception e) {
			log.error("error occured in menu paging...", e);
			dataSet = new DataSet<BaseVO>(new ArrayList<BaseVO>(), 0l, 0l);
		}
		return DatatablesResponse.build(dataSet, criterias);
	}

	@RequestMapping(value = "/pagingMembers/{id}", method = RequestMethod.GET)
	public @ResponseBody DatatablesResponse<BaseVO> findByGroupId(@DatatablesParams DatatablesCriterias criterias, MemberCriteria memberCriteria,
			HttpServletRequest request, @PathVariable Long id) {

		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
		int firstResult = criterias.getDisplayStart();
		int maxResults = criterias.getDisplaySize();
		ColumnDef columnDef = criterias.getSortingColumnDefs().get(0);

		SortDirectionEnum sortDirection = SortDirectionEnum.Ascending;
		String sortCriterion = columnDef.getName();
		memberCriteria.setActive(true);

		if ("0".equals(sortCriterion)) {
			sortCriterion = "indexInParent";
		}

		if (columnDef.getSortDirection().equals(SortDirection.DESC)) {
			sortDirection = SortDirectionEnum.Descending;
		}

		DataSet<BaseVO> dataSet;
		List<BaseVO> rows = new ArrayList<BaseVO>();
		long totalRecords = 0;

		try {

			List<MembershipVO> membershipVOs = membershipService.loadMembershipByGroupId(userInfo, id);
			List<Long> memberIds = new ArrayList<Long>();

			for (MembershipVO item : membershipVOs) {
				memberIds.add(item.getMember().getId());
			}

			memberCriteria.setIds(memberIds);
			memberCriteria.setTenantId(null);

			if (Validator.isNotNull(memberIds)) {
				rows = memberService.findByCriteria(userInfo, memberCriteria, firstResult, maxResults, sortDirection, sortCriterion);
				totalRecords = memberService.countByCriteria(userInfo, memberCriteria);
			}

			dataSet = new DataSet<BaseVO>(rows, (long) rows.size(), totalRecords);
		} catch (Exception e) {
			log.error("error occured in menu paging...", e);
			dataSet = new DataSet<BaseVO>(new ArrayList<BaseVO>(), 0l, 0l);
		}
		return DatatablesResponse.build(dataSet, criterias);
	}

	@RequestMapping(value = "/image/{id}", method = RequestMethod.GET)
	public String downlaodImage(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {

		// load image from db
		MemberVO memberVO = new MemberVO();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
		ByteArrayInputStream inputStream = null;
		OutputStream outputStream = null;

		try {
			memberVO = (MemberVO) memberService.get(userInfo, id);

			inputStream = new ByteArrayInputStream(memberVO.getFileImage());
			// inputStream = new FileInputStream(memberVO.getFileImageBase64());
			outputStream = response.getOutputStream();

			response.setContentType("image/jpeg");
			IOUtils.copy(inputStream, outputStream);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
				}
			}

			if (outputStream != null) {
				try {
					outputStream.flush();
					outputStream.close();
				} catch (IOException e) {
				}
			}
		}

		return null;
	}
}
