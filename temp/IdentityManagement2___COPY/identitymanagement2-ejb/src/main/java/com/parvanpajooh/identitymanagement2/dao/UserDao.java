package com.parvanpajooh.identitymanagement2.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.parvanpajooh.common.vo.BaseCriteria;
import com.parvanpajooh.ecourier.dao.GenericDao;
import com.parvanpajooh.identitymanagement2.model.User;

/**
 * 
 * @author moosa
 *
 */
public interface UserDao extends GenericDao<User, Long> {

}