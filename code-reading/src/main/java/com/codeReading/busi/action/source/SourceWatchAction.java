package com.codeReading.busi.action.source;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codeReading.busi.dpn.enums.PageSizeType;
import com.codeReading.busi.service.source.ISourceWatchService;
import com.codeReading.core.framework.db.PageBean;
import com.codeReading.core.framework.web.BaseAction;
import com.codeReading.core.framework.web.ResultData;
import com.codeReading.core.util.PageBeanUtil;

/**
 * 相关源码
 * @author tq
 *
 */
@Controller
public class SourceWatchAction extends BaseAction{
	private Logger log = LoggerFactory.getLogger(SourceWatchAction.class);
	
	@Autowired
	private ISourceWatchService sourceWatchService;
	
	@RequestMapping(value="source/add-watch", method=RequestMethod.POST)
	public void addWatch(String projectid, HttpServletRequest request, Map<String, Map<String, Object>> result) {
		log.info("#添加关注");
		try {
			String userid = (String)request.getSession().getAttribute("userid");
			ResultData data = sourceWatchService.add(projectid, userid);
			collect(data, result);
		} catch (Exception e){
			collect(e, result);
		}
	}
	
	@RequestMapping(value="sourcewatch/find-sourcewatch", method=RequestMethod.POST)
	public void findWatch(String userid, PageBean page, Map<String, Map<String, Object>> result) {
		log.info("#开始 查询用于关注的源码信息 userid={}",userid);
		try {
			PageBeanUtil.initialize(page, PageSizeType.SOURCEWATCH);
			ResultData data = sourceWatchService.findWatch(userid, page);
			collect(data, result);
		} catch (Exception e){
			collect(e, result);
		}
	}
}
