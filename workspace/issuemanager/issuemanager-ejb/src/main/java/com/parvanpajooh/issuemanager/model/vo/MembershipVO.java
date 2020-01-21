package com.parvanpajooh.issuemanager.model.vo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.parvanpajooh.commons.platform.ejb.model.vo.BaseVO;
import com.parvanpajooh.issuemanager.model.enums.MemberTypeEnum;

@XmlRootElement
public class MembershipVO extends BaseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5971686629027277987L;

	private Long id;

	private MemberVO member;

	private GroupVO groupId;
	
	private MemberTypeEnum type;
	
	private boolean active;	
	
	

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
	 * @return the member
	 */
	@XmlElement(name = "memberId")
	public MemberVO getMember() {
		return member;
	}

	/**
	 * @param member
	 *            the member to set
	 */
	public void setMember(MemberVO member) {
		this.member = member;
	}

	/**
	 * @return the groupId
	 */
	@XmlElement(name = "groupId")
	public GroupVO getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId
	 *            the groupId to set
	 */
	public void setGroupId(GroupVO groupId) {
		this.groupId = groupId;
	}

	/**
	 * @return the type
	 */
	public MemberTypeEnum getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(MemberTypeEnum type) {
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
		
}
