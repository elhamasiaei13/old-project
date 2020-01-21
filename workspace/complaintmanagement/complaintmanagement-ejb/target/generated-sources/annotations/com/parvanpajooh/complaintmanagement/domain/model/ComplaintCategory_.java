package com.parvanpajooh.complaintmanagement.domain.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ComplaintCategory.class)
public abstract class ComplaintCategory_ {

	public static volatile SingularAttribute<ComplaintCategory, String> code;
	public static volatile SingularAttribute<ComplaintCategory, String> nameFa;
	public static volatile SingularAttribute<ComplaintCategory, Boolean> active;
	public static volatile SingularAttribute<ComplaintCategory, String> description;
	public static volatile SingularAttribute<ComplaintCategory, ComplaintCategory> parentCategory;
	public static volatile SingularAttribute<ComplaintCategory, Long> id;
	public static volatile SingularAttribute<ComplaintCategory, String> nameEn;
	public static volatile SingularAttribute<ComplaintCategory, Integer> version;

}

