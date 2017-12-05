package com.codeReading.busi.action.sms;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codeReading.busi.service.sms.ISmsMsgService;
import com.codeReading.core.framework.web.BaseAction;

@Controller
public class SmsMsgCallbackAction extends BaseAction {
	
	private Logger log = LoggerFactory.getLogger(SmsMsgCallbackAction.class);
	
	@Autowired private ISmsMsgService  smsMsgService;
	
	@RequestMapping(value="baifen/asyn-send-callback", method=RequestMethod.POST)
	public void asynCallbackSendSmsOfBaifen(HttpServletRequest request, HttpServletResponse response,Map<String, Map<String, Object>> result)
	{
		log.debug("### 开始处理来自百分通联的通信异步回调请求");
		try {
			smsMsgService.asynCallbackSendSmsOfBaifen(request, response);
		} catch (Exception e) {
			collect(e, result);
		}
	}
}
