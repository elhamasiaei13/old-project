package com.parvanpajooh.issuemanager.model;

import com.parvanpajooh.issuemanager.model.enums.TaskMemberRelationEnum;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TaskMemberRelation.class)
public abstract class TaskMemberRelation_ extends com.parvanpajooh.commons.platform.ejb.model.BaseModel_ {

	public static volatile SingularAttribute<TaskMemberRelation, Task> task;
	public static volatile SingularAttribute<TaskMemberRelation, Member> member;
	public static volatile SingularAttribute<TaskMemberRelation, Long> id;
	public static volatile SingularAttribute<TaskMemberRelation, TaskMemberRelationEnum> type;

}

