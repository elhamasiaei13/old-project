package com.parvanpajooh.identitymanagement2.model.vo;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.parvanpajooh.common.vo.BaseVO;

@XmlRootElement
public class ObjectAccessScopeVO extends BaseVO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5971686629027277987L;

	private Long id;

	private String code;
	
	private String name;
	
	private String description;
	
	private List<PermissionVO> permission;


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
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
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
	 * @return the permission
	 */
	public List<PermissionVO> getPermission() {
		return permission;
	}

	/**
	 * @param permission the permission to set
	 */
	public void setPermission(List<PermissionVO> permission) {
		this.permission = permission;
	}

}
