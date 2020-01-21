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
public interface GenericLocalService<T, PK extends Serializable>{
	
	/**
     * Generic method used to get all objects of a particular type. This
     * is the same as lookup up all rows in a table.
     * @return List of populated objects
     */
	public List<T> findAll() throws ParvanServiceException;

	/**
	 * Generic method used to get pagination objects of a particular type.
	 * @param firstResult
	 * @param maxResults
	 * @param sortDirection
	 * @param sortCriterion
	 * @return
	 */
	public List<T> findAll( 
			int firstResult, 
			int maxResults,
			SortDirectionEnum sortDirection,
			String sortCriterion) throws ParvanServiceException;
	
	/**
	 * 
	 * @return
	 * @throws ParvanServiceException
	 */
	public int count() throws ParvanServiceException;
	
	/**
	 * Generic method used to get all objects of a particular type with ids. This
	 * is the same as lookup up all rows in a table by ids.
	 * @param ids
	 * @return List of populated objects
	 * @throws ParvanServiceException
	 */
	public List<T> findByIds(List<Long> ids) throws ParvanServiceException;
	
	/**
	 * Generic method used to get all objects of a particular type by criteria. 
	 * @param criteria
	 * @return List of populated objects
	 * @throws ParvanServiceException
	 */
	public List<T> findByCriteria(BaseCriteria criteria) throws ParvanServiceException;
	
	/**
	 * 
	 * @param criteria
	 * @return
	 * @throws ParvanServiceException
	 */
	public List<Long> findIdsByCriteria(BaseCriteria criteria) throws ParvanServiceException;
	
	/**
	 * 
	 * @param criteria
	 * @param firstResult
	 * @param maxResults
	 * @param sortDirection
	 * @param sortCriterion
	 * @return
	 * @throws ParvanServiceException
	 */
	public List<T> findByCriteria(
			BaseCriteria criteria,  
			int firstResult, 
			int maxResults,
			SortDirectionEnum sortDirection,
			String sortCriterion) throws ParvanServiceException;
	
	/**
	 * 
	 * @param criteria
	 * @param firstResult
	 * @param maxResults
	 * @param sortDirection
	 * @param sortCriterion
	 * @return
	 * @throws ParvanServiceException
	 */
	public List<Long> findIdsByCriteria(
			BaseCriteria criteria,  
			int firstResult, 
			int maxResults,
			SortDirectionEnum sortDirection,
			String sortCriterion) throws ParvanServiceException;
	
	/**
	 * 
	 * @param criteria
	 * @return
	 * @throws ParvanServiceException
	 */
	public int countByCriteria(BaseCriteria criteria) throws ParvanServiceException;
    
    /**
     * 
     * Generic method to get an object based on class and identifier. An
     * ObjectRetrievalFailureException Runtime Exception is thrown if
     * nothing is found.
     * @param id the identifier (primary key) of the object to get
     * @return a populated object
     * @throws ParvanServiceException
     */
	public T get(PK id) throws ParvanServiceException;
	
    /**
     * Checks for existence of an object of type T using the id arg.
     * @param id the identifier (primary key) of the object to get
     * @return - true if it exists, false if it doesn't
     * @throws ParvanServiceException
     */
	public boolean exists(PK id) throws ParvanServiceException;
	
    /**
     * Generic method to save an object - handles both update and insert.
     * @param object the object to save
     * @return the updated object
     * @throws ParvanServiceException
     */
    public T save(T object, UserInfo userInfo) throws ParvanServiceException;
    
    public BaseVO save(BaseVO baseVO, UserInfo userInfo) throws ParvanServiceException;
    
    /**
     * Generic method to delete an object based on class and id
     * @param id the identifier (primary key) of the object to remove
     * @throws ParvanServiceException
     */
    public void delete(PK id, UserInfo userInfo) throws ParvanServiceException;
    
    /**
     * Generic method to delete an object
     * @param object to remove
     * @throws ParvanServiceException
     */
    public void delete(T object, UserInfo userInfo) throws ParvanServiceException;
    
    /**
     * Generic method to delete an object based on class and id
     * @param id the identifier (primary key) of the object to remove
     * @throws ParvanServiceException
     */
    public void delete(UserInfo userInfo, PK...ids) throws ParvanServiceException;

    /**
     * 
     * @param out
     * @param fields
     * @param columnsNames
     * @param criteria
     * @param sortDirection
     * @param sortCriterion
     * @param listExportFormat
     * @throws ParvanServiceException
     */
	public void export(OutputStream out, List<String> fields,
			List<String> columnsNames, 
			BaseCriteria criteria, SortDirectionEnum sortDirection,
			String sortCriterion, ListExportFormatEnum listExportFormat) throws ParvanServiceException;
}
