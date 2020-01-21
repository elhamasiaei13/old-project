package com.parvanpajooh.geomanagement.service.adapter;

import javax.ejb.Stateless;

@Stateless
public class RarIdentityManagement2ServiceAdapterRest implements RarIdentityManagement2ServiceAdapter {

	/*private Identitymanagement2ApiRestClient identitymanagement2ApiRestClient;

	protected transient final Logger log = LoggerFactory.getLogger(getClass());

	public RarIdentityManagement2ServiceAdapterRest() {
		this.identitymanagement2ApiRestClient = new Identitymanagement2ApiRestClient();
	}
	
	@Override
	public RarUserVO loadUserByUsername(String username) throws ParvanServiceException {
		RarUserVO returnValue = null;
		try {
			UserMsg result = identitymanagement2ApiRestClient.loadUserByUsername(username);
			returnValue = _toUserVO(result);
		} catch (ParvanClientException e) {
			throw new ParvanUnrecoverableException(e.getMessage());
		}
		return returnValue;
	}
	
	@Override
	public RarUserVO loadUserByUuid(String uuid) throws ParvanServiceException {
		RarUserVO returnValue = null;
		try {
			UserMsg result = identitymanagement2ApiRestClient.loadUserByUuid(uuid);
			returnValue = _toUserVO(result);
		} catch (ParvanClientException e) {
			throw new ParvanUnrecoverableException(e.getMessage());
		}
		return returnValue;
	}
	
	private RarUserVO _toUserVO(UserMsg msg) {
		RarUserVO vo = new RarUserVO();
		vo.setId( msg.getId() );
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
			Set<RarRoleVO> roleVOs = new HashSet<>(roleMsgs.size());
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

	private RarUserGroupVO _toUserGroupVO(UserGroupMsg msg) {
		RarUserGroupVO vo = new RarUserGroupVO();
		vo.setId( msg.getId() );
		vo.setName( msg.getName() );
		return vo;
	}

	private RarRoleVO _toRoleVO(RoleMsg msg) {
		RarRoleVO vo = new RarRoleVO();
		vo.setId( msg.getId() );
		vo.setName( msg.getName() );
		vo.setType( msg.getType().name() );
		return vo;
	}*/
}
