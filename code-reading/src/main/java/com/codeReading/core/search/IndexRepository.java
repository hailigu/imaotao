package com.codeReading.core.search;

import org.elasticsearch.common.xcontent.XContentBuilder;

public interface IndexRepository {
	/**
	 * 检查能否索引
	 * @return
	 */
	public boolean canIndex();
	
	/**
	 * 创建资源json对象
	 * @return 成功则返回 builder对象，否则返回null
	 */
	public XContentBuilder toSourceBuilder();
}
