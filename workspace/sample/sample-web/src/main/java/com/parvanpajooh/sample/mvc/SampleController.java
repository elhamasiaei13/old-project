package com.parvanpajooh.sample.mvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.dandelion.datatables.core.ajax.ColumnDef;
import com.github.dandelion.datatables.core.ajax.ColumnDef.SortDirection;
import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import com.github.dandelion.datatables.core.ajax.DatatablesResponse;
import com.github.dandelion.datatables.extras.spring3.ajax.DatatablesParams;
import com.parvanpajooh.client.common.PageList;
import com.parvanpajooh.common.auth.UserInfoLoader;
import com.parvanpajooh.commons.enums.SortDirectionEnum;
import com.parvanpajooh.commons.platform.ejb.exceptions.ObjectNotFoundException;
import com.parvanpajooh.commons.platform.ejb.exceptions.ParvanServiceException;
import com.parvanpajooh.commons.platform.ejb.model.UserInfo;
import com.parvanpajooh.commons.platform.ejb.model.vo.BaseVO;
import com.parvanpajooh.commons.platform.ejb.service.GenericService;
import com.parvanpajooh.commons.util.Validator;
import com.parvanpajooh.sample.model.Sample;
import com.parvanpajooh.sample.model.criteria.SampleCriteria;
import com.parvanpajooh.sample.model.vo.SampleVO;
import com.parvanpajooh.sample.mvc.base.SampleAbstractController;
import com.parvanpajooh.sample.service.SampleService;

/**
 * 
 * @author
 *
 */
@Controller
@RequestMapping("/generalAgent")
public class SampleController extends SampleAbstractController<Sample, SampleVO, SampleCriteria> {

	@EJB(mappedName = "java:global/sample-ear/sample-ejb/SampleServiceImpl")
	private SampleService sampleService;


	@Override
	protected GenericService<Sample, Long> getService() {
		return sampleService;
	}

	@Override
	protected String getName() {
		return "samples";
	}

	/**
	 * 
	 * @param id
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/{id}/employees/add", method = RequestMethod.GET)
	public String addEmployees(@PathVariable Long id, Model model,HttpServletRequest request) {
		log.debug("Entering addEmployees (generalAgentId={})", id);
		
		Map<String, Object> result = new HashMap<String, Object>();
		SampleVO agentVO = null;
		
		try {

			UserInfo userInfo = UserInfoLoader.getInstance().getUserInfo();
			agentVO = (SampleVO) sampleService.get(userInfo, id);

		} catch (Exception e) {

			proccessException(e, result);
		}
		model.addAttribute("generalAgentName", agentVO.getName());
		model.addAttribute("generalAgentId", id);
		return "addEmployees";
	}

	/**
	 * 
	 * @param id
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/{id}/detail", method = RequestMethod.GET)
	public String agentDetail(@PathVariable Long id, Model model, HttpServletRequest request) {
		log.debug("Entering agentDetail (generalAgentId={})", id);
		Map<String, Object> result = new HashMap<String, Object>();
		SampleVO agentVO = null;

		try {
			UserInfo userInfo = UserInfoLoader.getInstance().getUserInfo();
			agentVO = (SampleVO) sampleService.get(userInfo, id);

			result.put("status", "success");
			
		} catch (Exception e) {

			proccessException(e, result);
		}
		model.addAttribute("generalAgentId", id);
		model.addAttribute("generalAgentVO", agentVO);
		return "agentDetail";
	}

	
	/**
	 * 
	 * @param criterias
	 * @param criteria
	 * @param term
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/parents/paging", method = RequestMethod.GET)
	public @ResponseBody DatatablesResponse<BaseVO> findAgents(@DatatablesParams DatatablesCriterias criterias, SampleCriteria criteria,String term, HttpServletRequest request) {
		log.debug("Entering paging(criteria={})", criteria);

		int firstResult = criterias.getStart();
		int maxResults = criterias.getLength();
		ColumnDef columnDef = criterias.getSortedColumnDefs().get(0);
		SortDirectionEnum sortDirection = getDefaultSortDirection();
		String sortCriterion = columnDef.getName();

		if ("0".equals(sortCriterion)) {
			sortCriterion = getDefaultSortCriterion();
		}

		if (columnDef.getSortDirection().equals(SortDirection.DESC)) {
			sortDirection = SortDirectionEnum.Descending;
		}
		
		criteria.setName(term);
				
		DataSet<BaseVO> dataSet;
		try {
			UserInfo userInfo = UserInfoLoader.getInstance().getUserInfo();
			List<BaseVO> rows = sampleService.findByCriteria(userInfo, criteria, firstResult, maxResults, sortDirection, sortCriterion);

			long totalRecords = getService().countByCriteria(userInfo, criteria);
			dataSet = new DataSet<BaseVO>(rows, (long) rows.size(), totalRecords);
		} catch (Exception e) {
			log.error("error occured in record paging...", e);
			e.printStackTrace();
			dataSet = new DataSet<BaseVO>(new ArrayList<BaseVO>(), 0l, 0l);
		}
		return DatatablesResponse.build(dataSet, criterias);
	}

}
