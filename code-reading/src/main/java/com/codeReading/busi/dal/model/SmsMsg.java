package com.codeReading.busi.dal.model;

import java.sql.Timestamp;

public class SmsMsg {
	private String smsid; //平台发送消息编号
	private String msgid; //消息编号(运营商)
	private String phone; //接受手机号码
	private String content; //发送内容
	private Integer send_state; //发送状态(运营商) 1.未反馈   2 成功 4 失败
	private Integer callback_state; //回馈状态(运营商) 1.未反馈   2 成功 4 失败
	private Timestamp modtime; //修改时间
	private Timestamp intime; //入库时间
	public String getSmsid() {
		return smsid;
	}
	public String getMsgid() {
		return msgid;
	}
	public String getPhone() {
		return phone;
	}
	public String getContent() {
		return content;
	}
	public Integer getSend_state() {
		return send_state;
	}
	public Integer getCallback_state() {
		return callback_state;
	}
	public Timestamp getModtime() {
		return modtime;
	}
	public Timestamp getIntime() {
		return intime;
	}
	public void setSmdid(String smsid) {
		this.smsid = smsid;
	}
	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setSend_state(Integer send_state) {
		this.send_state = send_state;
	}
	public void setCallback_state(Integer callback_state) {
		this.callback_state = callback_state;
	}
	public void setModtime(Timestamp modtime) {
		this.modtime = modtime;
	}
	public void setIntime(Timestamp intime) {
		this.intime = intime;
	}

}
