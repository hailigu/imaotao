package com.codeReading.busi.dpn.exception;

import com.codeReading.core.framework.exception.AbsBusiException;

public class IllegalVerificationCodeException extends AbsBusiException {
	private static final long serialVersionUID = 1L;
	
	private final static String retCode = "032004";
	
	public IllegalVerificationCodeException() {
		setRetCode(retCode);
	}
	
	public IllegalVerificationCodeException(String msg) {
		super(retCode, msg);
	}
}
