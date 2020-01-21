package com.parvanpajooh.issuemanager.model.criteria;

import com.parvanpajooh.commons.platform.ejb.model.vo.BaseCriteria;
import com.parvanpajooh.issuemanager.model.vo.GroupVO;

public class MembershipCriteria extends BaseCriteria {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6692696276170013041L;

	private String name;
	
	private Boolean active;
	
	private GroupVO group;
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SampleCriteria [name=" + name + "]";
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
	 * @return the group
	 */
	public GroupVO getGroup() {
		return group;
	}


	/**
	 * @param group the group to set
	 */
	public void setGroup(GroupVO group) {
		this.group = group;
	}	
		
}
