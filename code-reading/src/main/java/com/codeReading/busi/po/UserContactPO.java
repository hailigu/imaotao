package com.codeReading.busi.po;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

/**
 * @commons: 数据传输对象(用户联系人)
 * @vision: 1.0.1
 */
public class UserContactPO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String ucid; //联系方式编号
	private String contact; //联系方式
	private String type; //类型
	
	public String getUcid() {
		return ucid;
	}
	public void setUcid(String ucid) {
		this.ucid = ucid;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = StringUtils.trim(contact).replaceAll("<", "&lt;");
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@Override
	public String toString(){
		StringBuffer sb = new StringBuffer();
		if(null != contact)sb.append("contact=").append(contact).append(",");
		if(null != type)sb.append("type=").append(type).append("");
		return sb.toString();
	}
}
