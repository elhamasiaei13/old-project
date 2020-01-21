package com.parvanpajooh.identitymanagement2.model.criteria;

import com.parvanpajooh.common.vo.BaseCriteria;

public class UserGroupCriteria extends BaseCriteria {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6692696276170013041L;
	
	
	
	
	private Boolean active;
	
	private String canonicalSearchGroupName;


	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SampleCriteria [active="
				+ active +  "]";
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
	 * @return the canonicalSearchGroupName
	 */
	public String getCanonicalSearchGroupName() {
		return canonicalSearchGroupName;
	}


	/**
	 * @param canonicalSearchGroupName the canonicalSearchGroupName to set
	 */
	public void setCanonicalSearchGroupName(String canonicalSearchGroupName) {
		this.canonicalSearchGroupName = canonicalSearchGroupName;
	}
}
