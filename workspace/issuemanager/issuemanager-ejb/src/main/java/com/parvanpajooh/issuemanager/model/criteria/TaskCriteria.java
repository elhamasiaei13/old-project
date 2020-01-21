package com.parvanpajooh.issuemanager.model.criteria;

import java.time.LocalDateTime;
import java.util.List;

import com.parvanpajooh.commons.platform.ejb.model.vo.BaseCriteria;
import com.parvanpajooh.issuemanager.model.enums.TaskStatusEnum;
import com.parvanpajooh.issuemanager.model.vo.GroupVO;
import com.parvanpajooh.issuemanager.model.vo.MemberVO;

public class TaskCriteria extends BaseCriteria {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6692696276170013041L;

	private String subject;

	private String description;

	private Boolean active;

	private Long updateUserId;
	
	private Long createUserId;

	private MemberVO currentMember;

	private GroupVO group;
	
	private List<Long> groupIds;

	private TaskStatusEnum currentTaskStatus;

	private LocalDateTime dueDateFrom;

	private LocalDateTime dueDateTo;

	private LocalDateTime createDateTimeFrom;

	private LocalDateTime createDateTimeTo;

	private LocalDateTime updateDateTimeFrom;

	private LocalDateTime updateDateTimeTo;
	
	private Long taskSearchId;

	/**
	 * @return the taskSearchId
	 */
	public Long getTaskSearchId() {
		return taskSearchId;
	}


	/**
	 * @param taskSearchId the taskSearchId to set
	 */
	public void setTaskSearchId(Long taskSearchId) {
		this.taskSearchId = taskSearchId;
	}


	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject
	 *            the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return the active
	 */
	public Boolean getActive() {
		return active;
	}

	/**
	 * @param active
	 *            the active to set
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the updateUserId
	 */
	public Long getUpdateUserId() {
		return updateUserId;
	}

	/**
	 * @param updateUserId
	 *            the updateUserId to set
	 */
	public void setUpdateUserId(Long updateUserId) {
		this.updateUserId = updateUserId;
	}

	/**
	 * @return the createUserId
	 */
	public Long getCreateUserId() {
		return createUserId;
	}

	/**
	 * @param createUserId the createUserId to set
	 */
	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
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
	 * @return the currentTaskStatus
	 */
	public TaskStatusEnum getCurrentTaskStatus() {
		return currentTaskStatus;
	}

	/**
	 * @param currentTaskStatus
	 *            the currentTaskStatus to set
	 */
	public void setCurrentTaskStatus(TaskStatusEnum currentTaskStatus) {
		this.currentTaskStatus = currentTaskStatus;
	}

	/**
	 * @return the dueDateFrom
	 */
	public LocalDateTime getDueDateFrom() {
		return dueDateFrom;
	}

	/**
	 * @param dueDateFrom the dueDateFrom to set
	 */
	public void setDueDateFrom(LocalDateTime dueDateFrom) {
		this.dueDateFrom = dueDateFrom;
	}

	/**
	 * @return the dueDateTo
	 */
	public LocalDateTime getDueDateTo() {
		return dueDateTo;
	}

	/**
	 * @param dueDateTo the dueDateTo to set
	 */
	public void setDueDateTo(LocalDateTime dueDateTo) {
		this.dueDateTo = dueDateTo;
	}

	/**
	 * @return the createDateTimeFrom
	 */
	public LocalDateTime getCreateDateTimeFrom() {
		return createDateTimeFrom;
	}

	/**
	 * @param createDateTimeFrom the createDateTimeFrom to set
	 */
	public void setCreateDateTimeFrom(LocalDateTime createDateTimeFrom) {
		this.createDateTimeFrom = createDateTimeFrom;
	}

	/**
	 * @return the createDateTimeTo
	 */
	public LocalDateTime getCreateDateTimeTo() {
		return createDateTimeTo;
	}

	/**
	 * @param createDateTimeTo the createDateTimeTo to set
	 */
	public void setCreateDateTimeTo(LocalDateTime createDateTimeTo) {
		this.createDateTimeTo = createDateTimeTo;
	}

	/**
	 * @return the updateDateTimeFrom
	 */
	public LocalDateTime getUpdateDateTimeFrom() {
		return updateDateTimeFrom;
	}

	/**
	 * @param updateDateTimeFrom the updateDateTimeFrom to set
	 */
	public void setUpdateDateTimeFrom(LocalDateTime updateDateTimeFrom) {
		this.updateDateTimeFrom = updateDateTimeFrom;
	}

	/**
	 * @return the updateDateTimeTo
	 */
	public LocalDateTime getUpdateDateTimeTo() {
		return updateDateTimeTo;
	}

	/**
	 * @param updateDateTimeTo the updateDateTimeTo to set
	 */
	public void setUpdateDateTimeTo(LocalDateTime updateDateTimeTo) {
		this.updateDateTimeTo = updateDateTimeTo;
	}

	/**
	 * @return the groupIds
	 */
	public List<Long> getGroupIds() {
		return groupIds;
	}

	/**
	 * @param groupIds the groupIds to set
	 */
	public void setGroupIds(List<Long> groupIds) {
		this.groupIds = groupIds;
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


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TaskCriteria [subject=" + subject + ", description=" + description + ", active=" + active + ", updateUserId=" + updateUserId + ", createUserId="
				+ createUserId + ", currentMember=" + currentMember + ", group=" + group + ", groupIds=" + groupIds + ", currentTaskStatus=" + currentTaskStatus
				+ ", dueDateFrom=" + dueDateFrom + ", dueDateTo=" + dueDateTo + ", createDateTimeFrom=" + createDateTimeFrom + ", createDateTimeTo="
				+ createDateTimeTo + ", updateDateTimeFrom=" + updateDateTimeFrom + ", updateDateTimeTo=" + updateDateTimeTo + ", taskSearchId=" + taskSearchId
				+ "]";
	}
			
}
