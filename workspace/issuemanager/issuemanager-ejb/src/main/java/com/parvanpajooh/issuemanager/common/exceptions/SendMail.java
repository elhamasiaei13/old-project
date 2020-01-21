package com.parvanpajooh.issuemanager.common.exceptions;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail {

	public void send(String[] to, String body,String subject, String username, String password, String host, String port, String starttls, String auth, String realm)
			throws MessagingException {

		String socketFactoryClass = "javax.net.ssl.SSLSocketFactory";
		// String socketFactoryClass = "";
		boolean debug = true;

		String fallback = "false";

		Properties pr = new Properties();

		pr.put("mail.trasport.protocol", "smtp");

		pr.put("mail.smtp.user", username);

		pr.put("mail.smtp.host", host);

		if (!"".equals(port)) {
			pr.put("mail.smtp.port", port);
		}

		if (!"".equals(starttls)) {
			pr.put("mail.smtp.starttls.enable", starttls);

			if (!"".equals(socketFactoryClass) && starttls.equalsIgnoreCase("true")) {
				pr.put("mail.smtp.socketFactory.class", socketFactoryClass);
			}
		}

		pr.put("mail.smtp.auth", auth);

		if (realm != null && realm.length() > 0 && (!realm.equalsIgnoreCase("-"))) {
			pr.put("mail.smtp.sasl.realm", realm);
		}

		if (debug) {
			pr.put("mail.smtp.debug", "true");
		} else {
			pr.put("mail.smtp.debug", "false");
		}

		if (!"".equals(port)) {
			pr.put("mail.smtp.socketFactory.port", port);
		}

		if (!"".equals(fallback)) {
			pr.put("mail.smtp.socketFactory.fallback", fallback);
		}

		/*System.out.println();
		System.out.println("........................................................");
		System.out.println("SEND_MAIL: " + pr.toString());
		System.out.println("........................................................");
		System.out.println();
		System.out.println();*/

		/*
		 * String[] mechanisms = new String[]{"DIGEST-MD5", "PLAIN"}; SaslClient
		 * sc = Sasl.createSaslClient(mechanisms, authzid, protocol, serverName,
		 * props, callbackHandler);
		 */
		
		Session session = Session.getDefaultInstance(pr, null);
		session.setDebug(debug);
		MimeMessage msg = new MimeMessage(session);
		msg.setSentDate(new Date());
		msg.setFrom(new InternetAddress(username)); 
		for (int i = 0; i < to.length; i++) {			
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to[i]));
			msg.setText(body);
			msg.setSubject(subject);			
		}

		msg.saveChanges();
		Transport transport = session.getTransport("smtp");

		// ((SMTPTransport)transport).setSASLRealm(java.lang.String saslRealm)
		// Sets the SASL realm to be used for DIGEST-MD5 authentication.
		// http://java.sun.com/products/javamail/javadocs/index.html

		// log
		System.out.println();
		System.out.println("........................................................");
		System.out.println("SEND_MAIL: Try to connect...");
		System.out.println("........................................................");
		System.out.println();

		// connect
		transport.connect(host, username, password);

		// log
		System.out.println();
		System.out.println("........................................................");
		System.out.println("SEND_MAIL: Connected.");
		System.out.println("........................................................");
		System.out.println();

		// log
		System.out.println();
		System.out.println("........................................................");
		System.out.println("SEND_MAIL: Try to send...");
		System.out.println("........................................................");
		System.out.println();

		
		// send
		transport.sendMessage(msg, msg.getAllRecipients());

		// log
		System.out.println();
		System.out.println("........................................................");
		System.out.println("SEND_MAIL: sent.");
		System.out.println("........................................................");
		System.out.println();

		// close
		transport.close();

		// log
		System.out.println();
		System.out.println("........................................................");
		System.out.println("SEND_MAIL: close.");
		System.out.println("........................................................");
		System.out.println();

	}	

}
