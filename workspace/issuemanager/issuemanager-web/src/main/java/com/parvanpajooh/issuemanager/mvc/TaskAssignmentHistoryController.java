package com.parvanpajooh.issuemanager.mvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;

//import org.codehaus.jackson.JsonParseException;
//import org.codehaus.jackson.map.JsonMappingException;
//import org.codehaus.jackson.map.ObjectMapper;
//import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import com.parvanpajooh.issuemanager.model.criteria.RelationCriteria;
import com.parvanpajooh.issuemanager.model.vo.MemberVO;
import com.parvanpajooh.issuemanager.model.vo.RelationVO;
import com.parvanpajooh.issuemanager.model.vo.TaskAssignmentHistoryVO;
import com.parvanpajooh.issuemanager.model.vo.TaskAttachmentVO;
import com.parvanpajooh.issuemanager.model.vo.TaskStatusHistoryVO;
import com.parvanpajooh.issuemanager.model.vo.TaskVO;
import com.parvanpajooh.issuemanager.service.CommentService;
import com.parvanpajooh.issuemanager.service.MemberService;
import com.parvanpajooh.issuemanager.service.RelationService;
import com.parvanpajooh.issuemanager.service.TaskAssignmentHistoryService;
import com.parvanpajooh.issuemanager.service.TaskService;
import com.parvanpajooh.issuemanager.service.TaskStatusHistoryService;

@Controller
@RequestMapping("/")
public class TaskAssignmentHistoryController extends BaseController {

	@EJB(mappedName = "java:global/issuemanager-ear/issuemanager-ejb/TaskAssignmentHistoryServiceImpl")
	private TaskAssignmentHistoryService taskAssignmentService;

	@EJB(mappedName = "java:global/issuemanager-ear/issuemanager-ejb/TaskStatusHistoryServiceImpl")
	private TaskStatusHistoryService taskStatusHistoryService;

	@EJB(mappedName = "java:global/issuemanager-ear/issuemanager-ejb/CommentServiceImpl")
	private CommentService commentService;

	@EJB(mappedName = "java:global/issuemanager-ear/issuemanager-ejb/MemberServiceImpl")
	private MemberService memberService;

	@EJB(mappedName = "java:global/issuemanager-ear/issuemanager-ejb/TaskServiceImpl")
	private TaskService taskService;

	@EJB(mappedName = "java:global/issuemanager-ear/issuemanager-ejb/RelationServiceImpl")
	private RelationService relationService;

	@RequestMapping(value = "/addRelation/{id}", method = RequestMethod.GET)
	public String showRelation(final HttpServletRequest request, Model model, @PathVariable Long id) throws ParvanServiceException {
		Map<String, Object> result = new HashMap<String, Object>();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");

		try {

			model.addAttribute("tasks", taskService.findAll(userInfo));
			model.addAttribute("task", taskService.get(userInfo, id));
			model.addAttribute("tasksId", id);
			model.addAttribute("relationVO", new RelationVO());
			model.addAttribute("userInfo", userInfo);

		} catch (ParvanServiceException e) {
			proccessException(e, result);
			model.addAttribute("result", result);
			model.addAttribute("userInfo", userInfo);
			model.addAttribute("task", taskService.get(userInfo, id));
			return "addRelation";
		}

		return "addRelation";
	}

	@RequestMapping(value = "/addRelation/save", method = { RequestMethod.POST, RequestMethod.GET })
	public String saveRelation(final HttpServletRequest request, Model model, RelationVO relationVO) {

		Map<String, Object> result = new HashMap<String, Object>();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
		Long taskId = Long.parseLong(request.getParameter("currentTask"));
		String selectedTaskId = request.getParameter("ajaxSearching");
		TaskVO taskVO = null;
		TaskVO selectedTaskVO = null;

		try {
			taskVO = (TaskVO) taskService.get(userInfo, taskId);
			if (Validator.isNotNull(selectedTaskId)) {
				selectedTaskVO = (TaskVO) taskService.get(userInfo, Long.parseLong(selectedTaskId));
				relationVO.setToTask(selectedTaskVO);
			}
			relationVO.setFromTask(taskVO);

			// save relation
			relationService.save(userInfo, relationVO);

		} catch (ParvanServiceException e) {
			proccessException(e, result);
			model.addAttribute("result", result);
			model.addAttribute("tasksId", taskId);
			model.addAttribute("userInfo", userInfo);
			model.addAttribute("task", taskVO);
			return "addRelation";
		}

		return "redirect:/taskDetails/" + taskId + "?success=true";
	}

