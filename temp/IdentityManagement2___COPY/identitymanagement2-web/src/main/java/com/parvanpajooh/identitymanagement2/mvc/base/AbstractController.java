package com.parvanpajooh.identitymanagement2.mvc.base;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StreamUtils;
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
import com.github.dandelion.datatables.extras.spring4.ajax.DatatablesParams;
import com.parvanpajooh.common.UserInfo;
import com.parvanpajooh.common.constants.StringPool;
import com.parvanpajooh.common.enums.ListExportFormatEnum;
import com.parvanpajooh.common.enums.SortDirectionEnum;
import com.parvanpajooh.common.exceptions.ParvanServiceException;
import com.parvanpajooh.common.util.DateUtil;
import com.parvanpajooh.common.util.LocaleUtil;
import com.parvanpajooh.common.util.StringUtil;
import com.parvanpajooh.common.util.Validator;
import com.parvanpajooh.common.util.ZoneIdUtil;
import com.parvanpajooh.common.vo.BaseCriteria;
import com.parvanpajooh.common.vo.BaseVO;
import com.parvanpajooh.ecourier.model.BaseModel;
import com.parvanpajooh.ecourier.service.GenericService;

@Controller
public abstract class AbstractController<T extends BaseModel, V extends BaseVO, C extends BaseCriteria> extends BaseController {

	protected abstract GenericService<T, Long> getService();

	protected abstract String getName();

	protected String getDefaultSortCriterion() {
		return null;
	}

