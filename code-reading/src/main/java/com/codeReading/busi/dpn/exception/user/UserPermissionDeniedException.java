package com.codeReading.busi.dpn.exception.user;

import com.codeReading.core.framework.exception.AbsBusiException;

public class UserPermissionDeniedException extends AbsBusiException {
	private static final long serialVersionUID = 1L;
	
	private final static String retCode = "031005";
	
	public UserPermissionDeniedException() {
		setRetCode(retCode);
	}
	
	public UserPermissionDeniedException(String msg) {
		super(retCode, msg);
	}
}