	@RequestMapping(value = "/editRelation/{id}", method = { RequestMethod.POST, RequestMethod.GET })
	public String showEditRelation(final HttpServletRequest request, @PathVariable Long id, Model model) throws ParvanServiceException, ParvanDaoException {

		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");

		RelationVO relationVO = (RelationVO) relationService.get(userInfo, id);
		model.addAttribute("relationVO", relationVO);
		model.addAttribute("task", taskService.get(userInfo, relationVO.getFromTask().getId()));
		model.addAttribute("userInfo", userInfo);
		return "editRelation";
	}

	@RequestMapping(value = "/editRelation/save", method = { RequestMethod.POST, RequestMethod.GET })
	public String editRelation(final HttpServletRequest request, Model model, RelationVO relationVO) throws ParvanServiceException, ParvanDaoException {

		Map<String, Object> result = new HashMap<String, Object>();
		String selectedTaskId = request.getParameter("ajaxSearching");
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
		Long taskId = Long.parseLong(request.getParameter("tasksId"));
		TaskVO taskVO = null;
		TaskVO selectedTaskVO = null;

		try {

			taskVO = (TaskVO) taskService.get(userInfo, taskId);
			if (Validator.isNotNull(selectedTaskId)) {
				selectedTaskVO = (TaskVO) taskService.get(userInfo, Long.parseLong(selectedTaskId));
				relationVO.setToTask(selectedTaskVO);
			}
			relationVO.setFromTask(taskVO);

			// edit Relation
			relationService.save(userInfo, relationVO);

		} catch (ParvanServiceException e) {
			proccessException(e, result);
			model.addAttribute("result", result);
			model.addAttribute("tasksId", taskId);
			model.addAttribute("userInfo", userInfo);
			model.addAttribute("task", taskVO);
			return "editRelation";
		}
		return "redirect:/taskDetails/" + taskId;
	}

	@RequestMapping(value = "/removeRelation/{id}", method = { RequestMethod.POST, RequestMethod.GET })
	public String removeRelation(final HttpServletRequest request, Model model, @PathVariable Long id) {

		Map<String, Object> result = new HashMap<String, Object>();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
		Long taskId;
		TaskVO taskVO = null;

		try {

			RelationVO relationVO = (RelationVO) relationService.get(userInfo, id);
			taskId = relationVO.getFromTask().getId();
			taskVO = (TaskVO) taskService.get(userInfo, taskId);

			// remove relation
			relationService.delete(userInfo, id);

		} catch (ParvanServiceException e) {
			proccessException(e, result);
			model.addAttribute("result", result);
			model.addAttribute("task", taskVO);
			model.addAttribute("userInfo", userInfo);

			return "taskDetails";
		}

		return "redirect:/taskDetails/" + taskId + "?success=true";
	}

	@RequestMapping(value = "/showAssignments", method = RequestMethod.GET)
	public String showAssignments(final HttpServletRequest request, Model model) throws ParvanServiceException {

		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
		List<BaseVO> taskAssignmentsVO = taskAssignmentService.findAll(userInfo);
		model.addAttribute("taskAssignments", taskAssignmentsVO);
		model.addAttribute("userInfo", request.getSession().getAttribute("userInfo"));
		return "showAssignments";
	}

	@RequestMapping(value = "/taskAssignment/save", method = { RequestMethod.POST, RequestMethod.GET })
	public String saveTaskAssignment(final HttpServletRequest request, Model model, TaskAssignmentHistoryVO taskAssignmentVO)
			throws ParvanServiceException, ParvanDaoException {

		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
		Map<String, Object> result = new HashMap<String, Object>();
		Long taskId = taskAssignmentVO.getTask().getId();
		TaskVO taskVO = (TaskVO) taskService.get(userInfo, taskId);

		try {
			String selectedMemberId = request.getParameter("ajaxSearching");
			MemberVO memberTo = new MemberVO();
			if (Validator.isNotNull(selectedMemberId)) {
				memberTo.setId(Long.parseLong(selectedMemberId));
				taskAssignmentVO.setMemberTo(memberTo);
			}else{
				taskAssignmentVO.setMemberTo(null);
			}
			


			TaskAssignmentHistoryVO assignmentVO = taskAssignmentService.addTaskAssignment(userInfo, taskAssignmentVO, taskId);

		} catch (ParvanServiceException e)

		{
			proccessException(e, result);
			model.addAttribute("result", result);
			model.addAttribute("task", taskVO);
			model.addAttribute("taskAssignmentVO", taskAssignmentVO);
			model.addAttribute("userInfo", userInfo);
			return "assignTo";
		}

		return "redirect:/taskDetails/" + taskAssignmentVO.getTask().getId() + "?success=true";

	}

