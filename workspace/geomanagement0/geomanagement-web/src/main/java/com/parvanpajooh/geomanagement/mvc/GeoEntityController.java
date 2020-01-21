package com.parvanpajooh.geomanagement.mvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
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
import com.parvanpajooh.common.auth.UserInfoLoader;
import com.parvanpajooh.commons.enums.SortDirectionEnum;
import com.parvanpajooh.commons.platform.ejb.exceptions.ObjectNotFoundException;
import com.parvanpajooh.commons.platform.ejb.exceptions.ParvanServiceException;
import com.parvanpajooh.commons.platform.ejb.model.UserInfo;
import com.parvanpajooh.commons.platform.ejb.model.vo.BaseVO;
import com.parvanpajooh.commons.util.Validator;
import com.parvanpajooh.geomanagement.model.criteria.GeoEntityCriteria;
import com.parvanpajooh.geomanagement.model.vo.GeoEntityVO;
import com.parvanpajooh.geomanagement.mvc.base.GeoManagementBaseController;
import com.parvanpajooh.geomanagement.service.GeoEntityService;
import com.parvanpajooh.geomanagement.service.GeoTypeService;

@Controller
@RequestMapping("/geoEntity")
public class GeoEntityController extends GeoManagementBaseController {
	
	@EJB(mappedName="java:global/geomanagement-ear/geomanagement-ejb/GeoEntityServiceImpl") 
	private GeoEntityService geoEntityService;
	
	@EJB(mappedName="java:global/geomanagement-ear/geomanagement-ejb/GeoTypeServiceImpl") 
	private GeoTypeService geoTypeService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String showGeoEntitys(HttpServletRequest request, Model model) {
		
		model.addAttribute("rightMenu", "geoEntities");
	    return "geoEntities";
	}
	
	@ModelAttribute("geoTypes")
	public List<BaseVO> loadRateTypes(HttpServletRequest request) throws ObjectNotFoundException, ParvanServiceException {
		
		@SuppressWarnings("unchecked")
		List<BaseVO> geoTypes = (List<BaseVO>) request.getSession().getAttribute("geoTypes");
		
		if(Validator.isNull(geoTypes)){
			UserInfo userInfo = UserInfoLoader.getInstance().getUserInfo();
			
			geoTypes = geoTypeService.findAll(userInfo);
			
			request.getSession().setAttribute("geoTypes", geoTypes);
		}
		
		return geoTypes;
	}
	
	@RequestMapping(value="/save", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> saveGeoEntity(GeoEntityVO geoEntityVO, HttpServletRequest request) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			UserInfo userInfo = UserInfoLoader.getInstance().getUserInfo();
			
			geoEntityVO = (GeoEntityVO) geoEntityService.save(userInfo, geoEntityVO);
			
			result.put("status", "success");
			result.put("result", geoEntityVO);
			
		} catch (Exception e) {
			proccessException(e, result);
		}
		
		return result;
	}

	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getGeoEntity(@PathVariable Long id, HttpServletRequest request) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			UserInfo userInfo = UserInfoLoader.getInstance().getUserInfo();
			
			GeoEntityVO geoEntityVO = (GeoEntityVO) geoEntityService.get(userInfo, id);
			
			result.put("status", "success");
			result.put("result", geoEntityVO);
			
		} catch (Exception e) {
			proccessException(e, result);
		}
		
		return result;
	}
	
	@RequestMapping(value="/get", method = RequestMethod.GET)
	public @ResponseBody GeoEntityVO getGeoEntityByParam(Long id, HttpServletRequest request) {
		
		GeoEntityVO geoEntityVO = null;
		
		try {
			UserInfo userInfo = UserInfoLoader.getInstance().getUserInfo();
			
			geoEntityVO = (GeoEntityVO) geoEntityService.get(userInfo, id);
			
		} catch (Exception e) {
			log.error("error in loading geoEntity with id:" + id, e);
			geoEntityVO = new GeoEntityVO();
		}
		
		return geoEntityVO;
	}
	
	@RequestMapping(value="/del/{id}", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> deleteGeoEntity(@PathVariable Long id, HttpServletRequest request) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			UserInfo userInfo = UserInfoLoader.getInstance().getUserInfo();
			
			geoEntityService.delete(userInfo, id);
			
			result.put("status", "success");
			
		} catch (Exception e) {
			proccessException(e, result);
		}
		
		return result;
	}
	
	@RequestMapping(value="/delall", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> deleteGeoEntitys(@RequestParam("id") Long[] ids, HttpServletRequest request) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			UserInfo userInfo = UserInfoLoader.getInstance().getUserInfo();
			
			geoEntityService.delete(userInfo, ids);
			
			result.put("status", "success");
			
		} catch (Exception e) {
			proccessException(e, result);
		}
		
		return result;
	}
	
	@RequestMapping(value="/paging", method = RequestMethod.GET)
	public @ResponseBody DatatablesResponse<BaseVO> findAll(@DatatablesParams DatatablesCriterias criterias, GeoEntityCriteria geoEntityCriteria, HttpServletRequest request) {
		int firstResult = criterias.getStart();
		int maxResults = criterias.getLength();
		ColumnDef columnDef = criterias.getSortedColumnDefs().get(0);
		SortDirectionEnum sortDirection = SortDirectionEnum.Ascending;
		String sortCriterion = columnDef.getName();
		
		if("0".equals(sortCriterion)){
			sortCriterion = "sortField";
		}
		
		if(columnDef.getSortDirection().equals(SortDirection.DESC)){
			sortDirection = SortDirectionEnum.Descending;
		}

		DataSet<BaseVO> dataSet;
		try {
			UserInfo userInfo = UserInfoLoader.getInstance().getUserInfo();
			
			List<BaseVO> rows = geoEntityService.findByCriteria(userInfo, geoEntityCriteria, firstResult, maxResults, sortDirection, sortCriterion);
			long totalRecords = geoEntityService.countByCriteria(userInfo, geoEntityCriteria);
			dataSet = new DataSet<BaseVO>(rows, (long) rows.size(), totalRecords);
		} catch (Exception e) {
			log.error("error occured in geoEntity paging..." ,e);
			e.printStackTrace();
			dataSet = new DataSet<BaseVO>(new ArrayList<BaseVO>(), 0l, 0l);
		}
        return DatatablesResponse.build(dataSet, criterias);
    }
	
}
