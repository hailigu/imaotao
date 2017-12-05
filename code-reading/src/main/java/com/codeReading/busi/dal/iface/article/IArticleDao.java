package com.codeReading.busi.dal.iface.article;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.exceptions.PersistenceException;

import com.codeReading.busi.dal.model.Article;
import com.codeReading.core.framework.db.BaseDao;
import com.codeReading.core.framework.db.QueryBean;
import com.codeReading.core.framework.exception.DataAccessException;

public interface IArticleDao extends BaseDao<Article> {
	/**
	 * 根据主键获取文章信息
	 * @param articleid
	 * @return
	 */
	public Article get(String articleid) throws PersistenceException;
	public List<Article> getByProjectId(String projectid) throws PersistenceException;
	public List<Article> findArticleByPage(QueryBean<Map<String, Object>> query) throws DataAccessException;
	public List<Article> findProjectArticlesByPage(QueryBean<Map<String, Object>> query) throws DataAccessException;
	public List<Article> findAllArticleByPage(QueryBean<Map<String, Object>> query) throws DataAccessException;
	public List<Article> findOtherArticles(Map<String, Object> params) throws DataAccessException;
	public List<Article> findMyArticleByPage(QueryBean<Map<String, Object>> query) throws DataAccessException;
	public List<Article> findTheUserArticleList(String userid) throws DataAccessException;
}
