package com.codeReading.test;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;

public class TestMail {
	
	
	public static void main(String[] args) throws EmailException {
		String [] emails = {};
		Email email = new SimpleEmail();
		email = new HtmlEmail();
		email.setHostName("smtp.exmail.qq.com");
		email.setSSLOnConnect(false);
//		email.setSmtpPort(25);
		email.setAuthentication("kiford@rochern.com", "");
		email.setFrom("", "问津专家云平台");
		email.setCharset("UTF-8");
		email.addTo(emails);
		email.setSubject("");
		email.setMsg("");
	}
}
