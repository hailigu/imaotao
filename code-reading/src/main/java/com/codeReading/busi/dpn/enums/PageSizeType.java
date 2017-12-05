package com.codeReading.busi.dpn.enums;

import com.codeReading.core.framework.db.PageType;

/**
 * 分页大小设定
 * @author Rofly
 */
public enum PageSizeType implements PageType {
	DEFAULT, SERVICE, MY_ORDER, NEWS,CASES, ACCOUNT, ARTICLE,SOURCEPROJECT,SOURCEWATCH,ANNOTATION
	;

	@Override
	public int getPageSize() {
		//只需要指定非默认的分页大小
		switch (this) {
			case MY_ORDER: return 10;
			case NEWS: return 20;
			case CASES:return 20;
			case ACCOUNT: return 10;
			case ARTICLE: return 15;
			case SOURCEPROJECT: return 10;
			case SOURCEWATCH: return 10;
			case ANNOTATION:return 10;
			default: return 15;
		}
	}
	
}
