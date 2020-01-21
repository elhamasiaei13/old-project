package com.parvanpajooh.identitymanagement2.model.vo;

import javax.xml.bind.annotation.XmlRootElement;

import com.parvanpajooh.common.vo.BaseVO;

@XmlRootElement
public class PermissionVO extends BaseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5971686629027277987L;

	private Long id;

	private String resourceUri;

	private Long bitwiseValue;

	private String constraintRules;
	
	private RoleVO role;
	
	private ObjectAccessScopeVO objectAccessScope;

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
	 * @return the resourceUri
	 */
	public String getResourceUri() {
		return resourceUri;
	}

	/**
	 * @param resourceUri the resourceUri to set
	 */
	public void setResourceUri(String resourceUri) {
		this.resourceUri = resourceUri;
	}

	/**
	 * @return the bitwiseValue
	 */
	public Long getBitwiseValue() {
		return bitwiseValue;
	}

	/**
	 * @param bitwiseValue the bitwiseValue to set
	 */
	public void setBitwiseValue(Long bitwiseValue) {
		this.bitwiseValue = bitwiseValue;
	}

	/**
	 * @return the constraintRules
	 */
	public String getConstraintRules() {
		return constraintRules;
	}

	/**
	 * @param constraintRules the constraintRules to set
	 */
	public void setConstraintRules(String constraintRules) {
		this.constraintRules = constraintRules;
	}

	/**
	 * @return the role
	 */
	public RoleVO getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(RoleVO role) {
		this.role = role;
	}

	/**
	 * @return the objectAccessScope
	 */
	public ObjectAccessScopeVO getObjectAccessScope() {
		return objectAccessScope;
	}

	/**
	 * @param objectAccessScope the objectAccessScope to set
	 */
	public void setObjectAccessScope(ObjectAccessScopeVO objectAccessScope) {
		this.objectAccessScope = objectAccessScope;
	}

}
