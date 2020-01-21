package com.parvanpajooh.issuemanager.model.vo;

import java.time.LocalDateTime;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.parvanpajooh.commons.platform.ejb.model.vo.BaseVO;
import com.parvanpajooh.issuemanager.model.enums.EmailEnum;
import com.parvanpajooh.issuemanager.model.enums.TaskPriorityEnum;
import com.parvanpajooh.issuemanager.model.enums.TaskStatusEnum;
import com.parvanpajooh.issuemanager.model.enums.TaskTypeEnum;

@XmlRootElement
public class TaskVO extends BaseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5971686629027277987L;

	private Long id;

	private String subject;

	private String description;
	
	private String creatMemberUsername;
	
	private String updateMemberUsername;
	
	private LocalDateTime dueDate;
	
	private GroupVO group;
	
	private MemberVO currentMember;
	
	private boolean active;	

	private TaskPriorityEnum priority;
	
	private TaskStatusEnum currentTaskStatus;
	
	private TaskTypeEnum taskType;
	
	private EmailEnum emailStatus;
	
	private List<TaskMemberRelationVO> taskMemberRelations;
	
//	private String strDueDate;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the group
	 */
	public GroupVO getGroup() {
		return group;
	}

	/**
	 * @param group the group to set
	 */
	public void setGroup(GroupVO group) {
		this.group = group;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}


	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}


	/**
	 * @return the dueDate
	 */
	public LocalDateTime getDueDate() {
		return dueDate;
	}

	/**
	 * @param dueDate the dueDate to set
	 */
	public void setDueDate(LocalDateTime dueDate) {
		this.dueDate = dueDate;
	}

	/**
	 * @return the currentMember
	 */
	public MemberVO getCurrentMember() {
		return currentMember;
	}

	/**
	 * @param currentMember the currentMember to set
	 */
	public void setCurrentMember(MemberVO currentMember) {
		this.currentMember = currentMember;
	}
	
	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * @return the currentTaskStatus
	 */
	public TaskStatusEnum getCurrentTaskStatus() {
		return currentTaskStatus;
	}

	/**
	 * @param currentTaskStatus the currentTaskStatus to set
	 */
	public void setCurrentTaskStatus(TaskStatusEnum currentTaskStatus) {
		this.currentTaskStatus = currentTaskStatus;
	}

	/**
	 * @return the creatMemberUsername
	 */
	public String getCreatMemberUsername() {
		return creatMemberUsername;
	}

	/**
	 * @param creatMemberUsername the creatMemberUsername to set
	 */
	public void setCreatMemberUsername(String creatMemberUsername) {
		this.creatMemberUsername = creatMemberUsername;
	}

	/**
	 * @return the updateMemberUsername
	 */
	public String getUpdateMemberUsername() {
		return updateMemberUsername;
	}

	/**
	 * @param updateMemberUsername the updateMemberUsername to set
	 */
	public void setUpdateMemberUsername(String updateMemberUsername) {
		this.updateMemberUsername = updateMemberUsername;
	}

	/**
	 * @return the priority
	 */
	public TaskPriorityEnum getPriority() {
		return priority;
	}

	/**
	 * @param priority the priority to set
	 */
	public void setPriority(TaskPriorityEnum priority) {
		this.priority = priority;
	}

	/**
	 * @return the taskType
	 */
	public TaskTypeEnum getTaskType() {
		return taskType;
	}

	/**
	 * @param taskType the taskType to set
	 */
	public void setTaskType(TaskTypeEnum taskType) {
		this.taskType = taskType;
	}

	/**
	 * @return the taskMemberRelations
	 */
	public List<TaskMemberRelationVO> getTaskMemberRelations() {
		return taskMemberRelations;
	}

	/**
	 * @param taskMemberRelations the taskMemberRelations to set
	 */
	public void setTaskMemberRelations(List<TaskMemberRelationVO> taskMemberRelations) {
		this.taskMemberRelations = taskMemberRelations;
	}

	/**
	 * @return the emailStatus
	 */
	public EmailEnum getEmailStatus() {
		return emailStatus;
	}

	/**
	 * @param emailStatus the emailStatus to set
	 */
	public void setEmailStatus(EmailEnum emailStatus) {
		this.emailStatus = emailStatus;
	}



//	/**
//	 * @return the strDueDate
//	 */
//	public String getStrDueDate() {
//		return strDueDate;
//	}
//
//	/**
//	 * @param strDueDate the strDueDate to set
//	 */
//	public void setStrDueDate(String strDueDate) {
//		this.strDueDate = strDueDate;
//	}
	
}
