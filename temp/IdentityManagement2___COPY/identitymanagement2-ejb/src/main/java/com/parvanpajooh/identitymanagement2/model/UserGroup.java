package com.parvanpajooh.identitymanagement2.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
import com.parvanpajooh.identitymanagement2.model.vo.RoleVO;
import com.parvanpajooh.identitymanagement2.model.vo.UserGroupVO;
import com.parvanpajooh.identitymanagement2.model.vo.UserVO;

/**
 * @author moosa
 *
 */
@Entity
@XmlRootElement
@Table(name = "usergroup_tbl")
public class UserGroup extends BaseModel {

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
	@Column(unique = true)
	private String name;

	@NotNull
	@NotEmpty
	@Lob
	@Column(length = 10000)
	private String description;

	@Version
	private Integer version;
	
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "userGroup")
	private Set<User> user;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "userGroup")
	private Set<Role> role;

	@Override
	public UserGroupVO toVOLite() {
		UserGroupVO vo = new UserGroupVO();

		vo.setId(this.id);
		vo.setName(this.name);
		vo.setDescription(this.description);	
		vo.setVersion(this.version);

		setAuditToVO(vo);

		return vo;
	}

	@Override
	public UserGroupVO toVO() {

		UserGroupVO userGroupVO = (UserGroupVO) toVOLite();

		if (Validator.isNotNull(this.user)) {

			List<UserVO> list = new ArrayList<UserVO>();

			for (User user : this.user) {
				list.add(user.toVOLite());
			}
			userGroupVO.setUser(list);
		}

		 if( Validator.isNotNull( this.role ) ) {
		
		 List<RoleVO> list = new ArrayList<RoleVO>();
		
		 for (Role role: this.role) {
		 list.add(role.toVOLite());
		 }
		 userGroupVO.setRole(list);
		 }

		return userGroupVO;
	}

	@Override
	public void fromVO(BaseVO baseVO) {

		UserGroupVO vo = (UserGroupVO) baseVO;

		this.id = vo.getId();
		this.name = vo.getName();
		this.description=vo.getDescription();
		this.version=vo.getVersion();

		getAuditFromVO(vo);
	}

	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
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

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return the user
	 */
	public Set<User> getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(Set<User> user) {
		this.user = user;
	}

	/**
	 * @return the role
	 */
	public Set<Role> getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(Set<Role> role) {
		this.role = role;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Sample [id=" + id + ", version=" + version + "]";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof UserGroup)) {
			return false;
		}

		final UserGroup obj = (UserGroup) o;

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
