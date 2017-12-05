package com.codeReading.core.search;

import java.util.List;
import java.util.Map;

import com.codeReading.core.framework.db.PageBean;
import com.codeReading.core.framework.exception.AbsBusiException;

public interface ISearchAble<M> extends ISearchType {
	/**
	 * 获取单条记录
	 * @param id 记录主键
	 * @return 数据
	 * @throws AbsBusiException
	 */
	public Map<String, Object> get(String id) throws AbsBusiException;
	/**
	 * 一次获取多条记录
	 * @param ids 多条记录主键
	 * @return 数据集
	 * @throws AbsBusiException
	 */
	public List<Map<String, Object>> getForList(String ...ids) throws AbsBusiException;
	/**
	 * 执行搜索
	 * @param m
	 * @return
	 * @throws AbsBusiException
	 */
	public List<Map<String, Object>> search(M m, PageBean page) throws AbsBusiException;
	/**
	 * 获取当前操作索引名称
	 * @return 索引名称
	 */
	public String getIndex();
}
