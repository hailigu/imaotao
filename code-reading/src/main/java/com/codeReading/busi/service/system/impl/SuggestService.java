package com.codeReading.busi.service.system.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codeReading.busi.dal.iface.common.ISuggestDao;
import com.codeReading.busi.dal.model.Suggest;
import com.codeReading.busi.po.SuggestPO;
import com.codeReading.busi.service.system.ISuggestService;
import com.codeReading.core.framework.web.BaseService;
import com.codeReading.core.framework.web.ResultData;
import com.codeReading.core.util.SeqUtil;

@Service
public class SuggestService extends BaseService implements ISuggestService {
	private Logger log = LoggerFactory.getLogger(SuggestService.class);
	
	@Autowired ISuggestDao suggestDao;
	
	@Override
	@Transactional
	public ResultData save(SuggestPO po, String userid) throws Exception {
		log.debug("[服务]开始 保存投诉与建议信息 suggest={}, user={}", po, userid);
		ResultData result = ResultData.init();
		Suggest suggest = new Suggest();
		suggest.setSuggestid(SeqUtil.produceSuggestid());
		suggest.setContact(po.getContact());
		suggest.setContent(po.getContent());
		suggest.setPath(po.getPath());
		suggest.setUserid(userid);
		suggestDao.insert(suggest);
		log.info("[服务]完成 保存投诉与建议信息 result={}", result);
		return result;
	}
}
