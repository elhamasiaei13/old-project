package com.parvanpajooh.issuemanager.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TaskStatusHistory.class)
public abstract class TaskStatusHistory_ extends com.parvanpajooh.commons.platform.ejb.model.BaseModel_ {

	public static volatile SingularAttribute<TaskStatusHistory, String> toStatus;
	public static volatile SingularAttribute<TaskStatusHistory, Task> task;
	public static volatile SingularAttribute<TaskStatusHistory, String> fromStatus;
	public static volatile SingularAttribute<TaskStatusHistory, String> comment;
	public static volatile SingularAttribute<TaskStatusHistory, Long> id;
	public static volatile SingularAttribute<TaskStatusHistory, Integer> version;

}

