package com.parvanpajooh.sample.model.vo;

import javax.xml.bind.annotation.XmlRootElement;

import com.parvanpajooh.client.common.BaseMsg;
import com.parvanpajooh.client.identitymanagement2.model.ObjectAccessScopeMsg;
import com.parvanpajooh.client.identitymanagement2.model.RoleMsg;

@XmlRootElement
public class ObjectAccessScopeVO{

	private Long id;
	
	private String code;
	
	private String name;
	
	private String description;

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


	public void fromMsg(ObjectAccessScopeMsg objectAccessScopeMsg) {

		this.id = objectAccessScopeMsg.getId();
		this.code = objectAccessScopeMsg.getCode();
		this.name = objectAccessScopeMsg.getName();
		this.description = objectAccessScopeMsg.getDescription();

	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ObjectAccessScopeMsg [id=" + id + ", code=" + code + ", name=" + name + ", description=" + description + "]";
	}
}
