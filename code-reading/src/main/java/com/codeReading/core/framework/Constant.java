package com.codeReading.core.framework;

/**
 * 定义系统顶级常量配置
 * 
 * @author Rofly
 */
public class Constant {

	public final static String RESULT_ROOT_KEY = "result";

	/** 用户数据正常返回码 */
	public final static String SUCCESS_CODE = "000000";
	/** 用户数据正常返回码 */
	public final static String SUCCESS_REASON = "成功";

	/** 用户正常 */
	public final static String USER_STATE_NORMAL = "2";
	/** 用户主动锁定 */
	public final static String USER_STATE_DELETE = "4";
	/** 用户被管理员冻结 */
	public final static String USER_STATE_LOCKED = "6";
	/**
	 * /** 统计日志
	 */
	public final static String LOGGER_COUNT = "count";

}
