package com.parvanpajooh.identitymanagement2.model.vo;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.parvanpajooh.common.vo.BaseVO;

@XmlRootElement
public class RoleVO extends BaseVO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5971686629027277987L;

	private Long id;

	private boolean active;

	private String roleName;
	
	private String type;
	
	private String description;
	
	private UserGroupVO userGroup;
	
	private List<UserVO> user;

	private boolean selected;

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
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
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
	 * @return the selected
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * @param selected the selected to set
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}


	
}
