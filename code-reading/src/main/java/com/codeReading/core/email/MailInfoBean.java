package com.codeReading.core.email;

import java.io.Serializable;
import java.util.Map;

public class MailInfoBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private String senderName; //发件人 显示名
	private String[] to; //邮件接收人
	private String subject; //邮件主题
	private MailType type; //设置以找到对应模板
	private String[] messages; //模板中填充信息内容
	private Map<String, String> keyStore; //模板中以key-value存储使用的数据
	
	/** 发件人显示名（可选，默认为"问津"） */
	public String getSenderName() {
		return senderName;
	}
	/** 发件人显示名（可选，默认为"问津"） */
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	/** 收件人 */
	public String[] getTo() {
		return to;
	}
	/** 收件人 */
	public void setTo(String... to) {
		this.to = to;
	}
	/** 邮件主题 */
	public String getSubject() {
		return subject;
	}
	/** 邮件主题 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	/** 邮件类型 */
	public MailType getType() {
		return type;
	}
	/** 邮件类型 */
	public void setType(MailType type) {
		this.type = type;
	}
	/** 邮件模板中匹配的数据（顺序有关） */
	public String[] getMessages() {
		return messages;
	}
	/** 邮件模板中匹配的数据（顺序有关） */
	public void setMessages(String... messages) {
		this.messages = messages;
	}
	/** 邮件模板中匹配的数据 */
	public Map<String, String> getKeyStore() {
		return keyStore;
	}
	/** 邮件模板中匹配的数据 */
	public void setKeyStore(Map<String, String> keyStore) {
		this.keyStore = keyStore;
	}
}