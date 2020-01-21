package com.parvanpajooh.issuemanager.model.vo;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.parvanpajooh.commons.platform.ejb.model.vo.BaseVO;
import com.parvanpajooh.issuemanager.model.enums.TaskTypeEnum;

@XmlRootElement
public class MemberVO extends BaseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5971686629027277987L;

	private Long id;

	private String firstName;

	private String lastName;

	private String username;

	private String password;

	private boolean active;
	
	private byte[] fileImage;
	
	private String fileImageBase64;
	
	private String passwordConfirm;
	
	private Boolean selected;
	
	private TaskTypeEnum types;
	
	private List<String> roles;
	
	private String email;
	
	private List<TaskMemberRelationVO> taskMemberRelations;

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
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
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
	 * @return the fileImage
	 */
	public byte[] getFileImage() {
		return fileImage;
	}

	/**
	 * @param fileImage the fileImage to set
	 */
	public void setFileImage(byte[] fileImage) {
		this.fileImage = fileImage;
	}

	/**
	 * @return the fileImageBase64
	 */
	public String getFileImageBase64() {
		return fileImageBase64;
	}

	/**
	 * @param fileImageBase64 the fileImageBase64 to set
	 */
	public void setFileImageBase64(String fileImageBase64) {
		this.fileImageBase64 = fileImageBase64;
	}

	/**
	 * @return the passwordConfirm
	 */
	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	/**
	 * @param passwordConfirm the passwordConfirm to set
	 */
	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}


	/**
	 * @return the selected
	 */
	public Boolean getSelected() {
		return selected;
	}

	/**
	 * @param selected the selected to set
	 */
	public void setSelected(Boolean selected) {
		this.selected = selected;
	}

	/**
	 * @return the types
	 */
	public TaskTypeEnum getTypes() {
		return types;
	}

	/**
	 * @param types the types to set
	 */
	public void setTypes(TaskTypeEnum types) {
		this.types = types;
	}

	/**
	 * @return the roles
	 */
	public List<String> getRoles() {
		return roles;
	}

	/**
	 * @param roles the roles to set
	 */
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the taskMemberRelations
	 */
	public List<TaskMemberRelationVO> getTaskMemberRelations() {
		return taskMemberRelations;
	}

	/**
	 * @param taskMemberRelations the taskMemberRelations to set
	 */
	public void setTaskMemberRelations(List<TaskMemberRelationVO> taskMemberRelations) {
		this.taskMemberRelations = taskMemberRelations;
	}

			
}
