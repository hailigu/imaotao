package com.codeReading.busi.action.notify;

import java.security.Principal;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codeReading.busi.dpn.enums.PageSizeType;
import com.codeReading.busi.service.notify.INotifyService;
import com.codeReading.core.framework.db.PageBean;
import com.codeReading.core.framework.web.BaseAction;
import com.codeReading.core.framework.web.ResultData;
import com.codeReading.core.util.PageBeanUtil;

@Controller
public class NotifyAction extends BaseAction {
	private Logger log = LoggerFactory.getLogger(NotifyAction.class);
	
	@Autowired private INotifyService notifyService;
	
	/**
	 * GET获取通知信息页面
	 * @param result
	 * @return
	 */
	@RequestMapping(value="notify", method=RequestMethod.GET)
	public String notify(Map<String, Map<String, Object>> result){
		return "mykiford/notify";
	}
	
	/**
	 * POST获取通知列表数据
	 * @param principal 当前操作用户信息
	 * @param page 分页信息
	 * @param result
	 */
	@RequestMapping(value="notify", method=RequestMethod.POST)
	public void findNotify(Principal principal, PageBean page, Map<String, Map<String, Object>> result){
		try {
			log.info("#获取系统通知内容 principal={}", principal.getName());
			PageBeanUtil.initialize(page, PageSizeType.DEFAULT);
			ResultData data = notifyService.findNotify(principal.getName(), page);
			collect(data, result);
		}catch(Exception e){
			collect(e, result);
		}
	}
	
	/**
	 * POST获取未读通知列表数据
	 * @param principal 当前操作用户信息
	 * @param type 通知类型(可为null， 1.用户预约 2.账户变动 3.用户变动 4.信息审核 5.系统通告)
	 * @param result
	 */
	@RequestMapping(value="notify/unread", method=RequestMethod.POST)
	public void findUnreadNotify(Principal principal, String type, Map<String, Map<String, Object>> result){
		try {
			log.info("#获取当前用户未读系统通知内容 principal={}", principal.getName());
			ResultData data = notifyService.findUnreadNotify(principal.getName(), type);
			collect(data, result);
		}catch(Exception e){
			collect(e, result);
		}
	}
	
	/**
	 * POST获取当前用户新通知数目
	 * @param principal
	 * @param page
	 * @param result
	 */
	@RequestMapping(value="notify/new-count", method={RequestMethod.GET, RequestMethod.POST})
	public void newNotifyCount(Principal principal, Map<String, Map<String, Object>> result){
		try {
			log.info("#获取用户系统通知统计数量 principal={}", principal.getName());
			ResultData data = notifyService.countNewNotify(principal.getName());
			collect(data, result);
		}catch(Exception e){
			collect(e, result);
		}
	}
	
	@RequestMapping(value="notify/read", method=RequestMethod.POST)
	public void readNotifies(Principal principal, String notifyid, String type, Map<String, Map<String, Object>> result){
		try {
			log.info("#系统通知设为已读 principal={}, notifyid={}, type={}", principal.getName(), notifyid, type);
			ResultData data = notifyService.readNotifies(principal.getName(), notifyid, type);
			collect(data, result);
		}catch(Exception e){
			collect(e, result);
		}
	}
}
