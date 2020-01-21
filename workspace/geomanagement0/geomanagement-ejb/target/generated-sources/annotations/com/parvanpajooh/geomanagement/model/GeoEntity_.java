package com.parvanpajooh.geomanagement.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(GeoEntity.class)
public abstract class GeoEntity_ extends com.parvanpajooh.commons.platform.ejb.model.BaseModel_ {

	public static volatile SingularAttribute<GeoEntity, GeoEntity> parent;
	public static volatile SingularAttribute<GeoEntity, String> code;
	public static volatile SingularAttribute<GeoEntity, Double> geoLat;
	public static volatile SingularAttribute<GeoEntity, String> nameEn;
	public static volatile SingularAttribute<GeoEntity, String> nameOther;
	public static volatile SingularAttribute<GeoEntity, GeoType> type;
	public static volatile SingularAttribute<GeoEntity, Integer> version;
	public static volatile SingularAttribute<GeoEntity, Double> geoLng;
	public static volatile SetAttribute<GeoEntity, GeoEntity> children;
	public static volatile SingularAttribute<GeoEntity, String> sortField;
	public static volatile SingularAttribute<GeoEntity, String> nameFa;
	public static volatile SingularAttribute<GeoEntity, String> zoneId;
	public static volatile SingularAttribute<GeoEntity, Long> id;
	public static volatile SingularAttribute<GeoEntity, String> shortCode;

}

