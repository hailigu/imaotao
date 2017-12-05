package com.codeReading.busi.dpn.enums;

public enum SourceFileType {
	DIR("1"),
	FILE("2"),
	;
	private String type;
	
	private SourceFileType(String type){
		this.type = type;
	}
	
	public String getType(){
		return type;
	}
}
