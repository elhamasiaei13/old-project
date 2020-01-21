package com.parvanpajooh.identitymanagement2.model.vo;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.parvanpajooh.common.vo.BaseVO;
import com.parvanpajooh.identitymanagement2.model.enums.CalendarEnum;

@XmlRootElement
public class UserVO extends BaseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5971686629027277987L;

	private Long id;

	private boolean active;

	private String userName;

	private String password;
	
	private String confirmPassword;

	private String firstName;

	private String lastName;

	private String displayName;

	private String canonicalSearchName;

	private String local;

	private String timezoneId;

	private String calendar;

	private String email;

	private String remarks;

	private UserGroupVO userGroup;

	private List<RoleVO> roles;

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
	public String getCalendar() {
		return calendar;
	}

	/**
	 * @param calendar
	 *            the calendar to set
	 */
	public void setCalendar(String calendar) {
		this.calendar = calendar;
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
	 * @return the userGroup
	 */
	public UserGroupVO getUserGroup() {
		return userGroup;
	}

	/**
	 * @param userGroup the userGroup to set
	 */
	public void setUserGroup(UserGroupVO userGroup) {
		this.userGroup = userGroup;
	}

	/**
	 * @return the roles
	 */
	public List<RoleVO> getRoles() {
		return roles;
	}

	/**
	 * @param roles the roles to set
	 */
	public void setRoles(List<RoleVO> roles) {
		this.roles = roles;
	}

	/**
	 * @return the confirmPassword
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}

	/**
	 * @param confirmPassword the confirmPassword to set
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

}
