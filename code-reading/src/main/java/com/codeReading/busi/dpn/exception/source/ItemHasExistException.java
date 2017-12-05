package com.codeReading.busi.dpn.exception.source;

import com.codeReading.core.framework.exception.AbsBusiException;

public class ItemHasExistException extends AbsBusiException {
	private static final long serialVersionUID = 1L;
	
	private final static String retCode = "060003";
	
	public ItemHasExistException() {
		setRetCode(retCode);
	}
	
	public ItemHasExistException(String msg) {
		super(retCode, msg);
	}
}
