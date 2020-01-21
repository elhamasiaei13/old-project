package com.parvanpajooh.sample.model;

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
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotEmpty;

import com.google.common.base.Objects;
import com.parvanpajooh.commons.platform.ejb.model.BaseModel;
import com.parvanpajooh.commons.platform.ejb.model.vo.BaseVO;
import com.parvanpajooh.commons.util.Validator;
import com.parvanpajooh.sample.model.vo.SampleVO;

/**
 * @author dev-03
 *
 */

@Entity
@XmlRootElement
@Table(name = "sample_tbl")
public class Sample extends BaseModel {

	/**
	 *
	 */
	private static final long serialVersionUID = -4036378126742804100L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Version
	private Integer version;

	@NotNull
	@NotEmpty
	@Column(unique = true)
	private String name;

	private String canonicalSearchName;

	private String iataCode;

	private String address;

	private String tel;

	private String email;

	private String logoId;

	@NotNull
	private boolean branch;

	private String nodeCode;
	
	private Long nodeId;

	private String remarks;

	@NotNull
	private boolean active;
	
	private String tags;
	
	private Long personOrganizationId;
	
	private String cityShortCode;

	@ManyToOne
	@JoinColumn(name = "parentId", referencedColumnName = "id")
	private Sample parent;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
	private Set<Sample> children;

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
	 * @return the canonicalSearchName
	 */
	public String getCanonicalSearchName() {
		return canonicalSearchName;
	}

	/**
	 * @param canonicalSearchName
	 *            the canonicalSearchName to set
	 */
	public void setCanonicalSearchName(String canonicalSearchName) {
		this.canonicalSearchName = canonicalSearchName;
	}

	/**
	 * @return the iataCode
	 */
	public String getIataCode() {
		return iataCode;
	}

