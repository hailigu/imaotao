package com.codeReading.core.util;

import com.codeReading.core.framework.db.PageBean;
import com.codeReading.core.framework.db.PageType;

public class PageBeanUtil {
	public static PageBean initialize(int pageSize){
		PageBean page = new PageBean();
		page.setCurrentPage(1);
		page.setPageSize(pageSize);
		return page;
	}
	/**
	 * 初始化pageBean
	 * @param page
	 * @param type
	 * @return 传入的page对象和返回的page对象都会被初始化
	 */
	public static PageBean initialize(PageBean page, PageType type){
		if(null != page){
			if(null == page.getCurrentPage()) page.setCurrentPage(1);
			if(null == page.getPageSize()) page.setPageSize(type.getPageSize());
		}else{
			page = new PageBean();
			page.setCurrentPage(1);
			page.setPageSize(type.getPageSize());
		}
		return page;
	}
}
