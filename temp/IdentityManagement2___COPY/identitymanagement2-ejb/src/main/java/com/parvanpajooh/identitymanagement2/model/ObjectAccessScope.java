package com.parvanpajooh.identitymanagement2.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import com.parvanpajooh.common.util.Validator;
import com.parvanpajooh.common.vo.BaseVO;
import com.parvanpajooh.ecourier.model.BaseModel;
import com.parvanpajooh.identitymanagement2.model.vo.ObjectAccessScopeVO;
import com.parvanpajooh.identitymanagement2.model.vo.PermissionVO;
import com.parvanpajooh.identitymanagement2.model.vo.UserVO;

import javassist.tools.reflect.Sample;

/**
 * @author moosa
 *
 */
@Entity
@XmlRootElement
@Table(name = "objectaccessscope_tbl")
public class ObjectAccessScope extends BaseModel {

	/**
	 *
	 */
	private static final long serialVersionUID = -4036378126742804100L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private boolean active;

	private String code;

	private String name;

	@NotNull
	@NotEmpty
	@Lob
	@Column(length = 10000)
	private String description;

	@Version
	private Integer version;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "objectAccessScope")
	private Set<Permission> permission;

	@Override
	public ObjectAccessScopeVO toVOLite() {
		ObjectAccessScopeVO vo = new ObjectAccessScopeVO();

		vo.setId(this.id);
		vo.setCode(this.code);
		vo.setName(this.name);
		vo.setDescription(this.description);

		setAuditToVO(vo);

		return vo;
	}

	@Override
	public ObjectAccessScopeVO toVO() {

		ObjectAccessScopeVO objectAccessScopeVO = (ObjectAccessScopeVO) toVOLite();

		if (Validator.isNotNull(this.permission)) {

			List<PermissionVO> list = new ArrayList<PermissionVO>();

			for (Permission permission : this.permission) {
				list.add(permission.toVOLite());
			}
			objectAccessScopeVO.setPermission(list);
		}

		return objectAccessScopeVO;
	}

	@Override
	public void fromVO(BaseVO baseVO) {

		ObjectAccessScopeVO vo = (ObjectAccessScopeVO) baseVO;

		this.id = vo.getId();
		this.code=vo.getCode();
		this.name=vo.getName();
		this.description=vo.getDescription();

		getAuditFromVO(vo);
	}

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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
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
	 * @param description
	 *            the description to set
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
	 * @param version
	 *            the version to set
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}

	/**
	 * @return the permission
	 */
	public Set<Permission> getPermission() {
		return permission;
	}

	/**
	 * @param permission
	 *            the permission to set
	 */
	public void setPermission(Set<Permission> permission) {
		this.permission = permission;
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
		if (!(o instanceof ObjectAccessScope)) {
			return false;
		}

		final ObjectAccessScope obj = (ObjectAccessScope) o;

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
