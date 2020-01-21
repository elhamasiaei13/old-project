package com.parvanpajooh.geomanagement.model.criteria;

import com.google.common.base.MoreObjects;
import com.parvanpajooh.client.geomanagement.model.criteria.GeoEntityCriteriaMsg;
import com.parvanpajooh.commons.platform.ejb.model.vo.BaseCriteria;
import com.parvanpajooh.geomanagement.model.vo.GeoEntityVO;
import com.parvanpajooh.geomanagement.model.vo.GeoTypeVO;

public class GeoEntityCriteria extends BaseCriteria {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8000124586892926771L;

	private String shortCode;
	
	private String nameFa;
	
	private String nameEn;
	
	private String nameOther;
	
	private String allNames;
	
	private GeoTypeVO type;
	
	private String possibleChildCode;

	private GeoEntityVO parent;

	private GeoEntityVO children;

	/**
	 * @return the shortCode
	 */
	public String getShortCode() {
		return shortCode;
	}

	/**
	 * @param shortCode the shortCode to set
	 */
	public void setShortCode(String shortCode) {
		this.shortCode = shortCode;
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
	 * @return the parent
	 */
	public GeoEntityVO getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(GeoEntityVO parent) {
		this.parent = parent;
	}

	/**
	 * @return the children
	 */
	public GeoEntityVO getChildren() {
		return children;
	}

	/**
	 * @param children the children to set
	 */
	public void setChildren(GeoEntityVO children) {
		this.children = children;
	}

	/**
	 * @return the nameOther
	 */
	public String getNameOther() {
		return nameOther;
	}

	/**
	 * @param nameOther the nameOther to set
	 */
	public void setNameOther(String nameOther) {
		this.nameOther = nameOther;
	}

	/**
	 * @return the type
	 */
	public GeoTypeVO getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(GeoTypeVO type) {
		this.type = type;
	}

	/**
	 * @return the possibleChildCode
	 */
	public String getPossibleChildCode() {
		return possibleChildCode;
	}

	/**
	 * @param possibleChildCode the possibleChildCode to set
	 */
	public void setPossibleChildCode(String possibleChildCode) {
		this.possibleChildCode = possibleChildCode;
	}

	/**
	 * 
	 * @param msg
	 * @return
	 */
	public static GeoEntityCriteria fromMsg(GeoEntityCriteriaMsg msg) {
		GeoEntityCriteria vo = new GeoEntityCriteria();

		vo.setShortCode(msg.getShortCode());
		vo.setNameFa(msg.getNameFa());
		vo.setNameEn(msg.getNameEn());
		vo.setNameOther(msg.getNameOther());
		vo.setAllNames(msg.getAllNames());

		if (msg.getType() != null) {
			vo.setType(GeoTypeVO.fromMsg(msg.getType()));
		}

		vo.setPossibleChildCode(msg.getPossibleChildCode());
		if (msg.getParent() != null) {
			vo.setParent(GeoEntityVO.fromMsg(msg.getParent()));
		}

		if (msg.getChildren() != null) {
			vo.setChildren(GeoEntityVO.fromMsg(msg.getChildren()));
		}

		return vo;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("shortCode", shortCode)
			.add("nameFa", nameFa)
			.add("nameEn", nameEn)
			.add("nameOther", nameOther)
			.add("type", type)
			.add("possibleChildCode", possibleChildCode)
			.add("parent", parent)
			.add("children", children)
			.toString();
	}

}
