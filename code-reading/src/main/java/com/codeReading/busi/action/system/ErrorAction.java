package com.codeReading.busi.action.system;

import java.security.Principal;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codeReading.core.framework.web.BaseAction;

@Controller
public class ErrorAction extends BaseAction {
	private Logger log = LoggerFactory.getLogger(ErrorAction.class);

	@RequestMapping(value = "401")
	public String e401() {
		return "error/401";
	}

	@RequestMapping(value = "403")
	public String e403(Principal user) {
		if (null != user) {
			log.warn("User [{}] access protected page.", user.getName());
		}
		return "error/403";
	}

	@RequestMapping(value = "404")
	public String e404(String reason, Map<String, Object> result) {
		if (null != reason && reason.length() > 0) {
			result.put("reason", reason);
		} else {
			result.put("reason", "找不到页面");
		}
		return "error/404";
	}

	@RequestMapping("500")
	public String e500(String reason, Map<String, Object> result) {
		if (null != reason && reason.length() > 0) {
			result.put("reason", reason);
		} else {
			result.put("reason", "小津的网线绕在一起了");
		}
		return "error/500";
	}

	@RequestMapping("error")
	public String error(String reason, Map<String, Object> result) {
		if (null != reason && reason.length() > 0) {
			result.put("reason", reason);
		} else {
			result.put("reason", "小津处理不过来了");
		}
		return "error/error";
	}
}
