package com.codeReading.core.email;

import java.util.Iterator;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codeReading.core.util.Strings;

public class MailSender {
	private Logger log = LoggerFactory.getLogger(MailSender.class);
	
	private String hostName;
	private int smtpPort = 25;
	private boolean isSSL;
	private String authName; //邮件发送人 邮件地址
	private String authNameTitle; //如果 MailInfoBean 中的 senderName 没有被指定，则使用本字段作为邮件发送人署名
	private String authPassword;
	
	private boolean ready = false;
	
	public MailSender(String hostName, int smtpPort, boolean isSSL, String authName, String authNameTitle, String authPassword) {
		this.hostName = hostName;
		this.smtpPort = smtpPort;
		this.isSSL = isSSL;
		this.authName = authName;
		this.authNameTitle = authNameTitle;
		this.authPassword = authPassword;
		if(null != hostName && null != authName && null != authPassword){
			ready = true;
		}
	}
	
	public boolean send(MailInfoBean mailInfo){
		if(isReady()){
			return prepareAndSend(mailInfo);
		}else{
			log.error("邮件信息未初始化，不能发送");
			return false;
		}
	}

	private boolean prepareAndSend(MailInfoBean mailInfo) {
		try {
			String mailContent = prepareMailContent(mailInfo);
			HtmlEmail email = new HtmlEmail();
			//set send information
			email = new HtmlEmail();
			email.setHostName(hostName);
			email.setSSLOnConnect(isSSL);
			email.setSmtpPort(smtpPort);
			email.setAuthentication(authName, authPassword);
			String senderName = (null != mailInfo.getSenderName()) ? mailInfo.getSenderName() : getAuthNameTitle();
			email.setFrom(authName, senderName);
			email.setCharset("UTF-8");
			//set to information
			email.addTo(mailInfo.getTo());
			email.setSubject(mailInfo.getSubject());
			email.setHtmlMsg(mailContent);
			email.setTextMsg("您的邮件客户端暂不支持HTML，请检查相关设置。");
			if(null != email.send()){
				return true;
			}else{
				return false;
			}
		} catch (EmailException e) {
			log.error("邮件下发过程异常", e);
			return false;
		}
	}
	/**
	 * 准备待发送信息
	 * @param mailInfo
	 * @return
	 */
	private String prepareMailContent(MailInfoBean mailInfo) {
		String content = getMailTemplate(mailInfo.getType());
		//replace the mapable data.
		if(null != mailInfo.getKeyStore()){
			Iterator<String> keys = mailInfo.getKeyStore().keySet().iterator();
			String key = null;
			while(keys.hasNext()){
				key = keys.next();
				content = content.replaceAll("\\{"+key+"\\}", mailInfo.getKeyStore().get(key));
			}
		}
		//replace the listable data.
		return content;
	}

	/**
	 * 获取邮件模板
	 * @param type
	 * @return
	 */
	private String getMailTemplate(MailType type) {
		return getContentFromFile(type+".xml");
	}

	private String getContentFromFile(String templatename) {
		return Strings.readFileContent("mails/", templatename);
	}

	private boolean isReady() {
		return ready;
	}

	public String getAuthNameTitle() {
		return authNameTitle;
	}

	public void setAuthNameTitle(String authNameTitle) {
		this.authNameTitle = authNameTitle;
	}
}
