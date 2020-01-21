package com.parvanpajooh.geomanagement.dao.jpa;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
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
import com.parvanpajooh.geomanagement.dao.GeoEntityDao;
import com.parvanpajooh.geomanagement.model.GeoEntity;
import com.parvanpajooh.geomanagement.model.GeoEntity_;
import com.parvanpajooh.geomanagement.model.GeoType;
import com.parvanpajooh.geomanagement.model.GeoType_;
import com.parvanpajooh.geomanagement.model.criteria.GeoEntityCriteria;
import com.parvanpajooh.geomanagement.model.vo.GeoEntityVO;
import com.parvanpajooh.geomanagement.model.vo.GeoTypeVO;

/**
 * @author ali
 * @author Mohammad
 * @author mehrdad
 *
 */
public class GeoEntityDaoJpa extends GenericDaoJpa<GeoEntity, Long> implements GeoEntityDao {

	public GeoEntityDaoJpa() {
		super(GeoEntity.class);
	}

	@Override
	protected List<Predicate> buildPredicateList(
			BaseCriteria cri, 
			CriteriaBuilder builder, 
			Metamodel metamodel, 
			Root<GeoEntity> root, Map<String, Join> joins) throws ParvanException {

		//LOG
    	log.debug("Entering buildPredicateList( ... )");
    	
    	GeoEntityCriteria geoEntityCriteria = ( GeoEntityCriteria) cri;
    	
    	List<Predicate> predicateList = new ArrayList<Predicate>();
    	
    	String shortCode = geoEntityCriteria.getShortCode();
    	String nameFa = geoEntityCriteria.getNameFa();
    	String nameEn = geoEntityCriteria.getNameEn();
    	String nameOther = geoEntityCriteria.getNameOther();
    	String allNames = geoEntityCriteria.getAllNames();
    	String possibleChildCode = geoEntityCriteria.getPossibleChildCode();
    	GeoTypeVO type = geoEntityCriteria.getType();
    	GeoEntityVO parent = geoEntityCriteria.getParent();
    	GeoEntityVO children = geoEntityCriteria.getChildren();
    	
    	if(Validator.isNotNull( shortCode )){
    		Predicate predicate = builder.equal(root.<String>get(GeoEntity_.shortCode), shortCode);
    		predicateList.add(predicate);
    	}
    	
    	if(Validator.isNotNull( nameFa )){
    		Predicate predicate = builder.like(root.<String>get(GeoEntity_.nameFa),"%"+ nameFa + "%");
    		predicateList.add(predicate);
    	}
    	
    	if(Validator.isNotNull( nameEn )){
    		Predicate predicate = builder.like(root.<String>get(GeoEntity_.nameEn),"%"+ nameEn + "%");
    		predicateList.add(predicate);
    	}
    	
    	if(Validator.isNotNull( nameOther )){
    		Predicate predicate = builder.like(root.<String>get(GeoEntity_.nameOther),"%"+ nameOther + "%");
    		predicateList.add(predicate);
    	}
    	
    	if(Validator.isNotNull( allNames )){
    		
    		Predicate predicate = builder.or(
    				builder.like(root.<String>get(GeoEntity_.code), "%"+ allNames + "%"),
    				builder.like(root.<String>get(GeoEntity_.shortCode), "%"+ allNames + "%"),
    				builder.like(root.<String>get(GeoEntity_.nameFa), "%"+ allNames + "%"),
    				builder.like(root.<String>get(GeoEntity_.nameEn), "%"+ allNames + "%"),
    				builder.like(root.<String>get(GeoEntity_.nameOther), "%"+ allNames + "%"));
			
			predicateList.add(predicate);
    	}
    	
    	if( Validator.isNotNull(possibleChildCode) || Validator.isNotNull(type)) {
    		Join<GeoEntity, GeoType> join = root.join(GeoEntity_.type, JoinType.LEFT);
    		
    		joins.put("type", join);
    		
    		if( Validator.isNotNull(possibleChildCode) ) {
    			Join<GeoType, GeoType> possibleChildsJoin = join.join(GeoType_.possibleChilds, JoinType.LEFT);
    			
				Predicate predicate = builder.equal(possibleChildsJoin.<String>get(GeoType_.code), possibleChildCode);
				predicateList.add(predicate);
    		}
    		
    		if( Validator.isNotNull(type) ) {
    			if(Validator.isNotNull( type.getId() )){
    				Predicate predicate = builder.equal(join.<Long>get(GeoType_.id), type.getId());
    				predicateList.add(predicate);
    			}
    			
    			if(Validator.isNotNull( type.getCode() )){
    				Predicate predicate = builder.equal(join.<String>get(GeoType_.code), type.getCode());
    				predicateList.add(predicate);
    			}
    			
    			if(Validator.isNotNull( type.getNameEn() )){
    				Predicate predicate = builder.equal(join.<String>get(GeoType_.nameEn), type.getNameEn());
    				predicateList.add(predicate);
    			}
    			
    			if(Validator.isNotNull( type.getNameFa() )){
    				Predicate predicate = builder.equal(join.<String>get(GeoType_.nameFa), type.getNameFa());
    				predicateList.add(predicate);
    			}
    			
    		}
    	}
    	
    	if(Validator.isNotNull(parent)){
    		Join<GeoEntity, GeoEntity> join = root.join(GeoEntity_.parent, JoinType.LEFT);
    		
    		joins.put("parent", join);
    		
    		if(Validator.isNotNull( parent.getId() )){
    			Predicate predicate = builder.equal(join.<Long>get(GeoEntity_.id), parent.getId());
    			predicateList.add(predicate);
    		}
    		
    		if(Validator.isNotNull( parent.getCode() )){
        		Predicate predicate = builder.equal(join.<String>get(GeoEntity_.code), parent.getCode());
        		predicateList.add(predicate);
        	}
    		
    		if(Validator.isNotNull( parent.getShortCode() )){
    			Predicate predicate = builder.equal(join.<String>get(GeoEntity_.shortCode), parent.getShortCode());
    			predicateList.add(predicate);
    		}
        	
        	if(Validator.isNotNull( parent.getNameFa() )){
        		Predicate predicate = builder.equal(join.<String>get(GeoEntity_.nameFa), parent.getNameFa());
        		predicateList.add(predicate);
        	}
        	
        	if(Validator.isNotNull( parent.getNameEn() )){
        		Predicate predicate = builder.equal(join.<String>get(GeoEntity_.nameEn), parent.getNameEn());
        		predicateList.add(predicate);
        	}
        	
        	if(Validator.isNotNull( parent.getNameOther() )){
        		Predicate predicate = builder.equal(join.<String>get(GeoEntity_.nameOther), parent.getNameOther());
        		predicateList.add(predicate);
        	}
    	}
    	
    	if(Validator.isNotNull(children)){
    		Join<GeoEntity, GeoEntity> join = root.join(GeoEntity_.children, JoinType.LEFT);
    		
    		joins.put("children", join);
    		
    		if(Validator.isNotNull( children.getId() )){
    			Predicate predicate = builder.equal(join.<Long>get(GeoEntity_.id), children.getId());
    			predicateList.add(predicate);
    		}
    		
    		if(Validator.isNotNull( children.getCode() )){
    			Predicate predicate = builder.equal(join.<String>get(GeoEntity_.code), children.getCode());
    			predicateList.add(predicate);
    		}
    		
    		if(Validator.isNotNull( children.getCode() )){
    			Predicate predicate = builder.equal(join.<String>get(GeoEntity_.shortCode), children.getShortCode());
    			predicateList.add(predicate);
    		}
    		
    		if(Validator.isNotNull( children.getNameFa() )){
    			Predicate predicate = builder.equal(join.<String>get(GeoEntity_.nameFa), children.getNameFa());
    			predicateList.add(predicate);
    		}
    		
    		if(Validator.isNotNull( children.getNameEn() )){
    			Predicate predicate = builder.equal(join.<String>get(GeoEntity_.nameEn), children.getNameEn());
    			predicateList.add(predicate);
    		}
    		
    		if(Validator.isNotNull( children.getNameOther() )){
    			Predicate predicate = builder.equal(join.<String>get(GeoEntity_.nameOther), children.getNameOther());
    			predicateList.add(predicate);
    		}
    	}
    	
    	basePredicate((BaseCriteria) geoEntityCriteria, builder, root, predicateList);
		
		//LOG
    	log.debug("Leaving buildPredicateList(): {}", predicateList.size());
    	
    	 return predicateList;
	}

