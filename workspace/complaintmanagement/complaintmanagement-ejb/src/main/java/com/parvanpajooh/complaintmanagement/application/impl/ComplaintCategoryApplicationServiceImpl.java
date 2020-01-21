package com.parvanpajooh.complaintmanagement.application.impl;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.parvanpajooh.commons.platform.ejb.ddd.application.impl.GenericApplicationServiceImpl;
import com.parvanpajooh.complaintmanagement.application.ComplaintCategoryApplicationService;
import com.parvanpajooh.complaintmanagement.domain.model.ComplaintCategory;
import com.parvanpajooh.complaintmanagement.domain.service.ComplaintCategoryDomainService;

@Stateless
public class ComplaintCategoryApplicationServiceImpl extends GenericApplicationServiceImpl<ComplaintCategory, Long>
		implements ComplaintCategoryApplicationService {

	static final Logger log = LoggerFactory.getLogger(ComplaintCategoryApplicationServiceImpl.class);

	private ComplaintCategoryDomainService compliantCategoryDomainService;

	@Inject
	public void setCompliantCategoryDomainService(ComplaintCategoryDomainService compliantCategoryDomainService) {
		this.domainService = compliantCategoryDomainService;
		this.compliantCategoryDomainService = compliantCategoryDomainService;
	}

}
