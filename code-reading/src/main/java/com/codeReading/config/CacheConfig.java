package com.codeReading.config;

import java.io.Serializable;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;

@Configuration
public class CacheConfig {

	@Bean
	public RedisTemplate<String, Serializable> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, Serializable> redisTemplate = new RedisTemplate<String, Serializable>();
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		return redisTemplate;
	}

	@Bean
	public ValueOperations<String, Serializable> valueOperations(RedisTemplate<String, Serializable> redisTemplate) {
		return redisTemplate.opsForValue();
	}

	@Bean
	public HashOperations<String, String, Serializable> hashOperations(RedisTemplate<String, Serializable> redisTemplate) {
		return redisTemplate.opsForHash();
	}

	@Bean
	public ListOperations<String, Serializable> listOperations(RedisTemplate<String, Serializable> redisTemplate) {
		return redisTemplate.opsForList();
	}

	@Bean
	public SetOperations<String, Serializable> setOperations(RedisTemplate<String, Serializable> redisTemplate) {
		return redisTemplate.opsForSet();
	}
}