package com.codeReading.test;

import java.security.Principal;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.codeReading.config.AppConfig;
import com.codeReading.config.CacheConfig;
import com.codeReading.config.DataSourceConfig;
import com.codeReading.config.ElasticsearchConfig;
import com.codeReading.config.MCVConfig;
import com.codeReading.config.MybatisConfig;
import com.codeReading.config.OAuthConfig;
import com.codeReading.config.SessionConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={DataSourceConfig.class, SessionConfig.class, CacheConfig.class, MybatisConfig.class, ElasticsearchConfig.class,AppConfig.class, OAuthConfig.class, MCVConfig.class})
@WebAppConfiguration
public class TestBase {
	public MockMvc mock;
	public static final String $000000 = "000000";
	public static final String SUCCESS = "success";
	public static final String ERROR = "error";
	
	public class TestPrincipal implements Principal {
		private String userid;
		
		public TestPrincipal(String userid) {
			this.userid = userid;
		}
		@Override
		public String getName() {
			return userid;
		}
		
	}
}
