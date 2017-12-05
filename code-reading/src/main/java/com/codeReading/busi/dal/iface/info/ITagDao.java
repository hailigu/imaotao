package com.codeReading.busi.dal.iface.info;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.exceptions.PersistenceException;

import com.codeReading.busi.dal.model.Tag;
import com.codeReading.core.framework.db.BaseDao;
import com.codeReading.core.framework.exception.DataAccessException;

public interface ITagDao extends BaseDao<Tag> {
	/**
	 * 根据主键获取标签信息
	 * @param tagid 标签编号
	 * @return 标签信息
	 */
	public Tag get(String tagid) throws PersistenceException;
	
	/**
	 * 根据主键增加标签使用次数(每次加一)
	 * @param tagid 标签编号
	 * @return
	 */
	public void addUseCount(String tagid) throws DataAccessException;
	
	/**
	 * 根据主键减少标签使用次数(每次减一)
	 * @param tagid 标签编号
	 * @return
	 */
	public void decreaseUseCount(String tagid) throws DataAccessException;
	
	/**
	 * 根据主键删除标签信息
	 * @param tagid 标签编号
	 * @return
	 */
	public void delete(String tagid) throws PersistenceException;
	
	/**
	 * 根据标签名搜索标签信息
	 * @param map[tagname] map数据中必须包含tagname、pageSize
	 * @return
	 */
	public List<Tag> filterByTagname(Map<String, Object> param) throws DataAccessException;
	
	/**
	 * 根据用户编号获取标签信息
	 * @param userid 用户编号
	 * @return
	 */
	public List<Tag> findTagByUser(String userid) throws DataAccessException;
	/**
	 * 一次查询多个标签信息
	 * @param tagid
	 * @return
	 * @throws PersistenceException
	 */
	public List<Tag> findWhereIn(String[] tagid) throws PersistenceException;
}
