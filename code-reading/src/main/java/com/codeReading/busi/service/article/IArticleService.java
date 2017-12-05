package com.codeReading.busi.service.article;

import java.util.List;
import java.util.Map;

import com.codeReading.busi.dal.model.Article;
import com.codeReading.busi.po.ArticlePO;
import com.codeReading.busi.po.ArticleSearchPO;
import com.codeReading.core.framework.db.PageBean;
import com.codeReading.core.framework.web.InnerResultData;
import com.codeReading.core.framework.web.ResultData;

public interface IArticleService {
	/**
	 * 搜索文章信息
	 * @param articleSearch 文章查询过滤信息
	 * @param page 分页信息
	 * @return 文章结果数据
	 * @throws Exception
	 */
	public ResultData findArticles(ArticleSearchPO articleSearch, PageBean page) throws Exception;
	
	
	/**
	 * 获取文章所属工程名称信息
	 * @param projectid 工程编号
	 * @return 工程结果数据
	 * @throws Exception
	 */
	public ResultData getProjectDetail(String projectid) throws Exception;
	
	/**
	 * 获取我的文章列表信息
	 * @param userid 用户编号
	 * @param page 分页信息
	 * @return 文章列表结果数据
	 * @throws Exception
	 */
	
	public ResultData findMyArticles(String userid, PageBean page) throws Exception;
	
	/**
	 * 获取指定用户的文章列表
	 * @param userid 用户编号
	 * @return 文章列表结果数据
	 * @throws Exception
	 */
	public ResultData findTheUserArticleList(String userid) throws Exception;
	
	/**
	 * 获取文章及相关信息
	 * @param articleid 文章编号
	 * @return 文章结果数据
	 * @throws Exception
	 */
	public ResultData getDetail(String articleid) throws Exception;
	
	/**
	 * 保存文章(草稿，未发表)
	 * @param articlepo 文章信息
	 * @param userid 用户编号
	 * @return
	 * @throws Exception
	 */
	public ResultData save(ArticlePO articlepo, String userid) throws Exception;
	
	/**
	 * 发表文章(提交审核)
	 * @param articlepo 文章信息
	 * @param userid 用户编号
	 * @return
	 * @throws Exception
	 */
	public ResultData publish(ArticlePO articlepo, String userid) throws Exception;
	
	/**
	 * 删除文章
	 * @param userid 用户编号
	 * @param articleid 文章编号
	 * @return
	 * @throws Exception
	 */
	public ResultData delete(String userid, String articleid) throws Exception;
	
	/**
	 * 增加文章页面的浏览数
	 * @return
	 * @throws Exception
	 */
	public ResultData addArticlePV(String articleid) throws Exception;
	
	/**
	 * 增加文章的点赞数
	 * @param userid 用户编号
	 * @param articleid 文章编号
	 * @param opt 点赞or踩
	 * @return
	 * @throws Exception
	 */
	public ResultData addArticleSP(String userid,String articleid,Integer opt) throws Exception;


	/**
	 * 分页查找文章详细信息（包括文章、用户。）
	 * @param article
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public InnerResultData<List<Map<String, Object>>> findDetailByPage(Article article, PageBean page) throws Exception;
}
