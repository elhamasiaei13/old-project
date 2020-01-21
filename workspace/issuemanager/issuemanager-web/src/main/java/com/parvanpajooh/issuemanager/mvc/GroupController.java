package com.parvanpajooh.issuemanager.mvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
import com.parvanpajooh.issuemanager.model.Group;
import com.parvanpajooh.issuemanager.model.criteria.GroupCriteria;
import com.parvanpajooh.issuemanager.model.enums.RoleEnum;
import com.parvanpajooh.issuemanager.model.vo.GroupVO;
import com.parvanpajooh.issuemanager.model.vo.MembershipVO;
import com.parvanpajooh.issuemanager.service.GroupService;
import com.parvanpajooh.issuemanager.service.MembershipService;

@Controller
@RequestMapping("/")
public class GroupController extends BaseController {

	@EJB(mappedName = "java:global/issuemanager-ear/issuemanager-ejb/GroupServiceImpl")
	private GroupService groupService;

	@EJB(mappedName = "java:global/issuemanager-ear/issuemanager-ejb/MembershipServiceImpl")
	private MembershipService membershipService;

	@RequestMapping(value = "/showGroups", method = RequestMethod.GET)
	public String showGroups(final HttpServletRequest req, Model model) {

		Map<String, Object> result = new HashMap<String, Object>();
		UserInfo userInfo = (UserInfo) req.getSession().getAttribute("userInfo");
		List<BaseVO> list = null;

		try {
			// check access
			Set<String> userRoles = userInfo.getRoleNames();
			if (!(userRoles.contains(RoleEnum.ADMIN.value) && userRoles.contains(RoleEnum.MANAGER.value))) {
				throw new ParvanRecoverableException(ErrorCode.HAS_NOT_ACCESS);
			}

			model.addAttribute("userInfo", userInfo);

		} catch (ParvanServiceException e) {
			proccessException(e, result);
			model.addAttribute("result", result);
			model.addAttribute("userInfo", userInfo);

			return "mainPage";
		}
		return "showGroups";
	}

	@RequestMapping(value = "/addGroup", method = RequestMethod.GET)
	public String showAddGroupPage(final HttpServletRequest req, Model model) throws ParvanServiceException {
		model.addAttribute("group", new Group());
		model.addAttribute("userInfo", req.getSession().getAttribute("userInfo"));
		return "addGroup";
	}

	@RequestMapping(value = "/addGroup/save", method = { RequestMethod.POST, RequestMethod.GET })
	public String addGroup(final HttpServletRequest req, @ModelAttribute GroupVO group, Model model) {

		Map<String, Object> result = new HashMap<String, Object>();
		UserInfo userInfo = (UserInfo) req.getSession().getAttribute("userInfo");

		try {

			groupService.save(userInfo, group);

		} catch (ParvanServiceException e) {
			proccessException(e, result);
			model.addAttribute("result", result);
			model.addAttribute("group", new Group());
			model.addAttribute("userInfo", req.getSession().getAttribute("userInfo"));

			return "addGroup";
		}

		return "redirect:/showGroups?success=true";
	}

	@RequestMapping(value = "/editGroup/{id}", method = RequestMethod.GET)
	public String showEditPage(@PathVariable Long id, final HttpServletRequest req, Model model) throws ParvanServiceException, ParvanDaoException {

		UserInfo userInfo = (UserInfo) req.getSession().getAttribute("userInfo");
		GroupVO group = (GroupVO) groupService.get(userInfo, id);
		model.addAttribute("group", group);
		model.addAttribute("userInfo", req.getSession().getAttribute("userInfo"));
		return "editGroup";
	}

	@RequestMapping(value = "/editGroup/save", method = { RequestMethod.POST, RequestMethod.GET })
	public String editMember(@ModelAttribute GroupVO groupVO, Model model, HttpServletRequest request) throws ParvanServiceException, ParvanDaoException {

		Map<String, Object> result = new HashMap<String, Object>();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");

		try {
			groupService.save(userInfo, groupVO);

		} catch (ParvanServiceException e) {
			proccessException(e, result);
			model.addAttribute("result", result);
			model.addAttribute("group", groupVO);
			model.addAttribute("userInfo", userInfo);

			return "editGroup";
		}
		return "redirect:/showGroups";
	}

