package com.parvanpajooh.issuemanager.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TaskAttachment.class)
public abstract class TaskAttachment_ extends com.parvanpajooh.commons.platform.ejb.model.BaseModel_ {

	public static volatile SingularAttribute<TaskAttachment, String> path;
	public static volatile SingularAttribute<TaskAttachment, Task> task;
	public static volatile SingularAttribute<TaskAttachment, Long> size;
	public static volatile SingularAttribute<TaskAttachment, String> name;
	public static volatile SingularAttribute<TaskAttachment, Boolean> active;
	public static volatile SingularAttribute<TaskAttachment, String> comment;
	public static volatile SingularAttribute<TaskAttachment, Long> id;
	public static volatile SingularAttribute<TaskAttachment, String> mimeType;
	public static volatile SingularAttribute<TaskAttachment, Integer> version;

}

