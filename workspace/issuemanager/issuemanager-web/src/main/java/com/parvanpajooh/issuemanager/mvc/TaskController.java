package com.parvanpajooh.issuemanager.mvc;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
import com.parvanpajooh.common.formatter.DateProperyEditor;
import com.parvanpajooh.commons.enums.SortDirectionEnum;
import com.parvanpajooh.commons.platform.ejb.model.UserInfo;
import com.parvanpajooh.commons.platform.ejb.model.vo.BaseVO;
import com.parvanpajooh.commons.util.Validator;
import com.parvanpajooh.issuemanager.common.exceptions.ErrorCode;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanRecoverableException;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanServiceException;
import com.parvanpajooh.issuemanager.model.Comment_;
import com.parvanpajooh.issuemanager.model.Task;
import com.parvanpajooh.issuemanager.model.TaskAssignmentHistory;
import com.parvanpajooh.issuemanager.model.criteria.TaskCriteria;
import com.parvanpajooh.issuemanager.model.enums.EmailEnum;
import com.parvanpajooh.issuemanager.model.enums.TaskMemberRelationEnum;
import com.parvanpajooh.issuemanager.model.vo.AggregatedHistoryVO;
import com.parvanpajooh.issuemanager.model.vo.CommentVO;
import com.parvanpajooh.issuemanager.model.vo.GroupVO;
import com.parvanpajooh.issuemanager.model.vo.MemberVO;
import com.parvanpajooh.issuemanager.model.vo.MembershipVO;
import com.parvanpajooh.issuemanager.model.vo.RelationVO;
import com.parvanpajooh.issuemanager.model.vo.TaskAssignmentHistoryVO;
import com.parvanpajooh.issuemanager.model.vo.TaskAttachmentVO;
import com.parvanpajooh.issuemanager.model.vo.TaskMemberRelationVO;
import com.parvanpajooh.issuemanager.model.vo.TaskStatusHistoryVO;
import com.parvanpajooh.issuemanager.model.vo.TaskVO;
import com.parvanpajooh.issuemanager.service.AggregatedHistoryService;
import com.parvanpajooh.issuemanager.service.CommentService;
import com.parvanpajooh.issuemanager.service.GroupService;
import com.parvanpajooh.issuemanager.service.MemberService;
import com.parvanpajooh.issuemanager.service.MembershipService;
import com.parvanpajooh.issuemanager.service.RelationService;
import com.parvanpajooh.issuemanager.service.TaskAssignmentHistoryService;
import com.parvanpajooh.issuemanager.service.TaskAttachmentService;
import com.parvanpajooh.issuemanager.service.TaskMemberRelationService;
import com.parvanpajooh.issuemanager.service.TaskService;
import com.parvanpajooh.issuemanager.service.TaskStatusHistoryService;

@Controller
@RequestMapping("/")
public class TaskController extends BaseController {

	private static final String REPO_PATH = "/usr/local/issuemanager/";

	@EJB(mappedName = "java:global/issuemanager-ear/issuemanager-ejb/TaskServiceImpl")
	private TaskService taskService;

	@EJB(mappedName = "java:global/issuemanager-ear/issuemanager-ejb/TaskAssignmentHistoryServiceImpl")
	private TaskAssignmentHistoryService taskAssignmentService;

	@EJB(mappedName = "java:global/issuemanager-ear/issuemanager-ejb/TaskStatusHistoryServiceImpl")
	private TaskStatusHistoryService taskStatusHistoryService;

	@EJB(mappedName = "java:global/issuemanager-ear/issuemanager-ejb/TaskAttachmentServiceImpl")
	private TaskAttachmentService taskAttachmentService;

	@EJB(mappedName = "java:global/issuemanager-ear/issuemanager-ejb/MemberServiceImpl")
	private MemberService memberService;

	@EJB(mappedName = "java:global/issuemanager-ear/issuemanager-ejb/MembershipServiceImpl")
	private MembershipService membershipService;

	@EJB(mappedName = "java:global/issuemanager-ear/issuemanager-ejb/CommentServiceImpl")
	private CommentService commentService;

