package com.parvanpajooh.sample.model.vo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.parvanpajooh.client.agentmanagement.model.GeneralAgentMsg;
import com.parvanpajooh.commons.platform.ejb.model.vo.BaseVO;
import com.parvanpajooh.commons.util.Validator;

public class SampleVO extends BaseVO {

	private static final long serialVersionUID = -5971686629027277988L;

	private String name;

	private String canonicalSeachName;

	private String iataCode;

	private String address;

	private String tel;

	private String email;

	private String logoId;

	private boolean branch;

	private String nodeCode;
	
	private Long nodeId;

	private String remarks;

	private boolean active;
	
	private List<String> tags;

	private SampleVO parent;

	private List<SampleVO> children;

	private String currentAccountNumber;
	
	private Long personOrganizationId;
	
	private String cityShortCode;
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the canonicalSeachName
	 */
	public String getCanonicalSeachName() {
		return canonicalSeachName;
	}

	/**
	 * @param canonicalSeachName
	 *            the canonicalSeachName to set
	 */
	public void setCanonicalSeachName(String canonicalSeachName) {
		this.canonicalSeachName = canonicalSeachName;
	}

	/**
	 * @return the iataCode
	 */
	public String getIataCode() {
		return iataCode;
	}

	/**
	 * @param iataCode
	 *            the iataCode to set
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
	 * @param address
	 *            the address to set
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
	 * @param tel
	 *            the tel to set
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
	 * @param email
	 *            the email to set
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
	 * @param logoId
	 *            the logoId to set
	 */
	public void setLogoId(String logoId) {
		this.logoId = logoId;
	}

	/**
	 * @return the branch
	 */
	public Boolean getBranch() {
		return branch;
	}

	/**
	 * @param branch
	 *            the branch to set
	 */
	public void setBranch(Boolean branch) {
		this.branch = branch;
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
	 * @return the nodeCode
	 */
	public String getNodeCode() {
		return nodeCode;
	}

	/**
	 * @param nodeCode
	 *            the nodeCode to set
	 */
	public void setNodeCode(String nodeCode) {
		this.nodeCode = nodeCode;
	}

	/**
	 * @return the nodeId
	 */
	public Long getNodeId() {
		return nodeId;
	}

	/**
	 * @param nodeId the nodeId to set
	 */
	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks
	 *            the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the tags
	 */
	public List<String> getTags() {
		return tags;
	}

	/**
	 * @param tags the tags to set
	 */
	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	/**
	 * @return the parent
	 */
	public SampleVO getParent() {
		return parent;
	}

	/**
	 * @param parent
	 *            the parent to set
	 */
	public void setParent(SampleVO parent) {
		this.parent = parent;
	}

	/**
	 * @return the children
	 */
	public List<SampleVO> getChildren() {
		return children;
	}

	/**
	 * @param children
	 *            the children to set
	 */
	public void setChildren(List<SampleVO> children) {
		this.children = children;
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
	 * @return the personOrganizationId
	 */
	public Long getPersonOrganizationId() {
		return personOrganizationId;
	}

	/**
	 * @param personOrganizationId the personOrganizationId to set
	 */
	public void setPersonOrganizationId(Long personOrganizationId) {
		this.personOrganizationId = personOrganizationId;
	}

	/**
	 * @return the cityShortCode
	 */
	public String getCityShortCode() {
		return cityShortCode;
	}

	/**
	 * @param cityShortCode the cityShortCode to set
	 */
	public void setCityShortCode(String cityShortCode) {
		this.cityShortCode = cityShortCode;
	}

	/**
	 * 
	 * @return
	 */
	public GeneralAgentMsg toMsg() {

		GeneralAgentMsg msg = new GeneralAgentMsg();

		msg.setId( getId() );
		msg.setName(name);
		msg.setIataCode(iataCode);
		msg.setAddress(address);
		msg.setTel(tel);
		msg.setEmail(email);
		msg.setLogoId(logoId);
		msg.setBranch(branch);
		msg.setRemarks(remarks);
		msg.setActive(active);
		msg.setNodeCode(nodeCode);
		//msg.setNodeName(nodeName);//TODO
		msg.setNodeId(nodeId);
		msg.setPersonOrganizationId(personOrganizationId);
		msg.setCityShortCode(cityShortCode);
		
		if (Validator.isNotNull( getParent() )) {
			msg.setParentId( parent.getId() );
		}

		if (Validator.isNotNull( children )) {
			Set<Long> childrenIds = new HashSet<Long>();
			for (SampleVO agentVO : children) {
				childrenIds.add(agentVO.getId());
			}
			msg.setChildrenIds(childrenIds);
		}
		
		return msg;
	}

}
