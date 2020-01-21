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
import com.parvanpajooh.issuemanager.model.vo.MemberVO;
import com.parvanpajooh.issuemanager.model.vo.TaskAssignmentHistoryVO;
import com.parvanpajooh.issuemanager.model.vo.TaskVO;

/**
 * @author
 * 
 */
@Entity
@XmlRootElement
@Table(name = "taskAssignmentHistory_tbl")
public class TaskAssignmentHistory extends BaseModel {

	private static final long serialVersionUID = -4036378126742804100L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "memberFrom", referencedColumnName = "id")
	private Member memberFrom;

	@ManyToOne
	@JoinColumn(name = "memberTo", referencedColumnName = "id")
	private Member memberTo;

	@Version
	private Integer version;

	@Lob
	@Column(length = 10000)
	private String comment;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "taskId", nullable = false)
	private Task task;

	@Override
	public TaskAssignmentHistoryVO toVOLite() {
		TaskAssignmentHistoryVO vo = new TaskAssignmentHistoryVO();

		vo.setId(this.id);
		vo.setVersion(this.version);
		vo.setComment(this.comment);

		if (Validator.isNotNull(this.task)) {

			TaskVO taskVO = this.task.toVOLite();
			vo.setTask(taskVO);
		}

		if (Validator.isNotNull(this.memberFrom)) {

			MemberVO memberVO = this.memberFrom.toVOLite();
			vo.setMemberFrom(memberVO);
		}

		if (Validator.isNotNull(this.memberTo)) {

			MemberVO memberVO = this.memberTo.toVOLite();
			vo.setMemberTo(memberVO);
		}

		setAuditToVO(vo);

		return vo;
	}

	@Override
	public TaskAssignmentHistoryVO toVO() {

		TaskAssignmentHistoryVO taskAssignmentVO = (TaskAssignmentHistoryVO) toVOLite();

		return taskAssignmentVO;
	}

	@Override
	public void fromVO(BaseVO baseVO) {

		TaskAssignmentHistoryVO vo = (TaskAssignmentHistoryVO) baseVO;

		this.id = vo.getId();
		this.version = vo.getVersion();
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
	 * @return the memberFrom
	 */
	public Member getMemberFrom() {
		return memberFrom;
	}

	/**
	 * @param memberFrom
	 *            the memberFrom to set
	 */
	public void setMemberFrom(Member memberFrom) {
		this.memberFrom = memberFrom;
	}

	/**
	 * @return the memberTo
	 */
	public Member getMemberTo() {
		return memberTo;
	}

	/**
	 * @param memberTo
	 *            the memberTo to set
	 */
	public void setMemberTo(Member memberTo) {
		this.memberTo = memberTo;
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
		if (!(o instanceof TaskAssignmentHistory)) {
			return false;
		}

		final TaskAssignmentHistory obj = (TaskAssignmentHistory) o;

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
	public static String mapToJson(TaskAssignmentHistory obj) throws IOException {

		// initialize map
		Map<String, Object> map = new LinkedHashMap<>();
		map.put(TaskAssignmentHistory_.id.getName(), obj.getId());
		map.put(TaskAssignmentHistory_.createUserId.getName(), obj.getCreateUserId());
		if (Validator.isNotNull(obj.getMemberFrom())) {
			map.put(TaskAssignmentHistory_.memberFrom.getName(), obj.getMemberFrom().getUsername());
			map.put("memberFromId", obj.getMemberFrom().getId());
		}
		if(Validator.isNotNull(obj.getMemberTo())){
			map.put(TaskAssignmentHistory_.memberTo.getName(), obj.getMemberTo().getUsername());
			map.put("memberToId", obj.getMemberTo().getId());			
		}
		map.put(TaskAssignmentHistory_.comment.getName(), obj.getComment());
		map.put(TaskAssignmentHistory_.version.getName(), obj.getVersion());
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(map);

		return json;
	}
}
