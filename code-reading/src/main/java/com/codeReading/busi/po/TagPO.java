package com.codeReading.busi.po;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

/**
  * @commons: 数据传输对象(标签信息)
  * @vision: 1.0.1
  */
public class TagPO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String tagid;  //标签号
	private String tagname;  //标签名称
	private Integer count;  //使用次数
	private String description;  //标签描述
	private String state;  //状态：2.正常 4.注销
	
	public String getTagid(){
		return this.tagid;
	}
	public void setTagid(String tagid){
		this.tagid = tagid;
	}
	public String getTagname() {
		return tagname;
	}
	public void setTagname(String tagname) {
		this.tagname = StringUtils.trim(tagname).replaceAll("<", "&lt;");
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = StringUtils.trim(description).replaceAll("<", "&lt;");
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		if(null != tagid)sb.append("tagid=").append(tagid).append(",");
		if(null != tagname)sb.append("tagname=").append(tagname).append(",");
		if(null != count)sb.append("count=").append(count).append(",");
		if(null != description)sb.append("description=").append(description).append(",");
		if(null != state)sb.append("state=").append(state).append(",");
		return sb.toString();
	}
}