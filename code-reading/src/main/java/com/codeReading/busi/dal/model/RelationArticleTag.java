package com.codeReading.busi.dal.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @table: t_relation_aiticle_tag
 * @commons: 文章标签关系表
 */
public class RelationArticleTag implements Serializable {
	private static final long serialVersionUID = 1L;

	private String atrelationid;  // 标签关系编号
	private String articleid;  // 文章编号
	private String tagid;  // 标签编号
	private Timestamp modtime;  // 修改时间
	private Timestamp intime;  // 入库时间
	
	public String getAtrelationid() {
		return atrelationid;
	}
	public void setAtrelationid(String atrelationid) {
		this.atrelationid = atrelationid;
	}
	public String getArticleid() {
		return articleid;
	}
	public void setArticleid(String articleid) {
		this.articleid = articleid;
	}
	public String getTagid() {
		return tagid;
	}
	public void setTagid(String tagid) {
		this.tagid = tagid;
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
