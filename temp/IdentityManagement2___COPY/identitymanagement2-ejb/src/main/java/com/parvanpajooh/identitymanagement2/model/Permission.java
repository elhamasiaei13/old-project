package com.parvanpajooh.identitymanagement2.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.parvanpajooh.common.util.Validator;
import com.parvanpajooh.common.vo.BaseVO;
import com.parvanpajooh.ecourier.model.BaseModel;
import com.parvanpajooh.identitymanagement2.model.vo.ObjectAccessScopeVO;
import com.parvanpajooh.identitymanagement2.model.vo.PermissionVO;
import com.parvanpajooh.identitymanagement2.model.vo.RoleVO;
import com.parvanpajooh.identitymanagement2.model.vo.UserVO;

/**
 * @author moosa
 *
 */
@Entity
@XmlRootElement
@Table(name = "permission_tbl")
public class Permission extends BaseModel {

	/**
	 *
	 */
	private static final long serialVersionUID = -4036378126742804100L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private boolean active;

	private String resourceUri;

	private Long bitwiseValue;

	private String constraintRules;

	@Version
	private Integer version;

	@ManyToOne
	@JoinColumn(name = "roleId", referencedColumnName = "id")
	private Role role;

	@ManyToOne
	@JoinColumn(name = "objectAccessScopeId", referencedColumnName = "id")
	private ObjectAccessScope objectAccessScope;

	@Override
	public PermissionVO toVOLite() {
		PermissionVO vo = new PermissionVO();

		vo.setId(this.id);
		vo.setResourceUri(this.resourceUri);
		vo.setBitwiseValue(this.bitwiseValue);
		vo.setConstraintRules(this.constraintRules);

		if (Validator.isNotNull(this.role)) {

			RoleVO roleVO = this.role.toVOLite();
			vo.setRole(roleVO);
		}

		if (Validator.isNotNull(this.objectAccessScope)) {

			ObjectAccessScopeVO objectAccessScopVO = this.objectAccessScope.toVOLite();
			vo.setObjectAccessScope(objectAccessScopVO);
		}

		setAuditToVO(vo);

		return vo;
	}

	@Override
	public PermissionVO toVO() {

		PermissionVO permissionVO = (PermissionVO) toVOLite();

		return permissionVO;
	}

	@Override
	public void fromVO(BaseVO baseVO) {

		PermissionVO vo = (PermissionVO) baseVO;

		this.id = vo.getId();
		this.resourceUri = vo.getResourceUri();
		this.bitwiseValue = vo.getBitwiseValue();
		this.constraintRules = vo.getConstraintRules();

		getAuditFromVO(vo);
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active
	 *            the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * @return the resourceUri
	 */
	public String getResourceUri() {
		return resourceUri;
	}

	/**
	 * @param resourceUri
	 *            the resourceUri to set
	 */
	public void setResourceUri(String resourceUri) {
		this.resourceUri = resourceUri;
	}

	/**
	 * @return the bitwiseValue
	 */
	public Long getBitwiseValue() {
		return bitwiseValue;
	}

	/**
	 * @param bitwiseValue
	 *            the bitwiseValue to set
	 */
	public void setBitwiseValue(Long bitwiseValue) {
		this.bitwiseValue = bitwiseValue;
	}

	/**
	 * @return the constraintRules
	 */
	public String getConstraintRules() {
		return constraintRules;
	}

	/**
	 * @param constraintRules
	 *            the constraintRules to set
	 */
	public void setConstraintRules(String constraintRules) {
		this.constraintRules = constraintRules;
	}

	/**
	 * @return the version
	 */
	public Integer getVersion() {
		return version;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Permission [id=" + id + ", version=" + version + "]";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Permission)) {
			return false;
		}

		final Permission obj = (Permission) o;

		return new EqualsBuilder().append(this.id, obj.getId()).isEquals();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(-970989579, 1884842917).append(this.id).toHashCode();
	}

}
