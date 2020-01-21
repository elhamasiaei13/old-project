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
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parvanpajooh.commons.platform.ejb.model.BaseModel;
import com.parvanpajooh.commons.platform.ejb.model.vo.BaseVO;
import com.parvanpajooh.commons.util.Validator;
import com.parvanpajooh.issuemanager.model.vo.TaskAttachmentVO;
import com.parvanpajooh.issuemanager.model.vo.TaskVO;

/**
 * @author
 * 
 */
@Entity
@XmlRootElement
@Table(name = "taskAttachment_tbl")
public class TaskAttachment extends BaseModel {

	private static final long serialVersionUID = -4036378126742804100L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Version
	private Integer version;

	@Lob
	@Column(length = 10000)
	private String comment;

	private boolean active;
	private String name;
	private String mimeType;
	private String path;
	private Long size;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "taskId", nullable = false)
	private Task task;

	@Override
	public TaskAttachmentVO toVOLite() {
		TaskAttachmentVO vo = new TaskAttachmentVO();

		vo.setId(this.id);
		vo.setVersion(this.version);
		vo.setComment(this.comment);
		vo.setName(this.name);
		vo.setPath(this.path);
		vo.setMimeType(this.mimeType);
		vo.setSize(this.size);
		
		if (Validator.isNotNull(this.task)) {

			TaskVO taskVO = this.task.toVOLite();
			vo.setTask(taskVO);
		}
		

		setAuditToVO(vo);

		return vo;
	}

	@Override
	public TaskAttachmentVO toVO() {

		TaskAttachmentVO taskAttachmentVO = (TaskAttachmentVO) toVOLite();

		return taskAttachmentVO;
	}

	@Override
	public void fromVO(BaseVO baseVO) {

		TaskAttachmentVO vo = (TaskAttachmentVO) baseVO;

		this.id = vo.getId();
		this.version = vo.getVersion();
		this.comment = vo.getComment();
		this.name = vo.getName();
		this.path = vo.getPath();
		this.size =vo.getSize();
		this.mimeType =vo.getMimeType();

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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path
	 *            the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}
	

	/**
	 * @return the mimeType
	 */
	public String getMimeType() {
		return mimeType;
	}

	/**
	 * @param mimeType the mimeType to set
	 */
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	/**
	 * @return the size
	 */
	public Long getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(Long size) {
		this.size = size;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [id=" + id + ", version=" + version + "]";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof TaskAttachment)) {
			return false;
		}

		final TaskAttachment obj = (TaskAttachment) o;

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
	 * @throws IOException 
	 */
	public static String mapToJson(TaskAttachment obj) throws IOException {
		
	    // initialize map
	    Map<String, Object> map = new LinkedHashMap<>();
	    map.put(TaskAttachment_.id.getName(), obj.getId());
	    map.put(TaskAttachment_.createUserId.getName(), obj.getCreateUserId());
	    map.put(TaskAttachment_.name.getName(), obj.getName());
	    map.put(TaskAttachment_.mimeType.getName(), obj.getMimeType());
	    map.put(TaskAttachment_.size.getName(), obj.getSize());
	    map.put(TaskAttachment_.path.getName(), obj.getPath());
	    map.put(TaskAttachment_.comment.getName(), obj.getComment());
	    map.put(TaskAttachment_.version.getName(), obj.getVersion());
	    

	    ObjectMapper objectMapper = new ObjectMapper();
	    String json = objectMapper.writeValueAsString(map);

		return json;
	}

}
