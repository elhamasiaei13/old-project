/**
 * 
 */
package com.parvanpajooh.geomanagement.model.vo;

import java.io.Serializable;

import com.parvanpajooh.client.routemanagement.model.CityInfoMsg;

/**
 * @author mehrdad
 *
 */
public class CityInfoVO implements Serializable {
	
	private static final long serialVersionUID = -3609228886938600941L;
	
	private String shortCode;
	private String nameEn;
	private String nameFa;
	private String provinceShortCode;
	private String provinceNameEn;
	private String provinceNameFa;
	private String countryShortCode;
	private String countryNameEn;
	private String countryNameFa;
	
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
	 * @return the provinceShortCode
	 */
	public String getProvinceShortCode() {
		return provinceShortCode;
	}

	/**
	 * @param provinceShortCode the provinceShortCode to set
	 */
	public void setProvinceShortCode(String provinceShortCode) {
		this.provinceShortCode = provinceShortCode;
	}

	/**
	 * @return the provinceNameEn
	 */
	public String getProvinceNameEn() {
		return provinceNameEn;
	}

	/**
	 * @param provinceNameEn the provinceNameEn to set
	 */
	public void setProvinceNameEn(String provinceNameEn) {
		this.provinceNameEn = provinceNameEn;
	}

	/**
	 * @return the provinceNameFa
	 */
	public String getProvinceNameFa() {
		return provinceNameFa;
	}

	/**
	 * @param provinceNameFa the provinceNameFa to set
	 */
	public void setProvinceNameFa(String provinceNameFa) {
		this.provinceNameFa = provinceNameFa;
	}

	/**
	 * @return the countryShortCode
	 */
	public String getCountryShortCode() {
		return countryShortCode;
	}

	/**
	 * @param countryShortCode the countryShortCode to set
	 */
	public void setCountryShortCode(String countryShortCode) {
		this.countryShortCode = countryShortCode;
	}

	/**
	 * @return the countryNameEn
	 */
	public String getCountryNameEn() {
		return countryNameEn;
	}

	/**
	 * @param countryNameEn the countryNameEn to set
	 */
	public void setCountryNameEn(String countryNameEn) {
		this.countryNameEn = countryNameEn;
	}

	/**
	 * @return the countryNameFa
	 */
	public String getCountryNameFa() {
		return countryNameFa;
	}

	/**
	 * @param countryNameFa the countryNameFa to set
	 */
	public void setCountryNameFa(String countryNameFa) {
		this.countryNameFa = countryNameFa;
	}

	/**
	 * 
	 * @param cityInfoMsg
	 */
	public void fromMsg(CityInfoMsg cityInfoMsg) {
		this.shortCode = cityInfoMsg.getShortCode();
		this.nameEn = cityInfoMsg.getNameEn();
		this.nameFa = cityInfoMsg.getNameFa();
		this.provinceShortCode = cityInfoMsg.getProvinceShortCode();
		this.provinceNameEn = cityInfoMsg.getProvinceNameEn();
		this.provinceNameFa = cityInfoMsg.getProvinceNameFa();
		this.countryShortCode = cityInfoMsg.getCountryShortCode();
		this.countryNameEn = cityInfoMsg.getCountryNameEn();
		this.countryNameFa = cityInfoMsg.getCountryNameFa();
	}
	
	/**
	 * 
	 * @param vo
	 * @return
	 */
	public CityInfoMsg toMsg(GeoEntityVO vo) {
		CityInfoMsg msg = new CityInfoMsg();
		
		GeoEntityVO province = vo.getParent();
		GeoEntityVO country = province.getParent();
		msg.setShortCode(vo.getShortCode());
		msg.setNameEn(vo.getNameEn());
		msg.setNameFa(vo.getNameFa());
		msg.setProvinceShortCode(province.getShortCode());
		msg.setProvinceNameEn(province.getNameEn());
		msg.setProvinceNameFa(province.getNameFa());
		msg.setCountryShortCode(country.getShortCode());
		msg.setCountryNameEn(country.getNameEn());
		msg.setCountryNameFa(country.getNameFa());
		
		return msg;
	}
}
