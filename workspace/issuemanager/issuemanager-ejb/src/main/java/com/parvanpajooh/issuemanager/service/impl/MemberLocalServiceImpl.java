package com.parvanpajooh.issuemanager.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
import com.parvanpajooh.issuemanager.dao.MemberDao;
import com.parvanpajooh.issuemanager.model.Member;
import com.parvanpajooh.issuemanager.model.enums.RoleEnum;
import com.parvanpajooh.issuemanager.model.vo.MemberVO;
import com.parvanpajooh.issuemanager.service.MemberLocalService;

@Stateless
public class MemberLocalServiceImpl extends GenericLocalServiceImpl<Member, Long>implements MemberLocalService {
	/**
	 * Log variable for all child classes.
	 */
	static final Logger log = LoggerFactory.getLogger(MemberLocalServiceImpl.class);

	public MemberDao memberDao;

	@Inject
	public void setMemberDao(MemberDao memberDao) {
		this.dao = memberDao;
		this.memberDao = memberDao;
	}

	@Override
	public BaseVO save(BaseVO baseVO, UserInfo userInfo) throws ParvanServiceException {
		log.info("Entering save(baseVO={}, userInfo={})", baseVO, userInfo);

		MemberVO memberVO = (MemberVO) baseVO;

		try {

			String firstName = memberVO.getFirstName();
			String lastName = memberVO.getLastName();
			String userName = memberVO.getUsername();
			String password = memberVO.getPassword();
			byte[] fileImage = memberVO.getFileImage();

			if (Validator.isNull(firstName) || Validator.isNull(lastName) || Validator.isNull(userName) || Validator.isNull(fileImage)) {
				log.debug("feild is empty.");
				throw new ParvanRecoverableException(ErrorCode.FEILDS_IS_EMPTY);
			}

			Member member = null;

			if (Validator.isNull(memberVO.getId())) {
				
				if (Validator.isNull(password)) {
					throw new ParvanRecoverableException(ErrorCode.FEILDS_IS_EMPTY);
				}

				member = new Member();
				member.fromVO(memberVO);

				if (Validator.isNull(memberVO.getRoles())) {
					List<String> roles = new ArrayList<String>();
					roles.add(RoleEnum.REPORTER.value);
					member.setRoles(roles);
				}

			} else {
				member = memberDao.get(memberVO.getId());
				if(Validator.isNull(memberVO.getPassword())){					
					memberVO.setPassword(member.getPassword());
				}
				memberVO.setRoles(member.getRoles());
				member.fromVO(memberVO);
			}

			// save entity
			member.setActive(true);
			member = save(member, userInfo);

			memberVO = (MemberVO) member.toVO();

		} catch (ParvanServiceException e) {
			throw e;

		} catch (Exception e) {
			throw new ParvanUnrecoverableException("Error occurred while saving object.", e);
		}

		return memberVO;
	}

	@Override
	public MemberVO searchMemberByUsernamePass(String username, String pass) throws ParvanServiceException {

		MemberVO memberVO = null;
		try {
			Member member = memberDao.searchByUsernamePass(username, pass);
			memberVO = member.toVOLite();

		} catch (Exception e) {
			if (Validator.isNull(memberVO)) {
				log.debug("username and password is not correct.");
				throw new ParvanRecoverableException(ErrorCode.LOGIN_USER_PASS_NOT_CORRECT);
			} else {
				log.error("error occurred while searchMemberByUsernamPass members", e);
				throw new ParvanUnrecoverableException("error occurred while searchMemberByUsernamPass members", e);
			}
		}

		return memberVO;
	}

	@Override
	public void chanePass(UserInfo userInfo, MemberVO member) throws ParvanServiceException {
		if (Validator.equals(member.getPassword(), member.getPasswordConfirm())) {
			member.setUpdateUserId(member.getId());
			save(member, userInfo);

		} else {
			throw new ParvanRecoverableException(ErrorCode.NOT_EQUAL_PASSWORDS);
		}

	}

	@Override
	public void delete(Long id) throws ParvanServiceException {
		try {

			Member member = null;
			LocalDateTime now = LocalDateTime.now();

			member = memberDao.get(id);
			member.setActive(false);
			member.setUpdateDate(now);

			// refresh entity
			memberDao.refresh(member);

		} catch (Exception e) {
			throw new ParvanUnrecoverableException("Error occurred while deleting member.", e);
		}

	}
}
