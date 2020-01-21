package com.parvanpajooh.identitymanagement2.model.criteria;

import com.parvanpajooh.common.vo.BaseCriteria;
import com.parvanpajooh.identitymanagement2.model.enums.RoleTypeEnum;

public class RoleCriteria extends BaseCriteria {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6692696276170013041L;
	

	private Boolean active;
	
	private String canonicalSearchRoleName;
	
	private String type;


	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SampleCriteria [ active="
				+ active + "]";
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
	 * @return the canonicalSearchRoleName
	 */
	public String getCanonicalSearchRoleName() {
		return canonicalSearchRoleName;
	}

	/**
	 * @param canonicalSearchRoleName the canonicalSearchRoleName to set
	 */
	public void setCanonicalSearchRoleName(String canonicalSearchRoleName) {
		this.canonicalSearchRoleName = canonicalSearchRoleName;
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


}
