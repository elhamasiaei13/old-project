package com.parvanpajooh.complaintmanagement.domain.repository.jpa;

import java.util.List;

import javax.persistence.TypedQuery;

import com.parvanpajooh.commons.platform.ejb.ddd.domain.repository.jpa.GenericRepositoryJpa;
import com.parvanpajooh.commons.platform.ejb.exceptions.ParvanDaoException;
import com.parvanpajooh.complaintmanagement.domain.model.ComplaintCategory;
import com.parvanpajooh.complaintmanagement.domain.repository.ComplaintCategoryRepository;

public class ComplaintCategoryReposirotyJpa extends GenericRepositoryJpa<ComplaintCategory, Long>
		implements ComplaintCategoryRepository {

	public ComplaintCategoryReposirotyJpa() {
		super(ComplaintCategory.class);
	}
	
	@Override
	public boolean hasChildren(Long id) throws ParvanDaoException {

		// LOG
		log.trace("Entering hasChildren(id={})", id);

		Boolean hasChildren = false;

		try {
			// Make query
			TypedQuery<ComplaintCategory> q = getEntityManager().createQuery("from ComplaintCategory obj where obj.parent.id = ? " , ComplaintCategory.class);

			q.setParameter(1, id);

			List<ComplaintCategory> list = q.getResultList();

			if (list != null && list.size() > 0) {
				hasChildren = true;
			}

		} catch (Exception e) {
			throw new ParvanDaoException("Error occurred while checking if ComplaintCategory [" + id + "] has any child", e);
		}

		return hasChildren;
	}

}
