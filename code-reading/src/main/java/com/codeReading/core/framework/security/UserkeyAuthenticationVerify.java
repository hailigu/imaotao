package com.codeReading.core.framework.security;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import com.codeReading.busi.dpn.cache.VerifyImageCodeCache;

public class UserkeyAuthenticationVerify extends VerifyImageCodeCache {

	private final String VERIFICATION_COUNT = "try_count:";
	private final String VERIFICATION_NEED = "verify_need:";

	public void set(String sessionid, Integer count) {
		valueOperations.set(VERIFICATION_COUNT + sessionid, count, expireMinutes, TimeUnit.MINUTES);
	}

	public void set(String sessionid, Boolean isneed) {
		valueOperations.set(VERIFICATION_NEED + sessionid, isneed, expireMinutes, TimeUnit.MINUTES);
	}

	public int getCount(String sessionid) {
		Serializable value = valueOperations.get(VERIFICATION_COUNT + sessionid);
		if (null != value) {
			return (Integer) value;
		}
		return 0;
	}

	public boolean getNeed(String sessionid) {
		Serializable value = valueOperations.get(VERIFICATION_NEED + sessionid);
		if (null != value) {
			return (Boolean) value;
		}
		return false;
	}

	public void expire(String sessionid) {
		valueOperations.set(VERIFICATION_COUNT + sessionid, 0, 1, TimeUnit.MILLISECONDS);
		valueOperations.set(VERIFICATION_NEED + sessionid, false, 1, TimeUnit.MILLISECONDS);
	}
}
