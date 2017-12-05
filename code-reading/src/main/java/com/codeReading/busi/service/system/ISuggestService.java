package com.codeReading.busi.service.system;

import com.codeReading.busi.po.SuggestPO;
import com.codeReading.core.framework.web.ResultData;

public interface ISuggestService {
	/**
	 * 保存投诉与建议信息
	 * @param suggest 投诉与建议信息包
	 * @param userid 当前用户 （Nullable）
	 * @return
	 * @throws Exception
	 */
	public ResultData save(SuggestPO suggest, String userid) throws Exception;
}
