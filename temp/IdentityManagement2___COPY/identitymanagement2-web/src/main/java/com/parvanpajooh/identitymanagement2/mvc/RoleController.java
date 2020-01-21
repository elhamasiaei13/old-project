package com.parvanpajooh.identitymanagement2.mvc;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.dandelion.datatables.core.ajax.ColumnDef;
import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import com.github.dandelion.datatables.core.ajax.DatatablesResponse;
import com.github.dandelion.datatables.core.ajax.ColumnDef.SortDirection;
import com.github.dandelion.datatables.extras.spring4.ajax.DatatablesParams;
import com.parvanpajooh.common.UserInfo;
import com.parvanpajooh.common.enums.SortDirectionEnum;
import com.parvanpajooh.common.util.Validator;
import com.parvanpajooh.common.vo.BaseVO;
import com.parvanpajooh.ecourier.service.GenericService;
import com.parvanpajooh.identitymanagement2.model.Role;
import com.parvanpajooh.identitymanagement2.model.criteria.RoleCriteria;
import com.parvanpajooh.identitymanagement2.model.vo.RoleVO;
import com.parvanpajooh.identitymanagement2.model.vo.UserVO;
import com.parvanpajooh.identitymanagement2.mvc.base.AbstractController;
import com.parvanpajooh.identitymanagement2.service.RoleService;
import com.parvanpajooh.identitymanagement2.service.UserService;

/**
 * 
 * @author ali
 *
 */
@Controller
@RequestMapping("/role")
public class RoleController extends AbstractController<Role, RoleVO, RoleCriteria> {

	@EJB(mappedName = "java:global/identitymanagement2-ear/identitymanagement2-ejb/RoleServiceImpl")
	private RoleService roleService;

	@EJB(mappedName = "java:global/identitymanagement2-ear/identitymanagement2-ejb/UserServiceImpl")
	private UserService userService;

	@Override
	protected GenericService<Role, Long> getService() {
		return roleService;
	}

	@Override
	protected String getName() {
		return "roles";
	}

	@RequestMapping(value = "/findAll/{id}", method = RequestMethod.GET)
	public @ResponseBody DatatablesResponse<RoleVO> findAll(@PathVariable Long id, HttpServletRequest request) {
		log.debug("Entering findAllRoles");
		List<BaseVO> baseVO = null;
		List<RoleVO> roles = new ArrayList<RoleVO>();
		List<RoleVO> roleVOs = new ArrayList<RoleVO>();
		UserVO userVO = null;
		RoleCriteria roleCriteria = new RoleCriteria();
		DataSet<RoleVO> dataSet = null;
		DatatablesCriterias criterias = new DatatablesCriterias();

		try {
			UserInfo userInfo = getUserInfo(request);

			userVO = (UserVO) userService.get(getUserInfo(request), id);
			baseVO = roleService.findAll(getUserInfo(request));

			if (Validator.isNotNull(userVO.getRoles())) {
				 roleVOs = userVO.getRoles();
			}
			
				for (BaseVO role : baseVO) {
					RoleVO rVO = (RoleVO) role;
					for (RoleVO selectedRole : roleVOs) {
						if (Validator.equals(rVO.getId(), selectedRole.getId())) {
							rVO.setSelected(true);
						}
					}
					roles.add(rVO);
				}
		

			long totalRecords = roleService.countByCriteria(userInfo, roleCriteria);
			dataSet = new DataSet<RoleVO>(roles, (long) roles.size(), totalRecords);

		} catch (Exception e) {
			log.error("Error occurred while getEntity.", e);
		}
		return DatatablesResponse.build(dataSet, criterias);
	}
}
