package com.codeReading.busi.service.user.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.codeReading.busi.service.user.IPasswordService;
import com.codeReading.core.framework.web.BaseService;
import com.codeReading.core.framework.web.ResultData;

@Service
public class PasswordSerivce extends BaseService implements IPasswordService {
	private Logger log = LoggerFactory.getLogger(PasswordSerivce.class);

	@Override
	public ResultData forget(String keyinfo) throws Exception {
		log.debug("[服务] 开始访问忘记密码页面");
		ResultData result = ResultData.init();
		result.setData("keyinfo", keyinfo);
		log.info("[服务] 完成访问忘记密码页面");
		return result;
	}
}
