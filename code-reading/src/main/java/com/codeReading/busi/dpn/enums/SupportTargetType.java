package com.codeReading.busi.dpn.enums;

public enum SupportTargetType {
	ARTICLE("1"),
	ANNOTATION("2"),
	;
	private String type;
	
	private SupportTargetType(String type){
		this.type = type;
	}
	
	public String getType(){
		return type;
	}
}
