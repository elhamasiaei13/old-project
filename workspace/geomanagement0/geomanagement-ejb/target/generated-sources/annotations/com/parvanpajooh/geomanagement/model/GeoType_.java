package com.parvanpajooh.geomanagement.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(GeoType.class)
public abstract class GeoType_ extends com.parvanpajooh.commons.platform.ejb.model.BaseModel_ {

	public static volatile SingularAttribute<GeoType, String> code;
	public static volatile SingularAttribute<GeoType, String> nameFa;
	public static volatile SingularAttribute<GeoType, Long> id;
	public static volatile SingularAttribute<GeoType, String> nameEn;
	public static volatile SingularAttribute<GeoType, Integer> version;
	public static volatile SingularAttribute<GeoType, GeoType> parentType;
	public static volatile SetAttribute<GeoType, GeoType> possibleChilds;

}

