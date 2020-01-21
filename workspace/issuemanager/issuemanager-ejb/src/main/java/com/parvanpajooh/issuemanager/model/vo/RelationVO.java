package com.parvanpajooh.issuemanager.model.vo;

import javax.xml.bind.annotation.XmlRootElement;

import com.parvanpajooh.commons.platform.ejb.model.vo.BaseVO;
import com.parvanpajooh.issuemanager.model.enums.RelationTypeEnum;

@XmlRootElement
public class RelationVO extends BaseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5971686629027277987L;

	private Long id;

	private String description;

	private TaskVO fromTask;
	
	private TaskVO toTask;

	private String createUser;
	
	private String updateUser;
	
	private Boolean active;

	private RelationTypeEnum type;
	
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
	 * @return the fromTask
	 */
	public TaskVO getFromTask() {
		return fromTask;
	}

	/**
	 * @param fromTask the fromTask to set
	 */
	public void setFromTask(TaskVO fromTask) {
		this.fromTask = fromTask;
	}

	/**
	 * @return the toTask
	 */
	public TaskVO getToTask() {
		return toTask;
	}

	/**
	 * @param toTask the toTask to set
	 */
	public void setToTask(TaskVO toTask) {
		this.toTask = toTask;
	}

	/**
	 * @return the createUser
	 */
	public String getCreateUser() {
		return createUser;
	}

	/**
	 * @param createUser the createUser to set
	 */
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	/**
	 * @return the active
	 */
	public Boolean getActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}

	/**
	 * @return the type
	 */
	public RelationTypeEnum getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(RelationTypeEnum type) {
		this.type = type;
	}

	/**
	 * @return the updateUser
	 */
	public String getUpdateUser() {
		return updateUser;
	}

	/**
	 * @param updateUser the updateUser to set
	 */
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	
}
