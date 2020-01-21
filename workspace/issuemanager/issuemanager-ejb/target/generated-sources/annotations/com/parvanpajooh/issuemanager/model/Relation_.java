package com.parvanpajooh.issuemanager.model;

import com.parvanpajooh.issuemanager.model.enums.RelationTypeEnum;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Relation.class)
public abstract class Relation_ extends com.parvanpajooh.commons.platform.ejb.model.BaseModel_ {

	public static volatile SingularAttribute<Relation, Task> fromTask;
	public static volatile SingularAttribute<Relation, Task> toTask;
	public static volatile SingularAttribute<Relation, String> description;
	public static volatile SingularAttribute<Relation, Boolean> active;
	public static volatile SingularAttribute<Relation, Long> id;
	public static volatile SingularAttribute<Relation, RelationTypeEnum> type;
	public static volatile SingularAttribute<Relation, Integer> version;

}

