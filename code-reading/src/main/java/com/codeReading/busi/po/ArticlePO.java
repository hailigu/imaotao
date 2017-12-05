package com.codeReading.busi.po;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

/**
 * @commons: 数据传输对象(文章)
 * @vision: 1.0.1
 */
public class ArticlePO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String articleid;  // 文章编号
	private String projectid; //源码工程编号
	private String title;  // 文章标题
	private String summary; // 文章摘要
	private String content;  // 文章内容
	private String tagids; // 标签集
	
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = StringUtils.trim(title).replaceAll("<", "&lt;");;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = StringUtils.trim(summary).replaceAll("<", "&lt;");;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTagids() {
		return tagids;
	}
	public void setTagids(String tagids) {
		this.tagids = tagids;
	}
	
	@Override
	public String toString(){
		StringBuffer sb = new StringBuffer();
		if(null != articleid)sb.append("articleid=").append(articleid).append(",");
		if(null != projectid)sb.append("projectid=").append(projectid).append(",");
		if(null != title)sb.append("title=").append(title).append(",");
		if(null != summary)sb.append("summary=").append(summary).append(",");
		if(null != content)sb.append("content=").append(content).append(",");
		if(null != tagids)sb.append("tagids=").append(tagids);
		return sb.toString();
	}
}
