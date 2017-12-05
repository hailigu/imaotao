package com.codeReading.busi.dpn.exception.source;

import com.codeReading.core.framework.exception.AbsBusiException;

public class SourceProjectNotExistException extends AbsBusiException {
	private static final long serialVersionUID = 1L;
	
	private final static String retCode = "060000";
	
	public SourceProjectNotExistException() {
		setRetCode(retCode);
	}
	
	public SourceProjectNotExistException(String msg) {
		super(retCode, msg);
	}
}
