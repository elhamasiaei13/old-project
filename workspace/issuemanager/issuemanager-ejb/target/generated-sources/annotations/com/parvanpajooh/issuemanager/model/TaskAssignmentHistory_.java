package com.parvanpajooh.issuemanager.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TaskAssignmentHistory.class)
public abstract class TaskAssignmentHistory_ extends com.parvanpajooh.commons.platform.ejb.model.BaseModel_ {

	public static volatile SingularAttribute<TaskAssignmentHistory, Task> task;
	public static volatile SingularAttribute<TaskAssignmentHistory, String> comment;
	public static volatile SingularAttribute<TaskAssignmentHistory, Long> id;
	public static volatile SingularAttribute<TaskAssignmentHistory, Member> memberFrom;
	public static volatile SingularAttribute<TaskAssignmentHistory, Integer> version;
	public static volatile SingularAttribute<TaskAssignmentHistory, Member> memberTo;

}

