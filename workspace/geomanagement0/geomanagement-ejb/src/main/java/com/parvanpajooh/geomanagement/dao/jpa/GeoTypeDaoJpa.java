package com.parvanpajooh.geomanagement.dao.jpa;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Metamodel;

import com.parvanpajooh.commons.platform.ejb.dao.jpa.GenericDaoJpa;
import com.parvanpajooh.commons.platform.ejb.exceptions.ParvanDaoException;
import com.parvanpajooh.commons.platform.ejb.exceptions.ParvanException;
import com.parvanpajooh.commons.platform.ejb.model.vo.BaseCriteria;
import com.parvanpajooh.commons.util.Validator;
import com.parvanpajooh.geomanagement.dao.GeoTypeDao;
import com.parvanpajooh.geomanagement.model.GeoType;
import com.parvanpajooh.geomanagement.model.GeoType_;
import com.parvanpajooh.geomanagement.model.criteria.GeoTypeCriteria;
import com.parvanpajooh.geomanagement.model.vo.GeoTypeVO;

/**
 * @author ali
 *
 */
public class GeoTypeDaoJpa extends GenericDaoJpa<GeoType, Long> implements GeoTypeDao {

	public GeoTypeDaoJpa() {
		super(GeoType.class);
	}
	
	public GeoType findGeoTypeByCode(String code) throws ParvanDaoException{
		log.debug("Entering findGeoTypeByCode..");
		try {
    		// make query
    		Query q = getEntityManager().createQuery("select obj from GeoType obj where obj.code = ? ");
    		
    		q.setParameter(1, code);
    		
    		// execute query
    		return (GeoType) q.getSingleResult();
		
    	} catch (Exception e) {
    		return null;
		}
	}
	
	@Override
	public GeoType findGeoTypeByNameFa(String nameFa) throws ParvanDaoException {
		log.debug("Entering findGeoTypeByNameFa..");
		try {
    		// make query
    		Query q = getEntityManager().createQuery("select obj from GeoType obj where obj.nameFa = ? ");
    		
    		q.setParameter(1, nameFa);
    		
    		// execute query
    		return (GeoType) q.getSingleResult();
		
    	} catch (Exception e) {
    		return null;
		}
	}

	@Override
	public GeoType findGeoTypeByNameEn(String nameEn) throws ParvanDaoException {
		log.debug("Entering findGeoTypeByNameEn..");
		try {
    		// make query
    		Query q = getEntityManager().createQuery("select obj from GeoType obj where obj.nameEn = ? ");
    		
    		q.setParameter(1, nameEn);
    		
    		// execute query
    		return (GeoType) q.getSingleResult();
		
    	} catch (Exception e) {
    		return null;
		}
	}

