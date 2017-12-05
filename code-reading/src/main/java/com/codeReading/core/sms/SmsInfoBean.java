package com.codeReading.core.sms;

import java.util.Map;

public class SmsInfoBean {
	
	private String[] to; //短信接收人
	private SmsType type; //设置以找到对应模板
	private Map<String, String> keyStore; //模板中以key-value存储使用的数据
	
	/** 收件人 */
	public String[] getTo() {
		return to;
	}
	/** 收件人 */
	public void setTo(String ... to) {
		this.to = to;
	}
	/** 短信类型 */
	public SmsType getType() {
		return type;
	}
	/** 短信类型 */
	public void setType(SmsType type) {
		this.type = type;
	}
	/** 短信模板中匹配的数据 */
	public Map<String, String> getKeyStore() {
		return keyStore;
	}
	/** 短信模板中匹配的数据 */
	public void setKeyStore(Map<String, String> keyStore) {
		this.keyStore = keyStore;
	}
}