package com.codeReading.busi.dal.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @table: t_article
 * @commons: 文章信息表
 */
public class Article implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String articleid; // 文章编号
	private String projectid;// 源码工程编号
	private String userid; // 用户编号
	private String title; // 文章标题
	private String summary; // 文章摘要
	private String content; // 文章内容
	private String busistate; // 业务状态:1.草稿 2.审核通过 3.已发布（审核中） 4.审核不通过
	private String state; // 状态:正常（2）、关闭（3）、删除（4）、锁定（6）
	private Float weight; // 排序权重（1.0-1.99）
	private Integer pageview; // 文章浏览数
	private Integer support; // 赞数
	private String remark; // 备注:例如审核不通过的原因
	private Timestamp publishtime; // 发布时间
	private Timestamp modtime;  // 修改时间
	private Timestamp intime;  // 入库时间
	
	public String getArticleid() {
		return articleid;
	}
	public void setArticleid(String articleid) {
		this.articleid = articleid;
	}
	public String getProjectid() {
		return projectid;
	}
	public void setProjectid(String projectid) {
		this.projectid = projectid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	public Float getWeight() {
		return weight;
	}
	public void setWeight(Float weight) {
		this.weight = weight;
	}
	public Integer getPageview() {
		return pageview;
	}
	public void setPageview(Integer pageview) {
		this.pageview = pageview;
	}
	public Integer getSupport() {
		return support;
	}
	public void setSupport(Integer support) {
		this.support = support;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Timestamp getPublishtime() {
		return publishtime;
	}
	public void setPublishtime(Timestamp publishtime) {
		this.publishtime = publishtime;
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
