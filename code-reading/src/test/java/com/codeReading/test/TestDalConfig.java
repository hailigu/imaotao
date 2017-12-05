package com.codeReading.test;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.codeReading.config.MybatisConfig;

@Configuration
@Import(value={MybatisConfig.class, MybatisConfig.class})
public class TestDalConfig {

}
