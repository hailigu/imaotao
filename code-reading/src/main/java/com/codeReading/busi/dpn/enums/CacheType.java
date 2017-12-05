package com.codeReading.busi.dpn.enums;

import com.codeReading.core.cache.ICacheType;

public enum CacheType implements ICacheType{
	REGISTER_REGEX, VERIFICATION_CODE
	;

	@Override
	public String getType() {
		return this.toString().toLowerCase().replaceAll("_", ".");
	}
	
	@Override
	public int getTimeoutSeconds() {
		switch (this) {
		case REGISTER_REGEX:
			return 30*60;
			
		default:
			return 0;
		}
	}
}
