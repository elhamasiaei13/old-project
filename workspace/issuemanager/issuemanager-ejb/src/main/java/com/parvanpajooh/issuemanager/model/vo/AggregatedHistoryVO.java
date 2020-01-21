package com.parvanpajooh.issuemanager.model.vo;

import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import com.parvanpajooh.commons.platform.ejb.model.vo.BaseVO;
import com.parvanpajooh.issuemanager.model.enums.TableNameEnum;

@XmlRootElement
public class AggregatedHistoryVO extends BaseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5971686629027277987L;

	private Long id;

	private TableNameEnum type;
	
	private String body;	

	private TaskStatusHistoryVO taskStatusHistoryVO;
	
	private TaskVO taskVO;
	
	private Boolean active;
	
	private String createUser;
	
	private String updateUser;
	
	private Map<String,Object> jsonsMap;
	
	

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
	 * @return the type
	 */
	public TableNameEnum getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(TableNameEnum type) {
		this.type = type;
	}

	/**
	 * @return the body
	 */
	public String getBody() {
		return body;
	}

	/**
	 * @param body the body to set
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * @return the taskStatusHistoryVO
	 */
	public TaskStatusHistoryVO getTaskStatusHistoryVO() {
		return taskStatusHistoryVO;
	}

	/**
	 * @param taskStatusHistoryVO the taskStatusHistoryVO to set
	 */
	public void setTaskStatusHistoryVO(TaskStatusHistoryVO taskStatusHistoryVO) {
		this.taskStatusHistoryVO = taskStatusHistoryVO;
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
	 * @return the taskVO
	 */
	public TaskVO getTaskVO() {
		return taskVO;
	}

	/**
	 * @param taskVO the taskVO to set
	 */
	public void setTaskVO(TaskVO taskVO) {
		this.taskVO = taskVO;
	}

	/**
	 * @return the jsonsMap
	 */
	public Map<String, Object> getJsonsMap() {
		return jsonsMap;
	}

	/**
	 * @param jsonsMap the jsonsMap to set
	 */
	public void setJsonsMap(Map<String, Object> jsonsMap) {
		this.jsonsMap = jsonsMap;
	}

	/**
	 * @return the creatUser
	 */
	public String getCreateUser() {
		return createUser;
	}

	/**
	 * @param creatUser the creatUser to set
	 */
	public void setCreateUser(String creatUser) {
		this.createUser = creatUser;
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
