package com.codeReading.core.search;

import java.util.List;

import com.codeReading.core.framework.exception.AbsBusiException;

public interface IAddAble<M> extends ISearchType {
	/**
	 * 新增对象
	 * @param m 对象信息
	 * @return 是否顺利完成添加
	 * @throws AbsBusiException
	 */
	public boolean add(M m) throws AbsBusiException;
	/**
	 * 新增多个对象(throwError = fasle)
	 * @see #add(List, boolean)
	 * @param ms 多条对象信息
	 * @return 是否顺利完成添加
	 * @throws AbsBusiException
	 */
	public boolean add(List<M> ms) throws AbsBusiException;
	/**
	 * 新增多个对象
	 * @param ms 多条对象信息
	 * @param throwError 如果有未通过检测的用户信息，是否中断添加操作
	 * @return 是否顺利完成添加
	 * @throws AbsBusiException
	 */
	public boolean add(List<M> ms, boolean throwError) throws AbsBusiException;
	/**
	 * 获取当前操作索引名称
	 * @return 索引名称
	 */
	public String getIndex();
}
