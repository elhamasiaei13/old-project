package com.parvanpajooh.sample.service.adapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.parvanpajooh.client.common.PageList;
import com.parvanpajooh.client.common.ParvanClientException;
import com.parvanpajooh.client.identitymanagement2.Identitymanagement2ApiRestClient;
import com.parvanpajooh.client.identitymanagement2.model.ObjectAccessScopeMsg;
import com.parvanpajooh.client.identitymanagement2.model.RoleMsg;
import com.parvanpajooh.client.identitymanagement2.model.UserGroupMsg;
import com.parvanpajooh.client.identitymanagement2.model.UserMsg;
import com.parvanpajooh.commons.platform.ejb.exceptions.ParvanServiceException;
import com.parvanpajooh.commons.platform.ejb.exceptions.ParvanUnrecoverableException;
import com.parvanpajooh.sample.model.vo.ObjectAccessScopeVO;
import com.parvanpajooh.sample.model.vo.RoleVO;
import com.parvanpajooh.sample.model.vo.UserGroupVO;
import com.parvanpajooh.sample.model.vo.UserVO;

@Stateless
public class IdentityManagement2ServiceAdapterRest implements IdentityManagement2ServiceAdapter {

	private Identitymanagement2ApiRestClient identitymanagement2ApiRestClient;

	protected transient final Logger log = LoggerFactory.getLogger(getClass());

	public IdentityManagement2ServiceAdapterRest() {
		this.identitymanagement2ApiRestClient = new Identitymanagement2ApiRestClient();
	}

	@Override
	public PageList<UserVO> findUsers(String query, int page, int size) throws ParvanUnrecoverableException {
		PageList<UserVO> returnValue = null;
		try {
			PageList<UserMsg> result = identitymanagement2ApiRestClient.loadUsers(query, page, size);
			if (result != null) {
				List<UserMsg> elements = result.getElements();
				if (elements != null) {
					List<UserVO> voList = new ArrayList<>(elements.size());
					for (UserMsg msg : elements) {
						UserVO vo = _toUserVO(msg);
						voList.add(vo);
					}
					returnValue = new PageList<>(voList, result.getTotal());
				}
			}
		} catch (Exception e) {
			throw new ParvanUnrecoverableException("Error occurred while finding accounts", e);
		}
		return returnValue;
	}

	@Override
	public List<ObjectAccessScopeVO> loadScopes() throws ParvanUnrecoverableException {
		List<ObjectAccessScopeVO> list = null;
		ObjectAccessScopeVO objectAccessScopeVO = null;
		try {
			List<ObjectAccessScopeMsg> objectAccessScopeMsgs = identitymanagement2ApiRestClient.loadScopes();
			list = new ArrayList<ObjectAccessScopeVO>();
			for (ObjectAccessScopeMsg scopeMsg : objectAccessScopeMsgs) {
				objectAccessScopeVO = new ObjectAccessScopeVO();
				objectAccessScopeVO.fromMsg(scopeMsg);
				list.add(objectAccessScopeVO);
			}
		} catch (Exception e) {
			throw new ParvanUnrecoverableException("Error occurred while loadScopes", e);
		}
		return list;
	}
	
	/*@Override
	public UserVO loadUserByUsername(String username) throws ParvanServiceException {
		UserVO returnValue = null;
		try {
			UserMsg result = identitymanagement2ApiRestClient.loadUserByUsername(username);
			returnValue = _toUserVO(result);
		} catch (ParvanClientException e) {
			throw new ParvanUnrecoverableException(e.getMessage());
		}
		return returnValue;
	}*/
	
	@Override
	public UserVO loadUserByUuid(String uuid) throws ParvanServiceException {
		UserVO returnValue = null;
		try {
			UserMsg result = identitymanagement2ApiRestClient.loadUserByUuid(uuid);
			returnValue = _toUserVO(result);
		} catch (ParvanClientException e) {
			throw new ParvanUnrecoverableException(e.getMessage());
		}
		return returnValue;
	}
	
	private UserVO _toUserVO(UserMsg msg) {
		UserVO vo = new UserVO();
		vo.setId( msg.getId() );
		vo.setUuid( msg.getUuid() );
		vo.setUserName( msg.getUserName() );
		vo.setFirstName( msg.getFirstName() );
		vo.setLastName( msg.getLastName() );
		vo.setDisplayName( msg.getDisplayName() );
		vo.setLocale( msg.getLocale() );
		vo.setZoneId( msg.getZoneId() );
		vo.setCalendar( msg.getCalendar().name() );
		vo.setEmail( msg.getEmail() );
		if (msg.getRoleMsgs() != null) {
			Set<RoleMsg> roleMsgs = msg.getRoleMsgs();
			Set<RoleVO> roleVOs = new HashSet<>(roleMsgs.size());
			for (RoleMsg roleMsg : roleMsgs) {
				roleVOs.add( _toRoleVO(roleMsg) );
			}
			vo.setRoles(roleVOs);
		}
		if (msg.getUserGroupMsg() != null) {
			vo.setUserGroup( _toUserGroupVO(msg.getUserGroupMsg()) );
		}
		return vo;
	}

	private UserGroupVO _toUserGroupVO(UserGroupMsg msg) {
		UserGroupVO vo = new UserGroupVO();
		vo.setId( msg.getId() );
		vo.setName( msg.getName() );
		return vo;
	}

	private RoleVO _toRoleVO(RoleMsg msg) {
		RoleVO vo = new RoleVO();
		vo.setId( msg.getId() );
		vo.setName( msg.getName() );
		vo.setType( msg.getType().name() );
		return vo;
	}
}