	@RequestMapping(value = "/editTaskAssignment/{id}", method = { RequestMethod.POST, RequestMethod.GET })
	public String showEditTaskAssignment(final HttpServletRequest request, @PathVariable Long id, Model model)
			throws ParvanServiceException, ParvanDaoException {

		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
		Map<String, Object> result = new HashMap<String, Object>();
		TaskVO taskVO = null;

		try {

			TaskAssignmentHistoryVO taskAssignmentHistoryVO = (TaskAssignmentHistoryVO) taskAssignmentService.get(userInfo, id);
			taskVO = (TaskVO) taskService.get(userInfo, taskAssignmentHistoryVO.getTask().getId());
			model.addAttribute("taskAssignmentVO", taskAssignmentHistoryVO);
			model.addAttribute("task", taskVO);
			model.addAttribute("userInfo", userInfo);

		} catch (ParvanServiceException e) {
			proccessException(e, result);
			model.addAttribute("result", result);
			model.addAttribute("task", taskVO);
			model.addAttribute("userInfo", userInfo);
			return "editTaskAssignment";
		}
		return "editTaskAssignment";
	}

	@RequestMapping(value = "/editTaskStatus/{id}", method = { RequestMethod.POST, RequestMethod.GET })
	public String showEditTaskStatus(final HttpServletRequest request, @PathVariable Long id, Model model) {
		Map<String, Object> result = new HashMap<String, Object>();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
		TaskVO taskVO = null;
		try {

			TaskStatusHistoryVO taskStatusHistoryVO = (TaskStatusHistoryVO) taskStatusHistoryService.get(userInfo, id);
			taskVO = (TaskVO) taskService.get(userInfo, taskStatusHistoryVO.getTask().getId());
			model.addAttribute("taskStatusHistoryVO", taskStatusHistoryVO);
			model.addAttribute("task", taskVO);
			model.addAttribute("userInfo", userInfo);

		} catch (ParvanServiceException e) {
			proccessException(e, result);
			model.addAttribute("result", result);
			model.addAttribute("task", taskVO);
			model.addAttribute("userInfo", userInfo);
			return "editTaskStatus";
		}
		return "editTaskStatus";
	}

	@RequestMapping(value = "/editTaskAssignment/save", method = { RequestMethod.POST, RequestMethod.GET })
	public String editTaskStatus(final HttpServletRequest request, Model model, TaskAssignmentHistoryVO taskAssignmentVO)
			throws ParvanServiceException, ParvanDaoException {
		Map<String, Object> result = new HashMap<String, Object>();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
		TaskAssignmentHistoryVO taskAssignmentHistoryVO = (TaskAssignmentHistoryVO) taskAssignmentService.get(userInfo, taskAssignmentVO.getId());
		TaskVO taskVO = (TaskVO) taskService.get(userInfo, taskAssignmentHistoryVO.getTask().getId());

		try {

			taskAssignmentService.edit(userInfo, taskAssignmentVO);

		} catch (ParvanServiceException e) {
			proccessException(e, result);
			model.addAttribute("result", result);
			model.addAttribute("task", taskVO);
			model.addAttribute("taskAssignmentVO", taskAssignmentVO);
			model.addAttribute("userInfo", userInfo);
			return "assignTo";
		}
		return "redirect:/taskDetails/" + taskVO.getId();
	}

	@RequestMapping(value = "/editTaskStatus/save", method = { RequestMethod.POST, RequestMethod.GET })
	public String editTaskAssignment(final HttpServletRequest request, Model model, TaskStatusHistoryVO taskStatusHistoryVO)
			throws ParvanServiceException, ParvanDaoException {

		Map<String, Object> result = new HashMap<String, Object>();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
		Long taskId = Long.parseLong(request.getParameter("tasksId"));
		TaskVO taskVO = null;
		TaskStatusHistoryVO taskStatusVO = null;

		try {
			taskStatusVO = (TaskStatusHistoryVO) taskStatusHistoryService.get(userInfo, taskStatusHistoryVO.getId());
			taskVO = (TaskVO) taskService.get(userInfo, taskStatusVO.getTask().getId());
			taskStatusVO.setComment(taskStatusHistoryVO.getComment());
			taskStatusHistoryService.edit(userInfo, taskStatusVO);

		} catch (ParvanServiceException e) {
			proccessException(e, result);
			model.addAttribute("result", result);
			model.addAttribute("task", taskVO);
			model.addAttribute("userInfo", userInfo);
			return "editTaskStatus";
		}

		return "redirect:/taskDetails/" + taskId;
	}

