package com.parvanpajooh.complaintmanagement.domain.model;

import com.parvanpajooh.complaintmanagement.domain.model.enums.ComplaintStatus;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Complaint.class)
public abstract class Complaint_ {

	public static volatile SingularAttribute<Complaint, String> lastName;
	public static volatile SingularAttribute<Complaint, String> subject;
	public static volatile SingularAttribute<Complaint, String> description;
	public static volatile SingularAttribute<Complaint, String> userName;
	public static volatile SingularAttribute<Complaint, Integer> version;
	public static volatile SingularAttribute<Complaint, String> result;
	public static volatile SingularAttribute<Complaint, String> firstName;
	public static volatile SingularAttribute<Complaint, String> userUuid;
	public static volatile SingularAttribute<Complaint, String> attachment;
	public static volatile SingularAttribute<Complaint, String> tel;
	public static volatile SingularAttribute<Complaint, Long> id;
	public static volatile SingularAttribute<Complaint, ComplaintCategory> category;
	public static volatile SingularAttribute<Complaint, String> email;
	public static volatile SingularAttribute<Complaint, ComplaintStatus> status;

}

