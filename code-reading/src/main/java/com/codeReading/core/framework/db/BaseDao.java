package com.codeReading.core.framework.db;

import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;

public interface BaseDao<T> {
	/**
	 * 根据条件查找信息列表
	 * @param t 查询信息对象
	 * @return 对象列表
	 * @throws PersistenceException
	 */
	public List<T> find(T t) throws PersistenceException;
	/**
	 * 查找所有信息列表
	 * @return 对象列表
	 * @throws PersistenceException
	 */
	public List<T> findAll() throws PersistenceException;
	/**
	 * 根据条件查找信息列表
	 * @param t 查询信息对象
	 * @return 对象列表
	 * @throws PersistenceException
	 */
	public List<T> findByPage(QueryBean<T> query) throws PersistenceException;
	/**
	 * 查找所有信息列表
	 * @return 对象列表
	 * @throws PersistenceException
	 */
	public List<T> findAllByPage(PageBean page) throws PersistenceException;
	/**
	 * 统计信息数
	 * @return 数据U条数
	 * @throws PersistenceException
	 */
	public int count(T t) throws PersistenceException;
	/**
	 * 增加一个信息对象
	 * @param t 信息对象
	 * @return 受影响行数
	 * @throws PersistenceException
	 */
	public int insert(T t) throws PersistenceException;
	/**
	 * 修改信息对象
	 * @param t 信息对象
	 * @return 受影响行数
	 * @throws PersistenceException
	 */
	public int update(T t) throws PersistenceException;
}
