package com.codeReading.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.codeReading.core.opengrok.OpengrokBean;
import com.codeReading.core.opengrok.OpengrokInitializer;

@PropertySource(value = "classpath:opengrok.properties")
@Configuration
public class OpengrokConfig {
	
	@Autowired
	private Environment env;

	@Bean
	public OpengrokBean opengrokBean() {
		OpengrokBean bean = new OpengrokBean();
		bean.setDatapath(env.getRequiredProperty("opengrok.datapath"));
		bean.setSourcepath(env.getRequiredProperty("opengrok.sourcepath"));
		bean.setConfigurationpath(env.getRequiredProperty("opengrok.configuration"));
		return bean;
	}
	
	@Bean
	public OpengrokInitializer opengrokInitializer(){
		return new OpengrokInitializer();
	}
}