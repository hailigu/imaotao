package com.codeReading.busi.service.source;

import com.codeReading.busi.dpn.enums.SupportTargetType;
import com.codeReading.core.framework.web.InnerResultData;
import com.codeReading.core.framework.web.ResultData;

public interface ISupportService {
	/**
	 * 通过userid和targetid获取点赞记录
	 * @param userid 当前用户id
	 * @param targetid 点赞目标id
	 * @return 点赞结果数据
	 * @throws Exception
	 */
	public ResultData getDetail(String userid,String targetid) throws Exception;

	/**
	 * 
	 * @param userid 不能为空
	 * @param targetid 不能为空
	 * @param orientation 不能为空
	 * @return 影响统计的数值
	 * @throws Exception
	 */
	public InnerResultData<Integer> addSupport(String userid, String targetid, SupportTargetType targettype, int orientation)
			throws Exception;
}

