package com.codeReading.busi.dpn.exception.search;

import com.codeReading.core.framework.exception.AbsBusiException;

public class SearchOperationRuntimeException extends AbsBusiException {
	private static final long serialVersionUID = 1L;
	
	private final static String retCode = "033001";
	
	public SearchOperationRuntimeException() {
		setRetCode(retCode);
	}
	
	public SearchOperationRuntimeException(String msg) {
		super(retCode, msg);
	}
}
