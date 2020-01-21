package com.parvanpajooh.issuemanager.model;

import com.parvanpajooh.issuemanager.model.enums.TableNameEnum;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(AggregatedHistory.class)
public abstract class AggregatedHistory_ extends com.parvanpajooh.commons.platform.ejb.model.BaseModel_ {

	public static volatile SingularAttribute<AggregatedHistory, Task> task;
	public static volatile SingularAttribute<AggregatedHistory, TaskAssignmentHistory> taskAssignmentHistory;
	public static volatile SingularAttribute<AggregatedHistory, Boolean> active;
	public static volatile SingularAttribute<AggregatedHistory, TaskAttachment> taskAttachment;
	public static volatile SingularAttribute<AggregatedHistory, Comment> comment;
	public static volatile SingularAttribute<AggregatedHistory, Long> id;
	public static volatile SingularAttribute<AggregatedHistory, String> body;
	public static volatile SingularAttribute<AggregatedHistory, TableNameEnum> type;
	public static volatile SingularAttribute<AggregatedHistory, TaskStatusHistory> taskStatusHistory;
	public static volatile SingularAttribute<AggregatedHistory, Integer> version;
	public static volatile SingularAttribute<AggregatedHistory, Relation> relation;

}

