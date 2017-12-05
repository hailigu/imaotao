package com.codeReading.busi.dpn.exception;

import com.codeReading.core.framework.exception.AbsBusiException;

public class ItemNotFoundException extends AbsBusiException {
	private static final long serialVersionUID = 1L;
	
	private final static String retCode = "032007";
	
	public ItemNotFoundException() {
		setRetCode(retCode);
	}
	
	public ItemNotFoundException(String msg) {
		super(retCode, msg);
	}
}
