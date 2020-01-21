package com.parvanpajooh.issuemanager.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.parvanpajooh.commons.platform.ejb.model.BaseModel;
import com.parvanpajooh.commons.platform.ejb.model.vo.BaseVO;
import com.parvanpajooh.commons.util.Validator;
import com.parvanpajooh.issuemanager.model.enums.MemberTypeEnum;
import com.parvanpajooh.issuemanager.model.vo.GroupVO;
import com.parvanpajooh.issuemanager.model.vo.MemberVO;
import com.parvanpajooh.issuemanager.model.vo.MembershipVO;


/**
 * @author 
 * 
 */
@Entity
@XmlRootElement
@Table(name = "membership_tbl",uniqueConstraints = { @UniqueConstraint( columnNames = { "memberId", "groupId" } ) } )
public class Membership extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4036378126742804100L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "memberId", referencedColumnName = "id")
	private Member member;

	@ManyToOne
	@JoinColumn(name = "groupId", referencedColumnName = "id")
	private Group group;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "type")
	private MemberTypeEnum type;

	@Version
	private Integer version;

	private boolean active;
	
	@Override
	public MembershipVO toVOLite() {
		MembershipVO vo = new MembershipVO();

		vo.setId(this.id);
		vo.setVersion(this.version);
		vo.setType(this.type);
		vo.setActive(this.active);

		if (Validator.isNotNull(this.member)) {

			MemberVO memberVO = this.member.toVOLite();
			vo.setMember(memberVO);
		}
		if (Validator.isNotNull(this.group)) {

			GroupVO groupVO = this.group.toVOLite();
			vo.setGroupId(groupVO);
		}

		setAuditToVO(vo);

		return vo;
	}

	@Override
	public MembershipVO toVO() {

		MembershipVO membershipVO = (MembershipVO) toVOLite();

		return membershipVO;
	}

	@Override
	public void fromVO(BaseVO baseVO) {

		MembershipVO vo = (MembershipVO) baseVO;

		this.id = vo.getId();
		this.version = vo.getVersion();

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
	 * @return the member
	 */
	public Member getMember() {
		return member;
	}

	/**
	 * @param member the member to set
	 */
	public void setMember(Member member) {
		this.member = member;
	}

	/**
	 * @return the group
	 */
	public Group getGroup() {
		return group;
	}

	/**
	 * @param group the group to set
	 */
	public void setGroup(Group group) {
		this.group = group;
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
		return "User [id=" + id + ", version=" + version
				+ "]";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Membership)) {
			return false;
		}

		final Membership obj = (Membership) o;

		return new EqualsBuilder().append(this.id, obj.getId()).isEquals();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(-970989579, 1884842917).append(this.id)
				.toHashCode();
	}

}
