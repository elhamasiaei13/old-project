package com.parvanpajooh.issuemanager.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Member.class)
public abstract class Member_ extends com.parvanpajooh.commons.platform.ejb.model.BaseModel_ {

	public static volatile SetAttribute<Member, TaskMemberRelation> taskMemberRelations;
	public static volatile SingularAttribute<Member, String> firstName;
	public static volatile SingularAttribute<Member, String> lastName;
	public static volatile SingularAttribute<Member, String> password;
	public static volatile SingularAttribute<Member, String> roles;
	public static volatile SingularAttribute<Member, Boolean> active;
	public static volatile SingularAttribute<Member, Long> id;
	public static volatile SingularAttribute<Member, Integer> version;
	public static volatile SingularAttribute<Member, byte[]> fileImage;
	public static volatile SingularAttribute<Member, String> email;
	public static volatile SingularAttribute<Member, String> username;

}

