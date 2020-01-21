package com.parvanpajooh.complaintmanagement.mvc;

import java.util.List;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.dandelion.datatables.core.ajax.ColumnDef;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import com.github.dandelion.datatables.core.ajax.DatatablesResponse;
import com.github.dandelion.datatables.extras.spring3.ajax.DatatablesParams;
import com.parvanpajooh.commons.platform.ejb.ddd.application.GenericApplicationService;
import com.parvanpajooh.commons.util.Validator;
import com.parvanpajooh.complaintmanagement.application.ComplaintApplicationService;
import com.parvanpajooh.complaintmanagement.domain.model.Complaint;
import com.parvanpajooh.complaintmanagement.domain.model.criteria.ComplaintCriteria;
import com.parvanpajooh.complaintmanagement.domain.model.dto.ComplaintDto;
import com.parvanpajooh.complaintmanagement.mvc.base.ComplaintManagementAbstractController;

@Controller
@RequestMapping("/complaint")
public class ComplaintController extends ComplaintManagementAbstractController<Complaint, ComplaintDto, ComplaintCriteria> {

    @EJB(mappedName = "java:global/complaintmanagement-ear/complaintmanagement-ejb/ComplaintApplicationServiceImpl")
    private ComplaintApplicationService complaintApplicationService;

    @Override
    protected GenericApplicationService<Complaint, Long> getService() {
    	return complaintApplicationService;
    }
    
    @Override
    protected String getName() {
    	return "complaint";
    }
    
    @Override
    public DatatablesResponse<ComplaintDto> paging(@DatatablesParams DatatablesCriterias criterias, ComplaintCriteria criteria,
    		HttpServletRequest request) {
    	
    	List<ColumnDef> sortedCols = criterias.getSortedColumnDefs();
    	if (Validator.isNotNull(sortedCols)) {
    		ColumnDef sortCol = sortedCols.get(0);
    		String sortedColdName = sortCol.getName();
    		if (sortedColdName.equals("fullName")) {
    			sortCol.setName("firstName");
    			sortedCols.set(0, sortCol);
    		}
    	}
    	
    	return super.paging(criterias, criteria, request);
    }

}
