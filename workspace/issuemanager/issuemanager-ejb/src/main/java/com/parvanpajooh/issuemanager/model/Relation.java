package com.parvanpajooh.issuemanager.model;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parvanpajooh.commons.platform.ejb.model.BaseModel;
import com.parvanpajooh.commons.platform.ejb.model.vo.BaseVO;
import com.parvanpajooh.commons.util.Validator;
import com.parvanpajooh.issuemanager.model.enums.RelationTypeEnum;
import com.parvanpajooh.issuemanager.model.vo.RelationVO;
import com.parvanpajooh.issuemanager.model.vo.TaskVO;

@Entity
@XmlRootElement
@Table(name = "relation_tbl")
public class Relation extends BaseModel {

	private static final long serialVersionUID = -4036378126742804100L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@NotEmpty
	@Lob
	@Column(length = 10000)
	private String description;

	@ManyToOne
	@JoinColumn(name = "fromTask", referencedColumnName = "id")
	private Task fromTask;
	
	@ManyToOne
	@JoinColumn(name = "toTask", referencedColumnName = "id")
	private Task toTask;

	private boolean active;

	@Version
	private Integer version;
	
	@Enumerated(EnumType.STRING)
	private RelationTypeEnum type;
	
	@Override
	public boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		return false;
		
	}

	@Override
	public void fromVO(BaseVO baseVO) {
		RelationVO vo = (RelationVO) baseVO;

		this.id = vo.getId();
		this.description = vo.getDescription();
		this.version = vo.getVersion();
		this.type = vo.getType();
		
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
		
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
		
	}

	@Override
	public BaseVO toVO() {
		
		RelationVO relationVO = (RelationVO) toVOLite();
		return relationVO;		
	}

	@Override
	public BaseVO toVOLite() {
		RelationVO vo = new RelationVO();

		vo.setId(this.id);
		vo.setDescription(this.description);
		vo.setType(this.type);
		if (Validator.isNotNull(this.fromTask)) {

			TaskVO taskVO = this.fromTask.toVOLite();
			vo.setFromTask(taskVO);
		}
		
		if (Validator.isNotNull(this.toTask)) {

			TaskVO taskVO = this.toTask.toVOLite();
			vo.setToTask(taskVO);
		}

		setAuditToVO(vo);

		return vo;	
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
	 * @return the fromTask
	 */
	public Task getFromTask() {
		return fromTask;
	}

	/**
	 * @param fromTask the fromTask to set
	 */
	public void setFromTask(Task fromTask) {
		this.fromTask = fromTask;
	}

	/**
	 * @return the toTask
	 */
	public Task getToTask() {
		return toTask;
	}

	/**
	 * @param toTask the toTask to set
	 */
	public void setToTask(Task toTask) {
		this.toTask = toTask;
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
	 * @return the type
	 */
	public RelationTypeEnum getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(RelationTypeEnum type) {
		this.type = type;
	}

	/**
	 * convert map to json
	 * 
	 * @throws IOException
	 */
	public static String mapToJson(Relation obj) throws IOException {

		// initialize map
		Map<String, Object> map = new LinkedHashMap<>();
		map.put(Relation_.id.getName(), obj.getId());
		map.put(Relation_.createUserId.getName(), obj.getCreateUserId());
		map.put(Relation_.type.getName(), obj.getType().value);
		map.put(Relation_.description.getName(), obj.getDescription());
		map.put(Relation_.version.getName(), obj.getVersion());
		map.put(Relation_.fromTask.getName(), obj.getFromTask().getSubject());
		map.put(Relation_.toTask.getName(), obj.getToTask().getSubject());
		
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(map);

		return json;
	}
}
