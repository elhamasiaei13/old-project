package com.parvanpajooh.identitymanagement2.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import com.parvanpajooh.common.util.Validator;
import com.parvanpajooh.common.vo.BaseVO;
import com.parvanpajooh.ecourier.model.BaseModel;
import com.parvanpajooh.identitymanagement2.model.enums.RoleTypeEnum;
import com.parvanpajooh.identitymanagement2.model.vo.RoleVO;
import com.parvanpajooh.identitymanagement2.model.vo.UserGroupVO;
import com.parvanpajooh.identitymanagement2.model.vo.UserVO;

/**
 * @author moosa
 *
 */
@Entity
@XmlRootElement
@Table(name = "role_tbl")
public class Role extends BaseModel {

	/**
	 *
	 */
	private static final long serialVersionUID = -4036378126742804100L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private boolean active;

	@NotNull
	@NotEmpty
	private String roleName;

	@NotNull
	@NotEmpty
	@Lob
	@Column(length = 10000)
	private String description;

	@Enumerated(EnumType.STRING)
	private RoleTypeEnum type;

	@Version
	private Integer version;

	@ManyToOne
	@JoinColumn(name = "userGroupId", referencedColumnName = "id")
	private UserGroup userGroup;

	@ManyToMany
	@JoinTable(name = "user_role_tbl", joinColumns = { @JoinColumn(name = "role_id") }, inverseJoinColumns = { @JoinColumn(name = "user_id") })
	private Set<User> users;

	@Override
	public RoleVO toVOLite() {
		RoleVO vo = new RoleVO();

		vo.setId(this.id);
		vo.setRoleName(this.roleName);
		vo.setDescription(this.description);
		vo.setType(this.type.value);
		vo.setVersion(this.version);

		if (Validator.isNotNull(this.userGroup)) {

			UserGroupVO userGroupVO = this.userGroup.toVOLite();
			vo.setUserGroup(userGroupVO);
		}

		setAuditToVO(vo);

		return vo;
	}

	@Override
	public RoleVO toVO() {

		RoleVO roleVO = (RoleVO) toVOLite();

		if (Validator.isNotNull(this.users)) {

			List<UserVO> list = new ArrayList<UserVO>();

			for (User user : this.users) {
				list.add(user.toVOLite());
			}
			roleVO.setUser(list);
		}

		return roleVO;
	}

	@Override
	public void fromVO(BaseVO baseVO) {

		RoleVO vo = (RoleVO) baseVO;

		this.id = vo.getId();
		this.roleName = vo.getRoleName();
		this.description = vo.getDescription();
		this.version = vo.getVersion();
		this.type = RoleTypeEnum.fromValue(vo.getType());

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
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * @param roleName the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the type
	 */
	public RoleTypeEnum getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(RoleTypeEnum type) {
		this.type = type;
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
	 * @return the userGroup
	 */
	public UserGroup getUserGroup() {
		return userGroup;
	}

	/**
	 * @param userGroup the userGroup to set
	 */
	public void setUserGroup(UserGroup userGroup) {
		this.userGroup = userGroup;
	}

	/**
	 * @return the users
	 */
	public Set<User> getUsers() {
		return users;
	}

	/**
	 * @param users the users to set
	 */
	public void setUsers(Set<User> users) {
		this.users = users;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Role [id=" + id + ", version=" + version + "]";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Role)) {
			return false;
		}

		final Role obj = (Role) o;

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
