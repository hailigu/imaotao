package com.codeReading.busi.po;

import java.io.Serializable;

/**
 * @commons: 数据传输对象(文章评价)
 * @vision: 1.0.1
 */
public class ArticleReviewPO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String reviewid; // 评价编号
	private String articleid; // 文章编号
	private String content; // 评价内容
	
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content =  content;	//前端js做了xss注入防护，此处不再需要
	}
	
	@Override
	public String toString(){
		StringBuffer sb = new StringBuffer();
		if(null != reviewid)sb.append("reviewid=").append(reviewid).append(",");
		if(null != articleid)sb.append("articleid=").append(articleid).append(",");
		if(null != content)sb.append("content=").append(content).append(",");
		return sb.toString();
	}
}
