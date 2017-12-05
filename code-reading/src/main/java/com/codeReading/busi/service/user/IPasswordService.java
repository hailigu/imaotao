package com.codeReading.busi.service.user;

import com.codeReading.core.framework.web.ResultData;

/**
 * 用户密码相关服务
 * @author Rofly
 */
public interface IPasswordService {
	/**
	 * 访问忘记密码页面
	 * @param keyinfo
	 * @return
	 * @throws Exception
	 */
	public ResultData forget(String keyinfo) throws Exception;
}
