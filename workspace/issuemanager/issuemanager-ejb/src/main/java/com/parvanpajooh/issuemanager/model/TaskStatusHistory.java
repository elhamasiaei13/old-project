package com.parvanpajooh.issuemanager.model;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parvanpajooh.commons.platform.ejb.model.BaseModel;
import com.parvanpajooh.commons.platform.ejb.model.vo.BaseVO;
import com.parvanpajooh.commons.util.Validator;
import com.parvanpajooh.issuemanager.model.vo.TaskStatusHistoryVO;
import com.parvanpajooh.issuemanager.model.vo.TaskVO;

@Entity
@XmlRootElement
@Table(name = "taskStatusHistory_tbl")
public class TaskStatusHistory extends BaseModel {

	private static final long serialVersionUID = -4036378126742804100L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@NotEmpty
	private String fromStatus;

	@NotNull
	@NotEmpty
	private String toStatus;

	@Lob
	@Column(length = 10000)
	private String comment;

	@Version
	private Integer version;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "taskId", nullable = false)
	private Task task;

	@Override
	public TaskStatusHistoryVO toVOLite() {

		TaskStatusHistoryVO vo = new TaskStatusHistoryVO();
		vo.setId(this.id);
		vo.setFromStatus(this.fromStatus);
		vo.setToStatus(this.toStatus);
		vo.setComment(this.comment);

		if (Validator.isNotNull(this.task)) {

			TaskVO taskVO = this.task.toVOLite();
			vo.setTask(taskVO);
		}

		setAuditToVO(vo);

		return vo;
	}

	@Override
	public TaskStatusHistoryVO toVO() {

		TaskStatusHistoryVO taskStatusHistoryVO = (TaskStatusHistoryVO) toVOLite();

		return taskStatusHistoryVO;
	}

	@Override
	public void fromVO(BaseVO baseVO) {

		TaskStatusHistoryVO vo = (TaskStatusHistoryVO) baseVO;

		this.id = vo.getId();
		this.fromStatus = vo.getFromStatus();
		this.toStatus = vo.getToStatus();
		this.comment = vo.getComment();

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
	 * @return the fromStatus
	 */
	public String getFromStatus() {
		return fromStatus;
	}

	/**
	 * @param fromStatus
	 *            the fromStatus to set
	 */
	public void setFromStatus(String fromStatus) {
		this.fromStatus = fromStatus;
	}

	/**
	 * @return the toStatus
	 */
	public String getToStatus() {
		return toStatus;
	}

	/**
	 * @param toStatus
	 *            the toStatus to set
	 */
	public void setToStatus(String toStatus) {
		this.toStatus = toStatus;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment
	 *            the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
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
	 * @return the task
	 */
	public Task getTask() {
		return task;
	}

	/**
	 * @param task
	 *            the task to set
	 */
	public void setTask(Task task) {
		this.task = task;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [id=" + id + ", fromStatus=" + fromStatus + ", toStatus=" + toStatus + ", comment=" + comment + ", version=" + version + "]";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof TaskStatusHistory)) {
			return false;
		}

		final TaskStatusHistory obj = (TaskStatusHistory) o;

		return new EqualsBuilder().append(this.id, obj.getId()).isEquals();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(-970989579, 1884842917).append(this.id).toHashCode();
	}

	/**
	 * convert map to json
	 * 
	 * @throws IOException
	 */
	public static String mapToJson(TaskStatusHistory obj) throws IOException {

		// initialize map
		Map<String, Object> map = new LinkedHashMap<>();
		map.put(TaskStatusHistory_.id.getName(), obj.getId());
		map.put(TaskStatusHistory_.createUserId.getName(), obj.getCreateUserId());
		map.put(TaskStatusHistory_.fromStatus.getName(), obj.getFromStatus());
		map.put(TaskStatusHistory_.toStatus.getName(), obj.getToStatus());
		map.put(TaskStatusHistory_.comment.getName(), obj.getComment());
		map.put(TaskStatusHistory_.version.getName(), obj.getVersion());

		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(map);

		return json;
	}
}
