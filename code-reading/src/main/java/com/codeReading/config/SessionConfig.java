package com.codeReading.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
@Configuration
@PropertySource(value = "classpath:cache.properties")
@EnableRedisHttpSession(maxInactiveIntervalInSeconds=7200)
public class SessionConfig {
	@Autowired
	private Environment env;

	@Bean
	public JedisConnectionFactory connectionFactory() throws Exception {
		JedisConnectionFactory factory = new JedisConnectionFactory();
		factory.setHostName(env.getRequiredProperty("redis.host"));
		factory.setPort(Integer.parseInt(env.getProperty("redis.port", "6379")));
		factory.setPassword(env.getRequiredProperty("redis.password"));
		return factory;
	}
}
