package com.parvanpajooh.identitymanagement2.model.vo;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.parvanpajooh.common.vo.BaseVO;
import com.parvanpajooh.identitymanagement2.model.Role;

@XmlRootElement
public class UserGroupVO extends BaseVO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5971686629027277987L;

	private Long id;
		
	private boolean active;
	
	private String name;
	
	private String description;
	
	private List<UserVO> user;
	
	private List<RoleVO> role;
	
	
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
	 * @return the user
	 */
	public List<UserVO> getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(List<UserVO> user) {
		this.user = user;
	}

	/**
	 * @return the role
	 */
	public List<RoleVO> getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(List<RoleVO> role) {
		this.role = role;
	}
	
}
