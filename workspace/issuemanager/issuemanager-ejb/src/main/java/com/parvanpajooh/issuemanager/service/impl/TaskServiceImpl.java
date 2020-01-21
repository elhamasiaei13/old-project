package com.parvanpajooh.issuemanager.service.impl;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.parvanpajooh.commons.platform.ejb.model.UserInfo;
import com.parvanpajooh.commons.util.LocaleUtil;
import com.parvanpajooh.commons.util.Validator;
import com.parvanpajooh.issuemanager.common.exceptions.ErrorCode;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanRecoverableException;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanServiceException;
import com.parvanpajooh.issuemanager.model.Member;
import com.parvanpajooh.issuemanager.model.Task;
import com.parvanpajooh.issuemanager.model.TaskMemberRelation;
import com.parvanpajooh.issuemanager.model.enums.EmailEnum;
import com.parvanpajooh.issuemanager.model.enums.RoleEnum;
import com.parvanpajooh.issuemanager.model.enums.TaskMemberRelationEnum;
import com.parvanpajooh.issuemanager.service.MemberLocalService;
import com.parvanpajooh.issuemanager.service.TaskLocalService;
import com.parvanpajooh.issuemanager.service.TaskMemberRelationLocalService;
import com.parvanpajooh.issuemanager.service.TaskService;

/**
 * 
 * @author
 * 
 */
@Stateless
public class TaskServiceImpl extends GenericServiceImpl<Task, Long>implements TaskService {
	/**
	 * Log variable for all child classes.
	 */
	static final Logger log = LoggerFactory.getLogger(TaskServiceImpl.class);

	private TaskLocalService taskLocalService;

	private MemberLocalService memberLocalService;

	private TaskMemberRelationLocalService taskMemberRelationLocalService;

	@Inject
	public void setMemberLocalService(MemberLocalService memberLocalService) {
		this.memberLocalService = memberLocalService;
	}

	@Inject
	public void setTaskMemberRelationLocalService(TaskMemberRelationLocalService taskMemberRelationLocalService) {
		this.taskMemberRelationLocalService = taskMemberRelationLocalService;
	}

	@Inject
	public void setUserLocalService(TaskLocalService taskLocalService) {
		this.localService = taskLocalService;
		this.taskLocalService = taskLocalService;
	}

	@Override
	public void delete(UserInfo userInfo, Long id) throws ParvanServiceException {
		log.debug("Entering deleteTask(userInfo={})", userInfo);

		// check access
		Set<String> userRoles = userInfo.getRoleNames();
		if (!(userRoles.contains(RoleEnum.ADMIN.value) && userRoles.contains(RoleEnum.MANAGER.value))) {
			throw new ParvanRecoverableException(ErrorCode.HAS_NOT_ACCESS);
		}

		taskLocalService.delete(id);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void sendEmail(String mailServerUserName, String mailServerPassword, String mailServerHost, String mailServerPort, String mailIpAddress,
			String mailStarttls, String mailAuth, String mailRealm, String mailDefaultAssignee) throws ParvanServiceException {

		log.debug("Entering sendEmail in TaskServiceImple");

		List<Task> tasks = new ArrayList<Task>();
		String base = "\r\n جزییات بروزرسانی تسک در لینک زیر موجود می‌باشد :\r\n";
		String body = null;

		tasks = taskLocalService.findByEmailStatus(EmailEnum.NEED);

		Member member = null;
		String mailStr = null;
		String subject = null;
		UserInfo userInfo = null;

		for (Task task : tasks) {
			try {

				ZoneId timeZone = ZoneId.systemDefault();
				String defaultCalendar = "jalali";
				Member createUser = memberLocalService.get(task.getCreateUserId());
				Set<String> roleNamesSet = new HashSet<String>(createUser.getRoles());
				userInfo = new UserInfo(createUser.getId(), createUser.getUsername(), createUser.getFirstName(), createUser.getLastName(), null, null, null,
						roleNamesSet, LocaleUtil.fromLanguageId("fa").toString(), timeZone.getId(), defaultCalendar) {
				};

				List<TaskMemberRelation> taskMemberRelation = new ArrayList<TaskMemberRelation>();
				Set<String> mails = new HashSet<>();

				// add current member
				member = task.getCurrentMember();
				if (Validator.isNotNull(member)) {
					mailStr = memberLocalService.get(member.getId()).getEmail();
					mails.add(mailStr);
				} else {
					mails.add(mailDefaultAssignee);
				}

				// LOG
				log.debug("Sending mail (mail.size=)", mails.size());

				// add watching
				taskMemberRelation = taskMemberRelationLocalService.findByTaskIdAndType(task.getId(), TaskMemberRelationEnum.WATCHING);
				if (Validator.isNotNull(taskMemberRelation)) {
					for (TaskMemberRelation taskMembRelation : taskMemberRelation) {
						String email = memberLocalService.get(taskMembRelation.getMember().getId()).getEmail();
						if (Validator.isNotNull(email)) {
							mails.add(email);
						}
					}
				}

				// sending
				String[] mailArr = new String[mails.size()];
				mailArr = mails.toArray(mailArr);
				if (Validator.isNotNull(mailArr)) {

					subject = "IssueManager-Task (" + task.getId() + ")";
					body = base + mailIpAddress + task.getId();
					body = body + "\r\n گروه تسک = " + task.getGroup().getName();
					body = body + "\r\n عنوان تسک = " + task.getSubject();

					taskLocalService.sendEmail(mailArr, subject, body, mailServerUserName, mailServerPassword, mailServerHost, mailServerPort, mailIpAddress,
							mailStarttls, mailAuth, mailRealm);

					task.setEmailStatus(EmailEnum.SENT);
					taskLocalService.save(task, userInfo);

				}

				body = null;

			} catch (MessagingException e) {
				log.debug("Error occurred while sending email:", e);

				task.setEmailStatus(EmailEnum.FAILD);
				taskLocalService.save(task, userInfo);

			} catch (Exception e) {
				log.debug("Error occurred while sending email:", e);
				task.setEmailStatus(EmailEnum.FAILD);
				taskLocalService.save(task, userInfo);
			}
		}
	}
}
