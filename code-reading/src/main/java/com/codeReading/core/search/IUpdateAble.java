package com.codeReading.core.search;

import com.codeReading.core.framework.exception.AbsBusiException;

public interface IUpdateAble<M> extends ISearchType {
	/**
	 * 更新索引
	 * @param m
	 * @return
	 * @throws AbsBusiException
	 */
	public boolean update(M m) throws AbsBusiException;
	/**
	 * 更新或数据不存在新增索引
	 * @param m
	 * @return
	 * @throws AbsBusiException
	 */
	public boolean upsert(M m) throws AbsBusiException;
	/**
	 * 获取当前操作索引名称
	 * @return 索引名称
	 */
	public String getIndex();
}
