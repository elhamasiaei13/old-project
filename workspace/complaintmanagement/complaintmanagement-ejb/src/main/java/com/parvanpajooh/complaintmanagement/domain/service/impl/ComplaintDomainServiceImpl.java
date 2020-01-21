package com.parvanpajooh.complaintmanagement.domain.service.impl;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.parvanpajooh.commons.platform.ejb.ddd.domain.model.dto.BaseDto;
import com.parvanpajooh.commons.platform.ejb.ddd.domain.service.impl.GenericDomainServiceImpl;
import com.parvanpajooh.commons.platform.ejb.exceptions.ParvanServiceException;
import com.parvanpajooh.commons.platform.ejb.exceptions.ParvanUnrecoverableException;
import com.parvanpajooh.commons.platform.ejb.model.CurrentContext;
import com.parvanpajooh.commons.platform.ejb.model.UserInfo;
import com.parvanpajooh.commons.util.Validator;
import com.parvanpajooh.complaintmanagement.domain.model.Complaint;
import com.parvanpajooh.complaintmanagement.domain.model.ComplaintCategory;
import com.parvanpajooh.complaintmanagement.domain.model.dto.ComplaintDto;
import com.parvanpajooh.complaintmanagement.domain.model.enums.ComplaintStatus;
import com.parvanpajooh.complaintmanagement.domain.repository.ComplaintRepository;
import com.parvanpajooh.complaintmanagement.domain.service.ComplaintCategoryDomainService;
import com.parvanpajooh.complaintmanagement.domain.service.ComplaintDomainService;

public class ComplaintDomainServiceImpl extends GenericDomainServiceImpl<Complaint, Long>
		implements ComplaintDomainService {

	static final Logger log = LoggerFactory.getLogger(ComplaintDomainServiceImpl.class);

	@Inject
	private ComplaintCategoryDomainService complaintCategoryDomainService;

	private ComplaintRepository compliantRepository;
	
	@Inject
	public void setPackingTypeRepository(ComplaintRepository compliantRepository) {
		this.repository = compliantRepository;
		this.compliantRepository = compliantRepository;
	}

	@Override
	public BaseDto save(BaseDto baseDto) throws ParvanServiceException {
		
		try {
			UserInfo userInfo = CurrentContext.getCurrentUserInfo().get();
			ComplaintDto complaintDto = (ComplaintDto) baseDto;

			boolean isNew = (baseDto.getId() == null);

			Complaint complaint = new Complaint();
			complaint.fromDto(complaintDto);
			
			if (isNew) {
				complaint.setStatus(ComplaintStatus.NEW);
			} 

			// check for firstName & lastName & userName that not worng
			if (Validator.isNull(complaint.getFirstName()) || Validator.isNull(complaint.getLastName())) {
				complaint.setFirstName(userInfo.getFirstName());
				complaint.setLastName(userInfo.getLastName());
			}

			complaint.setUserName(userInfo.getUserName());

			// check for empty result
			if (Validator.isNotNull(complaint.getResult())) {
				complaint.setStatus(ComplaintStatus.CLOSED);
			}

			Long categoryId = complaintDto.getCategory().getId();
			ComplaintCategory complaintCategory = complaintCategoryDomainService.get(categoryId);
			complaint.setCategory(complaintCategory);
			
			complaint = super.save(complaint);
			baseDto = complaint.toDto();

		} catch (Exception e) {
			throw new ParvanUnrecoverableException("Error occurred while saving object.", e);
		}

		return baseDto;
	}

}
