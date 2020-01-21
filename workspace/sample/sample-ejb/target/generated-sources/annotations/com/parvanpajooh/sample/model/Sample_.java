package com.parvanpajooh.sample.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Sample.class)
public abstract class Sample_ extends com.parvanpajooh.commons.platform.ejb.model.BaseModel_ {

	public static volatile SingularAttribute<Sample, Sample> parent;
	public static volatile SingularAttribute<Sample, String> address;
	public static volatile SingularAttribute<Sample, String> nodeCode;
	public static volatile SingularAttribute<Sample, Boolean> active;
	public static volatile SingularAttribute<Sample, Integer> version;
	public static volatile SingularAttribute<Sample, Boolean> branch;
	public static volatile SingularAttribute<Sample, String> tags;
	public static volatile SingularAttribute<Sample, String> iataCode;
	public static volatile SingularAttribute<Sample, String> canonicalSearchName;
	public static volatile SingularAttribute<Sample, Long> personOrganizationId;
	public static volatile SetAttribute<Sample, Sample> children;
	public static volatile SingularAttribute<Sample, String> cityShortCode;
	public static volatile SingularAttribute<Sample, String> name;
	public static volatile SingularAttribute<Sample, String> tel;
	public static volatile SingularAttribute<Sample, Long> id;
	public static volatile SingularAttribute<Sample, Long> nodeId;
	public static volatile SingularAttribute<Sample, String> email;
	public static volatile SingularAttribute<Sample, String> logoId;
	public static volatile SingularAttribute<Sample, String> remarks;

}

