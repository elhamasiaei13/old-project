package com.parvanpajooh.issuemanager.model;

import com.parvanpajooh.issuemanager.model.enums.MemberTypeEnum;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Membership.class)
public abstract class Membership_ extends com.parvanpajooh.commons.platform.ejb.model.BaseModel_ {

	public static volatile SingularAttribute<Membership, Member> member;
	public static volatile SingularAttribute<Membership, Boolean> active;
	public static volatile SingularAttribute<Membership, Long> id;
	public static volatile SingularAttribute<Membership, MemberTypeEnum> type;
	public static volatile SingularAttribute<Membership, Integer> version;
	public static volatile SingularAttribute<Membership, Group> group;

}

