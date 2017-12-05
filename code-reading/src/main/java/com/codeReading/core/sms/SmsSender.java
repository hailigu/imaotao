package com.codeReading.core.sms;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.codeReading.busi.dal.iface.sms.ISmsMsgDao;
import com.codeReading.busi.dal.model.SmsMsg;
import com.codeReading.core.util.HttpUtil;
import com.codeReading.core.util.SeqUtil;

public class SmsSender {
	private Logger log = LoggerFactory.getLogger(SmsSender.class);
	
	@Autowired ISmsMsgDao smsMsgDao;
	
	private final String url;
	private final String authName;
	private final String password;
	private final String sprdid;
	private String scorpid = "";
	private String sign = "【问津】";
	
	private final Map<SmsType, String> templates;
	private Map<Integer, String> errorReasons = new HashMap<Integer, String>();
	
	private boolean ready = false;
	
	public SmsSender(String url, String authName, String password, String sprdid, Map<SmsType, String> templates) {
		this.url = url;
		this.authName = authName;
		this.password = password;
		this.sprdid = sprdid;
		this.templates = templates;
		if(null != this.url && null != this.authName && null != this.password && null != sprdid && null != this.templates){
			this.ready = true;
		}
	}
	
	public boolean send(SmsInfoBean smsInfo){
		if(isReady()){
			return prepareAndSend(smsInfo);
		}else{
			return false;
		}
	}

	private boolean prepareAndSend(SmsInfoBean smsInfo) {
		try {
			String smsContent = prepareSmsContent(smsInfo);
			if(null == smsContent || (null == smsInfo.getTo() || smsInfo.getTo().length==0)){
				log.error("发送消息信息结构不完整，取消发送。");
				return false;
			}
			Map<String, String> param = new HashMap<String, String>();
			param.put("sname", authName);
			param.put("spwd", password);
			param.put("scorpid", scorpid);
			param.put("sprdid", sprdid);
			param.put("sdst", StringUtils.join(smsInfo.getTo(), ","));
			param.put("smsg", smsContent + sign);
			String result = HttpUtil.getInstance().sendPost(url, param);
			log.trace("Send sms to user, return={}", result);
			int state = -99;
			String msgId = "";
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document xml = builder.parse(IOUtils.toInputStream(result, "UTF-8"));
			NodeList states = xml.getElementsByTagName("State");
			NodeList msgids = xml.getElementsByTagName("MsgID");
			if(null != states && 1 == states.getLength()) {
				state = Integer.parseInt(states.item(0).getTextContent());	
			}
			if(null != msgids && 1 == msgids.getLength()) {
				msgId = msgids.item(0).getTextContent();
			}
			
			SmsMsg model = new SmsMsg();
			model.setSmdid(SeqUtil.getSmsid());
			model.setPhone(StringUtils.join(smsInfo.getTo(), ","));
			model.setMsgid(msgId);
			model.setContent(smsContent + sign);
			
			if(0 == state){
				log.trace("发送短信成功 msg={}", smsContent);
				model.setSend_state(2);
				smsMsgDao.insert(model);
				return true;
			}else{
				log.error("下发短信失败：error state={}, reason={}", state, getErrorReason(state));
				model.setSend_state(4);
				smsMsgDao.insert(model);
				return false;
			}
		} catch (Exception e) {
			log.error("短信下发过程异常", e);
			return false;
		}
	}

	private String prepareSmsContent(SmsInfoBean smsInfo) {
		String content = getSmsTemplate(smsInfo.getType());
		if(null == content) return null;
		//replace the mapable data.
		if(null != smsInfo.getKeyStore()){
			Iterator<String> keys = smsInfo.getKeyStore().keySet().iterator();
			String key = null;
			while(keys.hasNext()){
				key = keys.next();
				content = content.replaceAll("\\{"+key+"\\}", smsInfo.getKeyStore().get(key));
			}
		}
		return content;
	}
	/**
	 * 根据短信错误码返回异常描述
	 * @param state 错误码
	 * @return 异常描述
	 */
	private String getErrorReason(int state) {
		return errorReasons.get(state);
	}

	private boolean isReady() {
		return ready;
	}
	private String getSmsTemplate(SmsType type) {
		return templates.get(type);
	}
	public void setScorpid(String scorpid) {
		this.scorpid = scorpid;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public void setErrorReasons(Map<Integer, String> errorReasons) {
		this.errorReasons = errorReasons;
	}
}