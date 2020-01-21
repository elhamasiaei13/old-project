package com.parvanpajooh.issuemanager.mvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import com.parvanpajooh.issuemanager.common.exceptions.ParvanDaoException;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanServiceException;
import com.parvanpajooh.issuemanager.model.criteria.MemberCriteria;
import com.parvanpajooh.issuemanager.model.criteria.MembershipCriteria;
import com.parvanpajooh.issuemanager.model.vo.GroupVO;
import com.parvanpajooh.issuemanager.model.vo.MemberVO;
import com.parvanpajooh.issuemanager.model.vo.MembershipVO;
import com.parvanpajooh.issuemanager.service.GroupService;
import com.parvanpajooh.issuemanager.service.MemberService;
import com.parvanpajooh.issuemanager.service.MembershipService;

@Controller
@RequestMapping("/")
public class MembershipController extends BaseController {

	@EJB(mappedName = "java:global/issuemanager-ear/issuemanager-ejb/MemberServiceImpl")
	private MemberService memberService;

	@EJB(mappedName = "java:global/issuemanager-ear/issuemanager-ejb/MembershipServiceImpl")
	private MembershipService membershipService;

	@EJB(mappedName = "java:global/issuemanager-ear/issuemanager-ejb/GroupServiceImpl")
	private GroupService groupService;

	protected transient final Logger log = LoggerFactory.getLogger(getClass());

	@RequestMapping(value = "/addMembership/{id}", method = RequestMethod.GET)
	public String showMemberships(final HttpServletRequest req, @PathVariable Long id, Model model) throws ParvanServiceException {

		UserInfo userInfo = (UserInfo) req.getSession().getAttribute("userInfo");

		model.addAttribute("groupId", id);
		model.addAttribute("userInfo", userInfo);
		model.addAttribute("member", new MemberVO());

		return "addMembership";
	}

	@RequestMapping(value = "/deleteMembership/{id}", method = RequestMethod.GET)
	public String showRemovePage(@PathVariable Long id, final HttpServletRequest request, Model model) {

		Map<String, Object> result = new HashMap<String, Object>();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
		List<MembershipVO> memberships = new ArrayList<>();

		try {
			memberships = membershipService.loadMembershipByGroupId(userInfo, id);
		} catch (ParvanServiceException e) {
			proccessException(e, result);
		}

		model.addAttribute("groupId", id);
		model.addAttribute("memberships", memberships);
		model.addAttribute("userInfo", request.getSession().getAttribute("userInfo"));
		return "deleteMembership";
	}

	@RequestMapping(value = "/deleteMembership/save", method = { RequestMethod.POST, RequestMethod.GET })
	public String removeMemberships(final HttpServletRequest request, Model model) {

		Map<String, Object> result = new HashMap<String, Object>();

		try {

			UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
			String[] membersId = request.getParameterValues("checkboxItems");

			// delete memberships
			for (String id : membersId) {
				membershipService.delete(userInfo, Long.parseLong(id));
			}
		} catch (ParvanServiceException e) {
			proccessException(e, result);
		}

		model.addAttribute("userInfo", request.getSession().getAttribute("userInfo"));
		return "redirect:/showGroups";
	}

	@RequestMapping(value = "/addMembership/save", method = { RequestMethod.POST, RequestMethod.GET })
	public String editMember(Model model, final HttpServletRequest request) throws ParvanServiceException, ParvanDaoException {
		Map<String, Object> result = new HashMap<String, Object>();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
		try {
			String[] membersId = request.getParameterValues("checkboxItems");
			String[] typesId = request.getParameterValues("typeItems");
			String groupId = request.getParameter("groupId");
			String[] allItems = request.getParameterValues("checkedItems");

			// save membership
			membershipService.editMembership(userInfo, Long.parseLong(groupId), membersId, typesId, allItems);
			model.addAttribute("userInfo", userInfo);

		} catch (ParvanServiceException e) {
			proccessException(e, result);
			model.addAttribute("result", result);
			model.addAttribute("userInfo", userInfo);

			return "addMembership";
		}

		return "redirect:/showGroups?success=true";
	}

	@RequestMapping(value = "/pagingMembership/{id}", method = RequestMethod.GET)
	public @ResponseBody DatatablesResponse<BaseVO> findAll(@PathVariable Long id, @DatatablesParams DatatablesCriterias criterias,
			MemberCriteria membershipCriteria, HttpServletRequest request) {

		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
		int firstResult = criterias.getDisplayStart();
		int maxResults = criterias.getDisplaySize();
		ColumnDef columnDef = criterias.getSortingColumnDefs().get(0);

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

			membershipCriteria.setTenantId(null);

			List<BaseVO> rows = memberService.findByCriteria(userInfo, membershipCriteria, firstResult, maxResults, sortDirection, sortCriterion);

			List<BaseVO> memberships = membershipService.findAll(userInfo);
			List<BaseVO> selectedRows = new ArrayList<>();
			for (BaseVO memb : rows) {
				for (BaseVO membship : memberships) {
					if (Validator.equals(((MemberVO) memb).getId(), ((MembershipVO) membship).getMember().getId())
							&& Validator.equals(id, ((MembershipVO) membship).getGroupId().getId())
							&& Validator.equals(((MembershipVO) membship).isActive(), true)) {
						((MemberVO) memb).setSelected(true);
						break;
					}
				}
			}

			for (BaseVO memb : rows) {
				if (Validator.isNull(((MemberVO) memb).getSelected())) {
					selectedRows.add(memb);
				}
			}

			long totalRecords = memberService.countByCriteria(userInfo, membershipCriteria);
			dataSet = new DataSet<BaseVO>(selectedRows, (long) rows.size(), totalRecords);

		} catch (Exception e) {
			log.error("error occured in menu paging...", e);
			dataSet = new DataSet<BaseVO>(new ArrayList<BaseVO>(), 0l, 0l);
		}
		return DatatablesResponse.build(dataSet, criterias);
	}

	@RequestMapping(value = "/pagingDeletedMembership/{id}", method = RequestMethod.GET)
	public @ResponseBody DatatablesResponse<BaseVO> findAllAdded(@PathVariable Long id, @DatatablesParams DatatablesCriterias criterias,
			MembershipCriteria membershipCriteria, HttpServletRequest request) {

		DataSet<BaseVO> dataSet;
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
		int firstResult = criterias.getDisplayStart();
		int maxResults = criterias.getDisplaySize();
		ColumnDef columnDef = criterias.getSortingColumnDefs().get(0);
		membershipCriteria.setActive(true);
		try {
			membershipCriteria.setGroup((GroupVO) groupService.get(userInfo, id));

			SortDirectionEnum sortDirection = SortDirectionEnum.Ascending;
			String sortCriterion = columnDef.getName();

			if ("0".equals(sortCriterion)) {
				sortCriterion = "indexInParent";
			}

			if (columnDef.getSortDirection().equals(SortDirection.DESC)) {
				sortDirection = SortDirectionEnum.Descending;
			}

			membershipCriteria.setTenantId(null);

			List<BaseVO> rows = membershipService.findByCriteria(userInfo, membershipCriteria, firstResult, maxResults, sortDirection, sortCriterion);
			long totalRecords = membershipService.countByCriteria(userInfo, membershipCriteria);

			dataSet = new DataSet<BaseVO>(rows, (long) rows.size(), totalRecords);

		} catch (Exception e) {
			log.error("error occured in menu paging...", e);
			dataSet = new DataSet<BaseVO>(new ArrayList<BaseVO>(), 0l, 0l);
		}
		return DatatablesResponse.build(dataSet, criterias);
	}
}
