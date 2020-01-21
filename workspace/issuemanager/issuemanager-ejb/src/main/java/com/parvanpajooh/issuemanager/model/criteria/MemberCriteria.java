package com.parvanpajooh.issuemanager.model.criteria;

import java.util.List;

import com.parvanpajooh.commons.platform.ejb.model.vo.BaseCriteria;

public class MemberCriteria extends BaseCriteria {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6692696276170013041L;

	private String name;
	
	private List<Long> ids;
	
	private Boolean active;
	
	
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
	 * @return the ids
	 */
	public List<Long> getIds() {
		return ids;
	}


	/**
	 * @param ids the ids to set
	 */
	public void setIds(List<Long> ids) {
		this.ids = ids;
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
	
}
