package com.parvanpajooh.issuemanager.model;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import com.parvanpajooh.commons.platform.ejb.model.BaseModel;
import com.parvanpajooh.commons.platform.ejb.model.vo.BaseVO;
import com.parvanpajooh.commons.util.Validator;
import com.parvanpajooh.issuemanager.model.enums.EmailEnum;
import com.parvanpajooh.issuemanager.model.enums.MemberTypeEnum;
import com.parvanpajooh.issuemanager.model.enums.TaskPriorityEnum;
import com.parvanpajooh.issuemanager.model.enums.TaskStatusEnum;
import com.parvanpajooh.issuemanager.model.enums.TaskTypeEnum;
import com.parvanpajooh.issuemanager.model.vo.GroupVO;
import com.parvanpajooh.issuemanager.model.vo.MemberVO;
import com.parvanpajooh.issuemanager.model.vo.TaskVO;



@Entity
@XmlRootElement
@Table(name = "task_tbl")
public class Task extends BaseModel {

	private static final long serialVersionUID = -4036378126742804100L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@NotEmpty
	private String subject;

	@Lob
	@Column(length = 10000)
	private String description;
	
	@Enumerated(EnumType.STRING)
	private MemberTypeEnum memberType;
	
	@Enumerated(EnumType.STRING)
	private TaskTypeEnum taskType;
	
	@Enumerated(EnumType.STRING)
	private EmailEnum emailStatus;
	
	@Lob
	@Basic(fetch = FetchType.LAZY)
	private String metadata;
	
	private boolean active;
	
	@Enumerated(EnumType.STRING)
	private TaskPriorityEnum priority;

	@ManyToOne
	@JoinColumn(name = "currentMember", referencedColumnName = "id")
	private Member currentMember;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "task")
	private Set<TaskAssignmentHistory> taskAssignmentHistory;	
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "task")
	private Set<TaskMemberRelation> taskMemberRelations;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "currentTaskStatus")
	private TaskStatusEnum currentTaskStatus;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "task")
	private Set<TaskStatusHistory> taskStatusHistory;
	
	private LocalDateTime dueDate;

	@ManyToOne
	@JoinColumn(name = "groupId", referencedColumnName = "id")
	private Group group;

	@Version
	private Integer version;

	@Override
	public TaskVO toVOLite() {
		TaskVO vo = new TaskVO();

		vo.setId(this.id);
		vo.setSubject(this.subject);
		vo.setDescription(this.description);
		vo.setDueDate(this.dueDate);
		vo.setVersion(this.version);
		vo.setActive(this.active);
		vo.setPriority(this.priority);
		vo.setTaskType(this.taskType);
		vo.setEmailStatus(this.emailStatus);
		vo.setCurrentTaskStatus(this.currentTaskStatus);

		if (Validator.isNotNull(this.group)) {

			GroupVO groupVO = this.group.toVOLite();
			vo.setGroup(groupVO);
		}

		if (Validator.isNotNull(this.currentMember)) {

			MemberVO memberVO = this.currentMember.toVOLite();
			vo.setCurrentMember(memberVO);
		}

		setAuditToVO(vo);

		return vo;
	}

	@Override
	public TaskVO toVO() {

		TaskVO taskVO = (TaskVO) toVOLite();


		return taskVO;
	}

	@Override
	public void fromVO(BaseVO baseVO) {

		TaskVO vo = (TaskVO) baseVO;

		this.id = vo.getId();
		this.subject = vo.getSubject();
		this.description = vo.getDescription();
		this.dueDate = vo.getDueDate();
		this.version = vo.getVersion();
		this.priority = vo.getPriority();
		this.taskType = vo.getTaskType();
		this.emailStatus = vo.getEmailStatus();


/*		if (Validator.isNotNull(vo.getGroup())) {

			GroupVO groupVO = vo.getGroup();
			this.group.fromVO(groupVO);
		}*/

		getAuditFromVO(vo);
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
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
	 * @return the version
	 */
	public Integer getVersion() {
		return version;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setVersion(Integer version) {
		this.version = version;
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
	 * @return the groups
	 */
	public Group getGroup() {
		return group;
	}

	/**
	 * @param groups the group to set
	 */
	public void setGroup(Group group) {
		this.group = group;
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
	 * @return the taskAssignmentHistory
	 */
	public Set<TaskAssignmentHistory> getTaskAssignmentHistory() {
		return taskAssignmentHistory;
	}

	/**
	 * @param taskAssignmentHistory the taskAssignmentHistory to set
	 */
	public void setTaskAssignmentHistory(Set<TaskAssignmentHistory> taskAssignmentHistory) {
		this.taskAssignmentHistory = taskAssignmentHistory;
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
	 * @return the currentMember
	 */
	public Member getCurrentMember() {
		return currentMember;
	}

	/**
	 * @param currentMember the currentMember to set
	 */
	public void setCurrentMember(Member currentMember) {
		this.currentMember = currentMember;
	}


	/**
	 * @return the taskStatusHistory
	 */
	public Set<TaskStatusHistory> getTaskStatusHistory() {
		return taskStatusHistory;
	}

	/**
	 * @param taskStatusHistory the taskStatusHistory to set
	 */
	public void setTaskStatusHistory(Set<TaskStatusHistory> taskStatusHistory) {
		this.taskStatusHistory = taskStatusHistory;
	}

	/**
	 * @return the memberType
	 */
	public MemberTypeEnum getMemberType() {
		return memberType;
	}

	/**
	 * @param memberType the memberType to set
	 */
	public void setMemberType(MemberTypeEnum memberType) {
		this.memberType = memberType;
	}

	/**
	 * @return the metadata
	 */
	public String getMetadata() {
		return metadata;
	}

	/**
	 * @param metadata the metadata to set
	 */
	public void setMetadata(String metadata) {
		this.metadata = metadata;
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

	/**
	 * @return the taskMemberRelations
	 */
	public Set<TaskMemberRelation> getTaskMemberRelations() {
		return taskMemberRelations;
	}

	/**
	 * @param taskMemberRelations the taskMemberRelations to set
	 */
	public void setTaskMemberRelations(Set<TaskMemberRelation> taskMemberRelations) {
		this.taskMemberRelations = taskMemberRelations;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [id=" + id + ", subject=" + subject + ", description=" + description + ", version=" + version + "]";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Task)) {
			return false;
		}

		final Task obj = (Task) o;

		return new EqualsBuilder().append(this.id, obj.getId()).isEquals();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(-970989579, 1884842917).append(this.id).toHashCode();
	}

}
