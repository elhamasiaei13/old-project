package com.parvanpajooh.complaintmanagement.domain.model.criteria;

import com.google.common.base.MoreObjects;
import com.parvanpajooh.commons.platform.ejb.ddd.domain.model.dto.BaseCriteria;
import com.parvanpajooh.complaintmanagement.domain.model.dto.ComplaintCategoryDto;
import com.parvanpajooh.complaintmanagement.domain.model.enums.ComplaintStatus;

public class ComplaintCriteria extends BaseCriteria {

	private static final long serialVersionUID = 1L;

	private Integer version;

	private String userName;

	private String userUuid;

	private String firstName;

	private String lastName;

	private String email;

	private String tel;

	private String subject;

	private String attachment;

	private String result;

	private ComplaintStatus status;

	private String description;
	
	private String term;

	private ComplaintCategoryDto categoryDto;

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserUuid() {
		return userUuid;
	}

	public void setUserUuid(String userUuid) {
		this.userUuid = userUuid;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public ComplaintStatus getStatus() {
		return status;
	}

	public void setStatus(ComplaintStatus status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public ComplaintCategoryDto getCategoryDto() {
		return categoryDto;
	}

	public void setCategoryDto(ComplaintCategoryDto categoryDto) {
		this.categoryDto = categoryDto;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("version", version).add("userName", userName).add("userUuid", userUuid)
				.add("firstName", firstName).add("lastName", lastName).add("email", email).add("tel", tel)
				.add("subject", subject).add("attachment", attachment).add("status", status)
				.add("description", description).toString();
	}

}