	@RequestMapping(value = "/assignTo/{id}", method = RequestMethod.GET)
	public String showAssignTo(final HttpServletRequest request, @PathVariable Long id, Model model) throws ParvanServiceException {

		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
		model.addAttribute("task", taskService.get(userInfo, id));
		model.addAttribute("taskAssignmentVO", new TaskAssignmentHistoryVO());
		model.addAttribute("taskId", id);
		model.addAttribute("userInfo", request.getSession().getAttribute("userInfo"));
		return "assignTo";
	}

	@RequestMapping(value = "/changeStatus/{id}", method = RequestMethod.GET)
	public String showchangeStatus(final HttpServletRequest request, @PathVariable Long id, Model model) throws ParvanServiceException {

		Map<String, Object> result = new HashMap<String, Object>();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");

		try {

			model.addAttribute("task", taskService.get(userInfo, id));
			model.addAttribute("taskStatusHistoryVO", new TaskStatusHistoryVO());
			model.addAttribute("taskId", id);
			model.addAttribute("userInfo", userInfo);

		} catch (ParvanServiceException e) {
			proccessException(e, result);
			model.addAttribute("result", result);
			model.addAttribute("task", taskService.get(userInfo, id));
			model.addAttribute("taskStatusHistoryVO", new TaskStatusHistoryVO());
			model.addAttribute("taskId", id);
			model.addAttribute("userInfo", userInfo);
			return "changeStatus";
		}
		return "changeStatus";
	}

	@RequestMapping(value = "/taskStatusHistory/save", method = { RequestMethod.POST, RequestMethod.GET })
	public String addTaskStatusHistory(final HttpServletRequest req, TaskStatusHistoryVO taskStatusHistoryVO, Model model)
			throws ParvanServiceException, ParvanDaoException {

		UserInfo userInfo = (UserInfo) req.getSession().getAttribute("userInfo");
		Map<String, Object> result = new HashMap<String, Object>();
		Long taskId = taskStatusHistoryVO.getTask().getId();
		TaskVO taskVO = (TaskVO) taskService.get(userInfo, taskId);

		try {

			taskStatusHistoryVO.setFromStatus(taskVO.getCurrentTaskStatus().name());
			TaskStatusHistoryVO taskStatusHistoryVO2 = (TaskStatusHistoryVO) taskStatusHistoryService.addTaskStatus(userInfo, taskStatusHistoryVO, taskId);

		} catch (ParvanServiceException e) {
			proccessException(e, result);
			model.addAttribute("result", result);
			model.addAttribute("task", taskVO);
			model.addAttribute("userInfo", userInfo);
			return "changeStatus";
		}

		return "redirect:/taskDetails/" + taskStatusHistoryVO.getTask().getId() + "?success=true";
	}

	@RequestMapping(value = "/addAttachment/{id}", method = RequestMethod.GET)
	public String showAddAttachment(final HttpServletRequest request, @PathVariable Long id, Model model) throws ParvanServiceException {

		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");

		model.addAttribute("task", taskService.get(userInfo, id));
		model.addAttribute("taskAttachmentVO", new TaskAttachmentVO());
		model.addAttribute("taskId", id);
		model.addAttribute("userInfo", request.getSession().getAttribute("userInfo"));
		return "addAttachment";
	}

	/**
	 * convert json to map
	 * 
	 * @throws IOException
	 */
	public Map<String, Object> jsonToMap(String json) {

		Map<String, Object> map = new HashMap<String, Object>();

		ObjectMapper mapper = new ObjectMapper();
		try {
			map = mapper.readValue(json, new TypeReference<HashMap<String, Object>>() {
			});
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return map;
	}

	@RequestMapping(value = "/ajaxSearching", method = RequestMethod.GET)
	public @ResponseBody DatatablesResponse<BaseVO> findAll(@DatatablesParams DatatablesCriterias criterias, RelationCriteria relationCriteria,
			HttpServletRequest request) {

		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
		int firstResult = criterias.getDisplayStart();
		int maxResults = criterias.getDisplaySize();
		ColumnDef columnDef = criterias.getSortingColumnDefs().get(0);
		relationCriteria.setActive(true);

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

			relationCriteria.setTenantId(null);
			List<BaseVO> rows = relationService.findByCriteria(userInfo, relationCriteria, firstResult, maxResults, sortDirection, sortCriterion);

			long totalRecords = relationService.countByCriteria(userInfo, relationCriteria);
			dataSet = new DataSet<BaseVO>(rows, (long) rows.size(), totalRecords);
		} catch (Exception e) {
			log.error("error occured in menu paging...", e);
			dataSet = new DataSet<BaseVO>(new ArrayList<BaseVO>(), 0l, 0l);
		}
		return DatatablesResponse.build(dataSet, criterias);
	}
}
