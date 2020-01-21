package com.parvanpajooh.issuemanager.model.vo;

import javax.xml.bind.annotation.XmlRootElement;

import com.parvanpajooh.commons.platform.ejb.model.vo.BaseVO;
import com.parvanpajooh.issuemanager.model.enums.TaskMemberRelationEnum;

@XmlRootElement
public class TaskMemberRelationVO extends BaseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5971686629027277987L;

	private Long id;

	private TaskVO task;
	
	private MemberVO member;
	
	private TaskMemberRelationEnum type;

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
	 * @return the task
	 */
	public TaskVO getTask() {
		return task;
	}

	/**
	 * @param task the task to set
	 */
	public void setTask(TaskVO task) {
		this.task = task;
	}

	/**
	 * @return the member
	 */
	public MemberVO getMember() {
		return member;
	}

	/**
	 * @param member the member to set
	 */
	public void setMember(MemberVO member) {
		this.member = member;
	}

	/**
	 * @return the type
	 */
	public TaskMemberRelationEnum getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(TaskMemberRelationEnum type) {
		this.type = type;
	}

}
