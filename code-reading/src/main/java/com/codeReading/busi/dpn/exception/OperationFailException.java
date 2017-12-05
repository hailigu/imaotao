package com.codeReading.busi.dpn.exception;

import com.codeReading.core.framework.exception.AbsBusiException;

public class OperationFailException extends AbsBusiException {
	private static final long serialVersionUID = 1L;
	
	private final static String retCode = "032005";
	
	public OperationFailException() {
		setRetCode(retCode);
	}
	
	public OperationFailException(String msg) {
		super(retCode, msg);
	}
}
