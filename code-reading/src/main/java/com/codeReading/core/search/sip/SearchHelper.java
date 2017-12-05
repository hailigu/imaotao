package com.codeReading.core.search.sip;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.client.support.AbstractClient;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;

import com.codeReading.core.framework.db.PageBean;
import com.codeReading.core.framework.exception.AbsBusiException;
import com.codeReading.core.search.IAddAble;
import com.codeReading.core.search.ISearchAble;
import com.codeReading.core.search.IUpdateAble;

public abstract class SearchHelper<M> implements ISearchAble<M>, IAddAble<M>, IUpdateAble<M> {
	
	@Autowired protected SearchOperator searchOperator;
	
	@Override
	public Map<String, Object> get(String id) throws AbsBusiException {
		return searchOperator.getOne(getSearchType(), id);
	}
	
	@Override
	public List<Map<String, Object>> getForList(String... ids) throws AbsBusiException {
		return searchOperator.getForList(getSearchType(), ids);
	}
	
	@Override
	public String getIndex(){
		return searchOperator.getIndex();
	}
	
	protected AbstractClient client() {
		return searchOperator.getClient();
	}
	
	/**
	 * 填充处理分页数据
	 * @param request
	 * @param page
	 */
	protected void fillInPageBean(SearchRequestBuilder request, PageBean page) {
		if(null != page){
			//排序
			if(StringUtils.isNotEmpty(page.getOrderKey())){
				if(StringUtils.isNotEmpty(page.getAscend())){
					request.addSort(page.getOrderKey(), "asc".equals(page.getAscend())? SortOrder.ASC : SortOrder.DESC);
				}else{
					request.addSort(page.getOrderKey(), SortOrder.DESC);
				}
			}
			//分页
			if(null != page.getPageSize()) request.setSize(page.getPageSize());
			if(null != page.getOffSet())request.setFrom(page.getOffSet());
		}
	}
	
	@Override
	public boolean add(List<M> ms) throws AbsBusiException {
		return add(ms, false);
	}
}
