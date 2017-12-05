package com.codeReading.busi.service.info;

import java.util.List;
import java.util.Map;

import com.codeReading.busi.dal.model.Tag;
import com.codeReading.busi.po.TagPO;
import com.codeReading.core.framework.web.InnerResultData;
import com.codeReading.core.framework.web.ResultData;

/**
  * @commonts: 服务接口
  * @vision 1.0.1
  */
public interface ITagService {
	/**
	 * 根据标签编号获取标签信息
	 */
	public ResultData get(String tagid) throws Exception;
	
	/**
	 * 根据标签名称获取标签信息
	 */
	public ResultData filterByTagname(String tagname, int pageSize) throws Exception;
	
	/**
	 * 添加标签
	 */
	public ResultData add(TagPO tagpo, String currentUserid) throws Exception;
	
	/**
	 * 根据标签编号删除标签信息
	 */
	public ResultData delete(String tagid, String currentUserid) throws Exception;

	/**
	 * 查找所有标签信息
	 */
	public ResultData findAll() throws Exception;
	
	/**
	 * 根据标签集查找标签信息
	 */
	public InnerResultData<List<Tag>> findMany(String tagids) throws Exception;
	
	/**
	 * 根据标签集查找标签信息
	 */
	public ResultData addHistoryTag(String currentUserid, String tagId) throws Exception;
	
	/**
	 * [内部调用] 根据专家编号获取标签信息
	 * @param expertid
	 * @return 专家所属的标签
	 * @throws Exception
	 */
	public InnerResultData<List<Map<String, Object>>> getByExpertid(
			String expertid) throws Exception;
}