package com.parvanpajooh.issuemanager.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.parvanpajooh.commons.enums.SortDirectionEnum;
import com.parvanpajooh.commons.platform.ejb.model.vo.BaseCriteria;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanDaoException;

/**
 * Generic DAO (Data Access Object) with common methods to CRUD POJOs.
 *
 * <p>Extend this interface if you want typesafe (no casting necessary) DAO's for your
 * domain objects.
 *
 * @param <T> a type variable
 * @param <PK> the primary key for that type
 */
public interface GenericDao <T, PK extends Serializable> {
	
    /**
     * Generic method used to get all objects of a particular type. This
     * is the same as lookup up all rows in a table.
     * @return List of populated objects
     */
    List<T> findAll() throws ParvanDaoException;

	/**
	 * Generic method used to get pagination objects of a particular type.
	 * 
	 * @param firstResult
	 * @param maxResults
	 * @param sortDirection
	 * @param sortCriterion
	 * @return Pagination list of populated objects
	 * @throws ParvanDaoException
	 */
	List<T> findAll(
			int firstResult, 
			int maxResults,
			SortDirectionEnum sortDirection,
			String sortCriterion) throws ParvanDaoException;
	
	/**
	 * get record count
	 * 
	 * @return
	 */
	int count() throws ParvanDaoException;
    
    /**
     * Generic method used to get all objects of a particular type by ids. This
     * is the same as lookup up all rows in a table by ids.
     * @param ids
     * @return List of populated objects
     * @throws ParvanDaoException
     */
    List<T> findByIds(List<Long> ids) throws ParvanDaoException;
    
    /**
     * Generic method used to find all objects of a particular type by criteria.
     * @param criteria
     * @return List of populated objects
	 * @throws ParvanDaoException
     */
    List<T> findByCriteria(BaseCriteria criteria) throws ParvanDaoException;
    
    List<Long> findIdsByCriteria(BaseCriteria criteria) throws ParvanDaoException;
    
    /**
     * Generic method used to find all objects of a particular type by criteria.
     * 
     * @param criteria
     * @param firstResult
     * @param maxResults
     * @param sortDirection
     * @param sortCriterion
     * @return
	 * @throws ParvanDaoException
     */
    public List<T> findByCriteria(
    		BaseCriteria criteria, 
    		int firstResult, 
    		int maxResults,
			SortDirectionEnum sortDirection,
            String sortCriterion) throws ParvanDaoException;
	
    public List<Long> findIdsByCriteria(
    		BaseCriteria criteria, 
    		int firstResult, 
    		int maxResults,
    		SortDirectionEnum sortDirection,
    		String sortCriterion) throws ParvanDaoException;
    
	/**
	 * get record count
	 * 
	 * @param criteria
	 * @return
	 * @throws ParvanDaoException
	 */
	int countByCriteria(BaseCriteria criteria) throws ParvanDaoException;

    /**
     * Generic method to get an object based on class and identifier. An
     * ObjectRetrievalFailureException Runtime Exception is thrown if
     * nothing is found.
     *
     * @param id the identifier (primary key) of the object to get
     * @return a populated object
	 * @throws ParvanDaoException
     */
    T get(PK id) throws ParvanDaoException;

    /**
     * Checks for existence of an object of type T using the id arg.
     * @param id the id of the entity
     * @return - true if it exists, false if it doesn't
	 * @throws ParvanDaoException
     */
    boolean exists(PK id) throws ParvanDaoException;

    /**
     * Generic method to save an object - handles both update and insert.
     * @param object the object to save
     * @return the persisted object
	 * @throws ParvanDaoException
     */
    T save(T object) throws ParvanDaoException;
    
    /**
     * 
     * @param objects
     * @return
     * @throws ParvanDaoException
     */
    Collection<T> bulkSave(List<T> objects) throws ParvanDaoException;

    /**
     * Generic method to delete an object based on class and id
     * @param id the identifier (primary key) of the object to remove
	 * @throws ParvanDaoException
     */
    void delete(PK id) throws ParvanDaoException;
    
    /**
     * Generic method to delete an object based on class and id
     * @param object to remove
	 * @throws ParvanDaoException
     */
    void delete(T obj) throws ParvanDaoException;
    
    /**
     * Generic method to delete an object based on class and id
     * @param object to remove
	 * @throws ParvanDaoException
     */
    void deleteByIds(PK[] ids) throws ParvanDaoException;
    
    /**
     * refreshes entity(this action completely resets a state of entity)
     * @param obj
	 * @throws ParvanDaoException
     */
    public void refresh(T obj) throws ParvanDaoException;
}