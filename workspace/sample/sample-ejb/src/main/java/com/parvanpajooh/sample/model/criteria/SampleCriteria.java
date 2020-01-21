package com.parvanpajooh.sample.model.criteria;

import java.util.Set;

import com.parvanpajooh.commons.platform.ejb.model.vo.BaseCriteria;
import com.parvanpajooh.sample.model.vo.SampleVO;

public class SampleCriteria extends BaseCriteria {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6692696276170013041L;

	private String canonicalSearchName;

	private Boolean active;
	
	private String name;
	
	private Long id;
	
	private SampleVO parent;
	
	private String iataCode;

	private String address;

	private String tel;

	private String email;

	private String logoId;

	private Boolean branch;

	private String nodeCode;
	
	private String nodeId;
	
	private String currentAccountNumber;
	
	private Set<Long> allowedAgentIds;

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

	/**
	 * @return the active
	 */
	public Boolean getActive() {
		return active;
	}

	/**
	 * @param active
	 *            the active to set
	 */
	public void setActive(Boolean active) {
		this.active = active;
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
	 * @return the parent
	 */
	public SampleVO getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(SampleVO parent) {
		this.parent = parent;
	}

	/**
	 * @return the iataCode
	 */
	public String getIataCode() {
		return iataCode;
	}

	/**
	 * @param iataCode the iataCode to set
	 */
	public void setIataCode(String iataCode) {
		this.iataCode = iataCode;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the tel
	 */
	public String getTel() {
		return tel;
	}

	/**
	 * @param tel the tel to set
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the logoId
	 */
	public String getLogoId() {
		return logoId;
	}

	/**
	 * @param logoId the logoId to set
	 */
	public void setLogoId(String logoId) {
		this.logoId = logoId;
	}

	/**
	 * @return the nodeCode
	 */
	public String getNodeCode() {
		return nodeCode;
	}

	/**
	 * @param nodeCode the nodeCode to set
	 */
	public void setNodeCode(String nodeCode) {
		this.nodeCode = nodeCode;
	}

	/**
	 * @return the nodeId
	 */
	public String getNodeId() {
		return nodeId;
	}

	/**
	 * @param nodeId the nodeId to set
	 */
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	/**
	 * @return the branch
	 */
	public Boolean getBranch() {
		return branch;
	}

	/**
	 * @param branch the branch to set
	 */
	public void setBranch(Boolean branch) {
		this.branch = branch;
	}

	/**
	 * @return the currentAccountNumber
	 */
	public String getCurrentAccountNumber() {
		return currentAccountNumber;
	}

	/**
	 * @param currentAccountNumber the currentAccountNumber to set
	 */
	public void setCurrentAccountNumber(String currentAccountNumber) {
		this.currentAccountNumber = currentAccountNumber;
	}

	/**
	 * @return the allowedAgentIds
	 */
	public Set<Long> getAllowedAgentIds() {
		return allowedAgentIds;
	}

	/**
	 * @param allowedAgentIds the allowedAgentIds to set
	 */
	public void setAllowedAgentIds(Set<Long> allowedAgentIds) {
		this.allowedAgentIds = allowedAgentIds;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GeneralAgentCriteria [canonicalSearchName=" + canonicalSearchName + ", active=" + active + ", id=" + id + ", iataCode=" + iataCode
				+ ", address=" + address + ", tel=" + tel + ", email=" + email + ", logoId=" + logoId + ", branch=" + branch + ", nodeCode=" + nodeCode
				+ ", nodeId=" + nodeId + ", currentAccountNumber=" + currentAccountNumber + ", allowedAgentIds=" + allowedAgentIds + "]";
	}
}
