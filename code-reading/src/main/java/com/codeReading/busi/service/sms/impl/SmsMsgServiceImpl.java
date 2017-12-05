package com.codeReading.busi.service.sms.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codeReading.busi.dal.iface.sms.ISmsMsgDao;
import com.codeReading.busi.dal.model.SmsMsg;
import com.codeReading.busi.service.sms.ISmsMsgService;
import com.codeReading.core.email.MailSender;
import com.codeReading.core.framework.web.BaseService;
import com.codeReading.core.util.DateUtil;

@Service
public class SmsMsgServiceImpl extends BaseService implements ISmsMsgService {
	
	private Logger log = LoggerFactory.getLogger(SmsMsgServiceImpl.class);
	
	@Autowired private ISmsMsgDao smsMsgDao; 
	@Autowired private MailSender mailsender;

	@Override
	public void asynCallbackSendSmsOfBaifen(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.debug("###[服务] 开始处理来自百分通联的通信异步回调请求");		
		String MsgID = request.getParameter("MsgID");
        String MobilePhone = request.getParameter("MobilePhone");
		String ReportState = request.getParameter("ReportState");  //回执状态
		String SendState = request.getParameter("SendState"); //发送状态
		log.debug("### MsgID=[{}]",MsgID);
		log.debug("### MobilePhone=[{}]",MobilePhone);
		log.debug("### ReportState=[{}]",ReportState);
		log.debug("### SendState=[{}]",SendState);
		//检查回执状态
		if("1".equals(ReportState) || "True".equals(ReportState))
		{
			ReportState = "2"; // 成功
		}
		else{
			ReportState = "4"; // 失败
		}
		//检查发送状态
		if("1".equals(SendState) || "True".equals(SendState))
		{
			SendState = "2"; // 成功
		}
		else{
			SendState = "4"; // 失败
		}
		
		//更新业务状态
		SmsMsg smsmsg = new SmsMsg();
		smsmsg.setMsgid(MsgID);
		smsmsg.setPhone(MobilePhone);
		List<SmsMsg> list_msg = smsMsgDao.find(smsmsg);
		if(list_msg!=null && list_msg.size()==1){
			SmsMsg tmp = list_msg.get(0);
			//更新业务
			SmsMsg model = new SmsMsg();
			model.setSmdid(tmp.getSmsid());
			model.setSend_state(Integer.parseInt(SendState));
			model.setCallback_state(Integer.parseInt(ReportState));
			model.setModtime(DateUtil.currentTimestamp());
			smsMsgDao.update(model);
		}
		
		log.info("###[服务] 完成处理来自百分通联的通信异步回调请求");
	}

}
