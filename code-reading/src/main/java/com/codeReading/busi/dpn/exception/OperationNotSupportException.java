package com.codeReading.busi.dpn.exception;

import com.codeReading.core.framework.exception.AbsBusiException;

public class OperationNotSupportException extends AbsBusiException {
	private static final long serialVersionUID = 1L;
	
	private final static String retCode = "032006";
	
	public OperationNotSupportException() {
		setRetCode(retCode);
	}
	
	public OperationNotSupportException(String msg) {
		super(retCode, msg);
	}
}
