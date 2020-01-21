package com.parvanpajooh.issuemanager.service.impl;

import java.io.FileInputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Properties;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timer;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.parvanpajooh.issuemanager.common.exceptions.ParvanServiceException;
import com.parvanpajooh.issuemanager.common.exceptions.ParvanUnrecoverableException;
import com.parvanpajooh.issuemanager.dao.MemberDao;
import com.parvanpajooh.issuemanager.dao.TaskMemberRelationDao;
import com.parvanpajooh.issuemanager.service.TaskService;

@Singleton
@Lock(LockType.READ) // allows timers to execute in parallel
@Startup
public class TimingJob {
	/**
	 * Log variable for all child classes.
	 */
	private static final Logger log = LoggerFactory.getLogger(TimingJob.class);

	private static boolean SEND_EMAIL__IS_RUNNING = false;

	@Inject
	private TaskService taskService;

	private String mailServerHost;
	private String mailServerPort;
	private String mailServerUserName;
	private String mailServerPassword;
	private String mailIpAddress;
	private String mailStarttls;
	private String mailAuth;
	private String mailRealm;
	private String mailDefaultAssignee;

	public TimingJob() throws ParvanUnrecoverableException {
		try {
			// read config.properties
			String userHome = System.getProperty("user.home");
			if (userHome != null && userHome.length() > 0) {
				InputStream stream = new FileInputStream(userHome + "/issueManager-config.properties");

				// load properties
				Properties props = new Properties();
				props.load(stream);
				stream.close();

				this.mailServerHost = (String) props.get("mail.server.host");
				this.mailServerPort = (String) props.get("mail.server.port");
				this.mailServerUserName = (String) props.get("mail.server.userName");
				this.mailServerPassword = (String) props.get("mail.server.password");
				this.mailIpAddress = (String) props.get("mail.ipAddress");
				this.mailStarttls = (String) props.get("mail.starttls");
				this.mailAuth = (String) props.get("mail.auth");
				this.mailRealm = (String) props.get("mail.realm");
				this.mailDefaultAssignee = (String) props.get("assignee.default");
							
			}
		} catch (Exception ex) {

			throw new ParvanUnrecoverableException("set properties faild.", ex);
		}

		log.debug("Job Created.");
	}

	@Schedule(minute = "*/30", hour = "*", info = "MANIFEST_INDEX", persistent = false)
	public void sendEmail(Timer timer) throws ParvanServiceException {
		log.debug("start running job indexManifest [{}]", LocalDateTime.now());

		try {
			if (SEND_EMAIL__IS_RUNNING) {
				log.debug("ignoring timer due to currently running job. [{}]");

			} else {

				SEND_EMAIL__IS_RUNNING = true;
				taskService.sendEmail(mailServerUserName,mailServerPassword,mailServerHost,mailServerPort,mailIpAddress,mailStarttls,mailAuth,mailRealm,mailDefaultAssignee);
			}

		} finally {
			SEND_EMAIL__IS_RUNNING = false;
		}
	}
}