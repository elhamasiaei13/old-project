package com.parvanpajooh.issuemanager.service.impl;

import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.interceptor.Interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.parvanpajooh.commons.enums.SortDirectionEnum;
import com.parvanpajooh.commons.platform.ejb.model.BaseModel;
import com.parvanpajooh.commons.platform.ejb.model.UserInfo;
import com.parvanpajooh.commons.platform.ejb.model.enums.ListExportFormatEnum;
import com.parvanpajooh.commons.platform.ejb.model.vo.BaseCriteria;
import com.parvanpajooh.commons.platform.ejb.model.vo.BaseVO;
import com.parvanpajooh.commons.platform.ejb.util.interceptor.CrossCuttingInterceptor;
import com.parvanpajooh.commons.util.Validator;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanServiceException;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanUnrecoverableException;
import com.parvanpajooh.issuemanager.service.GenericLocalService;
import com.parvanpajooh.issuemanager.service.GenericService;

/**
 * This class serves as the Base class for all other Services - namely to hold
 * common CRUD methods that they might all use. You should only need to extend
 * this class when your require custom CRUD logic.
 * 
 * <p>
 * To register this class in your Spring context file, use the following XML.
 * 
 * <pre>
 *     &lt;bean id="userService" class="com.parvanpajooh.mediabank.services.GenericServiceImpl"&gt;
 *         &lt;constructor-arg&gt;
 *             &lt;bean class="com.parvanpajooh.mediabank.localService.jpa.GenericDaoHibernate"&gt;
 *                 &lt;constructor-arg value="com.parvanpajooh.mediabank.domain.Media"/&gt;
 *                 &lt;property name="sessionFactory" ref="sessionFactory"/&gt;
 *             &lt;/bean&gt;
 *         &lt;/constructor-arg&gt;
 *     &lt;/bean&gt;
 * </pre>
 * 
 * @param <T>
 *            a type variable
 * @param <PK>
 *            the primary key for that type
 */