	protected SortDirectionEnum getDefaultSortDirection() {
		return SortDirectionEnum.Ascending;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String show(HttpServletRequest request, Model model) {
		log.debug("Entering show");

		try {
			init(request, model);

		} catch (Exception e) {
			log.error("Error in init method");
			// TODO Mo: handle proper exceptions
		}

		model.addAttribute("rightMenu", getName());
		return getName();
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> save(V baseVO, HttpServletRequest request) {
		log.debug("Entering save(vo={})", baseVO);

		Map<String, Object> result = new HashMap<String, Object>();


		try {

			convertDates(baseVO);
			Field[] fields = baseVO.getClass().getDeclaredFields();
			for (Field field : fields) {
				if (field.getName().equals("serialVersionUID")) {
					continue;
				}

				if (field.getType().equals(Date.class)) {
					String value = null;

					try {
						field.setAccessible(true);

						String fieldName = field.getName();
						Method m = baseVO.getClass().getMethod("getStr" + StringUtils.capitalize(fieldName));
						value = (String) m.invoke(baseVO);

					} catch (Exception e) {
						continue;
					}

					if (value == null) {
						continue;
					} else {
						// Date date =
						// DateUtil.convertStringToDate("yyyy-MM-dd", value);
						Date date = null;

						try {
							String fieldName = field.getName();
							Method m = baseVO.getClass().getMethod("set" + StringUtils.capitalize(fieldName), Date.class);
							m.invoke(baseVO, date);
						} catch (Exception e) {
						}
					}

				}

			}
			
			baseVO = (V) getService().save(getUserInfo(request), baseVO);

			result.put("status", "success");
			result.put("result", baseVO);

			request.getSession().removeAttribute(getName());

		} catch (Exception e) {
			proccessException(e, result);
		}

		return result;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> get(@PathVariable Long id, HttpServletRequest request) {
		log.debug("Entering get(id={})", id);

		Map<String, Object> result = new HashMap<String, Object>();

		try {
			V baseVO = (V) getService().get(getUserInfo(request), id);

			Field[] fields = baseVO.getClass().getDeclaredFields();
			for (Field field : fields) {
				if (field.getName().equals("serialVersionUID")) {
					continue;
				}

				if (field.getType().equals(LocalDateTime.class)) {
					LocalDateTime value = null;
					try {
						field.setAccessible(true);

						String fieldName = field.getName();
						Method m = baseVO.getClass().getMethod("get" + StringUtils.capitalize(fieldName));
						value = (LocalDateTime) m.invoke(baseVO);

					} catch (IllegalArgumentException e) {
						continue;
					} catch (IllegalAccessException e) {
						continue;
					}

					if (value == null) {
						continue;
					}

					if (value instanceof LocalDateTime) {
						// String strDate = DateUtil.convertDateToString(value,
						// "yyyy-MM-dd");
						String strDate = null;

						try {
							String fieldName = field.getName();
							Method m = baseVO.getClass().getMethod("setStr" + StringUtils.capitalize(fieldName), String.class);
							m.invoke(baseVO, strDate);
						} catch (Exception e) {
						}
					}
				}

			}

			result.put("status", "success");
			result.put("result", baseVO);

		} catch (Exception e) {
			proccessException(e, result);
		}

		return result;
	}

	/**
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public @ResponseBody BaseVO getEntity(Long id, HttpServletRequest request) {
		log.debug("Entering getEntity(id={})", id);
		BaseVO baseVO = null;
		try {
			baseVO = getService().get(getUserInfo(request), id);

		} catch (Exception e) {
			log.error("Error occurred while getEntity.", e);
		}
		log.debug("Exiting getEntity()");
		return baseVO;
	}

	@RequestMapping(value = "/del/{id}", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> delete(@PathVariable Long id, HttpServletRequest request) {
		log.debug("Entering delete(id={})", id);

		Map<String, Object> result = new HashMap<String, Object>();

		try {
			getService().delete(getUserInfo(request), id);

			result.put("status", "success");

			request.getSession().removeAttribute(getName());

		} catch (Exception e) {
			proccessException(e, result);
		}

		return result;
	}

	@RequestMapping(value = "/delall", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> delete(@RequestParam("id") Long[] ids, HttpServletRequest request) {
		log.debug("Entering delete(ids={})", (Object) ids);

		Map<String, Object> result = new HashMap<String, Object>();

		try {
			getService().delete(getUserInfo(request), ids);

			result.put("status", "success");

			request.getSession().removeAttribute(getName());

		} catch (Exception e) {
			proccessException(e, result);
		}

		return result;
	}

	@RequestMapping(value = "/paging", method = RequestMethod.GET)
	public @ResponseBody DatatablesResponse<V> paging(@DatatablesParams DatatablesCriterias criterias, C criteria, HttpServletRequest request) {
		log.debug("Entering paging(criteria={})", criteria);

		int firstResult = criterias.getDisplayStart();
		int maxResults = criterias.getDisplaySize();
//		ColumnDef columnDef = criterias.getSortingColumnDefs().get(0);
		ColumnDef columnDef = new ColumnDef();
		columnDef.setSortDirection(SortDirection.DESC);
		SortDirectionEnum sortDirection = getDefaultSortDirection();
		String sortCriterion = columnDef.getName();

		if ("0".equals(sortCriterion)) {
			sortCriterion = getDefaultSortCriterion();
		}

		if (columnDef.getSortDirection().equals(SortDirection.DESC)) {
			sortDirection = SortDirectionEnum.Descending;
		}

		convertDates(criteria);

		DataSet<V> dataSet;
		try {
			UserInfo userInfo = getUserInfo(request);
			List<V> rows = (List<V>) getService().findByCriteria(userInfo, criteria, firstResult, maxResults, sortDirection, sortCriterion);

			long totalRecords = getService().countByCriteria(userInfo, criteria);
			dataSet = new DataSet<V>(rows, (long) rows.size(), totalRecords);
		} catch (Exception e) {
			log.error("error occured in record paging...", e);
			e.printStackTrace();
			dataSet = new DataSet<V>(new ArrayList<V>(), 0l, 0l);
		}
		return DatatablesResponse.build(dataSet, criterias);
	}
	

	@RequestMapping(value = "/pagingSelective", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> pagingSelective(String action, Long id, HttpServletRequest request) {
		log.debug("Entering pagingSelective(action={}, id={})", action, id);

		Map<String, Object> result = new HashMap<String, Object>();

		String name = getName();
		name = name.substring(0, name.length() - 2);
		name = StringUtils.capitalize(name);

		changeSelected(request, name, action, id);

		result.put("status", "success");

		return result;
	}

	/**
	 * 
	 * @param id
	 * @param request
	 * @return
	 * @throws ParvanServiceException
	 */
	@RequestMapping(value = "/export", method = RequestMethod.POST)
	public String export(C criteria, String[] columns, String[] keys, HttpServletRequest request, HttpServletResponse response, ModelMap model)
			throws Exception {
		log.debug("Entering export(criteria={}, columns={}, keys={})", criteria, columns, keys);

		UserInfo userInfo = getUserInfo(request);

		List<String> fields = Arrays.asList(columns);
		List<String> columnsNames = new ArrayList<>();
		for (String key : keys) {
			columnsNames.add(getMessage(key, (Object) null));
		}

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		String time = DateUtil.getCurrentDateTime(LocaleUtil.fromLanguageId(userInfo.getLocale()), userInfo.getCalendar(),
				ZoneIdUtil.getZoneId(userInfo.getZoneId()));

		time = StringUtil.replace(time, StringPool.SLASH, StringPool.DASH);
		time = StringUtil.replace(time, StringPool.SPACE, StringPool.DASH);

		// output filename
		String reportId = getName() + "_" + time + "_" + ".xlsx";

		convertDates(criteria);

		// generate report
		getService().export(userInfo, out, fields, columnsNames, criteria, SortDirectionEnum.Ascending, "createDate", ListExportFormatEnum.ExcelXml);

		response.setHeader("Content-Disposition", "attachment; filename=\"" + reportId + "\"");
		response.setContentType("application/vnd.ms-excel");
		response.setContentLength((int) out.size());

		StreamUtils.copy(new ByteArrayInputStream(out.toByteArray()), response.getOutputStream());

		model.clear();

		log.debug("Leaving export(criteria={}, columns={})", criteria, columns);

		return null;
	}

	protected void init(HttpServletRequest request, Model model) throws Exception {

	}

	protected void convertDates(Object object) {
		_convertDates(object, object.getClass());
		_convertDates(object, object.getClass().getSuperclass());
	}

	private void _convertDates(Object object, Class<?> claszz) {
		Field[] criteriaFields = claszz.getDeclaredFields();
		for (Field field : criteriaFields) {
			if (field.getName().equals("serialVersionUID")) {
				continue;
			}

			if (field.getType().equals(Date.class)) {
				String value = null;

				try {
					field.setAccessible(true);

					String fieldName = field.getName();
					Method m = claszz.getMethod("getStr" + StringUtils.capitalize(fieldName));
					value = (String) m.invoke(object);

				} catch (Exception e) {
					continue;
				}

				if (Validator.isNull(value)) {
					continue;
				} else {
					String dateFormat = "yyyy/MM/dd";
					if (value.indexOf('-') > -1) {
						dateFormat = "yyyy-MM-dd";
					}
					// Date date = DateUtil.convertStringToDate(dateFormat,
					// value);
					Date date = null;

					try {
						String fieldName = field.getName();
						Method m = claszz.getMethod("set" + StringUtils.capitalize(fieldName), Date.class);
						m.invoke(object, date);
					} catch (Exception e) {
					}
				}

			}

		}
	}
}
