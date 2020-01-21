package com.parvanpajooh.complaintmanagement.application.impl;


import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.parvanpajooh.commons.platform.ejb.ddd.application.impl.GenericApplicationServiceImpl;
import com.parvanpajooh.complaintmanagement.application.ComplaintApplicationService;
import com.parvanpajooh.complaintmanagement.domain.model.Complaint;
import com.parvanpajooh.complaintmanagement.domain.service.ComplaintDomainService;

@Stateless
public class ComplaintApplicationServiceImpl extends GenericApplicationServiceImpl<Complaint, Long>
		implements ComplaintApplicationService {

	static final Logger log = LoggerFactory.getLogger(ComplaintApplicationServiceImpl.class);

	private ComplaintDomainService complaintDomainService;

	@Inject
	public void setCompliantDomainService(ComplaintDomainService compliantDomainService) {
		this.domainService = compliantDomainService;
		this.complaintDomainService = compliantDomainService;
	}
	
}
