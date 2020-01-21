package com.parvanpajooh.complaintmanagement.domain.repository;

import com.parvanpajooh.commons.platform.ejb.ddd.domain.repository.GenericRepository;
import com.parvanpajooh.commons.platform.ejb.exceptions.ParvanDaoException;
import com.parvanpajooh.complaintmanagement.domain.model.ComplaintCategory;

public interface ComplaintCategoryRepository extends GenericRepository<ComplaintCategory, Long> {

	public boolean hasChildren(Long id) throws ParvanDaoException;

}
