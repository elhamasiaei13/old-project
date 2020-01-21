package com.parvanpajooh.issuemanager.model.vo;

import javax.xml.bind.annotation.XmlRootElement;

import com.parvanpajooh.commons.platform.ejb.model.vo.BaseVO;

@XmlRootElement
public class TaskAssignmentHistoryVO extends BaseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5971686629027277987L;

	private Long id;

	private MemberVO memberFrom;
	
	private MemberVO memberTo;

	private String comment;
	
	private TaskVO task;
	
	private String createUser;
	
	private String updateUser;
	

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
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
	public MemberVO getMemberFrom() {
		return memberFrom;
	}

	/**
	 * @param memberFrom the memberFrom to set
	 */
	public void setMemberFrom(MemberVO memberFrom) {
		this.memberFrom = memberFrom;
	}

	/**
	 * @return the memberTo
	 */
	public MemberVO getMemberTo() {
		return memberTo;
	}

	/**
	 * @param memberTo the memberTo to set
	 */
	public void setMemberTo(MemberVO memberTo) {
		this.memberTo = memberTo;
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
	 * @return the createUser
	 */
	public String getCreateUser() {
		return createUser;
	}

	/**
	 * @param createUser the createUser to set
	 */
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	/**
	 * @return the updateUser
	 */
	public String getUpdateUser() {
		return updateUser;
	}

	/**
	 * @param updateUser the updateUser to set
	 */
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	
}
