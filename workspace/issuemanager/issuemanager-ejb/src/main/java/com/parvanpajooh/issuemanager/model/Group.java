package com.parvanpajooh.issuemanager.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import com.parvanpajooh.commons.platform.ejb.model.BaseModel;
import com.parvanpajooh.commons.platform.ejb.model.vo.BaseVO;
import com.parvanpajooh.issuemanager.model.vo.GroupVO;

/**
 * @author 
 *
 */
@Entity
@XmlRootElement
@Table(name="group_tbl")
public class Group extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4036378126742804100L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Column(unique = true) 
	private String name;
	
	private boolean active;
	
	@NotNull
	@NotEmpty
	@Lob
	@Column(length = 10000)
	private String description;

	@Version
    private Integer version;
	
	@Override
	public GroupVO toVOLite() {
		GroupVO vo = new GroupVO();
		
		vo.setId(this.id);
		vo.setName(this.name);
		vo.setDescription(this.description);
		vo.setVersion(this.version);
		
		setAuditToVO(vo);
		
		return vo;
	}
	
	@Override
	public GroupVO toVO () {
		
		GroupVO groupVO = (GroupVO) toVOLite();

		return groupVO;
	}

	@Override
	public void fromVO(BaseVO baseVO) {
		
		GroupVO vo = (GroupVO) baseVO;
		
		this.id = vo.getId();
		this.name = vo.getName();
		this.description = vo.getDescription();
		this.version = vo.getVersion();

		getAuditFromVO(vo);
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}	
	
	
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
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
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" 
				+ name + ", description=" + description + ", version=" + version +"]";
	}

	/**
     * {@inheritDoc}
     */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
            return true;
        }
        if (!(o instanceof Group)) {
            return false;
        }

        final Group obj = (Group) o;

        return new EqualsBuilder()
    			.append(this.id, obj.getId())
    			.isEquals();
	}

	/**
     * {@inheritDoc}
     */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(-970989579, 1884842917)
			.append(this.id)
			.toHashCode();
	}

}
