package com.codeReading.core.search;

import org.elasticsearch.common.xcontent.XContentBuilder;

public interface UpdateRepository {
	/**
	 * 检查能否执行修改操作
	 * @return
	 */
	public boolean canUpdate();
	/**
	 * 创建资源json对象
	 * @return 成功则返回 builder对象，否则返回null
	 */
	public XContentBuilder toSourceBuilder();
}
