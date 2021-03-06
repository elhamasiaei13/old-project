package com.parvanpajooh.complaintmanagement.domain.model.criteria;

import com.parvanpajooh.commons.platform.ejb.ddd.domain.model.dto.BaseCriteria;
import com.parvanpajooh.complaintmanagement.domain.model.dto.ComplaintCategoryDto;

public class ComplaintCategoryCriteria extends BaseCriteria {

	private static final long serialVersionUID = 1L;

	private String code;

	private String nameFa;

	private String nameEn;

	private Boolean active;

	private ComplaintCategoryDto parentCategoryDto;

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

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
	
	public ComplaintCategoryDto getParentCategoryDto() {
		return parentCategoryDto;
	}

	public void setParentCategoryDto(ComplaintCategoryDto parentCategoryDto) {
		this.parentCategoryDto = parentCategoryDto;
	}

	@Override
	public String toString() {
		return "CompliantCategory [code=" + code + ", nameFa=" + nameFa + ", nameEn=" + nameEn + ", active=" + active
				+ "]";
	}

}
