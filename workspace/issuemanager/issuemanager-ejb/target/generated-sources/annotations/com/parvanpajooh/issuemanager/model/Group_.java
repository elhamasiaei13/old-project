package com.parvanpajooh.issuemanager.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Group.class)
public abstract class Group_ extends com.parvanpajooh.commons.platform.ejb.model.BaseModel_ {

	public static volatile SingularAttribute<Group, String> name;
	public static volatile SingularAttribute<Group, Boolean> active;
	public static volatile SingularAttribute<Group, String> description;
	public static volatile SingularAttribute<Group, Long> id;
	public static volatile SingularAttribute<Group, Integer> version;

}

