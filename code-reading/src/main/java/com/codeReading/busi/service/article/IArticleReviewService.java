package com.codeReading.busi.service.article;

import com.codeReading.busi.dal.model.ArticleReview;
import com.codeReading.busi.po.ArticleReviewPO;
import com.codeReading.core.framework.db.PageBean;
import com.codeReading.core.framework.web.InnerResultData;
import com.codeReading.core.framework.web.ResultData;

/**
 * @commonts: 服务接口
 * @vision 1.0.1
 */
public interface IArticleReviewService {
	/**
	 * 保存文章评价
	 * @param reviewpo 评价信息
	 * @param currentUserid 当前操作用户
	 * @return
	 */
	public ResultData save(ArticleReviewPO reviewpo, String currentUserid) throws Exception;
	
	/**
	 * 获取文章的评价信息
	 * @param articleid 文章编号
	 * @param page 页
	 * @return
	 */
	public ResultData getArticleReviews(String articleid,PageBean page) throws Exception;
	
	/**
	 * [内部调用]根据评价编号获取评价信息
	 * @param reviewid 评价编号
	 * @return
	 * @throws Exception
	 */
	public InnerResultData<ArticleReview> getArticleReview(String reviewid) throws Exception;
}
