/**
 * 
 */
package com.parvanpajooh.geomanagement.model.vo;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.MoreObjects;
import com.parvanpajooh.client.geomanagement.model.CityInfoMsg;
import com.parvanpajooh.client.geomanagement.model.GeoEntityMsg;
import com.parvanpajooh.commons.platform.ejb.model.vo.BaseVO;
import com.parvanpajooh.commons.util.Validator;

/**
 * @author mohammad sharifi
 * @author ali
 *
 */
public class GeoEntityVO  extends BaseVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6488415887289844744L;

	private String code;
	
	private String shortCode;
	
	private String nameFa;
	
	private String nameEn;
	
	private String nameOther;
	
	private String sortField;

	private GeoTypeVO type;

	private GeoEntityVO parent;

	private List<GeoEntityVO> children;
	
	private Double geoLat;
	
	private Double geoLng;
	
	private String zoneId;

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
	 * @return the sortField
	 */
	public String getSortField() {
		return sortField;
	}

	/**
	 * @param sortField the sortField to set
	 */
	public void setSortField(String sortField) {
		this.sortField = sortField;
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
	public List<GeoEntityVO> getChildren() {
		return children;
	}

	/**
	 * @param children the children to set
	 */
	public void setChildren(List<GeoEntityVO> children) {
		this.children = children;
	}
	
	/**
	 * @return the geoLat
	 */
	public Double getGeoLat() {
		return geoLat;
	}

	/**
	 * @param geoLat the geoLat to set
	 */
	public void setGeoLat(Double geoLat) {
		this.geoLat = geoLat;
	}

	/**
	 * @return the geoLng
	 */
	public Double getGeoLng() {
		return geoLng;
	}

	/**
	 * @param geoLng the geoLng to set
	 */
	public void setGeoLng(Double geoLng) {
		this.geoLng = geoLng;
	}

	/**
	 * @return the zoneId
	 */
	public String getZoneId() {
		return zoneId;
	}

	/**
	 * @param zoneId the zoneId to set
	 */
	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}
	
	/**
	 * 
	 * @return
	 */
	public GeoEntityMsg toMsg() {
		GeoEntityMsg msg = new GeoEntityMsg();

		msg.setId(this.getId());
		msg.setCode(this.code);
		msg.setShortCode(this.shortCode);
		msg.setNameFa(this.nameFa);
		msg.setNameEn(this.nameEn);
		msg.setNameOther(this.nameOther);
		msg.setSortField(this.sortField);

		if (this.type != null) {
			msg.setType(this.type.toMsg());
		}

		if (this.parent != null) {
			msg.setParent(this.parent.toMsg());
		}

		if (this.children != null) {
			List<GeoEntityMsg> msgs = new ArrayList<>(children.size());
			for (GeoEntityVO thisVO : children) {
				msgs.add(thisVO.toMsg());
			}
			msg.setChildren(msgs);
		}

		msg.setGeoLat(this.geoLat);
		msg.setGeoLng(this.geoLng);
		msg.setZoneId(this.zoneId);

		return msg;
	}
	
	/**
	 * 
	 * @param msg
	 * @return
	 */
	public static GeoEntityVO fromMsg(GeoEntityMsg msg) {
		GeoEntityVO vo = new GeoEntityVO();

		vo.setId(msg.getId());
		vo.setCode(msg.getCode());
		vo.setShortCode(msg.getShortCode());
		vo.setNameFa(msg.getNameFa());
		vo.setNameEn(msg.getNameEn());
		vo.setNameOther(msg.getNameOther());
		vo.setSortField(msg.getSortField());
		
		/*if (msg.getGeoLocations() != null) {
			vo.setGeoLocations(geoLocations.fromMsg(msg.getGeoLocations()));
		}*/

		if (msg.getType() != null) {
			vo.setType(GeoTypeVO.fromMsg(msg.getType()));
		}

		if (msg.getParent() != null) {
			vo.setParent(GeoEntityVO.fromMsg(msg.getParent()));
		}

		/*if (msg.getChildren() != null) {
			vo.setChildren(children.fromMsg(msg.getChildren()));
		}*/

		vo.setGeoLat(msg.getGeoLat());
		vo.setGeoLng(msg.getGeoLng());
		vo.setZoneId(msg.getZoneId());

		return vo;
	}
	
	/**
	 * 
	 * @param vo
	 * @return
	 */
	public CityInfoMsg toCityInfoMsg() {
		CityInfoMsg msg = new CityInfoMsg();

		GeoEntityVO province = this.getParent();
		msg.setShortCode(this.getShortCode());
		msg.setNameEn(this.getNameEn());
		msg.setNameFa(this.getNameFa());

		if (Validator.isNotNull(province)) {
			GeoEntityVO country = province.getParent();
			msg.setProvinceShortCode(province.getShortCode());
			msg.setProvinceNameEn(province.getNameEn());
			msg.setProvinceNameFa(province.getNameFa());
			if (Validator.isNotNull(country)) {
				msg.setCountryShortCode(country.getShortCode());
				msg.setCountryNameEn(country.getNameEn());
				msg.setCountryNameFa(country.getNameFa());
			}
		}

		return msg;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("id", getId())
			.add("code", code)
			.add("shortCode", shortCode)
			.add("nameFa", nameFa)
			.add("nameEn", nameEn)
			.add("nameOther", nameOther)
			.add("sortField", sortField)
			.add("type", type)
			.add("parent", parent)
			.add("children", children)
			.add("geoLat", geoLat)
			.add("geoLng", geoLng)
			.add("zoneId", zoneId)
			.toString();
	}
}
