package com.codeReading.busi.dpn.cache;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@Component
public class VerifyImageCodeCache {
	@Autowired
	protected ValueOperations<String, Serializable> valueOperations;
	protected final String VERIFICATION_KEY = "verify:";

	protected long expireMinutes = 30; // 默认30分钟

	/**
	 * 缓存验证码值域信息
	 * 
	 * @param keyinfo
	 *            验证存储信息
	 * @param code
	 *            验证码
	 */
	public void set(String sessionid, String code) {
		valueOperations.set(VERIFICATION_KEY + sessionid, code, expireMinutes, TimeUnit.MINUTES);
	}

	/**
	 * 验证，如果成功则会移除掉已用过的验证码
	 * 
	 * @param keyinfo
	 *            验证存储信息
	 * @param code
	 *            验证码
	 * @return 是否通过验证
	 */
	public boolean verify(String sessionid, String code) {
		String storeCode = (String) valueOperations.get(VERIFICATION_KEY + sessionid);
		valueOperations.set(VERIFICATION_KEY + sessionid, Math.random(), 1, TimeUnit.MILLISECONDS);
		if (code.equalsIgnoreCase(storeCode)) {
			return true;
		}
		return false;
	}

	public void setExpireMinutes(long expireMinutes) {
		this.expireMinutes = expireMinutes;
	}

	public void setValueOperations(ValueOperations<String, Serializable> valueOperations) {
		this.valueOperations = valueOperations;
	}

}
