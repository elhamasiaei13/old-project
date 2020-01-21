package com.parvanpajooh.issuemanager.model;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
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

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.parvanpajooh.commons.platform.ejb.model.BaseModel;
import com.parvanpajooh.commons.platform.ejb.model.vo.BaseVO;
import com.parvanpajooh.commons.util.Validator;
import com.parvanpajooh.issuemanager.model.vo.MemberVO;

/**
 * @author moosa
 * 
 */
@Entity
@XmlRootElement
@Table(name = "member_tbl")
public class Member extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4036378126742804100L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@NotEmpty
	private String firstName;

	@NotNull
	@NotEmpty
	private String lastName;

	@NotNull
	@NotEmpty
	@Column(unique = true)
	private String username;

	@NotNull
	@NotEmpty
	private String password;
	
	@NotNull
	@NotEmpty
	private String roles;

	private boolean active;

	@Version
	private Integer version;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte[] fileImage;

	private String email;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "task")
	private Set<TaskMemberRelation> taskMemberRelations;
	
	@Override
	public MemberVO toVOLite() {
		MemberVO vo = new MemberVO();
		Encoder encoder = Base64.getEncoder();

		vo.setId(this.id);
		vo.setActive(this.active);
		vo.setVersion(this.version);
		vo.setFirstName(this.firstName);
		vo.setLastName(this.lastName);
		vo.setUsername(this.username);
		vo.setPassword(this.password);
		vo.setFileImage(this.fileImage);
		vo.setEmail(this.email);
		vo.setRoles(getRoles());
		
		if (Validator.isNotNull(this.fileImage)) {
			vo.setFileImageBase64(encoder.encodeToString(this.fileImage));
		}		

		setAuditToVO(vo);

		return vo;
	}

	@Override
	public MemberVO toVO() {

		MemberVO memberVO = (MemberVO) toVOLite();
		
		
		return memberVO;
	}

	@Override
	public void fromVO(BaseVO baseVO) {

		MemberVO vo = (MemberVO) baseVO;

		this.id = vo.getId();
		this.firstName = vo.getFirstName();
		this.lastName = vo.getLastName();
		this.username = vo.getUsername();
		this.password = vo.getPassword();
		this.fileImage = vo.getFileImage();
		this.email = vo.getEmail();
		setRoles(vo.getRoles());

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
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the fileImage
	 */
	public byte[] getFileImage() {
		return fileImage;
	}
	

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the taskMemberRelations
	 */
	public Set<TaskMemberRelation> getTaskMemberRelations() {
		return taskMemberRelations;
	}

	/**
	 * @param taskMemberRelations the taskMemberRelations to set
	 */
	public void setTaskMemberRelations(Set<TaskMemberRelation> taskMemberRelations) {
		this.taskMemberRelations = taskMemberRelations;
	}

	/**
	 * @param roles the roles to set
	 */
	public void setRoles(String roles) {
		this.roles = roles;
	}

	/**
	 * @param fileImage
	 *            the fileImage to set
	 */
	public void setFileImage(byte[] fileImage) {
		this.fileImage = fileImage;
	}
	/**
	 * @return the roles
	 */
	public List<String> getRoles() {
		List<String> result = new ArrayList<String>();
		if(Validator.isNotNull(this.roles)){
			for (String s : Splitter.on(',').split(this.roles)) {
				result.add(s.trim());
			}
		}
		return result;
	}

	/**
	 * @param roles the tags to set
	 */
	public void setRoles(List<String> roles) {
		if(Validator.isNotNull(roles) && roles.size() > 0){
			this.roles = Joiner.on(',').join(roles);
		} else {
			this.roles = null;
		}
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", username=" + username + ", " + "password=" + password + ", active="
				+ active + ", " + "version=" + version + "]";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Member)) {
			return false;
		}

		final Member obj = (Member) o;

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
