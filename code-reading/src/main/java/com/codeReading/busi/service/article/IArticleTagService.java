package com.codeReading.busi.service.article;

import java.util.List;
import java.util.Map;

import com.codeReading.core.framework.web.InnerResultData;

/**
 * @commonts: 服务接口
 * @vision 1.0.1
 */
public interface IArticleTagService {
	/**
	 * 保存文章标签关系
	 * @param articleid 文章编号
	 * @param tagids 标签编号集(多条以,分隔)
	 * @return 如果有新增或修改，则返回整个标签集数据；否则返回null
	 * @throws Exception
	 */
	public InnerResultData<Boolean> save(String articleid, String ... tagids) throws Exception;
	/**
	 * [内部调用] 根据文章编号获取标签信息
	 * @param articleid 文章编号
	 * @return 标签信息
	 * @throws Exception
	 */
	public InnerResultData<List<Map<String,Object>>> findByArticle(String articleid) throws Exception;
}
