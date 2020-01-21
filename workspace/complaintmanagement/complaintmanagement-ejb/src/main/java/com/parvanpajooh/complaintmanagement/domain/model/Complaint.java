package com.parvanpajooh.complaintmanagement.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.parvanpajooh.commons.platform.ejb.ddd.domain.model.BaseModel;
import com.parvanpajooh.commons.platform.ejb.ddd.domain.model.dto.BaseDto;
import com.parvanpajooh.commons.util.Validator;
import com.parvanpajooh.complaintmanagement.domain.model.dto.ComplaintDto;
import com.parvanpajooh.complaintmanagement.domain.model.enums.ComplaintStatus;

@Entity
@Table(name = "complaint_tbl")
public class Complaint extends BaseModel {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Version
	private Integer version;

	private String userName;

	private String userUuid;

	private String firstName;

	private String lastName;

	private String email;

	private String tel;

	private String subject;

	private String attachment;
	
	@Column(length = 1024)
	private String result;

	private ComplaintStatus status;

	@Column(length = 1024)
	private String description;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "complaintCategoryId", referencedColumnName = "id")
	private ComplaintCategory category;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	@Override
	public ComplaintDto toDto() {
		ComplaintDto dto = toDtoLite();

		return dto;
	}

	public ComplaintCategory getCategory() {
		return category;
	}

	public void setCategory(ComplaintCategory category) {
		this.category = category;
	}

	@Override
	public ComplaintDto toDtoLite() {
		ComplaintDto dto = new ComplaintDto();

		dto.setId(this.id);
		dto.setVersion(this.version);
		dto.setUserName(this.userName);
		dto.setUserUuid(this.userUuid);
		dto.setFirstName(this.firstName);
		dto.setLastName(this.lastName);
		dto.setEmail(this.email);
		dto.setTel(this.tel);
		dto.setSubject(this.subject);
		dto.setAttachment(this.attachment);
		dto.setStatus(this.status);
		dto.setDescription(this.description);
		dto.setResult(this.result);

		if (Validator.isNotNull(category)) {
			dto.setCategory(category.toDtoLite());
		}

		setAuditToDto(dto);

		return dto;
	}

	@Override
	public void fromDto(BaseDto baseDto) {
		ComplaintDto complaintDto = (ComplaintDto) baseDto;

		this.id = complaintDto.getId();
		this.version = complaintDto.getVersion();
		this.userName = complaintDto.getUserName();
		this.userUuid = complaintDto.getUserUuid();
		this.firstName = complaintDto.getFirstName();
		this.lastName = complaintDto.getLastName();
		this.email = complaintDto.getEmail();
		this.tel = complaintDto.getTel();
		this.subject = complaintDto.getSubject();
		this.attachment = complaintDto.getAttachment();
		this.status = complaintDto.getStatus();
		this.description = complaintDto.getDescription();
		this.result = complaintDto.getResult();

		getAuditFromDto(complaintDto);
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("id", id).add("version", version).add("userName", userName)
				.add("userUuid", userUuid).add("firstName", firstName).add("lastName", lastName).add("email", email)
				.add("tel", tel).add("subject", subject).add("attachment", attachment).add("status", status)
				.add("description", description).add("result", result).toString();
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Complaint) {
			Complaint that = (Complaint) o;
			return Objects.equal(this.id, that.id);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

}
