package com.parvanpajooh.identitymanagement2.model.criteria;

import java.util.List;

import com.parvanpajooh.common.vo.BaseCriteria;

public class UserCriteria extends BaseCriteria {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6692696276170013041L;
	
	
	private String firstName;
	
	private String lastName;
	
	private String searchName;
	
	private String link;
	
	private Boolean active;
	
	private List<String> roles;
	
	private String canonicalSearchName;

	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SampleCriteria [link=" + link + ", active="
				+ active + ", roles=" + roles + "]";
	}	

	/**
	 * @return the searchName
	 */
	public String getSearchName() {
		return searchName;
	}


	/**
	 * @param searchName the searchName to set
	 */
	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}


	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the link
	 */
	public String getLink() {
		return link;
	}

	/**
	 * @param link the link to set
	 */
	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * @return the roles
	 */
	public List<String> getRoles() {
		return roles;
	}

	/**
	 * @param roles the roles to set
	 */
	public void setRoles(List<String> roles) {
		this.roles = roles;
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
	 * @return the canonicalSearchName
	 */
	public String getCanonicalSearchName() {
		return canonicalSearchName;
	}

	/**
	 * @param canonicalSearchName the canonicalSearchName to set
	 */
	public void setCanonicalSearchName(String canonicalSearchName) {
		this.canonicalSearchName = canonicalSearchName;
	}	

}