	@RequestMapping(value = "/removeGroup", method = RequestMethod.POST)
	public String removeMember(final HttpServletRequest req) throws ParvanServiceException, ParvanDaoException {
		Long rowId = Long.valueOf(req.getParameter("id"));

		UserInfo userInfo = (UserInfo) req.getSession().getAttribute("userInfo");
		groupService.delete(userInfo, rowId);
		return "redirect:/showGroups";
	}

	@RequestMapping(value = "/groups/{id}", method = RequestMethod.GET)
	public @ResponseBody GroupVO getGroup(@PathVariable Long id, final HttpServletRequest req, Model model) throws ParvanServiceException, ParvanDaoException {

		UserInfo userInfo = (UserInfo) req.getSession().getAttribute("userInfo");
		
		GroupVO group = (GroupVO) groupService.get(userInfo, id);
		
		return group;
	}

	@RequestMapping(value = "/pagingAllGroups", method = RequestMethod.GET)
	public @ResponseBody DatatablesResponse<BaseVO> findAllGroups(@DatatablesParams DatatablesCriterias criterias, GroupCriteria groupCriteria,
			HttpServletRequest request) {

		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
		int firstResult = criterias.getDisplayStart();
		int maxResults = criterias.getDisplaySize();
		ColumnDef columnDef = criterias.getSortingColumnDefs().get(0);
		groupCriteria.setActive(true);

		SortDirectionEnum sortDirection = SortDirectionEnum.Ascending;
		String sortCriterion = columnDef.getName();

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

			groupCriteria.setTenantId(null);

			rows = groupService.findByCriteria(userInfo, groupCriteria, firstResult, maxResults, sortDirection, sortCriterion);
			totalRecords = groupService.countByCriteria(userInfo, groupCriteria);

			dataSet = new DataSet<BaseVO>(rows, (long) rows.size(), totalRecords);
		} catch (Exception e) {
			log.error("error occured in menu paging...", e);
			dataSet = new DataSet<BaseVO>(new ArrayList<BaseVO>(), 0l, 0l);
		}
		return DatatablesResponse.build(dataSet, criterias);
	}

	@RequestMapping(value = "/pagingGroups", method = RequestMethod.GET)
	public @ResponseBody DatatablesResponse<BaseVO> findAll(@DatatablesParams DatatablesCriterias criterias, GroupCriteria groupCriteria,
			HttpServletRequest request) {

		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
		int firstResult = criterias.getDisplayStart();
		int maxResults = criterias.getDisplaySize();
		ColumnDef columnDef = criterias.getSortingColumnDefs().get(0);

		SortDirectionEnum sortDirection = SortDirectionEnum.Ascending;
		String sortCriterion = columnDef.getName();
		groupCriteria.setActive(true);

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
			List<MembershipVO> membershipVOs = membershipService.findByMemberId(userInfo);
			List<Long> groupIds = new ArrayList<Long>();

			for (MembershipVO item : membershipVOs) {
				groupIds.add(item.getGroupId().getId());
			}

			groupCriteria.setIds(groupIds);
			groupCriteria.setTenantId(null);
			if (Validator.isNotNull(groupIds)) {
				rows = groupService.findByCriteria(userInfo, groupCriteria, firstResult, maxResults, sortDirection, sortCriterion);
				totalRecords = groupService.countByCriteria(userInfo, groupCriteria);
			}
			dataSet = new DataSet<BaseVO>(rows, (long) rows.size(), totalRecords);
		} catch (Exception e) {
			log.error("error occured in menu paging...", e);
			dataSet = new DataSet<BaseVO>(new ArrayList<BaseVO>(), 0l, 0l);
		}
		return DatatablesResponse.build(dataSet, criterias);
	}
}
