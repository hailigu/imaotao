package com.codeReading.core.framework.web;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codeReading.core.framework.Constant;
import com.codeReading.core.framework.exception.AbsBusiException;
import com.codeReading.core.framework.exception.SystemRuntimeException;

public class BaseAction {
	private Logger log = LoggerFactory.getLogger(BaseAction.class);
	
	public static final String SUCCESS = "success"; 
	public static final String ERROR = "error/500"; 
	protected static final String REDIRECT = "redirect"; 
	
	public String redirect(String path) {
		return REDIRECT + ":" + (path.startsWith("/") ? "" : "/") + path;
	}
	
	/**
	 * 处理正常数据
	 * @param data
	 * @param result 
	 */
	protected void collect(ResultData data, Map<String, Map<String, Object>> result){
		Map<String, Object> _data = new HashMap<String, Object>();
		_data.put("retCode", data.getRetCode());
		_data.put("reason", data.getReason());
		_data.put("time", data.getTime());
		_data.put("data", data.getData());
		result.clear();
		result.put(Constant.RESULT_ROOT_KEY, _data);
	}
	/**
	 * 处理异常
	 * @param e
	 */
	protected void collect(Exception e, Map<String, Map<String, Object>> result) {
		if(e instanceof AbsBusiException){
			AbsBusiException exc = (AbsBusiException)e;
			Map<String, Object> _data = new HashMap<String, Object>();
			_data.put("retCode", exc.getRetCode());
			_data.put("reason", exc.getReason());
			_data.put("time", System.currentTimeMillis());
			result.clear();
			result.put("result", _data);
			log.error("Collection exception: ", exc);
		}else{
			SystemRuntimeException exc = new SystemRuntimeException();
			Map<String, Object> _data = new HashMap<String, Object>();
			_data.put("retCode", exc.getRetCode());
			_data.put("reason", exc.getReason());
			_data.put("time", System.currentTimeMillis());
			result.clear();
			result.put("result", _data);
			log.error("Collection exception: ", e);
		}
	}
}
