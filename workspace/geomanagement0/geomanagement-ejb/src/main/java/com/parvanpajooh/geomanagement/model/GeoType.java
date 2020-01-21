/**
 * 
 */
package com.parvanpajooh.geomanagement.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.parvanpajooh.commons.platform.ejb.model.BaseModel;
import com.parvanpajooh.commons.platform.ejb.model.vo.BaseVO;
import com.parvanpajooh.commons.util.Validator;
import com.parvanpajooh.geomanagement.model.vo.GeoTypeVO;

/**
 * @author Mohammad
 * @author ali
 * 
 */
@Entity
@Cacheable
@Table(name="geoType_tbl")
public class GeoType extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7287491680002073898L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String code;
	
	@NotNull
	private String nameFa;
	
	@NotNull
	private String nameEn;
	
	@ManyToOne
	@JoinColumn(name = "parentId", referencedColumnName = "id")
	private GeoType parentType;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parentType")
	private Set<GeoType> possibleChilds;
	
	@Version
    private Integer version;

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
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
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
	public Set<GeoType> getPossibleChilds() {
		return possibleChilds;
	}

	/**
	 * @param possibleChilds the possibleChilds to set
	 */
	public void setPossibleChilds(Set<GeoType> possibleChilds) {
		this.possibleChilds = possibleChilds;
	}

	/**
	 * @return the parentType
	 */
	public GeoType getParentType() {
		return parentType;
	}

	/**
	 * @param parentType the parentType to set
	 */
	public void setParentType(GeoType parentType) {
		this.parentType = parentType;
	}

	/**
	 * @return the version
	 */
	public Integer getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}

	@Override
	public GeoTypeVO toVO() {
		GeoTypeVO vo = toVOLite();
		
		List<GeoTypeVO> possibleChildVOs = new ArrayList<GeoTypeVO>();
		if( Validator.isNotNull(this.possibleChilds ) ) {
			
			for (GeoType geoType : possibleChilds) {
				possibleChildVOs.add(geoType.toVOLite());
			}
			
			vo.setPossibleChilds(possibleChildVOs);
		}
		
		if( Validator.isNotNull( this.parentType ) ) {
			
			GeoTypeVO geoTypeVO = this.parentType.toVOLite();
			vo.setParentType(geoTypeVO);
		}
		
		return vo;
	}

	@Override
	public GeoTypeVO toVOLite() {
		GeoTypeVO vo = new GeoTypeVO();
		
		vo.setId(id);
		vo.setCode(code);
		vo.setNameFa(nameFa);
		vo.setNameEn(nameEn);
		vo.setVersion(version);
		
		setAuditToVO(vo);
		
		return vo;
	}

	@Override
	public void fromVO(BaseVO baseVO) {
		GeoTypeVO vo = (GeoTypeVO) baseVO;
		
		this.id = vo.getId();
		this.code = vo.getCode();
		this.nameFa = vo.getNameFa();
		this.nameEn = vo.getNameEn();
		this.version = vo.getVersion();
		getAuditFromVO(vo);
		
	}

	@Override
	public int hashCode(){
		return Objects.hashCode(id);
	}
	
	@Override
	public boolean equals(Object object){
		if (object instanceof GeoType) {
			GeoType that = (GeoType) object;
			return Objects.equal(this.id, that.id);
		}
		return false;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("id", id)
			.add("code", code)
			.add("nameFa", nameFa)
			.add("nameEn", nameEn)
			.toString();
	}

}
