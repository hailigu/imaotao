package com.codeReading.busi.dpn.exception.user;

import com.codeReading.core.framework.exception.AbsBusiException;

public class UserNickNameExistException extends AbsBusiException {
	private static final long serialVersionUID = 1L;
	
	private final static String retCode = "031006";
	
	public UserNickNameExistException() {
		setRetCode(retCode);
	}
	
	public UserNickNameExistException(String msg) {
		super(retCode, msg);
	}
}
