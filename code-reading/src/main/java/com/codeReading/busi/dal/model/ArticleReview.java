package com.codeReading.busi.dal.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class ArticleReview implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String reviewid;  // 评价编号
	private String articleid;  // 文章编号
	private String objectid;  // 文章编号或者用户编号
	private String content;  // 评价内容
	private String attachments;  // 评价附件
	private String author; // 评价作者
	private String busistate; // 业务状态:2.审核通过 3.已发布（审核中） 4.审核不通过
	private String state; // 状态:正常（2）、删除（4）、锁定（6）
	private Timestamp modtime;  // 修改时间
	private Timestamp intime;  // 入库时间
	
	public String getReviewid() {
		return reviewid;
	}
	public void setReviewid(String reviewid) {
		this.reviewid = reviewid;
	}
	public String getArticleid() {
		return articleid;
	}
	public void setArticleid(String articleid) {
		this.articleid = articleid;
	}
	public String getObjectid() {
		return objectid;
	}
	public void setObjectid(String objectid) {
		this.objectid = objectid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAttachments() {
		return attachments;
	}
	public void setAttachments(String attachments) {
		this.attachments = attachments;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
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
