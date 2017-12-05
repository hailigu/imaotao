package com.codeReading.busi.service.sms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ISmsMsgService {
	public void asynCallbackSendSmsOfBaifen(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
