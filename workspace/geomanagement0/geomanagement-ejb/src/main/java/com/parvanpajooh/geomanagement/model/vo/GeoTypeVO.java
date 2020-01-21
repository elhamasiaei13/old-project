/**
 * 
 */
package com.parvanpajooh.geomanagement.model.vo;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.MoreObjects;
import com.parvanpajooh.client.geomanagement.model.GeoTypeMsg;
import com.parvanpajooh.commons.platform.ejb.model.vo.BaseVO;

/**
 * @author mohammad sharifi
 * @author ali
 *
 */
public class GeoTypeVO extends BaseVO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2866317634959473614L;
	
	private String code;
	
	private String nameFa;
	
	private String nameEn;
	
	private List<GeoTypeVO> possibleChilds;
	
	private GeoTypeVO parentType;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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
	 * @return the possibleChilds
	 */
	public List<GeoTypeVO> getPossibleChilds() {
		return possibleChilds;
	}

	/**
	 * @param possibleChilds the possibleChilds to set
	 */
	public void setPossibleChilds(List<GeoTypeVO> possibleChilds) {
		this.possibleChilds = possibleChilds;
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
	 * @return
	 */
	public GeoTypeMsg toMsg() {
		GeoTypeMsg msg = new GeoTypeMsg();

		msg.setId(this.getId());
		msg.setCode(this.code);
		msg.setNameFa(this.nameFa);
		msg.setNameEn(this.nameEn);
		
		if (this.possibleChilds != null) {
			List<GeoTypeMsg> msgs = new ArrayList<>(possibleChilds.size());
			for (GeoTypeVO thisVO : possibleChilds) {
				msgs.add(thisVO.toMsg());
			}
			msg.setPossibleChilds(msgs);
		}

		if (this.parentType != null) {
			msg.setParentType(this.parentType.toMsg());
		}

		return msg;
	}
	
	/**
	 * 
	 * @param msg
	 * @return
	 */
	public static GeoTypeVO fromMsg(GeoTypeMsg msg) {
		GeoTypeVO vo = new GeoTypeVO();

		vo.setId(msg.getId());
		vo.setCode(msg.getCode());
		vo.setNameFa(msg.getNameFa());
		vo.setNameEn(msg.getNameEn());
		
		if (msg.getPossibleChilds() != null) {
			List<GeoTypeVO> vos = new ArrayList<>(msg.getPossibleChilds().size());
			for (GeoTypeMsg thisMsg : msg.getPossibleChilds()) {
				vos.add(GeoTypeVO.fromMsg(thisMsg));
			}
			
			vo.setPossibleChilds(vos);
		}

		if (msg.getParentType() != null) {
			vo.setParentType(GeoTypeVO.fromMsg(msg.getParentType()));
		}


		return vo;
	}


	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("id", getId())
			.add("code", code)
			.add("nameFa", nameFa)
			.add("nameEn", nameEn)
			.add("possibleChilds", possibleChilds)
			.add("parentType", parentType)
			.toString();
	}
	
}
