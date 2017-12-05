package com.codeReading.busi.service.notify.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codeReading.busi.dal.iface.notify.INotifyDao;
import com.codeReading.busi.dal.model.Notify;
import com.codeReading.busi.dpn.enums.NotifyConstant;
import com.codeReading.busi.service.notify.INotifyService;
import com.codeReading.core.framework.db.PageBean;
import com.codeReading.core.framework.db.QueryBean;
import com.codeReading.core.framework.web.BaseService;
import com.codeReading.core.framework.web.InnerResultData;
import com.codeReading.core.framework.web.ResultData;
import com.codeReading.core.util.SeqUtil;

@Service
public class NotifyService extends BaseService implements INotifyService {
	private Logger log = LoggerFactory.getLogger(NotifyService.class);
	
	@Autowired INotifyDao notifyDao;
	
	@Override
	public InnerResultData<Boolean> notify(String type, String content, String objectid, String... userids) throws Exception {
		log.debug("[服务]开始给用户发送通知信息，type={}, user={}", type, Arrays.toString(userids));
		InnerResultData<Boolean> result= new InnerResultData<Boolean>();
		Notify notify = new Notify();
		notify.setNotifyid(SeqUtil.produceNotifyid());
		notify.setContent(content);
		notify.setState(NotifyConstant.STATE_UNREAD);
		notify.setType(type);
		notify.setObjectid(objectid);
		
		for(String userid: userids){
			notify.setUserid(userid);
			notifyDao.insert(notify);
		}
		
		result.setData(true);
		
		log.info("[服务]完成给用户发送通知信息, result={}", result);
		return result;
	}
	
	@Override
	public ResultData findNotify(String userid, PageBean page) throws Exception {
		log.debug("[服务]开始查询用户的通知信息, user={}, page={}", userid, page);
		ResultData result = ResultData.init();
		Notify notify = new Notify();
		notify.setUserid(userid);
		page.setOrderKey("intime");
		page.setAscend("desc");
		
		List<Notify> notifies = notifyDao.findByPage(new QueryBean<Notify>(page, notify));
		
		result.setData("notifies", getFieldValueList(notifies));
		result.setData("page", getFieldValueMap(page));
		log.info("[服务]完成查询用户的通知信息, result={}", result);
		return result;
	}
	
	@Override
	public ResultData findUnreadNotify(String userid, String type) throws Exception {
		log.debug("[服务]开始查询用户的未读通知信息, user={}", userid);
		ResultData result = ResultData.init();
		Notify notify = new Notify();
		notify.setUserid(userid);
		notify.setState(NotifyConstant.STATE_UNREAD);
		notify.setType(type);
		
		List<Notify> notifies = notifyDao.find(notify);
		
		result.setData("notifies", getFieldValueList(notifies));
		log.info("[服务]完成查询用户的未读通知信息, result={}", result);
		return result;
	}

	@Override
	public ResultData countNewNotify(String userid) throws Exception {
		log.debug("[服务]开始统计用户的新通知信息, user={}", userid);
		ResultData result = ResultData.init();
		Notify notify = new Notify();
		notify.setUserid(userid);
		notify.setState(NotifyConstant.STATE_UNREAD);
		List<Notify> notifies = notifyDao.find(notify);
		int order = 0, account = 0, user = 0, info = 0, announce = 0 , reward=0 ;
		for(Notify n: notifies){
			if(NotifyConstant.TYPE_USER.equals(n.getType())){
				user++;
			}
			//FIXME Add other msg type here.
		}
		Map<String, Object> counts = new HashMap<String, Object>();
		counts.put("order", order);
		counts.put("account", account);
		counts.put("user", user);
		counts.put("info", info);
		counts.put("announce", announce);
		counts.put("reward", reward);
		
		result.setData("notifies", counts);
		log.info("[服务]完成统计用户的新通知信息, result={}", result);
		return result;
	}

	@Override
	public ResultData readNotifies(String userid, String notifyid, String type) throws Exception {
		log.debug("[服务]开始将用户的系统通知设为已读, user={}, notifyid={}, type={}", userid, notifyid, type);
		ResultData result = ResultData.init();
		Notify notify = new Notify();
		notify.setUserid(userid);
		if(null != notifyid) notify.setNotifyid(notifyid);
		if(null != type) notify.setType(type);
		notifyDao.readNotifies(notify);
		log.info("[服务]完成将用户的系统通知设为已读, result={}", result);
		return result;
	}
}
