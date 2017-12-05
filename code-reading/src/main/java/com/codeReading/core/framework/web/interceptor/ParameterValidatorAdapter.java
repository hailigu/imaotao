/* *
 * Author Rofly
 * Date 2014年2月21日
 */
package com.codeReading.core.framework.web.interceptor;

import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.tree.ConfigurationNode;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.codeReading.core.framework.exception.ParameterNotAllowException;
import com.codeReading.core.framework.exception.ParameterNotEnoughException;

/**
 * 实现参数验证，根据配置文件中的参数格式配置来校验所给参数是否为有效
 * @author Rofly
 */
public class ParameterValidatorAdapter extends HandlerInterceptorAdapter{
	private Logger log = LoggerFactory.getLogger(ParameterValidatorAdapter.class);
	
	// ~ must inject ~
	private HierarchicalConfiguration[] validatorConfigurations;
	
	/**
	 * 请求参数的配置按照请求所给的url来作为key值
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		log.trace("#参数校验开始，校验对象{}", request.getRequestURI());
		Assert.notEmpty(validatorConfigurations, "Validator configurations should must be injected.");
		String url = request.getRequestURI().substring(1);
		for(HierarchicalConfiguration config : validatorConfigurations){
			for(HierarchicalConfiguration subConfig: config.configurationsAt("service")){
				if(StringUtils.trim(url).startsWith(subConfig.getString("[@path]")+".")){
					List<ConfigurationNode> childs= subConfig.getRootNode().getChildren();
					for(ConfigurationNode cNode :childs){
						List<ConfigurationNode> requires = cNode.getAttributes("require");
						if(!requires.isEmpty() && "true".equals(requires.get(0).getValue())){//必传
							String param = request.getParameter(cNode.getName());
							if(null != param){
								String value = (String)cNode.getValue();
								if(null != value && value.length()>0){
									Pattern p = Pattern.compile(value);
									if(p.matcher(param).matches()){
										continue; //正则匹配
									}else{
										log.debug("#参数校验不通过，检查参数:{} = {}", cNode.getName(), param);
										throw new ParameterNotAllowException();
									}
								}else{
									continue; //不需要验证正则
								}
							}else{
								log.debug("#传入参数不足，检查参数:{}", cNode.getName());
								throw new ParameterNotEnoughException();
							}
						}else{ //非必传
							String param = request.getParameter(cNode.getName());
							if(!StringUtils.isEmpty(param)){
								String value = (String)cNode.getValue();
								if(null != value && value.length()>0){
									Pattern p = Pattern.compile(value);
									if(p.matcher(param).matches()){
										continue; //正则匹配
									}else{
										log.debug("#参数校验不通过，检查参数:{} = {}, pattern={}", cNode.getName(), param, p);
										throw new ParameterNotAllowException();
									}
								}else{
									continue;
								}
							}else{
								//非必传参数，未传，继续校验
								continue;
							}
						}
					}
				}
			}
		}
		log.trace("#参数校验通过");
		return super.preHandle(request, response, handler);
	}
	
	public void setValidatorConfigurations(HierarchicalConfiguration ... validatorConfigurations) {
		this.validatorConfigurations = validatorConfigurations;
	}
}