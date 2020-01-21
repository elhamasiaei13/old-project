package com.parvanpajooh.geomanagement.mvc;

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
import com.parvanpajooh.common.auth.UserInfoLoader;
import com.parvanpajooh.commons.enums.SortDirectionEnum;
import com.parvanpajooh.commons.platform.ejb.model.UserInfo;
import com.parvanpajooh.commons.platform.ejb.model.vo.BaseVO;
import com.parvanpajooh.geomanagement.model.criteria.GeoTypeCriteria;
import com.parvanpajooh.geomanagement.model.vo.GeoTypeVO;
import com.parvanpajooh.geomanagement.mvc.base.GeoManagementBaseController;
import com.parvanpajooh.geomanagement.service.GeoTypeService;

@Controller
@RequestMapping("/geoType")
public class GeoTypeController extends GeoManagementBaseController {
	
	@EJB(mappedName="java:global/geomanagement-ear/geomanagement-ejb/GeoTypeServiceImpl") 
	private GeoTypeService geoTypeService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String showGeoTypes(HttpServletRequest request, Model model) {
		
		model.addAttribute("rightMenu", "geoTypes");
	    return "geoTypes";
	}
	
	@RequestMapping(value="/save", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> saveGeoType(GeoTypeVO geoTypeVO, HttpServletRequest request) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			UserInfo userInfo = UserInfoLoader.getInstance().getUserInfo();
			
			geoTypeVO = (GeoTypeVO) geoTypeService.save(userInfo, geoTypeVO);
			
			result.put("status", "success");
			result.put("result", geoTypeVO);
			
		} catch (Exception e) {
			proccessException(e, result);
		}
		
		return result;
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getGeoType(@PathVariable Long id, HttpServletRequest request) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			UserInfo userInfo = UserInfoLoader.getInstance().getUserInfo();
			
			GeoTypeVO geoTypeVO = (GeoTypeVO) geoTypeService.get(userInfo, id);
			
			result.put("status", "success");
			result.put("result", geoTypeVO);
			
		} catch (Exception e) {
			proccessException(e, result);
		}
		
		return result;
	}
	
	@RequestMapping(value="/get", method = RequestMethod.GET)
	public @ResponseBody GeoTypeVO getGeoTypeByParam(Long id, HttpServletRequest request) {
		
		GeoTypeVO geoTypeVO = null;
		
		try {
			UserInfo userInfo = UserInfoLoader.getInstance().getUserInfo();
			
			geoTypeVO = (GeoTypeVO) geoTypeService.get(userInfo, id);
			
		} catch (Exception e) {
			geoTypeVO = new GeoTypeVO();
		}
		
		return geoTypeVO;
	}
	
	@RequestMapping(value="/del/{id}", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> deleteGeoType(@PathVariable Long id, HttpServletRequest request) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			UserInfo userInfo = UserInfoLoader.getInstance().getUserInfo();
			
			geoTypeService.delete(userInfo, id);
			
			result.put("status", "success");
			
		} catch (Exception e) {
			proccessException(e, result);
		}
		
		return result;
	}
	
	@RequestMapping(value="/delall", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> deleteGeoTypes(@RequestParam("id") Long[] ids, HttpServletRequest request) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			UserInfo userInfo = UserInfoLoader.getInstance().getUserInfo();
			
			geoTypeService.delete(userInfo, ids);
			
			result.put("status", "success");
			
		} catch (Exception e) {
			proccessException(e, result);
		}
		
		return result;
	}
	
	@RequestMapping(value="/paging", method = RequestMethod.GET)
	public @ResponseBody DatatablesResponse<BaseVO> findAll(@DatatablesParams DatatablesCriterias criterias, GeoTypeCriteria geoTypeCriteria, HttpServletRequest request) {
		int firstResult = criterias.getStart();
		int maxResults = criterias.getLength();
		ColumnDef columnDef = criterias.getSortedColumnDefs().get(0);
		SortDirectionEnum sortDirection = SortDirectionEnum.Ascending;
		String sortCriterion = columnDef.getName();
		
		if("0".equals(sortCriterion)){
			sortCriterion = null;
		}
		
		if(columnDef.getSortDirection().equals(SortDirection.DESC)){
			sortDirection = SortDirectionEnum.Descending;
		}

		DataSet<BaseVO> dataSet;
		try {
			UserInfo userInfo = UserInfoLoader.getInstance().getUserInfo();
			
			List<BaseVO> rows = geoTypeService.findByCriteria(userInfo, geoTypeCriteria, firstResult, maxResults, sortDirection, sortCriterion);
			long totalRecords = geoTypeService.countByCriteria(userInfo, geoTypeCriteria);
			dataSet = new DataSet<BaseVO>(rows, (long) rows.size(), totalRecords);
		} catch (Exception e) {
			log.error("error occured in getoType paging..." ,e);
			e.printStackTrace();
			dataSet = new DataSet<BaseVO>(new ArrayList<BaseVO>(), 0l, 0l);
		}
        return DatatablesResponse.build(dataSet, criterias);
    }
	
}