@Interceptors(CrossCuttingInterceptor.class)
public class GenericServiceImpl<T, PK extends Serializable> implements
		GenericService<T, PK> {
	/**
	 * Log variable for all child classes.
	 */
	static final Logger log = LoggerFactory.getLogger(GenericServiceImpl.class);

	/**
	 * GenericDao instance, set by constructor of child classes
	 */
	protected GenericLocalService<T, PK> localService;

	public GenericServiceImpl() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.parvanpajooh.platform.parvan.entityconfig.service.GenericService#
	 * findAll(com.parvanpajooh.platform.parvan.common.UserInfo)
	 */
	/* @TransactionAttribute(TransactionAttributeType.SUPPORTS) */
	public List<BaseVO> findAll(UserInfo userInfo)
			throws ParvanServiceException {
		// log.debug("findAll");

		List<BaseVO> listVO = null;

		try {
			// find list
			List<T> list = localService.findAll();

			listVO = new ArrayList<BaseVO>(list.size());
			for (T baseObject : list) {

				BaseVO baseVO = toVO(baseObject, true, userInfo);

				listVO.add(baseVO);
			}

		} catch (Exception e) {
			throw new ParvanUnrecoverableException(
					"Error occurred while getting list of all records.", e);
		}

		return listVO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.parvanpajooh.platform.parvan.entityconfig.service.GenericService#
	 * findAllPaged(com.parvanpajooh.platform.parvan.common.UserInfo, int, int,
	 * com.parvanpajooh.platform.parvan.common.enums.SortDirectionEnum,
	 * java.lang.String)
	 */
	/* @TransactionAttribute(TransactionAttributeType.SUPPORTS) */
	public List<BaseVO> findAll(UserInfo userInfo, int firstResult,
			int maxResults, SortDirectionEnum sortDirection,
			String sortCriterion) throws ParvanServiceException {

		log.debug("findList");

		List<BaseVO> listVO = null;

		try {
			// find list
			List<T> list = localService.findAll(firstResult, maxResults,
					sortDirection, sortCriterion);

			listVO = new ArrayList<BaseVO>(list.size());
			for (T baseObject : list) {

				BaseVO baseVO = toVO(baseObject, true, userInfo);

				listVO.add(baseVO);
			}

		} catch (Exception e) {
			throw new ParvanUnrecoverableException(
					"Error occurred while getting paged-list of records.", e);
		}

		return listVO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.parvanpajooh.platform.parvan.entityconfig.service.GenericService#
	 * findCount(com.parvanpajooh.platform.parvan.common.UserInfo)
	 */
	/* @TransactionAttribute(TransactionAttributeType.SUPPORTS) */
	public int count(UserInfo userInfo) throws ParvanServiceException {

		log.debug("find count ");

		int count = 0;

		try {
			// get count
			count = localService.count();

		} catch (Exception e) {
			throw new ParvanUnrecoverableException(
					"Error occurred while getting count of all records.", e);
		}

		return count;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.parvanpajooh.platform.parvan.entityconfig.service.GenericService#
	 * findByIds(com.parvanpajooh.platform.parvan.common.UserInfo,
	 * java.util.List)
	 */
	/* @TransactionAttribute(TransactionAttributeType.SUPPORTS) */
	public List<BaseVO> findByIds(UserInfo userInfo, List<Long> id)
			throws ParvanServiceException {

		log.debug("findByIds");

		List<BaseVO> listVO = null;

		try {
			// find list
			List<T> list = localService.findByIds(id);

			listVO = new ArrayList<BaseVO>(list.size());
			for (T baseObject : list) {

				BaseVO baseVO = toVO(baseObject, true, userInfo);

				listVO.add(baseVO);
			}

		} catch (Exception e) {
			throw new ParvanUnrecoverableException(
					"Error occurred while getting list of records by their IDs.",
					e);
		}

		return listVO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.parvanpajooh.platform.parvan.entityconfig.service.GenericService#
	 * findByCriteria(com.parvanpajooh.platform.parvan.common.UserInfo,
	 * com.parvanpajooh.platform.parvan.common.vo.BaseCriteria)
	 */
	/* @TransactionAttribute(TransactionAttributeType.SUPPORTS) */
	public List<BaseVO> findByCriteria(UserInfo userInfo, BaseCriteria criteria)
			throws ParvanServiceException {

		log.debug("findByCriteria");

		List<BaseVO> listVO = null;

		try {

			// find by criteria
			List<T> list = localService.findByCriteria(criteria);

			listVO = new ArrayList<BaseVO>(list.size());
			for (T baseObject : list) {

				BaseVO baseVO = toVO(baseObject, true, userInfo);

				listVO.add(baseVO);
			}

		} catch (Exception e) {
			throw new ParvanUnrecoverableException(
					"Error occurred while getting list of records by criteria.",
					e);
		}

		return listVO;
	}
	
	@Override
	/* @TransactionAttribute(TransactionAttributeType.SUPPORTS) */
	public List<Long> findIdsByCriteria(UserInfo userInfo, BaseCriteria criteria)
			throws ParvanServiceException {
		
		log.debug("findIdsByCriteria (userInfo={}, criteria={})", userInfo, criteria);
		
		List<Long> list = null;
		
		try {
			
			// find by criteria
			list = localService.findIdsByCriteria(criteria);
			
		} catch (Exception e) {
			throw new ParvanUnrecoverableException(
					"Error occurred while getting list of record's ids by criteria.",
					e);
		}
		
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.parvanpajooh.platform.parvan.entityconfig.service.GenericService#
	 * findByCriteria(com.parvanpajooh.platform.parvan.common.UserInfo,
	 * com.parvanpajooh.platform.parvan.common.vo.BaseCriteria, int, int,
	 * com.parvanpajooh.platform.parvan.common.enums.SortDirectionEnum,
	 * java.lang.String)
	 */
	/* @TransactionAttribute(TransactionAttributeType.SUPPORTS) */
	public List<BaseVO> findByCriteria(UserInfo userInfo,
			BaseCriteria criteria, int firstResult, int maxResults,
			SortDirectionEnum sortDirection, String sortCriterion)
			throws ParvanServiceException {

		log.debug("find pagination T by " + criteria);

		List<BaseVO> listVO = null;

		try {
			// find by criteria
			List<T> list = localService.findByCriteria(criteria, firstResult,
					maxResults, sortDirection, sortCriterion);

			listVO = new ArrayList<BaseVO>(list.size());
			for (T baseObject : list) {

				BaseVO baseVO = toVO(baseObject, true, userInfo);

				listVO.add(baseVO);
			}

		} catch (Exception e) {
			throw new ParvanUnrecoverableException(
					"Error occurred while getting paged-list of records by criteria.",
					e);
		}

		return listVO;
	}
	
	@Override
	/* @TransactionAttribute(TransactionAttributeType.SUPPORTS) */
	public List<Long> findIdsByCriteria(UserInfo userInfo,
			BaseCriteria criteria, int firstResult, int maxResults,
			SortDirectionEnum sortDirection, String sortCriterion)
					throws ParvanServiceException {
		
		log.debug("findIdsByCriteria (userInfo={}, criteria={})", userInfo, criteria);
		
		List<Long> list = null;
		
		try {
			// find by criteria
			list = localService.findIdsByCriteria(criteria, firstResult, maxResults, sortDirection, sortCriterion);
			
		} catch (Exception e) {
			throw new ParvanUnrecoverableException(
					"Error occurred while getting paged-list of record's ids by criteria.",
					e);
		}
		
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.parvanpajooh.platform.parvan.entityconfig.service.GenericService#
	 * countByCriteria(com.parvanpajooh.platform.parvan.common.UserInfo,
	 * com.parvanpajooh.platform.parvan.common.vo.BaseCriteria)
	 */
	/* @TransactionAttribute(TransactionAttributeType.SUPPORTS) */
	public int countByCriteria(UserInfo userInfo, BaseCriteria criteria)
			throws ParvanServiceException {

		log.debug("get count by " + criteria);

		int count = 0;

		try {
			// get count by criteria
			count = localService.countByCriteria(criteria);

		} catch (Exception e) {
			throw new ParvanUnrecoverableException(
					"Error occurred while getting count of records by criteria.",
					e);
		}

		return count;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.parvanpajooh.platform.parvan.entityconfig.service.GenericService#
	 * get(com.parvanpajooh.platform.parvan.common.UserInfo,
	 * java.io.Serializable)
	 */
	/* @TransactionAttribute(TransactionAttributeType.SUPPORTS) */
	public BaseVO get(UserInfo userInfo, PK id) throws ParvanServiceException {

		log.debug("get");

		BaseVO entityVO = null;

		try {
			// get entity
			T entity = localService.get(id);

			entityVO = toVO(entity, true, userInfo);

		} catch (Exception e) {
			log.error("Error occurred while getting records by its ID.", e);
			throw new ParvanUnrecoverableException(
					"Error occurred while getting records by its ID.", e);
		}

		return entityVO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.parvanpajooh.platform.parvan.entityconfig.service.GenericService#
	 * exists(com.parvanpajooh.platform.parvan.common.UserInfo,
	 * java.io.Serializable)
	 */
	/* @TransactionAttribute(TransactionAttributeType.SUPPORTS) */
	public boolean exists(UserInfo userInfo, PK id)
			throws ParvanServiceException {

		log.debug("exists");

		boolean exists = false;

		try {
			// check existence
			exists = localService.exists(id);

		} catch (Exception e) {
			throw new ParvanUnrecoverableException(
					"Error occurred while checking record existence.", e);
		}

		return exists;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.parvanpajooh.issuemanager.service.GenericService#save(com.parvanpajooh
	 * .common.UserInfo, com.parvanpajooh.common.vo.BaseVO)
	 */
	public BaseVO save(UserInfo userInfo, BaseVO baseVO)
			throws ParvanServiceException {

		log.debug("save");

		BaseVO entityVO = null;

		try {

			if (userInfo != null && Validator.isNotNull(userInfo.getUserId())) {
				baseVO.setCreateUserId(userInfo.getUserId());
				baseVO.setUpdateUserId(userInfo.getUserId());
			}

			// save entity
			entityVO = localService.save(baseVO, userInfo);

		} catch (ParvanServiceException e) {
			throw e;

		} catch (Exception e) {
			throw new ParvanUnrecoverableException(
					"Error occurred while saving object.", e);
		}

		return entityVO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.parvanpajooh.platform.parvan.entityconfig.service.GenericService#
	 * delete(com.parvanpajooh.platform.parvan.common.UserInfo,
	 * java.io.Serializable)
	 */
	public void delete(UserInfo userInfo, PK id) throws ParvanServiceException {

		log.debug("Entering delete( userInfo={}, id={})", userInfo, id);

		try {
			// delete entity
			localService.delete(id, userInfo);

		} catch (ParvanServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ParvanUnrecoverableException("Error occurred while .", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.parvanpajooh.platform.parvan.entityconfig.service.GenericService#
	 * delete(com.parvanpajooh.platform.parvan.common.UserInfo,
	 * java.io.Serializable)
	 */
	public void delete(UserInfo userInfo, PK... ids)
			throws ParvanServiceException {

		log.debug("Entering delete( userInfo={}, ids={})", userInfo, ids);

		try {
			// delete entity
			localService.delete(userInfo, ids);

		} catch (Exception e) {
			throw new ParvanUnrecoverableException("Error occurred while .", e);
		}
	}

	@Override
	public void export(UserInfo userInfo, OutputStream out, List<String> fields, 
			List<String> columnsNames,
			BaseCriteria criteria,
			SortDirectionEnum sortDirection, String sortCriterion,
			ListExportFormatEnum listExportFormat)
			throws ParvanServiceException {
		
		log.debug("Entering export");
		
		try {
			// delete entity
			localService.export(out, fields, columnsNames, criteria,
					sortDirection, sortCriterion,
					listExportFormat);

		} catch (Exception e) {
			throw new ParvanUnrecoverableException("Error occurred while exporting.", e);
		}
		
		log.debug("Exit export");
	}

	protected BaseVO toVO(T entity, boolean isLite, UserInfo userInfo)
			throws ParvanServiceException {
		BaseVO baseVO = null;

		if (isLite) {
			baseVO = ((BaseModel) entity).toVOLite();
		} else {
			baseVO = ((BaseModel) entity).toVO();
		}

		return baseVO;
	}
}
