package com.codeReading.busi.action.user;

import java.security.Principal;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codeReading.busi.po.UserContactPO;
import com.codeReading.busi.service.user.IUserContactService;
import com.codeReading.core.framework.web.BaseAction;
import com.codeReading.core.framework.web.ResultData;

/**
 * 联系用户表
 * @author: liuxue
 */
@Controller
@RequestMapping("contact")
public class UserContactAction extends BaseAction {
	private Logger log = LoggerFactory.getLogger(UserContactAction.class);
	
	@Autowired
	private IUserContactService contactService;
	
	/**
	 * 获取联系方式
	 * @param userid 用户编号
	 * @param principal
	 * @param result 返回值
	 */
	@RequestMapping("/get")
	public void get(Principal principal, Map<String, Map<String, Object>> result) throws Exception {
		log.info("#查询联系方式 userid={}", principal.getName());
		try {
			ResultData data = contactService.get(principal.getName());
			collect(data, result);
		} catch (Exception e) {
			collect(e, result);
		}
	}
	
	/**
	 * 保存联系方式
	 * @param contact 联系方式
	 * @param principal
	 * @param result 返回值
	 */
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public void save(UserContactPO contactpo, Principal principal, Map<String, Map<String, Object>> result) throws Exception {
		log.info("#保存联系方式 contactpo=[{}]", contactpo);
		try {
			ResultData data = contactService.save(contactpo, principal.getName());
			collect(data, result);
		} catch (Exception e) {
			collect(e, result);
		}
	}
	
	/**
	 * 删除联系方式
	 * @param contactid 联系方式编号
	 * @param principal
	 * @param result 返回值
	 */
	@RequestMapping("/delete")
	public void delete(String contactid, Principal principal, Map<String, Map<String, Object>> result) throws Exception {
		log.info("#删除联系方式 contactid={}", contactid);
		try {
			ResultData data = contactService.delete(contactid,principal.getName());
			collect(data, result);
		} catch (Exception e) {
			collect(e, result);
		}
	}
}
