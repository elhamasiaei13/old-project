package com.parvanpajooh.issuemanager.service;

import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;

import com.parvanpajooh.commons.enums.SortDirectionEnum;
import com.parvanpajooh.commons.platform.ejb.model.UserInfo;
import com.parvanpajooh.commons.platform.ejb.model.enums.ListExportFormatEnum;
import com.parvanpajooh.commons.platform.ejb.model.vo.BaseCriteria;
import com.parvanpajooh.commons.platform.ejb.model.vo.BaseVO;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanServiceException;

/**
 * Generic Service that talks to GenericDao to CRUD POJOs.
 *
 * <p>Extend this interface if you want typesafe (no casting necessary) services
 * for your domain objects.
 *
 * @param <T> a type variable
 * @param <PK> the primary key for that type
 */
public interface GenericService<T, PK extends Serializable> {
	
	/**
	 * Generic method used to get all objects of a particular type. This
     * is the same as lookup up all rows in a table.
	 * @param userInfo
	 * @return List of populated objects
	 * @throws ParvanServiceException
	 */
	public List<BaseVO> findAll(UserInfo userInfo) throws ParvanServiceException;
	
	/**
	 * 
	 * @param userInfo
	 * @return
	 * @throws ParvanServiceException
	 */
	public int count(UserInfo userInfo) throws ParvanServiceException;

	/**
	 * Generic method used to get pagination objects of a particular type.
	 * @param userInfo
	 * @param firstResult
	 * @param maxResults
	 * @param sortDirection
	 * @param sortCriterion
	 * @return
	 * @throws ParvanServiceException
	 */
	public List<BaseVO> findAll(
			UserInfo userInfo, 
			int firstResult, 
			int maxResults,
			SortDirectionEnum sortDirection,
			String sortCriterion) throws ParvanServiceException;
	
	/**
	 * Generic method used to get all objects of a particular type with ids. This
	 * is the same as lookup up all rows in a table by ids.
	 * @param userInfo
	 * @param id
	 * @return List of populated objects
	 * @throws ParvanServiceException
	 */
	public List<BaseVO> findByIds(UserInfo userInfo, List<Long> id) throws ParvanServiceException;
	
	/**
	 * Generic method used to get all objects of a particular type by criteria. 
	 * @param userInfo
	 * @param criteria
	 * @return List of populated objects
	 * @throws ParvanServiceException
	 */
	public List<BaseVO> findByCriteria(UserInfo userInfo, BaseCriteria criteria) throws ParvanServiceException;
	
	/**
	 * 
	 * @param userInfo
	 * @param criteria
	 * @return
	 * @throws ParvanServiceException
	 */
	public List<Long> findIdsByCriteria(UserInfo userInfo, BaseCriteria criteria) throws ParvanServiceException;
	
	/**
	 * Generic method used to get all objects of a particular type by criteria. 
	 * @param userInfo
	 * @param criteria
	 * @param firstResult
	 * @param maxResults
	 * @param sortDirection
	 * @param sortCriterion
	 * @return
	 * @throws ParvanServiceException
	 */
	public List<BaseVO> findByCriteria(
			UserInfo userInfo, 
			BaseCriteria criteria, 
			int firstResult, 
			int maxResults,
			SortDirectionEnum sortDirection,
			String sortCriterion) throws ParvanServiceException;
	
	/**
	 * 
	 * @param userInfo
	 * @param criteria
	 * @param firstResult
	 * @param maxResults
	 * @param sortDirection
	 * @param sortCriterion
	 * @return
	 * @throws ParvanServiceException
	 */
	public List<Long> findIdsByCriteria(
			UserInfo userInfo, 
			BaseCriteria criteria,  
			int firstResult, 
			int maxResults,
			SortDirectionEnum sortDirection,
			String sortCriterion) throws ParvanServiceException;
	
	/**
	 * 
	 * @param userInfo
	 * @param criteria
	 * @return
	 * @throws ParvanServiceException
	 */
	public int countByCriteria(UserInfo userInfo, BaseCriteria criteria) throws ParvanServiceException;
	
    /**
     * Checks for existence of an object of type T using the id arg.
     * @param userInfo
     * @param id the identifier (primary key) of the object to get
     * @return - true if it exists, false if it doesn't
     * @throws ParvanServiceException
     */
	public boolean exists(UserInfo userInfo, PK id) throws ParvanServiceException;
    
    /**
     * Generic method to get an object based on class and identifier. An
     * ObjectRetrievalFailureException Runtime Exception is thrown if
     * nothing is found.
     * @param userInfo
     * @param id the identifier (primary key) of the object to get
     * @return a populated object
     * @throws ParvanServiceException
     */
	public BaseVO get(UserInfo userInfo, PK id) throws ParvanServiceException;
	
    /**
     * Generic method to save an object - handles both update and insert.
     * @param userInfo
     * @param object the object to save
     * @param object
     * @return the updated object
     * @throws ParvanServiceException
     */
	public BaseVO save(UserInfo userInfo, BaseVO object) throws ParvanServiceException;
    
    /**
     * Generic method to delete an object based on class and id
     * @param userInfo
     * @param id the identifier (primary key) of the object to remove
     * @throws ParvanServiceException
     */
	public void delete(UserInfo userInfo, PK id) throws ParvanServiceException;
	
	/**
	 * Generic method to delete an object based on class and id
	 * @param userInfo
	 * @param id the identifier (primary key) of the object to remove
	 * @throws ParvanServiceException
	 */
	public void delete(UserInfo userInfo, PK...id) throws ParvanServiceException;
	
	/**
	 * Generic method to export all objects of a particular type by criteria
	 * @param userInfo
	 * @param out
	 * @param fields
	 * @param columnsNames
	 * @param criteria
	 * @param sortDirection
	 * @param sortCriterion
	 * @param listExportFormat
	 * @throws ParvanServiceException
	 */
	public void export(UserInfo userInfo, OutputStream out, List<String> fields, 
			List<String> columnsNames,
			BaseCriteria criteria,
			SortDirectionEnum sortDirection, 
			String sortCriterion,
			ListExportFormatEnum listExportFormat) throws ParvanServiceException;
}
