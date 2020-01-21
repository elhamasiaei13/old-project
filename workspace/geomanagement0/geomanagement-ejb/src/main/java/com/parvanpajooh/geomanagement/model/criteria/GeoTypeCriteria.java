package com.parvanpajooh.geomanagement.model.criteria;

import com.google.common.base.MoreObjects;
import com.parvanpajooh.client.geomanagement.model.criteria.GeoTypeCriteriaMsg;
import com.parvanpajooh.commons.platform.ejb.model.vo.BaseCriteria;
import com.parvanpajooh.geomanagement.model.vo.GeoTypeVO;

public class GeoTypeCriteria extends BaseCriteria {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8000124586892926771L;

	private String code;
	
	private String nameFa;
	
	private String nameEn;
	
	private String allNames;
	
	private GeoTypeVO possibleChild;
	
	private GeoTypeVO parentType;

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the allNames
	 */
	public String getAllNames() {
		return allNames;
	}

	/**
	 * @param allNames the allNames to set
	 */
	public void setAllNames(String allNames) {
		this.allNames = allNames;
	}

	/**
	 * @return the nameFa
	 */
	public String getNameFa() {
		return nameFa;
	}

	/**
	 * @param nameFa the nameFa to set
	 */
	public void setNameFa(String nameFa) {
		this.nameFa = nameFa;
	}

	/**
	 * @return the nameEn
	 */
	public String getNameEn() {
		return nameEn;
	}

	/**
	 * @param nameEn the nameEn to set
	 */
	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	/**
	 * @return the possibleChild
	 */
	public GeoTypeVO getPossibleChild() {
		return possibleChild;
	}

	/**
	 * @param possibleChild the possibleChild to set
	 */
	public void setPossibleChild(GeoTypeVO possibleChild) {
		this.possibleChild = possibleChild;
	}

	/**
	 * @return the parentType
	 */
	public GeoTypeVO getParentType() {
		return parentType;
	}

	/**
	 * @param parentType the parentType to set
	 */
	public void setParentType(GeoTypeVO parentType) {
		this.parentType = parentType;
	}
	
	/**
	 * 
	 * @param msg
	 * @return
	 */
	public static GeoTypeCriteria fromMsg(GeoTypeCriteriaMsg msg) {
		GeoTypeCriteria vo = new GeoTypeCriteria();

		vo.setCode(msg.getCode());
		vo.setNameFa(msg.getNameFa());
		vo.setNameEn(msg.getNameEn());
		vo.setAllNames(msg.getAllNames());
		
		if (msg.getPossibleChild() != null) {
			vo.setPossibleChild(GeoTypeVO.fromMsg(msg.getPossibleChild()));
		}

		if (msg.getParentType() != null) {
			vo.setParentType(GeoTypeVO.fromMsg(msg.getParentType()));
		}


		return vo;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("code", code)
			.add("nameFa", nameFa)
			.add("nameEn", nameEn)
			.add("allNames", allNames)
			.add("possibleChild", possibleChild)
			.add("parentType", parentType)
			.toString();
	}

}
