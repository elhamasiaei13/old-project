package com.parvanpajooh.geomanagement.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.parvanpajooh.client.geomanagement.model.CityInfoMsg;
import com.parvanpajooh.common.auth.BaseRestController;
import com.parvanpajooh.commons.enums.SortDirectionEnum;
import com.parvanpajooh.commons.platform.ejb.exceptions.ObjectNotFoundException;
import com.parvanpajooh.commons.platform.ejb.exceptions.ParvanServiceException;
import com.parvanpajooh.commons.platform.ejb.model.UserInfo;
import com.parvanpajooh.commons.platform.ejb.model.vo.BaseVO;
import com.parvanpajooh.commons.util.Validator;
import com.parvanpajooh.geomanagement.model.criteria.GeoEntityCriteria;
import com.parvanpajooh.geomanagement.model.vo.GeoEntityVO;
import com.parvanpajooh.geomanagement.model.vo.GeoTypeVO;
import com.parvanpajooh.geomanagement.service.GeoEntityService;

@RestController
@RequestMapping("/v1")
public class GeoManagementRestController extends BaseRestController {

	protected transient final Logger log = LoggerFactory.getLogger(getClass());

	@EJB(mappedName="java:global/geomanagement-ear/geomanagement-ejb/GeoEntityServiceImpl") 
	private GeoEntityService geoEntityService;

	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public String helloWord(
			HttpServletRequest request, 
			@RequestHeader(value = "user_name") String userName, 
			@RequestHeader(value = "ip") String ip) {

		// LOG
		log.debug("Entering helloWord(userName={} , ip={})", userName, ip);
		
		return "hello " + userName;
	}
	
	@RequestMapping(value = "/cities", method = RequestMethod.GET)
	public List<CityInfoMsg> findCityByNameOrShortCode(
			@RequestHeader(value = "user_name") String userName, 
			@RequestHeader(value = "ip") String ip, 
			@RequestParam(value="term", required=false) String term,
			@RequestParam(value="start", required=false) Integer start,
			@RequestParam(value="length", required=false) Integer length,
			@RequestParam(value="sort_direction", required=false) String sortDirection,
			@RequestParam(value="sort_criterion", required=false) String sortCriterion,
			@Context HttpServletResponse response) throws ObjectNotFoundException, ParvanServiceException {
		
		log.debug("Entering findCityByNameOrShortCode(userName={},term={})", userName, term);
		
		UserInfo userInfo = loadUserInfo(userName, ip);
		
		if(Validator.isNull(start)){
			start = 0;
		}
		
		if(Validator.isNull(length)){
			length = 20;
		}
		
		List<CityInfoMsg> cityInfoMsgs = null;
		
		GeoTypeVO geoType = new GeoTypeVO();
		geoType.setCode("CITY");
		GeoEntityCriteria criteria = new GeoEntityCriteria();
		criteria.setType(geoType);
		criteria.setAllNames(term);
		
		SortDirectionEnum directionEnum = SortDirectionEnum.Descending;
		if ( Validator.isNotNull(sortDirection) ) {
			directionEnum = SortDirectionEnum.fromString(sortDirection);
		}
		
		List<BaseVO> cities = geoEntityService.findByCriteria(userInfo, criteria, start, length, directionEnum, sortCriterion);
		int total = geoEntityService.countByCriteria(userInfo, criteria);
		
		cityInfoMsgs = new ArrayList<CityInfoMsg>();
		
		if (cities != null) {
			for (BaseVO baseVO : cities) {
				GeoEntityVO geoEntityVO = (GeoEntityVO) baseVO;
				CityInfoMsg cityInfoMsg = geoEntityVO.toCityInfoMsg();
				cityInfoMsgs.add(cityInfoMsg);
			}
		}
		
		response.addIntHeader("total", total);
		
		return cityInfoMsgs;
	}
	
	@RequestMapping(value = "/cities/{shortCode}", method = RequestMethod.GET)
	public CityInfoMsg getCityByShortCode(
			@RequestHeader(value = "user_name") String userName, 
			@RequestHeader(value = "ip") String ip,
			@PathVariable(value="shortCode") String shortCode) throws ObjectNotFoundException, ParvanServiceException {
		log.debug("Entering getCityByShortCode(userName={},shortCode={})", userName, shortCode);
		
		UserInfo userInfo = loadUserInfo(userName, ip);
		
		CityInfoMsg cityInfoMsg = null;
		
		GeoTypeVO geoType = new GeoTypeVO();
		geoType.setCode("CITY");
		GeoEntityCriteria crit = new GeoEntityCriteria();
		crit.setType(geoType);
		crit.setShortCode(shortCode);
		
		List<BaseVO> cities = geoEntityService.findByCriteria(userInfo, crit);
		
		if (cities != null && cities.size() == 1) {
			GeoEntityVO city = (GeoEntityVO) cities.get(0);
			cityInfoMsg = city.toCityInfoMsg();
		} else {
			throw new ObjectNotFoundException("No city found with short code : " + shortCode);
		}
		
		return cityInfoMsg;
	}
}
