package com.parvanpajooh.issuemanager.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.parvanpajooh.commons.platform.ejb.model.BaseModel;
import com.parvanpajooh.commons.platform.ejb.model.vo.BaseVO;
import com.parvanpajooh.commons.util.Validator;
import com.parvanpajooh.issuemanager.model.enums.TaskMemberRelationEnum;
import com.parvanpajooh.issuemanager.model.vo.MemberVO;
import com.parvanpajooh.issuemanager.model.vo.TaskMemberRelationVO;

@Entity
@XmlRootElement
@Table(name = "task_member_relation_tbl")
public class TaskMemberRelation extends BaseModel {

	private static final long serialVersionUID = -4036378126742804100L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private TaskMemberRelationEnum type;

	@ManyToOne
	@JoinColumn(name = "taskId", referencedColumnName = "id")
	private Task task;

	@ManyToOne
	@JoinColumn(name = "memberId", referencedColumnName = "id")
	private Member member;

	@Override
	public TaskMemberRelationVO toVOLite() {
		TaskMemberRelationVO vo = new TaskMemberRelationVO();

		vo.setId(this.id);
		vo.setType(this.type);

		if( Validator.isNotNull( this.member ) ) {

			MemberVO memberVO = this.member.toVOLite();
			vo.setMember(memberVO);
		}
				
		setAuditToVO(vo);

		return vo;
	}

	@Override
	public TaskMemberRelationVO toVO() {

		TaskMemberRelationVO taskMemberRelationVO = (TaskMemberRelationVO) toVOLite();
		return taskMemberRelationVO;
	}

	@Override
	public void fromVO(BaseVO baseVO) {

		TaskMemberRelationVO vo = (TaskMemberRelationVO) baseVO;

		this.id = vo.getId();
		this.type = vo.getType();

		if (Validator.isNotNull(vo.getTask())) {
			Task task = new Task();
			task.setId(vo.getTask().getId());
			this.task = task;

		}

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
	 * @return the type
	 */
	public TaskMemberRelationEnum getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(TaskMemberRelationEnum type) {
		this.type = type;
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
	 * @return the member
	 */
	public Member getMember() {
		return member;
	}

	/**
	 * @param member
	 *            the member to set
	 */
	public void setMember(Member member) {
		this.member = member;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [id=" + id + "]";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof TaskMemberRelation)) {
			return false;
		}

		final TaskMemberRelation obj = (TaskMemberRelation) o;

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
