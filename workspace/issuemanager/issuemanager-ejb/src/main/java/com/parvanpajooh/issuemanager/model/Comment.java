package com.parvanpajooh.issuemanager.model;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
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
import com.parvanpajooh.issuemanager.model.vo.CommentVO;
import com.parvanpajooh.issuemanager.model.vo.TaskVO;
import com.parvanpajooh.commons.util.Validator;

@Entity
@XmlRootElement
@Table(name = "comment_tbl")
public class Comment extends BaseModel {

	private static final long serialVersionUID = -4036378126742804100L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@NotEmpty
	@Lob
	@Column(length = 10000)
	private String description;

	@ManyToOne
	@JoinColumn(name = "taskId", referencedColumnName = "id")
	private Task task;

	private boolean active;

	@Version
	private Integer version;

	@Override
	public CommentVO toVOLite() {
		CommentVO vo = new CommentVO();

		vo.setId(this.id);
		vo.setDescription(this.description);
		vo.setVersion(this.version);
		if (Validator.isNotNull(this.task)) {

			TaskVO taskVO = this.task.toVOLite();
			vo.setTask(taskVO);
		}

		setAuditToVO(vo);

		return vo;
	}

	/**
	 * @return the taskId
	 */
	public Task getTask() {
		return task;
	}

	/**
	 * @param taskId
	 *            the taskId to set
	 */
	public void setTask(Task task) {
		this.task = task;
	}

	@Override
	public CommentVO toVO() {

		CommentVO commentVO = (CommentVO) toVOLite();

		return commentVO;
	}

	@Override
	public void fromVO(BaseVO baseVO) {

		CommentVO vo = (CommentVO) baseVO;

		this.id = vo.getId();
		this.description = vo.getDescription();
		this.version = vo.getVersion();

		// getAuditFromVO(vo);
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", description=" + description + ", taskId=" + task + "]";
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
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active
	 *            the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
	

	/**
	 * @return the version
	 */
	public Integer getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Comment)) {
			return false;
		}

		final Comment obj = (Comment) o;

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
	public static String mapToJson(Comment obj) throws IOException {

		// initialize map
		Map<String, Object> map = new LinkedHashMap<>();
		map.put(Comment_.id.getName(), obj.getId());
		map.put(Comment_.createUserId.getName(), obj.getCreateUserId());
		map.put(Comment_.description.getName(), obj.getDescription());
		map.put(Comment_.version.getName(), obj.getVersion());
		
		
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(map);

		return json;
	}

}
