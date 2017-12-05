package com.codeReading.core.framework.db;

public class QueryBean<T> {
	private PageBean page;
	private T param;
	
	public QueryBean(){}
	public QueryBean(PageBean page, T t){
		this.page = page;
		this.param = t;
	}
	
	public PageBean getPage() {
		return page;
	}
	public void setPage(PageBean page) {
		this.page = page;
	}
	public T getParam() {
		return param;
	}
	public void setParam(T param) {
		this.param = param;
	}
}