	@Override
	public GeoEntity findGeoEntityByShortCode(String shortCode) throws ParvanDaoException {
		
		log.debug("Entering findGeoEntityByShortCode(shortCode={})", shortCode);
		try {
			// make query
			Query q = getEntityManager().createQuery("select obj from GeoEntity obj where obj.shortCode = ? ");
			
			q.setParameter(1, shortCode);
			
			//TODO: MEHRDAD: find method should not use getSingleResult. find method may return null value.
			// execute query
			return (GeoEntity) q.getSingleResult();
			
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public GeoEntity findGeoEntityByName(String name) throws ParvanDaoException {
		
		log.debug("Entering findGeoEntityByShortCode(name={})", name);
		
		GeoEntity geoEntity = null;
		try {
			// make query
			TypedQuery<GeoEntity> q = getEntityManager().createQuery("select obj from GeoEntity obj where obj.nameFa = ? ",GeoEntity.class);
			
			q.setParameter(1, name);
			
			List<GeoEntity> list = q.getResultList();
			if(list != null && list.size() == 1){
				geoEntity = (GeoEntity) list.get(0);
			}
			
		} catch (Exception e) {
			log.debug("error in load geo entity by name");
		}
		
		return geoEntity;
	}
	
	@Override
	protected void basePredicate(BaseCriteria cri, CriteriaBuilder builder, Root<GeoEntity> root, List<Predicate> predicateList){
		
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
