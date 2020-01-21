package com.parvanpajooh.issuemanager.model;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import com.parvanpajooh.commons.platform.ejb.model.BaseModel;
import com.parvanpajooh.commons.platform.ejb.model.vo.BaseVO;
import com.parvanpajooh.commons.platform.ejb.util.*;
import com.parvanpajooh.commons.util.Validator;
import com.parvanpajooh.issuemanager.model.enums.TableNameEnum;
import com.parvanpajooh.issuemanager.model.vo.AggregatedHistoryVO;
import com.parvanpajooh.issuemanager.model.vo.CommentVO;
import com.parvanpajooh.issuemanager.model.vo.TaskStatusHistoryVO;
import com.parvanpajooh.issuemanager.model.vo.TaskVO;

@Entity
@XmlRootElement
@Table(name = "aggregatedhistory_tbl")
public class AggregatedHistory extends BaseModel {

	private static final long serialVersionUID = -4036378126742804100L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@NotEmpty
	@Lob
	@Column(length = 10000)
	private String body;

	@Enumerated(EnumType.STRING)
	private TableNameEnum type;
	
	private boolean active;	
	
	@OneToOne
	@JoinColumn(name = "taskStatusHistoryId", referencedColumnName = "id")
	private TaskStatusHistory taskStatusHistory;
	
	@OneToOne
	@JoinColumn(name = "taskAssignmentHistoryId", referencedColumnName = "id")
	private TaskAssignmentHistory taskAssignmentHistory;
	
	@OneToOne
	@JoinColumn(name = "taskAttachmentId", referencedColumnName = "id")
	private TaskAttachment taskAttachment;
	
	@OneToOne
	@JoinColumn(name = "commentId", referencedColumnName = "id")
	private Comment comment;
	
	@OneToOne
	@JoinColumn(name = "relationId", referencedColumnName = "id")
	private Relation relation;
    	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "taskId", nullable = false)
	private Task task;
	
	@Version
    private Integer version;

	@Override
	public AggregatedHistoryVO toVOLite() {
		AggregatedHistoryVO vo = new AggregatedHistoryVO();

		vo.setId(this.id);
		vo.setVersion(this.version);
		vo.setBody(this.body);
		vo.setType(this.type);
		
		
		if (Validator.isNotNull(this.task)) {

			TaskVO taskVO = this.task.toVOLite();
			vo.setTaskVO(taskVO);
		}

		setAuditToVO(vo);

		return vo;
	}
	

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the body
	 */
	public String getBody() {
		return body;
	}

	/**
	 * @param body the body to set
	 */
	public void setBody(String body) {
		this.body = body;
	}


	/**
	 * @return the type
	 */
	public TableNameEnum getType() {
		return type;
	}


	/**
	 * @param type the type to set
	 */
	public void setType(TableNameEnum type) {
		this.type = type;
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
	 * @return the taskStatusHistory
	 */
	public TaskStatusHistory getTaskStatusHistory() {
		return taskStatusHistory;
	}

	/**
	 * @param taskStatusHistory the taskStatusHistory to set
	 */
	public void setTaskStatusHistory(TaskStatusHistory taskStatusHistory) {
		this.taskStatusHistory = taskStatusHistory;
	}

	
	/**
	 * @return the taskAssignmentHistory
	 */
	public TaskAssignmentHistory getTaskAssignmentHistory() {
		return taskAssignmentHistory;
	}


	/**
	 * @param taskAssignmentHistory the taskAssignmentHistory to set
	 */
	public void setTaskAssignmentHistory(TaskAssignmentHistory taskAssignmentHistory) {
		this.taskAssignmentHistory = taskAssignmentHistory;
	}


	/**
	 * @return the taskAttachment
	 */
	public TaskAttachment getTaskAttachment() {
		return taskAttachment;
	}


	/**
	 * @param taskAttachment the taskAttachment to set
	 */
	public void setTaskAttachment(TaskAttachment taskAttachment) {
		this.taskAttachment = taskAttachment;
	}


	/**
	 * @return the comment
	 */
	public Comment getComment() {
		return comment;
	}


	/**
	 * @param comment the comment to set
	 */
	public void setComment(Comment comment) {
		this.comment = comment;
	}


	/**
	 * @return the relation
	 */
	public Relation getRelation() {
		return relation;
	}


	/**
	 * @param relation the relation to set
	 */
	public void setRelation(Relation relation) {
		this.relation = relation;
	}


	/**
	 * @return the version
	 */
	public Integer getVersion() {
		return version;
	}
	
	

	/**
	 * @return the task
	 */
	public Task getTask() {
		return task;
	}


	/**
	 * @param task the task to set
	 */
	public void setTask(Task task) {
		this.task = task;
	}


	/**
	 * @param version the version to set
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}

	@Override
	public AggregatedHistoryVO toVO() {

		AggregatedHistoryVO aggregatedHistoryVO = (AggregatedHistoryVO) toVOLite();

		return aggregatedHistoryVO;
	}

	@Override
	public void fromVO(BaseVO baseVO) {

		AggregatedHistoryVO vo = (AggregatedHistoryVO) baseVO;

		this.id = vo.getId();
		this.version = vo.getVersion();


		// getAuditFromVO(vo);
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", type=" + type + " ]";
	}	

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof AggregatedHistory)) {
			return false;
		}

		final AggregatedHistory obj = (AggregatedHistory) o;

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