	/**
	 * @param iataCode
	 *            the iataCode to set
	 */
	public void setIataCode(String iataCode) {
		this.iataCode = iataCode;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the tel
	 */
	public String getTel() {
		return tel;
	}

	/**
	 * @param tel
	 *            the tel to set
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the logoId
	 */
	public String getLogoId() {
		return logoId;
	}

	/**
	 * @param logoId
	 *            the logoId to set
	 */
	public void setLogoId(String logoId) {
		this.logoId = logoId;
	}

	/**
	 * @return the branch
	 */
	public Boolean getBranch() {
		return branch;
	}

	/**
	 * @param branch
	 *            the branch to set
	 */
	public void setBranch(Boolean branch) {
		this.branch = branch;
	}

	/**
	 * @return the active
	 */
	public Boolean getActive() {
		return active;
	}

	/**
	 * @param active
	 *            the active to set
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}

	/**
	 * @return the nodeId
	 */
	public Long getNodeId() {
		return nodeId;
	}

	/**
	 * @param nodeId
	 *            the nodeId to set
	 */
	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	/**
	 * @return the nodeCode
	 */
	public String getNodeCode() {
		return nodeCode;
	}

	/**
	 * @param nodeCode
	 *            the nodeCode to set
	 */
	public void setNodeCode(String nodeCode) {
		this.nodeCode = nodeCode;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks
	 *            the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the parent
	 */
	public Sample getParent() {
		return parent;
	}

	/**
	 * @param parent
	 *            the parent to set
	 */
	public void setParent(Sample parent) {
		this.parent = parent;
	}

	/**
	 * @return the children
	 */
	public Set<Sample> getChildren() {
		return children;
	}

	/**
	 * @param children
	 *            the children to set
	 */
	public void setChildren(Set<Sample> children) {
		this.children = children;
	}

	/**
	 * @return the personOrganizationId
	 */
	public Long getPersonOrganizationId() {
		return personOrganizationId;
	}

	/**
	 * @param personOrganizationId the personOrganizationId to set
	 */
	public void setPersonOrganizationId(Long personOrganizationId) {
		this.personOrganizationId = personOrganizationId;
	}
	
	/**
	 * @return the cityShortCode
	 */
	public String getCityShortCode() {
		return cityShortCode;
	}

	/**
	 * @param cityShortCode the cityShortCode to set
	 */
	public void setCityShortCode(String cityShortCode) {
		this.cityShortCode = cityShortCode;
	}

	
	@Override
	public SampleVO toVOLite() {
		SampleVO vo = new SampleVO();

		vo.setId(this.id);
		vo.setVersion(this.version);
		vo.setName(this.name);
		vo.setCanonicalSeachName(this.canonicalSearchName);
		vo.setIataCode(this.iataCode);
		vo.setAddress(this.address);
		vo.setTel(this.tel);
		vo.setEmail(this.email);
		vo.setLogoId(this.logoId);
		vo.setBranch(this.branch);
		vo.setNodeCode(this.nodeCode);
		vo.setRemarks(this.remarks);
		vo.setActive(this.active);
		vo.setNodeId(this.nodeId);
		vo.setPersonOrganizationId(personOrganizationId);
		vo.setCityShortCode(this.cityShortCode);
		//vo.setTags(this.getTags());


		setAuditToVO(vo);

		return vo;
	}

	@Override
	public SampleVO toVO() {

		SampleVO generalAgentVO = (SampleVO) toVOLite();

		if (Validator.isNotNull(this.parent)) {
			 SampleVO parentGeneralAgentVO = this.parent.toVOLite();
			 generalAgentVO.setParent(parentGeneralAgentVO);
		}

		return generalAgentVO;
	}

	@Override
	public void fromVO(BaseVO baseVO) {

		SampleVO vo = (SampleVO) baseVO;

		this.id = vo.getId();
		this.version = vo.getVersion();
		this.name = vo.getName();
		this.canonicalSearchName = vo.getCanonicalSeachName();
		this.iataCode = vo.getIataCode();
		this.address = vo.getAddress();
		this.tel = vo.getTel();
		this.email = vo.getEmail();
		this.logoId = vo.getLogoId();
		this.branch = vo.getBranch();
		this.active = vo.getActive();
		this.nodeCode = vo.getNodeCode();
		this.remarks = vo.getRemarks();
		this.nodeId = vo.getNodeId();
		this.personOrganizationId = vo.getPersonOrganizationId();
		this.cityShortCode = vo.getCityShortCode();
		//this.setTags(vo.getTags());

		// if (Validator.isNotNull(vo.getAccountNumbers())) {
		// AccountNumber accountNumber = new AccountNumber();
		// List<AccountNumber> list = new ArrayList<AccountNumber>();
		//
		// for (AccountNumberVO accountNumberVO : vo.getAccountNumbers()) {
		// accountNumber.fromVO(accountNumberVO);
		// list.add(accountNumber);
		// }
		// this.accountNumbers = new HashSet<AccountNumber>(list);
		// }

		// if (Validator.isNotNull(vo.getEmployees())) {
		// Employee employee = new Employee();
		// List<Employee> list = new ArrayList<Employee>();
		//
		// for (EmployeeVO employeeVO : vo.getEmployees()) {
		// employee.fromVO(employeeVO);
		// list.add(employee);
		// }
		// this.employees = new HashSet<Employee>(list);
		// }

		// if (Validator.isNotNull(vo.getParent())) {
		// GeneralAgent agent = new GeneralAgent();
		// agent.fromVO(vo.getParent());
		//
		// this.parent = agent;
		// }

		getAuditFromVO(vo);
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.add("id", id)
				.add("version", version)
				.add("name", name)
				.add("canonicalSearchName", canonicalSearchName)
				.add("iataCode", iataCode)
				.add("address", address)
				.add("tel", tel)
				.add("email", email)
				.add("logoId", logoId)
				.add("branch", branch)
				.add("nodeCode", nodeCode)
				.add("nodeId", nodeId)
				.add("remarks", remarks)
				.add("active", active)
				.add("tags", tags)
				.add("personOrganizationId", personOrganizationId)
				.add("parent", parent)
				//disabled due to StackOverFlow .add("children", children)
				.add(cityShortCode, cityShortCode)
				.toString();
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof Sample) {
			Sample that = (Sample) object;
			return Objects.equal(this.id, that.id);
		}
		return false;
	}

}
