package com.codeReading.busi.dal.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 系统通知信息对象
 * @author Rofly
 */
public class Notify implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String notifyid; //通知编号
	private String type; //1.用户预约  2.账户变动 3.用户变动 4.信息审核 5.文章审核 6.系统通知
	private String userid; //用户编号
	private String objectid; //关联信息号(orderid/accountid/userid/expertid/articleid)
	private String state; //状态：2.未读 3.已读  4.删除
	private String content; //通知内容
	private Timestamp readtime; //阅读时间
	private Timestamp modtime; //修改时间
	private Timestamp intime; //入库时间
	
	public String getNotifyid() {
		return notifyid;
	}
	public void setNotifyid(String notifyid) {
		this.notifyid = notifyid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getObjectid() {
		return objectid;
	}
	public void setObjectid(String objectid) {
		this.objectid = objectid;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Timestamp getReadtime() {
		return readtime;
	}
	public void setReadTime(Timestamp readtime) {
		this.readtime = readtime;
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
