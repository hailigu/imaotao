package com.codeReading.busi.service.source.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.codeReading.busi.dal.iface.source.ISupportDao;
import com.codeReading.busi.dal.model.Support;
import com.codeReading.busi.dpn.enums.SupportTargetType;
import com.codeReading.busi.service.source.ISupportService;
import com.codeReading.core.framework.exception.ParameterNotAllowException;
import com.codeReading.core.framework.web.BaseService;
import com.codeReading.core.framework.web.InnerResultData;
import com.codeReading.core.framework.web.ResultData;
import com.codeReading.core.util.DateUtil;
import com.codeReading.core.util.SeqUtil;

@Component
public class SupportService extends BaseService implements ISupportService {
	private Logger log = LoggerFactory.getLogger(SupportService.class);
	@Autowired
	private ISupportDao supportDao;
	@Override
	public ResultData getDetail(String userid, String targetid) throws Exception {
		ResultData result = ResultData.init();
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userid", userid);
		params.put("targetid", targetid);
		Support support = supportDao.getByUserAndTarget(params);
		
		result.setData("support",support);
		
		return result;
	}

	/**
	 * 
	 * @param userid 不能为空
	 * @param targetid 不能为空
	 * @param orientation 不能为空
	 * @return 影响统计的数值
	 * @throws Exception
	 */
	@Override
	public InnerResultData<Integer> addSupport(String userid, String targetid, SupportTargetType targettype, int orientation) throws Exception{
		log.info("###[内部服务] 开始 添加点赞 userid={}, targetid={}, orientation={}", userid, targetid, orientation);
		InnerResultData<Integer> result = new InnerResultData<Integer>();
		
		if(orientation != 1 && orientation != -1){
			throw new ParameterNotAllowException();
		}
		
		//获取点赞信息记录
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userid", userid);
		params.put("targetid", targetid);
		Support supportorg = supportDao.getByUserAndTarget(params);
		
		int supportResult = 0;
		if (supportorg != null) {
			//以前赞过或踩过
			if(orientation != supportorg.getOrientation()){
				Support supportupdate = new Support();
				supportupdate.setSupportid(supportorg.getSupportid());
				supportupdate.setOrientation(orientation);
				supportupdate.setIntime(DateUtil.currentTimestamp());
				supportDao.update(supportupdate);
				supportResult = orientation - supportorg.getOrientation();
			}
		}else {
			//首次点赞操作
			Support supportnew = new Support();
			supportnew.setSupportid(SeqUtil.produceSupportid());
			supportnew.setUserid(userid);
			supportnew.setTargetid(targetid);
			supportnew.setTargettype(targettype.getType());
			supportnew.setOrientation(orientation);
			supportDao.insert(supportnew);
			supportResult = orientation;
		}
		result.setData(supportResult);
		log.info("###[内部服务] 完成 添加点赞 result={}", result);
		return result;
	}
}
