package com.codeReading.busi.dal.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @commons: t_tag 数据持久化对象
 * @vision: 1.0.1 
 * @comment:  标签信息表
 */
public class Tag implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String tagid;  //标签号
	private String tagname;  //标签名称
	private Integer count;  //使用次数
	private Integer totalcount;  //累计子类的使用次数
	private String description;  //标签描述
	private String state;  //状态：2.正常 4.注销
	private String inuser;  //创建人
	private Timestamp modtime; //修改时间
	private Timestamp intime;  //入库时间
	
	public String getTagid() {
		return tagid;
	}
	public void setTagid(String tagid) {
		this.tagid = tagid;
	}
	public String getTagname() {
		return tagname;
	}
	public void setTagname(String tagname) {
		this.tagname = tagname;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Integer getTotalcount() {
		return totalcount;
	}
	public void setTotalcount(Integer totalcount) {
		this.totalcount = totalcount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getInuser() {
		return inuser;
	}
	public void setInuser(String inuser) {
		this.inuser = inuser;
	}
	public Timestamp getModtime() {
		return modtime;
	}
	public void setModtime(Timestamp modtime) {
		this.modtime = modtime;
	}
	public Timestamp getIntime() {
		return intime;
	}
	public void setIntime(Timestamp intime) {
		this.intime = intime;
	}
}
