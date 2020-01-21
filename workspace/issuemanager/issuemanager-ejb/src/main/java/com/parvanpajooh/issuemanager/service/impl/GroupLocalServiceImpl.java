package com.parvanpajooh.issuemanager.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.parvanpajooh.commons.platform.ejb.model.UserInfo;
import com.parvanpajooh.commons.platform.ejb.model.vo.BaseVO;
import com.parvanpajooh.commons.util.Validator;
import com.parvanpajooh.issuemanager.common.exceptions.ErrorCode;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanRecoverableException;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanServiceException;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanUnrecoverableException;
import com.parvanpajooh.issuemanager.dao.GroupDao;
import com.parvanpajooh.issuemanager.model.Group;
import com.parvanpajooh.issuemanager.model.enums.RoleEnum;
import com.parvanpajooh.issuemanager.model.vo.GroupVO;
import com.parvanpajooh.issuemanager.service.GroupLocalService;

@Stateless
public class GroupLocalServiceImpl extends GenericLocalServiceImpl<Group, Long>implements GroupLocalService {
	/**
	 * Log variable for all child classes.
	 */
	static final Logger log = LoggerFactory.getLogger(GroupLocalServiceImpl.class);

	public GroupDao groupDao;

	@Inject
	public void setGroupDao(GroupDao groupDao) {
		this.dao = groupDao;
		this.groupDao = groupDao;
	}


	@Override
	public BaseVO save(BaseVO baseVO, UserInfo userInfo) throws ParvanServiceException {
		log.info("Entering save(baseVO={}, userInfo={})", baseVO, userInfo);

		GroupVO groupVO = (GroupVO) baseVO;

		try {
			boolean isNew = Validator.isNull(groupVO.getId());

			if (!isNew) {

				// check access
				Set<String> userRoles = userInfo.getRoleNames();
				if (!(userRoles.contains(RoleEnum.ADMIN.value) && userRoles.contains(RoleEnum.MANAGER.value))) {
					throw new ParvanRecoverableException(ErrorCode.HAS_NOT_ACCESS);
				}
			}

			String name = groupVO.getName();
			String description = groupVO.getDescription();
			if (Validator.isNull(name) || Validator.isNull(description)) {
				log.debug("name is empty.");
				throw new ParvanRecoverableException(ErrorCode.FEILDS_IS_EMPTY);
			}

			Group group = null;
			LocalDateTime now = LocalDateTime.now();

			if (isNew) {
				group = new Group();
				group.fromVO(groupVO);
				group.setCreateDate(now);
				group.setCreateUserId(userInfo.getUserId());

			} else {
				group = groupDao.get(groupVO.getId());
				group.fromVO(groupVO);
			}

			group.setActive(true);
			group.setUpdateDate(now);
			group.setUpdateUserId(userInfo.getUserId());

			// save entity
			group = save(group, userInfo);

			groupVO = (GroupVO) group.toVO();

		} catch (ParvanServiceException e) {
			throw e;

		} catch (Exception e) {
			throw new ParvanUnrecoverableException("Error occurred while saving object.", e);
		}

		return groupVO;
	}

	/**
	 * 
	 * @param members
	 * @return
	 */
	private List<GroupVO> convertToTree(List<Group> members) {

		List<GroupVO> result = new ArrayList<GroupVO>();
		Map<Long, GroupVO> map = new HashMap<Long, GroupVO>();

		Iterator<Group> iterator = members.iterator();
		while (iterator.hasNext()) {
			Group thisGroup = iterator.next();
			// if (thisGroup.getId() == null) {
			GroupVO memberVO = thisGroup.toVOLite();

			// check permission
			if (true/* hasAccess(memberVO) */) {
				// GroupVO.setChildren(new ArrayList<GroupVO>());
				result.add(memberVO);
			}

			map.put(thisGroup.getId(), memberVO);
			iterator.remove();
			// } else {
			// // GroupVO parentGroupVO =
			// // map.get(thisGroup.getParent().getId());
			//
			// GroupVO memberVO = thisGroup.toVOLite();
			// map.put(thisGroup.getId(), memberVO);
			// iterator.remove();
			// }

		}

		return result;
	}

	/*
	 * public void refresh(GroupVO groupVO) throws ParvanServiceException,
	 * ParvanDaoException { log.info("Entering refresh(m={})", groupVO);
	 * 
	 * Group group=new Group(); LocalDateTime now = LocalDateTime.now(); group =
	 * groupDao.get(groupVO.getId()); group.setUpdateDate(now);
	 * group.fromVO(groupVO); group.setActive(true);
	 * 
	 * // refresh entity groupDao.refresh(group); }
	 */

	@Override
	public void delete(Long id) throws ParvanServiceException {
		try {

			Group group = null;
			LocalDateTime now = LocalDateTime.now();

			group = groupDao.get(id);
			group.setActive(false);
			group.setUpdateDate(now);

			// refresh entity
			groupDao.refresh(group);

		} catch (Exception e) {
			throw new ParvanUnrecoverableException("Error occurred while deleting group.", e);
		}

	}
}
