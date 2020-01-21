package com.parvanpajooh.identitymanagement2.mvc;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.parvanpajooh.ecourier.service.GenericService;
import com.parvanpajooh.identitymanagement2.model.User;
import com.parvanpajooh.identitymanagement2.model.criteria.UserCriteria;
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
@RequestMapping("/user")
public class UserController extends AbstractController<User, UserVO, UserCriteria> {

	@EJB(mappedName = "java:global/identitymanagement2-ear/identitymanagement2-ejb/UserServiceImpl")
	private UserService userService;

	@EJB(mappedName = "java:global/identitymanagement2-ear/identitymanagement2-ejb/RoleServiceImpl")
	private RoleService roleService;

	@Override
	protected GenericService<User, Long> getService() {
		return userService;
	}

	@Override
	protected String getName() {
		return "users";
	}

	@Autowired
	private MessageSource messageSource;
	ResourceBundle resourceBundle = ResourceBundle.getBundle("Messages");

	/**
	 * 
	 * @param userVO
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/saveRoles", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> saveRoles(UserVO userVO, HttpServletRequest request) {
		log.debug("Entering save(vo={})", userVO);

		Map<String, Object> result = new HashMap<String, Object>();
		String[] ids = request.getParameterValues("roles.id");

		try {

			convertDates(userVO);
			Field[] fields = userVO.getClass().getDeclaredFields();
			for (Field field : fields) {
				if (field.getName().equals("serialVersionUID")) {
					continue;
				}

				if (field.getType().equals(Date.class)) {
					String value = null;

					try {
						field.setAccessible(true);

						String fieldName = field.getName();
						Method m = userVO.getClass().getMethod("getStr" + StringUtils.capitalize(fieldName));
						value = (String) m.invoke(userVO);

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
							Method m = userVO.getClass().getMethod("set" + StringUtils.capitalize(fieldName), Date.class);
							m.invoke(userVO, date);
						} catch (Exception e) {
						}
					}
				}
			}

			userVO = (UserVO) userService.save(getUserInfo(request), userVO, ids);

			result.put("status", "success");
			result.put("result", userVO);

			request.getSession().removeAttribute(getName());

		} catch (Exception e) {
			proccessException(e, result);
		}

		return result;
	}

	/**
	 * 
	 * @param userVO
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/changePass", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> changePass(UserVO userVO, HttpServletRequest request) {
		log.debug("Entering save(vo={})", userVO);

		Map<String, Object> result = new HashMap<String, Object>();

		try {

			userVO = (UserVO) userService.changePass(userVO, getUserInfo(request));

			result.put("status", "success");
			result.put("result", userVO);

			request.getSession().removeAttribute(getName());

		} catch (Exception e) {
			result.put("status", "ErrorCode");
			result.put("errorMessage", getMessage(resourceBundle.getString("error_not_equal")));
			proccessException(e, result);
		}

		return result;
	}
	
	

}
