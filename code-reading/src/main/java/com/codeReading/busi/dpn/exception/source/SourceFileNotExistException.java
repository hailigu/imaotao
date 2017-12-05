package com.codeReading.busi.dpn.exception.source;

import com.codeReading.core.framework.exception.AbsBusiException;

public class SourceFileNotExistException extends AbsBusiException {
	private static final long serialVersionUID = 1L;
	
	private final static String retCode = "060001";
	
	public SourceFileNotExistException() {
		setRetCode(retCode);
	}
	
	public SourceFileNotExistException(String msg) {
		super(retCode, msg);
	}
}
