package com.parvanpajooh.complaintmanagement.domain.model.dto;

import com.parvanpajooh.client.complaintmanagement.model.ComplaintCategoryMsg;
import com.parvanpajooh.commons.platform.ejb.ddd.domain.model.dto.BaseDto;

public class ComplaintCategoryDto extends BaseDto {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String code;

	private String nameFa;

	private String nameEn;

	private Integer version;

	private boolean active;

	private String description;

	private ComplaintCategoryDto parent;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getNameFa() {
		return nameFa;
	}

	public void setNameFa(String nameFa) {
		this.nameFa = nameFa;
	}

	public String getNameEn() {
		return nameEn;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public boolean getActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ComplaintCategoryDto getParent() {
		return parent;
	}

	public void setParent(ComplaintCategoryDto parent) {
		this.parent = parent;
	}

	/**
	 * 
	 * @param msg
	 */
	public void fromMsg(ComplaintCategoryMsg msg) {

		this.id = msg.getId();
		this.code = msg.getCode();
		this.nameFa = msg.getNameFa();
		this.nameEn = msg.getNameEn();
		this.description = msg.getDescription();

	}
	
	/**
	 * 
	 * @return
	 */
	public ComplaintCategoryMsg toMsg() {

		ComplaintCategoryMsg msg = new ComplaintCategoryMsg();

		msg.setId(getId());
		msg.setCode(code);
		msg.setNameFa(nameFa);
		msg.setNameEn(nameEn);
		msg.setDescription(description);
		
		return msg;
	}
	
	@Override
	public String toString() {
		return "CompliantCategory [id=" + id + ", code=" + code + ", nameFa=" + nameFa + ", nameEn=" + nameEn
				+ ", version=" + version + ", active=" + active + ", description=" + description + "]";
	}

}