	@EJB(mappedName = "java:global/issuemanager-ear/issuemanager-ejb/GroupServiceImpl")
	private GroupService groupService;

	@EJB(mappedName = "java:global/issuemanager-ear/issuemanager-ejb/AggregatedHistoryServiceImpl")
	private AggregatedHistoryService aggregatedHistoryService;

	@EJB(mappedName = "java:global/issuemanager-ear/issuemanager-ejb/RelationServiceImpl")
	private RelationService relationService;

	@EJB(mappedName = "java:global/issuemanager-ear/issuemanager-ejb/TaskMemberRelationServiceImpl")
	private TaskMemberRelationService taskMemberRelationService;


	@InitBinder
	public void initBinder(WebDataBinder binder) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
		binder.registerCustomEditor(Timestamp.class, new CustomDateEditor(format, true));
		binder.registerCustomEditor(LocalDateTime.class, new DateProperyEditor(true, true));
		binder.registerCustomEditor(Timestamp.class, new DateProperyEditor(true, true));
	}

	@RequestMapping(value = "/task/{id}/vote", method = { RequestMethod.POST, RequestMethod.GET })
	public String vote(@PathVariable Long id, HttpServletRequest request, Model model, TaskVO taskVO) {

		Map<String, Object> result = new HashMap<String, Object>();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
		TaskMemberRelationVO taskMemberRelationVO = new TaskMemberRelationVO();

		try {

			TaskVO task = new TaskVO();
			task = (TaskVO) taskService.get(userInfo, id);
			taskMemberRelationVO.setTask(task);
			taskMemberRelationVO.setType(TaskMemberRelationEnum.VOTING);

			// save comment
			taskMemberRelationService.save(userInfo, taskMemberRelationVO);

		} catch (ParvanServiceException e) {
			proccessException(e, result);
			model.addAttribute("result", result);
			model.addAttribute("userInfo", userInfo);
		}

		return "redirect:/taskDetails/" + id + "?success=true";
	}

	@RequestMapping(value = "/task/{id}/watch", method = { RequestMethod.POST, RequestMethod.GET })
	public String watch(@PathVariable Long id, HttpServletRequest request, Model model, TaskVO taskVO) {

		Map<String, Object> result = new HashMap<String, Object>();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
		TaskMemberRelationVO taskMemberRelationVO = new TaskMemberRelationVO();

		try {

			TaskVO task = new TaskVO();
			task = (TaskVO) taskService.get(userInfo, id);
			taskMemberRelationVO.setTask(task);
			taskMemberRelationVO.setType(TaskMemberRelationEnum.WATCHING);

			// save comment
			taskMemberRelationService.save(userInfo, taskMemberRelationVO);

		} catch (ParvanServiceException e) {
			proccessException(e, result);
			model.addAttribute("result", result);
			model.addAttribute("userInfo", userInfo);
		}

		return "redirect:/taskDetails/" + id + "?success=true";
	}

	@RequestMapping(value = "/showTasks", method = RequestMethod.GET)
	public String showTasks(final HttpServletRequest req, Model model) {
		Map<String, Object> result = new HashMap<String, Object>();
		UserInfo userInfo = (UserInfo) req.getSession().getAttribute("userInfo");

		try {
			List<MembershipVO> membershipVOs = membershipService.findByMemberId(userInfo);
			List<Long> groupIds = new ArrayList<Long>();
			TaskCriteria taskCriteria = new TaskCriteria();
			List<BaseVO> taskVOs = new ArrayList<BaseVO>();

			for (MembershipVO item : membershipVOs) {
				groupIds.add(item.getGroupId().getId());
			}

			taskCriteria.setGroupIds(groupIds);
			taskCriteria.setTenantId(null);
			taskCriteria.setActive(true);

			if (Validator.isNotNull(groupIds)) {
				taskVOs = taskService.findByCriteria(userInfo,taskCriteria,0,10,SortDirectionEnum.Ascending,"id");
			}			
			

			
					
			List<BaseVO> groupVOs = groupService.findAll(userInfo);
			List<BaseVO> memberVOs = memberService.findAll(userInfo);
			model.addAttribute("members", memberVOs);
			model.addAttribute("rowId", null);
			model.addAttribute("groups", groupVOs);
			model.addAttribute("tasks", taskVOs);
			model.addAttribute("userInfo", userInfo);
			

		} catch (ParvanServiceException e) {
			proccessException(e, result);
			model.addAttribute("result", result);
			model.addAttribute("members", new MemberVO());
			model.addAttribute("rowId", null);
			model.addAttribute("groups", new GroupVO());
			model.addAttribute("tasks", new TaskVO());
			model.addAttribute("userInfo", userInfo);

			return "showTasks";
		}

		return "showTasks";
	}

	@RequestMapping(value = "/taskDetails/{id}", method = RequestMethod.GET)
	public String taskDetails(final HttpServletRequest req, @PathVariable Long id, Model model) {
		UserInfo userInfo = (UserInfo) req.getSession().getAttribute("userInfo");
		Map<String, Object> result = new HashMap<String, Object>();
		TaskVO taskVO = null;
		String creatMemberUsername;
		String updateMemberUsername;

		try {
			taskVO = (TaskVO) taskService.get(userInfo, id);

			MemberVO creatMember = (MemberVO) memberService.get(userInfo, taskVO.getCreateUserId());
			MemberVO updateMember = (MemberVO) memberService.get(userInfo, taskVO.getUpdateUserId());
			creatMemberUsername = creatMember.getUsername();
			updateMemberUsername = updateMember.getUsername();
			taskVO.setCreatMemberUsername(creatMemberUsername);
			taskVO.setUpdateMemberUsername(updateMemberUsername);

			List<AggregatedHistoryVO> aggregatedHistoryVOs = aggregatedHistoryService.loadByTaskId(userInfo, id);
			if (Validator.isNotNull(aggregatedHistoryVOs)) {
				for (AggregatedHistoryVO row : aggregatedHistoryVOs) {

					Map<String, Object> m;

					m = jsonToMap(row.getBody());
					row.setJsonsMap(m);
					Long memberId = Long.parseLong(row.getJsonsMap().get(Comment_.createUserId.getName()).toString());
					MemberVO member = (MemberVO) memberService.get(userInfo, memberId);
					row.setCreateUser(member.getUsername());
				}
			}

			List<TaskMemberRelationVO> voteVOs = taskMemberRelationService.loadByTaskId(userInfo, id, TaskMemberRelationEnum.VOTING);
			List<TaskMemberRelationVO> voteVO = taskMemberRelationService.findByTaskIdAndMemberId(userInfo, id, userInfo.getUserId(),
					TaskMemberRelationEnum.VOTING.toString());
			List<TaskMemberRelationVO> watchVOs = taskMemberRelationService.loadByTaskId(userInfo, id, TaskMemberRelationEnum.WATCHING);
			List<TaskMemberRelationVO> watchVO = taskMemberRelationService.findByTaskIdAndMemberId(userInfo, id, userInfo.getUserId(),
					TaskMemberRelationEnum.WATCHING.toString());

			List<CommentVO> commentVOList = commentService.loadCommentByTaskId(userInfo, id);

			if (Validator.isNotNull(commentVOList)) {
				for (CommentVO row : commentVOList) {
					MemberVO createdMember = (MemberVO) memberService.get(userInfo, row.getCreateUserId());
					MemberVO updatedMember = (MemberVO) memberService.get(userInfo, row.getUpdateUserId());
					row.setCreateUser(createdMember.getUsername());
					row.setUpdateUser(updatedMember.getUsername());
				}
			}

			List<TaskStatusHistoryVO> taskStatusHistoryVOs = taskStatusHistoryService.loadByTaskId(userInfo, id);
			if (Validator.isNotNull(taskStatusHistoryVOs)) {
				for (TaskStatusHistoryVO row : taskStatusHistoryVOs) {
					TaskStatusHistoryVO taskStatusHistoryVO = new TaskStatusHistoryVO();
					MemberVO createdMember = (MemberVO) memberService.get(userInfo, row.getCreateUserId());
					MemberVO updatedMember = (MemberVO) memberService.get(userInfo, row.getUpdateUserId());
					taskStatusHistoryVO = (TaskStatusHistoryVO) row;
					taskStatusHistoryVO.setCreateUser(createdMember.getUsername());
					taskStatusHistoryVO.setUpdateUser(updatedMember.getUsername());
				}
			}

			List<TaskAssignmentHistoryVO> taskAssignmentVOs = taskAssignmentService.loadTaskAssignmentByTaskId(userInfo, id);
			if (Validator.isNotNull(taskAssignmentVOs)) {
				for (TaskAssignmentHistoryVO row : taskAssignmentVOs) {
					TaskAssignmentHistoryVO taskAssignmentVO = new TaskAssignmentHistoryVO();
					MemberVO createdMember = (MemberVO) memberService.get(userInfo, row.getCreateUserId());
					MemberVO updatedMember = (MemberVO) memberService.get(userInfo, row.getUpdateUserId());
					taskAssignmentVO = (TaskAssignmentHistoryVO) row;
					taskAssignmentVO.setCreateUser(createdMember.getUsername());
					taskAssignmentVO.setCreateUser(updatedMember.getUsername());
				}
			}
			List<TaskAttachmentVO> taskAttachmentVOs = taskAttachmentService.loadByTaskId(userInfo, id);
			if (Validator.isNotNull(taskAttachmentVOs)) {
				for (TaskAttachmentVO row : taskAttachmentVOs) {
					TaskAttachmentVO taskAttachmentVO = new TaskAttachmentVO();
					MemberVO createdMember = (MemberVO) memberService.get(userInfo, row.getCreateUserId());
					MemberVO updatedMember = (MemberVO) memberService.get(userInfo, row.getUpdateUserId());
					taskAttachmentVO = (TaskAttachmentVO) row;
					taskAttachmentVO.setCreateUser(createdMember.getUsername());
					taskAttachmentVO.setCreateUser(updatedMember.getUsername());

				}
			}
			List<RelationVO> relationVOs = relationService.loadRelationByTaskId(userInfo, id);
			if (Validator.isNotNull(relationVOs)) {
				for (RelationVO row : relationVOs) {
					RelationVO relationVO = new RelationVO();
					MemberVO createdMember = (MemberVO) memberService.get(userInfo, row.getCreateUserId());
					MemberVO updatedMember = (MemberVO) memberService.get(userInfo, row.getUpdateUserId());
					relationVO = (RelationVO) row;
					relationVO.setCreateUser(createdMember.getUsername());
					relationVO.setCreateUser(updatedMember.getUsername());
				}
			}

			model.addAttribute("task", taskVO);
			model.addAttribute("taskAssignment", new TaskAssignmentHistory());
			model.addAttribute("members", memberService.findAll(userInfo));
			model.addAttribute("voters", voteVOs);
			model.addAttribute("watchers", watchVOs);
			model.addAttribute("relationsHistory", relationVOs);
			model.addAttribute("aggregatedHistoryVOs", aggregatedHistoryVOs);
			model.addAttribute("comments", commentVOList);
			model.addAttribute("commentsChangeStatus", taskStatusHistoryVOs);
			model.addAttribute("attachmentsHistory", taskAttachmentVOs);
			model.addAttribute("commentsChangeAssignTo", taskAssignmentVOs);
			model.addAttribute("userInfo", req.getSession().getAttribute("userInfo"));

			if (Validator.isNotNull(voteVO)) {
				model.addAttribute("voteVO", voteVO.get(0));
			} else {
				model.addAttribute("voteVO", voteVO);
			}
			if (Validator.isNotNull(watchVO)) {
				model.addAttribute("watchVO", watchVO.get(0));
			} else {
				model.addAttribute("watchVO", watchVO);
			}
			if (Validator.isNotNull(voteVOs)) {
				model.addAttribute("voteSize", voteVOs.size());
			}
			if (Validator.isNull(voteVOs)) {
				model.addAttribute("voteSize", 0L);
			}

			if (Validator.isNotNull(watchVOs)) {
				model.addAttribute("watchSize", watchVOs.size());
			}
			if (Validator.isNull(watchVOs)) {
				model.addAttribute("watchSize", 0L);
			}

			if (Validator.isNotNull(aggregatedHistoryVOs)) {
				model.addAttribute("aggregatedSize", aggregatedHistoryVOs.size());
			}
			if (Validator.isNotNull(commentVOList)) {
				model.addAttribute("commentSize", commentVOList.size());
			}
			if (Validator.isNotNull(taskStatusHistoryVOs)) {
				model.addAttribute("statusHistorySize", taskStatusHistoryVOs.size());
			}
			if (Validator.isNotNull(taskAssignmentVOs)) {
				model.addAttribute("taskAssignmentSize", taskAssignmentVOs.size());
			}
			if (Validator.isNotNull(taskAttachmentVOs)) {
				model.addAttribute("taskAttachmentSize", taskAttachmentVOs.size());
			}
			if (Validator.isNotNull(relationVOs)) {
				model.addAttribute("relationSize", relationVOs.size());
			}

		} catch (ParvanServiceException e) {
			proccessException(e, result);
			model.addAttribute("result", result);
			model.addAttribute("userInfo", req.getSession().getAttribute("userInfo"));
			return "taskDetails";
		}

		return "taskDetails";
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

	@RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
	public String showComments(final HttpServletRequest req, @PathVariable Long id, Model model) {

		Map<String, Object> result = new HashMap<String, Object>();
		UserInfo userInfo = (UserInfo) req.getSession().getAttribute("userInfo");

		try {

			model.addAttribute("task", taskService.get(userInfo, id));
			model.addAttribute("tasksId", id);
			model.addAttribute("commentVO", new CommentVO());
			model.addAttribute("userInfo", userInfo);

		} catch (ParvanServiceException e) {
			proccessException(e, result);
			model.addAttribute("result", result);
			model.addAttribute("userInfo", userInfo);
			return "comments";
		}

		return "comments";
	}

	@RequestMapping(value = "/comment/save", method = { RequestMethod.POST, RequestMethod.GET })
	public String saveComment(final HttpServletRequest request, Model model, CommentVO commentVO) {

		Map<String, Object> result = new HashMap<String, Object>();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
		Long taskId = null;

		try {

			taskId = Long.parseLong(request.getParameter("tasksId"));
			TaskVO taskVO = new TaskVO();
			taskVO.setId(taskId);
			commentVO.setTask(taskVO);

			// save comment
			commentService.save(userInfo, commentVO);

		} catch (ParvanServiceException e) {
			proccessException(e, result);
			model.addAttribute("result", result);
			model.addAttribute("userInfo", userInfo);
		}

		return "redirect:/taskDetails/" + taskId + "?success=true";
	}

	@RequestMapping(value = "/removeComment/{id}", method = { RequestMethod.POST, RequestMethod.GET })
	public String removeComment(final HttpServletRequest request, Model model, @PathVariable Long id) {

		Map<String, Object> result = new HashMap<String, Object>();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
		Long taskId;
		TaskVO taskVO = null;

		try {

			CommentVO commentVO = (CommentVO) commentService.get(userInfo, id);
			taskId = commentVO.getTask().getId();
			taskVO = (TaskVO) taskService.get(userInfo, taskId);

			// remove comment
			commentService.delete(userInfo, id);

		} catch (ParvanServiceException e) {
			proccessException(e, result);
			model.addAttribute("result", result);
			model.addAttribute("task", taskVO);
			model.addAttribute("userInfo", userInfo);

			return "taskDetails";
		}

		return "redirect:/taskDetails/" + taskId + "?success=true";
	}

	@RequestMapping(value = "/editComment/{id}", method = RequestMethod.GET)
	public String editCommentShow(final HttpServletRequest req, @PathVariable Long id, Model model) {
		Map<String, Object> result = new HashMap<String, Object>();
		UserInfo userInfo = (UserInfo) req.getSession().getAttribute("userInfo");

		try {
			CommentVO commentVO = (CommentVO) commentService.get(userInfo, id);
			TaskVO taskVO = (TaskVO) taskService.get(userInfo, commentVO.getTask().getId());
			model.addAttribute("commentVO", commentVO);
			model.addAttribute("task", taskVO);
			model.addAttribute("userInfo", userInfo);

		} catch (ParvanServiceException e) {
			proccessException(e, result);
			model.addAttribute("result", result);
			model.addAttribute("userInfo", userInfo);

			return "editComment";
		}
		return "editComment";
	}

	@RequestMapping(value = "/editComment/save", method = { RequestMethod.POST, RequestMethod.GET })
	public String editComment(final HttpServletRequest request, Model model, CommentVO commentVO) {

		Map<String, Object> result = new HashMap<String, Object>();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
		Long taskId = null;

		try {
			// edit comment
			commentService.editComment(userInfo, commentVO);

			taskId = Long.parseLong(request.getParameter("tasksId"));

		} catch (ParvanServiceException e) {
			proccessException(e, result);
			model.addAttribute("result", result);
			model.addAttribute("userInfo", userInfo);
			return "editComment";
		}
		return "redirect:/taskDetails/" + taskId;
	}

	@RequestMapping(value = "/addTask", method = RequestMethod.GET)
	public String showAddTaskPage(final HttpServletRequest req, Model model) {
		UserInfo userInfo = (UserInfo) req.getSession().getAttribute("userInfo");
		Map<String, Object> result = new HashMap<String, Object>();
		try {

			List<BaseVO> groupVOs = groupService.findAll(userInfo);
			model.addAttribute("task", new Task());
			model.addAttribute("groups", groupVOs);
			model.addAttribute("userInfo", userInfo);

		} catch (ParvanServiceException e) {
			proccessException(e, result);
			model.addAttribute("result", result);
			model.addAttribute("userInfo", userInfo);
			return "showTasks";
		}
		return "addTask";
	}

	@RequestMapping(value = "/addTask/save", method = { RequestMethod.POST, RequestMethod.GET })
	public String addTask(final HttpServletRequest req, TaskVO taskVO, Model model) {

		Map<String, Object> result = new HashMap<String, Object>();
		UserInfo userInfo = (UserInfo) req.getSession().getAttribute("userInfo");

		try {
			if (Validator.isNotNull(req.getParameter("ajaxSearching"))) {
				Long selectedGroupId = Long.parseLong(req.getParameter("ajaxSearching"));
				GroupVO groupVO = (GroupVO) groupService.get(userInfo, selectedGroupId);
				taskVO.setGroup(groupVO);
			}

			// save Task
			taskVO = (TaskVO) taskService.save(userInfo, taskVO);

		} catch (ParvanServiceException e) {
			proccessException(e, result);
			model.addAttribute("result", result);
			model.addAttribute("task", new Task());
			model.addAttribute("userInfo", req.getSession().getAttribute("userInfo"));

			return "addTask";
		}

		return "redirect:/showTasks?success=true";
	}

	@RequestMapping(value = "/editTask/{id}", method = RequestMethod.GET)
	public String showEditPage(@PathVariable Long id, final HttpServletRequest req, Model model) {

		Map<String, Object> result = new HashMap<String, Object>();
		UserInfo userInfo = (UserInfo) req.getSession().getAttribute("userInfo");
		try {

			// List<BaseVO> groupVOs = groupService.findAll(userInfo);
			TaskVO task = (TaskVO) taskService.get(userInfo, id);
			// model.addAttribute("groups", groupVOs);
			model.addAttribute("task", task);
			model.addAttribute("userInfo", userInfo);

		} catch (ParvanServiceException e) {
			proccessException(e, result);
			model.addAttribute("result", result);
			model.addAttribute("task", new Task());
			model.addAttribute("userInfo", req.getSession().getAttribute("userInfo"));

			return "addTask";
		}

		return "editTask";
	}

	@RequestMapping(value = "/editTask/save", method = { RequestMethod.POST, RequestMethod.GET })
	public String editTask(final HttpServletRequest req, TaskVO taskVO, Model model) {
		UserInfo userInfo = (UserInfo) req.getSession().getAttribute("userInfo");
		Map<String, Object> result = new HashMap<String, Object>();
		List<BaseVO> groupVOs = new ArrayList<>();

		try {
			if (Validator.isNotNull(req.getParameter("ajaxSearching"))) {
				Long selectedGroupId = Long.parseLong(req.getParameter("ajaxSearching"));
				GroupVO groupVO = (GroupVO) groupService.get(userInfo, selectedGroupId);
				taskVO.setGroup(groupVO);
			}
			groupVOs = groupService.findAll(userInfo);

			// edit Task
			taskVO.setEmailStatus(EmailEnum.NEED);
			taskService.save(userInfo,taskVO);

		} catch (ParvanServiceException e) {
			proccessException(e, result);
			model.addAttribute("result", result);
			model.addAttribute("task", taskVO);
			model.addAttribute("groups", groupVOs);
			model.addAttribute("userInfo", req.getSession().getAttribute("userInfo"));

			return "editTask";
		}
		return "redirect:/showTasks?success=true";
	}

	@RequestMapping(value = "/addAttachment/save", method = { RequestMethod.POST })
	public String addTaskAttachment(final HttpServletRequest req, TaskAttachmentVO taskAttachmentVO, Model model, @RequestParam("file") MultipartFile file) {

		UserInfo userInfo = (UserInfo) req.getSession().getAttribute("userInfo");
		Map<String, Object> result = new HashMap<String, Object>();
		Long taskId = taskAttachmentVO.getTask().getId();
		TaskVO taskVO = null;

		try {

			taskVO = (TaskVO) taskService.get(userInfo, taskId);
			byte[] bytes = file.getBytes();

			// Creating the directory to store file
			File dir = new File(REPO_PATH + taskVO.getId().toString());
			if (!dir.exists()) {
				dir.mkdirs();
			}

			// Create the file on server
			File serverFile = new File(dir.getAbsolutePath() + File.separator + file.getOriginalFilename());
			if (serverFile.exists()) {
				throw new ParvanRecoverableException(ErrorCode.FILE_EXIST);
			}
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
			stream.write(bytes);
			stream.close();

			taskAttachmentVO.setPath(taskVO.getId().toString() + "/" + file.getOriginalFilename());
			taskAttachmentVO.setName(file.getOriginalFilename());
			taskAttachmentVO.setSize(file.getSize());
			taskAttachmentVO.setMimeType(file.getContentType());

			TaskAttachmentVO taskAttachmentVO2 = taskAttachmentService.addTaskAttachment(userInfo, taskAttachmentVO, taskId);

		} catch (ParvanServiceException e) {
			proccessException(e, result);
			model.addAttribute("result", result);
			model.addAttribute("task", taskVO);
			model.addAttribute("userInfo", userInfo);
			return "addAttachment";

		} catch (NullPointerException e) {
			proccessException(e, result);
			model.addAttribute("result", result);
			model.addAttribute("task", taskVO);
			model.addAttribute("userInfo", userInfo);
			return "addAttachment";
		} catch (IOException e) {
			proccessException(e, result);
			model.addAttribute("result", result);
			model.addAttribute("task", taskVO);
			model.addAttribute("userInfo", userInfo);
			return "addAttachment";
		}

		return "redirect:/taskDetails/" + taskAttachmentVO.getTask().getId() + "?success=true";
	}

	@RequestMapping(value = "/downloadAttachment/{id}", method = { RequestMethod.POST, RequestMethod.GET })
	public String downlaodAttachment(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {

		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
		TaskAttachmentVO taskAttachmentVO = new TaskAttachmentVO();

		// get path

		FileInputStream inputStream = null;
		OutputStream outputStream = null;

		try {
			taskAttachmentVO = (TaskAttachmentVO) taskAttachmentService.get(userInfo, id);
			inputStream = new FileInputStream(REPO_PATH + taskAttachmentVO.getPath());
			outputStream = response.getOutputStream();

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

	@RequestMapping(value = "/removeAttachment/{id}", method = { RequestMethod.POST, RequestMethod.GET })
	public String removeAttachment(final HttpServletRequest req, Model model, @PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		UserInfo userInfo = (UserInfo) req.getSession().getAttribute("userInfo");
		TaskAttachmentVO taskAttachmentVO = new TaskAttachmentVO();
		Long taskId;

		try {
			taskAttachmentVO = (TaskAttachmentVO) taskAttachmentService.get(userInfo, id);
			taskId = taskAttachmentVO.getTask().getId();
			File dir = new File(REPO_PATH + taskAttachmentVO.getPath());

			if (dir.exists()) {
				dir.delete();
			}

			taskAttachmentService.delete(userInfo, id);
		} catch (ParvanServiceException e) {
			proccessException(e, result);
			model.addAttribute("result", result);
			model.addAttribute("task", new Task());
			model.addAttribute("userInfo", userInfo);

			return "taskDetails";
		}

		return "redirect:/taskDetails/" + taskId + "?success=true";
	}

	@RequestMapping(value = "/removeTask", method = { RequestMethod.POST, RequestMethod.GET })
	public String removeTask(final HttpServletRequest req, Model model) {
		Map<String, Object> result = new HashMap<String, Object>();
		UserInfo userInfo = (UserInfo) req.getSession().getAttribute("userInfo");
		Long rowId = null;
		try {
			rowId = Long.valueOf(req.getParameter("taskId"));

			taskService.delete(userInfo, rowId);

		} catch (ParvanServiceException e) {
			proccessException(e, result);
			model.addAttribute("result", result);
			model.addAttribute("rowId", rowId);
			model.addAttribute("userInfo", userInfo);

			return "showTasks";
		}

		return "redirect:/showTasks?success=true";
	}

	@RequestMapping(value = "/paging", method = RequestMethod.GET)
	public @ResponseBody DatatablesResponse<BaseVO> findAll(@DatatablesParams DatatablesCriterias criterias, TaskCriteria taskCriteria,
			HttpServletRequest request) {

		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
		int firstResult = criterias.getDisplayStart();
		int maxResults = criterias.getDisplaySize();
		ColumnDef columnDef = criterias.getSortingColumnDefs().get(0);

		taskCriteria.setActive(true);

		SortDirectionEnum sortDirection = SortDirectionEnum.Ascending;
		String sortCriterion = columnDef.getName();

		if ("0".equals(sortCriterion)) {
			sortCriterion = "indexInParent";
		}

		if (columnDef.getSortDirection().equals(SortDirection.DESC)) {
			sortDirection = SortDirectionEnum.Descending;
		}

		DataSet<BaseVO> dataSet;
		List<BaseVO> taskRows = new ArrayList<>();
		long totalRecords = 0;
		try {
			List<MembershipVO> membershipVOs = membershipService.findByMemberId(userInfo);
			List<Long> groupIds = new ArrayList<Long>();

			for (MembershipVO item : membershipVOs) {
				groupIds.add(item.getGroupId().getId());
			}

			taskCriteria.setGroupIds(groupIds);
			taskCriteria.setTenantId(null);

			if (Validator.isNotNull(groupIds)) {
				List<BaseVO> rows = taskService.findByCriteria(userInfo, taskCriteria, firstResult, maxResults, sortDirection, sortCriterion);

				for (BaseVO row : rows) {
					String createUsername = null;
					String updateUsername = null;
					TaskVO taskVO = new TaskVO();
					MemberVO createMember = (MemberVO) memberService.get(userInfo, row.getCreateUserId());
					MemberVO updateMember = (MemberVO) memberService.get(userInfo, row.getUpdateUserId());
					createUsername = createMember.getUsername();
					updateUsername = updateMember.getUsername();
					taskVO = (TaskVO) row;
					taskVO.setCreatMemberUsername(createUsername);
					taskVO.setUpdateMemberUsername(updateUsername);
					taskRows.add(taskVO);
				}

				totalRecords = taskService.countByCriteria(userInfo, taskCriteria);
			}
			dataSet = new DataSet<BaseVO>(taskRows, (long) taskRows.size(), totalRecords);
		} catch (Exception e) {
			log.error("error occured in menu paging...", e);
			dataSet = new DataSet<BaseVO>(new ArrayList<BaseVO>(), 0l, 0l);
		}
		return DatatablesResponse.build(dataSet, criterias);
	}

}