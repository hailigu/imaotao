package com.codeReading.core.search;

import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.sort.SortBuilder;


public interface SearchRepository {
	/**
	 * 检查能否执行搜索操作
	 */
	public boolean canSearch();
	/**
	 * 根据搜索相关内容构建搜索查询对象
	 * @return 搜索查询对象
	 */
	public QueryBuilder makeQuery();
	
	/**
	 * 根据搜索过滤相关内容构建搜索查询对象
	 * @return 搜索过滤对象
	 */
	public FilterBuilder makeFilter();
	
	/**
	 * 根据排序条件，构建排序对象
	 * @return 排序对象
	 */
	public SortBuilder makeSort();
}
