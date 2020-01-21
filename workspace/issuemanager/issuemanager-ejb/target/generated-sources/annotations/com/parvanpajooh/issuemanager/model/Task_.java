package com.parvanpajooh.issuemanager.model;

import com.parvanpajooh.issuemanager.model.enums.EmailEnum;
import com.parvanpajooh.issuemanager.model.enums.MemberTypeEnum;
import com.parvanpajooh.issuemanager.model.enums.TaskPriorityEnum;
import com.parvanpajooh.issuemanager.model.enums.TaskStatusEnum;
import com.parvanpajooh.issuemanager.model.enums.TaskTypeEnum;
import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Task.class)
public abstract class Task_ extends com.parvanpajooh.commons.platform.ejb.model.BaseModel_ {

	public static volatile SetAttribute<Task, TaskMemberRelation> taskMemberRelations;
	public static volatile SingularAttribute<Task, String> metadata;
	public static volatile SingularAttribute<Task, String> subject;
	public static volatile SetAttribute<Task, TaskAssignmentHistory> taskAssignmentHistory;
	public static volatile SingularAttribute<Task, TaskStatusEnum> currentTaskStatus;
	public static volatile SingularAttribute<Task, LocalDateTime> dueDate;
	public static volatile SingularAttribute<Task, String> description;
	public static volatile SingularAttribute<Task, Boolean> active;
	public static volatile SingularAttribute<Task, TaskPriorityEnum> priority;
	public static volatile SetAttribute<Task, TaskStatusHistory> taskStatusHistory;
	public static volatile SingularAttribute<Task, Integer> version;
	public static volatile SingularAttribute<Task, Member> currentMember;
	public static volatile SingularAttribute<Task, TaskTypeEnum> taskType;
	public static volatile SingularAttribute<Task, EmailEnum> emailStatus;
	public static volatile SingularAttribute<Task, Long> id;
	public static volatile SingularAttribute<Task, MemberTypeEnum> memberType;
	public static volatile SingularAttribute<Task, Group> group;

}

