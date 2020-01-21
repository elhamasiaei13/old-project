/**
 * 
 */
package com.parvanpajooh.geomanagement.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.google.common.base.Objects;
import com.parvanpajooh.commons.platform.ejb.model.BaseModel;
import com.parvanpajooh.commons.platform.ejb.model.vo.BaseVO;
import com.parvanpajooh.commons.util.Validator;
import com.parvanpajooh.geomanagement.model.vo.GeoEntityVO;
import com.parvanpajooh.geomanagement.model.vo.GeoTypeVO;

/**
 * @author Mohammad
 * @author ali
 * 
 */
@Entity
@Table(name = "geoEntity_tbl")
public class GeoEntity extends BaseModel {


	/**
	 * 
	 */
	private static final long serialVersionUID = -5081767878290114185L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 12)
	private String shortCode;

	@NotNull
	@NotEmpty
	@Column(unique=true)
	private String code;
	
	@NotNull
	@NotEmpty
	private String nameFa;
	
	@NotNull
	@NotEmpty
	private String nameEn;
	
	private String nameOther;
	
	@NotNull
	@NotEmpty
	private String sortField;
	
	@NotNull
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "type")
	private GeoType type;

	@ManyToOne
	@JoinColumn(name = "parentId", referencedColumnName = "id")
	private GeoEntity parent;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
	private Set<GeoEntity> children;
	
	private Double geoLat;
	
	private Double geoLng;
	
	private String zoneId;
	
	@Version
    private Integer version;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
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
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
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
	 * @return the type
	 */
	public GeoType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(GeoType type) {
		this.type = type;
	}

	/**
	 * @return the parent
	 */
	public GeoEntity getParent() {
		return parent;
	}

	/**
	 * @param parent
	 *            the parent to set
	 */
	public void setParent(GeoEntity parent) {
		this.parent = parent;
	}

	/**
	 * @return the children
	 */
	public Set<GeoEntity> getChildren() {
		return children;
	}

	/**
	 * @param children the children to set
	 */
	public void setChildren(Set<GeoEntity> children) {
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

	@Override
	public GeoEntityVO toVO() {
		GeoEntityVO vo = toVOLite();
		
		if( Validator.isNotNull( this.children ) ) {
			
			List<GeoEntityVO> list = new ArrayList<GeoEntityVO>();
			
			for (GeoEntity geoEntity : this.children) {
				list.add(geoEntity.toVOLite());
			}
			vo.setChildren(list);
		}
		
		return vo;
	}

	@Override
	public GeoEntityVO toVOLite() {
		GeoEntityVO vo = new GeoEntityVO();
		
		vo.setId(id);
		vo.setShortCode(shortCode);
		vo.setCode(code);
		vo.setNameFa(nameFa);
		vo.setNameEn(nameEn);
		vo.setNameOther(nameOther);
		vo.setSortField(sortField);
		vo.setGeoLat(geoLat);
		vo.setGeoLng(geoLng);
		vo.setZoneId(zoneId);
		vo.setVersion(version);
		
		if( Validator.isNotNull( this.type ) ) {
			
			GeoTypeVO geoTypeVO = this.type.toVOLite();
			vo.setType(geoTypeVO);
		}
		
		if( Validator.isNotNull( this.parent ) ) {
			
			GeoEntityVO geoEntityVO = this.parent.toVOLite();
			vo.setParent(geoEntityVO);
		}
		
		setAuditToVO(vo);
		
		return vo;
	}

	@Override
	public void fromVO(BaseVO baseVO) {
		GeoEntityVO vo = ( GeoEntityVO ) baseVO;
		
		this.id = vo.getId();
		this.code = vo.getCode();
		this.shortCode = vo.getShortCode();
		this.nameFa = vo.getNameFa();
		this.nameEn = vo.getNameEn();
		this.nameOther = vo.getNameOther();
		this.geoLat = vo.getGeoLat();
		this.geoLng = vo.getGeoLng();
		this.zoneId = vo.getZoneId();
		this.version = vo.getVersion();
		//this.sortField = vo.getSortField();
		//this.type = vo.getType();
		//this.geoLocations = vo.getGeoLocations();
		getAuditFromVO(vo);
		
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GeoEntity [id=" + id + ", shortCode=" + shortCode + ", code=" + code + ", nameFa=" + nameFa
				+ ", nameEn=" + nameEn + ", nameOther=" + nameOther + ", sortField=" + sortField +
				", type=" + type + ", geoLat=" + geoLat + ", geoLng=" + geoLng + ", zoneId=" + zoneId
				+ ", version=" + version + "]";
	}

	@Override
	public int hashCode(){
		return Objects.hashCode(id);
	}
	
	@Override
	public boolean equals(Object object){
		if (object instanceof GeoEntity) {
			GeoEntity that = (GeoEntity) object;
			return Objects.equal(this.id, that.id);
		}
		return false;
	}
	
    protected void setAuditToVO(BaseVO vo){
    	vo.setTenantId(null);
		vo.setCreateDate(getCreateDate());
		vo.setUpdateDate(getUpdateDate());
		vo.setCreateUserId(getCreateUserId());
		vo.setUpdateUserId(getUpdateUserId());
    }
    
    protected void getAuditFromVO(BaseVO vo){
    	setTenantId(null);
		setCreateDate(vo.getCreateDate());
		setUpdateDate(vo.getUpdateDate());
		setCreateUserId(vo.getCreateUserId());
		setUpdateUserId(vo.getUpdateUserId());
    }

}