	@Override
	protected List<Predicate> buildPredicateList(
			BaseCriteria cri, 
			CriteriaBuilder builder, 
			Metamodel metamodel, 
			Root<GeoType> root, Map<String, Join> joins) throws ParvanException {

		//LOG
    	log.debug("Entering buildPredicateList( ... )");
    	
    	GeoTypeCriteria geoTypeCriteria = ( GeoTypeCriteria) cri;
    	
    	List<Predicate> predicateList = new ArrayList<Predicate>();
    	
    	String code = geoTypeCriteria.getCode();
    	String nameFa = geoTypeCriteria.getNameFa();
    	String nameEn = geoTypeCriteria.getNameEn();
    	String allNames = geoTypeCriteria.getAllNames();
    	GeoTypeVO possibleChild = geoTypeCriteria.getPossibleChild();
    	GeoTypeVO parentType = geoTypeCriteria.getParentType();
    	
    	if(Validator.isNotNull( code )){
    		Predicate predicate = builder.like(root.<String>get(GeoType_.code), code);
    		predicateList.add(predicate);
    	}
    	
    	if(Validator.isNotNull( nameFa )){
    		Predicate predicate = builder.like(root.<String>get(GeoType_.nameFa),"%"+ nameFa + "%");
    		predicateList.add(predicate);
    	}
    	
    	if(Validator.isNotNull( nameEn )){
    		Predicate predicate = builder.like(root.<String>get(GeoType_.nameEn),"%"+ nameEn + "%");
    		predicateList.add(predicate);
    	}
    	
    	
    	if(Validator.isNotNull( allNames )){
    		
    		Predicate predicate = builder.or(
    				builder.like(root.<String>get(GeoType_.code), "%"+ allNames + "%"),
    				builder.like(root.<String>get(GeoType_.nameFa), "%"+ allNames + "%"),
    				builder.like(root.<String>get(GeoType_.nameEn), "%"+ allNames + "%"));
			
			predicateList.add(predicate);
    	}
    	
    	if( Validator.isNotNull(possibleChild) ) {
    		
    		Join<GeoType, GeoType> join = root.join(GeoType_.possibleChilds, JoinType.LEFT);
    		
    		joins.put("possibleChilds", join);
			
			if(Validator.isNotNull( possibleChild.getId() )){
				Predicate predicate = builder.equal(join.<Long>get(GeoType_.id), possibleChild.getId());
				predicateList.add(predicate);
			}
			
			if(Validator.isNotNull( possibleChild.getCode() )){
	    		Predicate predicate = builder.equal(root.<String>get(GeoType_.code), possibleChild.getCode());
	    		predicateList.add(predicate);
	    	}
			
			if(Validator.isNotNull( possibleChild.getNameEn() )){
				Predicate predicate = builder.equal(root.<String>get(GeoType_.nameEn), possibleChild.getNameEn());
				predicateList.add(predicate);
			}
			
			if(Validator.isNotNull( possibleChild.getNameFa() )){
				Predicate predicate = builder.equal(root.<String>get(GeoType_.nameFa), possibleChild.getNameFa());
				predicateList.add(predicate);
			}
    		
    	}
    	
    	if( Validator.isNotNull(parentType)) {
    		
    		Join<GeoType, GeoType> join = root.join(GeoType_.parentType, JoinType.LEFT);
    		
    		joins.put("parentType", join);
    		
    		if(Validator.isNotNull( parentType.getId() )){
    			Predicate predicate = builder.equal(join.<Long>get(GeoType_.id), parentType.getId());
    			predicateList.add(predicate);
    		}
    		
    		if(Validator.isNotNull( parentType.getCode() )){
    			Predicate predicate = builder.equal(root.<String>get(GeoType_.code), parentType.getCode());
    			predicateList.add(predicate);
    		}
    		
    		if(Validator.isNotNull( parentType.getNameEn() )){
    			Predicate predicate = builder.equal(root.<String>get(GeoType_.nameEn), parentType.getNameEn());
    			predicateList.add(predicate);
    		}
    		
    		if(Validator.isNotNull( parentType.getNameFa() )){
    			Predicate predicate = builder.equal(root.<String>get(GeoType_.nameFa), parentType.getNameFa());
    			predicateList.add(predicate);
    		}
    		
    	}
    	
    	basePredicate((BaseCriteria) geoTypeCriteria, builder, root, predicateList);
		
		//LOG
    	log.debug("Leaving buildPredicateList(): {}", predicateList.size());
    	
    	 return predicateList;
	}
	
	protected void basePredicate(BaseCriteria cri, CriteriaBuilder builder, Root<GeoType> root, List<Predicate> predicateList){
		
		if(Validator.isNotNull( cri.getCreateUserId() )){
			Predicate predicate = builder.equal(root.<String>get("createUserId"), cri.getCreateUserId());
			predicateList.add(predicate);
		}
		
		if(Validator.isNotNull( cri.getUpdateUserId() )){
			Predicate predicate = builder.equal(root.<String>get("updateUserId"), cri.getUpdateUserId());
			predicateList.add(predicate);
		}
		
		if(Validator.isNotNull(cri.getCreateDateFrom())){
    		Predicate predicate = builder.greaterThanOrEqualTo(root.<LocalDateTime>get("createDate"), cri.getCreateDateFrom());
    		predicateList.add(predicate);
    	}
    	
    	if(Validator.isNotNull(cri.getCreateDateTo())){
    		Predicate predicate = builder.lessThanOrEqualTo(root.<LocalDateTime>get("createDate"), cri.getCreateDateTo());
    		predicateList.add(predicate);
    	}
    	
    	if(Validator.isNotNull(cri.getUpdateDateFrom())){
    		Predicate predicate = builder.greaterThanOrEqualTo(root.<LocalDateTime>get("updateDate"), cri.getUpdateDateFrom());
    		predicateList.add(predicate);
    	}
    	
    	if(Validator.isNotNull(cri.getUpdateDateTo())){
    		Predicate predicate = builder.lessThanOrEqualTo(root.<LocalDateTime>get("updateDate"), cri.getUpdateDateTo());
    		predicateList.add(predicate);
    	}
	}
}
