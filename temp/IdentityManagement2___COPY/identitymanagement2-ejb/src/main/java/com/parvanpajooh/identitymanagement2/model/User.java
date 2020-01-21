package com.parvanpajooh.identitymanagement2.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
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
import javax.persistence.ManyToMany;
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
import com.parvanpajooh.identitymanagement2.model.enums.CalendarEnum;
import com.parvanpajooh.identitymanagement2.model.vo.RoleVO;
import com.parvanpajooh.identitymanagement2.model.vo.UserGroupVO;
import com.parvanpajooh.identitymanagement2.model.vo.UserVO;

/**
 * @author moosa
 *
 */
@Entity
@XmlRootElement
@Table(name = "user_tbl")
public class User extends BaseModel {

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
	private String userName;

	private String password;

	@NotNull
	@NotEmpty
	private String firstName;

	@NotNull
	@NotEmpty
	private String lastName;

	private String displayName;

	private String canonicalSearchName;

	private String local;

	private String timezoneId;

	@Enumerated(EnumType.STRING)
	private CalendarEnum calendar;

	private String email;

	private String remarks;

	@ManyToMany
	@JoinTable(name = "user_role_tbl", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "role_id") })
	private Set<Role> roles;

	@ManyToOne
	@JoinColumn(name = "userGroupId", referencedColumnName = "id")
	private UserGroup userGroup;

	@Version
	private Integer version;

	@Override
	public UserVO toVOLite() {
		UserVO vo = new UserVO();

		vo.setId(this.id);
		vo.setUserName(this.userName);
		vo.setActive(this.active);
		vo.setPassword(this.password);
		vo.setFirstName(this.firstName);
		vo.setLastName(this.lastName);
		vo.setDisplayName(this.displayName);
		vo.setCanonicalSearchName(this.canonicalSearchName);
		vo.setLocal(local);
		vo.setTimezoneId(this.timezoneId);
		vo.setCalendar(this.calendar.value);
		vo.setVersion(this.version);
		vo.setEmail(this.email);
		vo.setRemarks(this.remarks);
		
		if (Validator.isNotNull(this.roles)) {

			List<RoleVO> list = new ArrayList<RoleVO>();

			for (Role role : this.roles) {
				list.add(role.toVOLite());
			}
			vo.setRoles(list);
		}
		
		if (Validator.isNotNull(this.userGroup)) {

			UserGroupVO userGroupVO = this.userGroup.toVOLite();
			vo.setUserGroup(userGroupVO);
		}


		setAuditToVO(vo);

		return vo;
	}

	@Override
	public UserVO toVO() {

		UserVO userVO = (UserVO) toVOLite();

		
//		if (Validator.isNotNull(this.roles)) {
//
//			List<RoleVO> list = new ArrayList<RoleVO>();
//
//			for (Role role : this.roles) {
//				list.add(role.toVOLite());
//			}
//			userVO.setRoles(list);
//		}

		return userVO;
	}

	@Override
	public void fromVO(BaseVO baseVO) {

		UserVO vo = (UserVO) baseVO;

		this.id = vo.getId();
		this.userName = vo.getUserName();
		this.password = vo.getPassword();
		this.firstName = vo.getFirstName();
		this.lastName = vo.getLastName();
		this.displayName = vo.getDisplayName();
		this.canonicalSearchName = vo.getCanonicalSearchName();
		this.local = vo.getLocal();
		this.timezoneId = vo.getTimezoneId();
		this.calendar = CalendarEnum.fromValue(vo.getCalendar());
		this.email = vo.getEmail();
		this.remarks = vo.getRemarks();
		this.version = vo.getVersion();
		this.active = vo.isActive();		

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
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @param displayName
	 *            the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * @return the canonicalSearchName
	 */
	public String getCanonicalSearchName() {
		return canonicalSearchName;
	}

	/**
	 * @param canonicalSearchName
	 *            the canonicalSearchName to set
	 */
	public void setCanonicalSearchName(String canonicalSearchName) {
		this.canonicalSearchName = canonicalSearchName;
	}

	/**
	 * @return the local
	 */
	public String getLocal() {
		return local;
	}

	/**
	 * @param local
	 *            the local to set
	 */
	public void setLocal(String local) {
		this.local = local;
	}

	/**
	 * @return the timezoneId
	 */
	public String getTimezoneId() {
		return timezoneId;
	}

	/**
	 * @param timezoneId
	 *            the timezoneId to set
	 */
	public void setTimezoneId(String timezoneId) {
		this.timezoneId = timezoneId;
	}

	/**
	 * @return the calendar
	 */
	public CalendarEnum getCalendar() {
		return calendar;
	}

	/**
	 * @param calendar
	 *            the calendar to set
	 */
	public void setCalendar(CalendarEnum calendar) {
		this.calendar = calendar;
	}

	/**
	 * @return the roles
	 */
	public Set<Role> getRoles() {
		return roles;
	}

	/**
	 * @param roles
	 *            the roles to set
	 */
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
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
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks
	 *            the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
	 * @param version
	 *            the version to set
	 */
	public void setVersion(Integer version) {
		this.version = version;
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
		if (!(o instanceof User)) {
			return false;
		}

		final User obj = (User) o;

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
