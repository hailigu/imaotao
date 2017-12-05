package com.codeReading.busi.dpn.cache;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@Component
public class VerificationCodeCache {
	@Autowired
	private ValueOperations<String, Serializable> valueOperations;

	private final String VERIFICATION_KEY = "verify:";
	private final String VERIFICATION_COUNT = "verify_count:";
	private final int EXPIRE_COUNT = 3;
	private long expireMinutes = 30; // 默认30分钟

	/**
	 * 缓存验证码值域信息
	 * 
	 * @param keyinfo
	 *            验证存储信息
	 * @param code
	 *            验证码
	 */
	public void set(String keyinfo, String code) {
		valueOperations.set(VERIFICATION_KEY + keyinfo, code, expireMinutes, TimeUnit.MINUTES);
		valueOperations.set(VERIFICATION_COUNT + keyinfo, 0, expireMinutes, TimeUnit.MINUTES);
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
	public boolean verify(String keyinfo, String code) {
		String storeCode = (String) valueOperations.get(VERIFICATION_KEY + keyinfo);
		Integer verifyCount = (Integer) valueOperations.get(VERIFICATION_COUNT + keyinfo);
		valueOperations.set(VERIFICATION_COUNT + keyinfo, ++verifyCount, expireMinutes, TimeUnit.MINUTES);

		if (verifyCount <= EXPIRE_COUNT && code.equals(storeCode)) {
			expire(keyinfo);
			return true;
		} else {
			if (verifyCount > EXPIRE_COUNT) {
				expire(keyinfo);
			}
		}
		return false;
	}

	/**
	 * 使验证存储信息过期
	 * 
	 * @param keyinfo
	 *            验证存储信息
	 */
	public void expire(String keyinfo) {
		valueOperations.set(VERIFICATION_KEY + keyinfo, Math.random(), 1, TimeUnit.MILLISECONDS);
		valueOperations.set(VERIFICATION_COUNT + keyinfo, EXPIRE_COUNT, 1, TimeUnit.MILLISECONDS);
	}

	public void setExpireMinutes(long expireMinutes) {
		this.expireMinutes = expireMinutes;
	}

	public void setValueOperations(ValueOperations<String, Serializable> valueOperations) {
		this.valueOperations = valueOperations;
	}

}
