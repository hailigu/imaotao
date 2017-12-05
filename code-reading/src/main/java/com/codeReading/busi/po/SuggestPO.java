package com.codeReading.busi.po;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

public class SuggestPO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String content;
	private String contact;
	private String path;
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = StringUtils.trim(content).replaceAll("<", "&lt;");
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = StringUtils.trim(contact).replaceAll("<", "&lt;");
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("suggestpo=[");
		if(null != path)sb.append("path=").append(path).append(",");
		if(null != contact)sb.append("contact=").append(contact).append(",");
		if(null != content)sb.append("content=").append(content);
		sb.append("]");
		return sb.toString();
	}
}
