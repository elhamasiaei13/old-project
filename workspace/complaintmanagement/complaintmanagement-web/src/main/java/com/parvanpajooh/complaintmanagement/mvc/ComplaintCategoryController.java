package com.parvanpajooh.complaintmanagement.mvc;

import javax.ejb.EJB;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.parvanpajooh.commons.platform.ejb.ddd.application.GenericApplicationService;
import com.parvanpajooh.complaintmanagement.application.ComplaintCategoryApplicationService;
import com.parvanpajooh.complaintmanagement.domain.model.ComplaintCategory;
import com.parvanpajooh.complaintmanagement.domain.model.criteria.ComplaintCategoryCriteria;
import com.parvanpajooh.complaintmanagement.domain.model.dto.ComplaintCategoryDto;
import com.parvanpajooh.complaintmanagement.mvc.base.ComplaintManagementAbstractController;

@Controller
@RequestMapping("/complaintCategory")
public class ComplaintCategoryController extends
		ComplaintManagementAbstractController<ComplaintCategory, ComplaintCategoryDto, ComplaintCategoryCriteria> {

	@EJB(mappedName = "java:global/complaintmanagement-ear/complaintmanagement-ejb/ComplaintCategoryApplicationServiceImpl")
	private ComplaintCategoryApplicationService complaintCategoryApplicationService;

	@Override
	protected GenericApplicationService<ComplaintCategory, Long> getService() {
		return complaintCategoryApplicationService;
	}
	
	@Override
	protected String getName() {
		return "complaintcategory";
	}

}
