package com.parvanpajooh.complaintmanagement.domain.repository;

import java.util.List;

import com.parvanpajooh.commons.platform.ejb.ddd.domain.repository.GenericRepository;
import com.parvanpajooh.commons.platform.ejb.exceptions.ParvanDaoException;
import com.parvanpajooh.complaintmanagement.domain.model.Complaint;

public interface ComplaintRepository extends GenericRepository<Complaint, Long> {

	public List<Complaint> findByComplaintCategoryId(Long complaintCategoryId) throws ParvanDaoException;
}
