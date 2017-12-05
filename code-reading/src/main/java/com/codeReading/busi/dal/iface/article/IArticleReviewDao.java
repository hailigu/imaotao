package com.codeReading.busi.dal.iface.article;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.exceptions.PersistenceException;

import com.codeReading.busi.dal.model.ArticleReview;
import com.codeReading.core.framework.db.BaseDao;
import com.codeReading.core.framework.db.QueryBean;
import com.codeReading.core.framework.exception.DataAccessException;

public interface IArticleReviewDao extends BaseDao<ArticleReview>{
	/**
	 * 根据主键获取评价信息
	 * @param reviewid 评价编号
	 * @return 评价信息
	 */
	public ArticleReview get(String reviewid) throws PersistenceException;
	
	/**
	 * 按页获取文章评价
	 * @param param 参数 articleid：文章编号 busistate：业务状态 sate：数据状态
	 * @return
	 */
	public List<Map<String, Object>> findReviewsByPage(QueryBean<Map<String, Object>> query) throws DataAccessException;
	
	/**
	 * 获取评价的回复信息
	 * @param articleid：文章编号、author：目标评价的用户编号
	 * @return 评价信息
	 */
	public List<Map<String, Object>> findReviewReplys(Map<String, Object> params) throws DataAccessException;
}
