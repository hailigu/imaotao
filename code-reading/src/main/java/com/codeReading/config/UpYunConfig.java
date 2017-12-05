package com.codeReading.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.context.WebApplicationContext;

import com.codeReading.core.resource.ResourceConfiguration;
import com.codeReading.core.resource.UpYunConfiguration;

@Configuration
@PropertySource(value="classpath:upyun.properties")
public class UpYunConfig {

	@Autowired private Environment env;
	
	@Autowired private WebApplicationContext context;

	@Bean
	public ResourceConfiguration upYunConfiguration() {
		UpYunConfiguration configuration = new UpYunConfiguration();
		configuration.setBucketName(env.getProperty("upyun.bucket_name"));
		configuration.setOperatorName(env.getProperty("upyun.operator_name"));
		configuration.setOperatorPassword(env.getProperty("upyun.operator_password"));
		configuration.setPictureRoot(env.getProperty("upyun.picture_root"));
		configuration.setBackupPath(env.getProperty("upyun.backup_path"));
		configuration.setAvatarDir(env.getProperty("upyun.avatar_dir"));
		configuration.setAttachDir(env.getProperty("upyun.attach_dir"));
		
		context.getServletContext().setAttribute("picture_root", env.getProperty("upyun.picture_root"));
		
		return configuration;
	}

}
