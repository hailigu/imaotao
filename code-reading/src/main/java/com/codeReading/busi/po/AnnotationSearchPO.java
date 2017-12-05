package com.codeReading.busi.po;

import java.io.Serializable;

public class AnnotationSearchPO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String keywords;
	private String tag;
	private String userid;
	private String projectid;
	
	private String busistate;
	private String state;
	
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getBusistate() {
		return busistate;
	}
	public void setBusistate(String busistate) {
		this.busistate = busistate;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getProjectid() {
		return projectid;
	}
	public void setProjectid(String projectid) {
		this.projectid = projectid;
	}
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		if(null != keywords)sb.append("keywords").append("=").append(keywords).append(", ");
		if(null != tag)sb.append("tag").append("=").append(tag).append(", ");
		if(null != projectid)sb.append("projectid").append("=").append(projectid).append(", ");
		if(null != userid)sb.append("userid").append("=").append(userid).append(", ");
		if(null != busistate)sb.append("busistate").append("=").append(busistate).append(", ");
		if(null != state)sb.append("state").append("=").append(state);
		sb.append("]");
		return sb.toString();
	}
	
}
