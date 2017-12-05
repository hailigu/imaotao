package com.codeReading.busi.service.source.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.codeReading.busi.dal.iface.source.ISourceWatchDao;
import com.codeReading.busi.dal.model.SourceProjectStatistics;
import com.codeReading.busi.dal.model.SourceWatch;
import com.codeReading.busi.dpn.enums.DataState;
import com.codeReading.busi.dpn.exception.OperationFailException;
import com.codeReading.busi.dpn.exception.OperationNotSupportException;
import com.codeReading.busi.dpn.exception.source.ItemHasExistException;
import com.codeReading.busi.service.source.ISourceProjectStatisticsService;
import com.codeReading.busi.service.source.ISourceWatchService;
import com.codeReading.core.framework.db.PageBean;
import com.codeReading.core.framework.db.QueryBean;
import com.codeReading.core.framework.exception.ParameterNotAllowException;
import com.codeReading.core.framework.web.BaseService;
import com.codeReading.core.framework.web.InnerResultData;
import com.codeReading.core.framework.web.ResultData;
import com.codeReading.core.util.SeqUtil;

@Service
public class SourceWatchServiceImpl extends BaseService implements ISourceWatchService {
	private Logger log = LoggerFactory.getLogger(SourceReadingServiceImpl.class);
	
	@Autowired private ISourceWatchDao sourceWatchDao;
	@Autowired private ISourceProjectStatisticsService sourceProjectStatistics;
	
	@Override
	public ResultData add(String projectid, String userid) throws Exception{
		log.info("###[服务] 开始 添加源码工程关注， projectid={}, userid={}", projectid, userid);
		ResultData result = ResultData.init();
		if(StringUtils.isEmpty(userid)){
			throw new OperationNotSupportException();
		}
		InnerResultData<SourceProjectStatistics> innerResult = addWatch(projectid, userid);
		if(!innerResult.isSuccess()){
			if("060003".equals(innerResult.getRetCode())){
				throw new ItemHasExistException("您已经关注了该工程");
			}else{
				throw new OperationFailException(innerResult.getReason());
			}
		}else{
			SourceProjectStatistics statistics = innerResult.getData();
			result.setData("statistics", statistics);
		}
		
		log.info("###[内部服务] 完成 添加源码工程关注， result={}", result);
		return result;
	}
	
	@Override
	public InnerResultData<SourceProjectStatistics> addWatch(String projectid, String userid) throws Exception{
		log.info("###[内部服务] 开始 添加源码工程关注， projectid={}, userid={}", projectid, userid);
		InnerResultData<SourceProjectStatistics> result = new InnerResultData<SourceProjectStatistics>();
		if(StringUtils.isEmpty(userid)|| StringUtils.isEmpty(projectid)){
			throw new ParameterNotAllowException();
		}
		SourceWatch watch = new SourceWatch();
		watch.setUserid(userid);
		watch.setProjectid(projectid);
		List<SourceWatch> watchs = sourceWatchDao.find(watch);
		if(watchs.size()==0){
			//没有关注
			watch.setWatchid(SeqUtil.produceSourceWatchid());
			watch.setState(DataState.NORMAL);
			sourceWatchDao.insert(watch);
			//添加关注数
			SourceProjectStatistics statistics = sourceProjectStatistics.addWatchCount(projectid, 1).getData();
			result.setData(statistics);
		}else{
			result.setReason("您已经关注了该工程");
			result.setRetCode("060003");
		}
		
		log.info("###[内部服务] 完成 添加源码工程关注， result={}", result);
		return result;
	}

	@Override
	public ResultData findWatch(String userid, PageBean page) throws Exception {
		log.info("### 开始  查找用户关注的源码信息  userid={}", userid);
		ResultData result = ResultData.init();
		page.setOrderKey("intime");
		page.setAscend("desc");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userid", userid);
		List<Map<String, Object>> sourcewatchlist = sourceWatchDao.findWatchByPage(new QueryBean<Map<String, Object>>(page,params));
		result.setData("sourcewatchlist",sourcewatchlist);
		result.setData("page", page);
		log.info("### 结束   查找用户关注的源码信息result={}", result);
		return result;	
	}
}
