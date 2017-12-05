package com.codeReading.busi.dal.iface.article;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.exceptions.PersistenceException;

import com.codeReading.busi.dal.model.RelationArticleTag;
import com.codeReading.core.framework.db.BaseDao;
import com.codeReading.core.framework.db.QueryBean;
import com.codeReading.core.framework.exception.DataAccessException;

public interface IArticleTagDao extends BaseDao<RelationArticleTag> {
	/**
	 * 根据主键获取文章标签关联信息
	 * @param atrelationid
	 * @return 文章标签关联信息
	 */
	public RelationArticleTag get(String atrelationid) throws PersistenceException;
	
	/**
	 * 根据文章编号获取文章标签信息
	 * @param articleid
	 * @return 文章标签信息列表
	 */
	public List<Map<String, Object>> findByArticle(String articleid) throws PersistenceException;
	
	/**
	 * 根据标签编号分页获取文章标签信息
	 * @param query：tagid
	 * @return 文章标签信息列表
	 */
	public List<RelationArticleTag> findArticleTagByPage(QueryBean<Map<String, Object>> query) throws DataAccessException;
	
	/**
	 * 根据主键删除记录
	 * @param atrelationid
	 */
	public void delete(String atrelationid) throws PersistenceException;
	
	/**
	 * 根据文章编号删除记录
	 * @param articleid
	 */
	public int deleteByArticleid(String articleid) throws PersistenceException;
}
