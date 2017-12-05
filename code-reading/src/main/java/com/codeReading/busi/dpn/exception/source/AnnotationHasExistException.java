package com.codeReading.busi.dpn.exception.source;

import com.codeReading.core.framework.exception.AbsBusiException;

public class AnnotationHasExistException extends AbsBusiException {
	private static final long serialVersionUID = 1L;
	
	private final static String retCode = "060002";
	
	public AnnotationHasExistException() {
		setRetCode(retCode);
	}
	
	public AnnotationHasExistException(String msg) {
		super(retCode, msg);
	}
}
