package com.codeReading.config;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.codeReading.busi.dpn.auth.KifordAuthFailureHandler;
import com.codeReading.busi.dpn.auth.KifordAuthSuccessHandler;
import com.codeReading.core.framework.security.CompatiblePasswordUserDetailProvider;
import com.codeReading.core.framework.security.IUserDetailProvider;
import com.codeReading.core.framework.security.UserAccessDeniedHandler;
import com.codeReading.core.framework.security.UserForbiddenEntryPoint;
import com.codeReading.core.framework.security.UserkeyAuthenticationFilter;
import com.codeReading.core.framework.security.UserkeyAuthenticationProvider;
import com.codeReading.core.framework.security.UserkeyAuthenticationVerify;

@Configuration
@EnableWebSecurity
@Import(value = { CacheConfig.class })
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserkeyAuthenticationFilter userkeyAuthenticationFilter;

	@Autowired
	private UserkeyAuthenticationProvider userkeyAuthenticationProvider;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(userkeyAuthenticationProvider);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		// 设置不需要经过spring security保护的资源
		web.ignoring().antMatchers("/js/**") // JS
				.antMatchers("/css/**") // 样式
				.antMatchers("/images/**") // 图片
				.antMatchers("/html/**") // 静态页面
				.antMatchers("/plugins/**") // 组件页面
				.antMatchers("/favicon.ico") // 网站图片
				.antMatchers("/robots.txt") // 蜘蛛规则
		;

		web.ignoring()
		// 开放信息
				.antMatchers(HttpMethod.GET, "/test/*") //FIXME 测试，后需要删掉
				.antMatchers(HttpMethod.POST, "/test/*") //FIXME 测试，后需要删掉
				
				.antMatchers(HttpMethod.GET, "/", "/index", "/home") // 首页
				.antMatchers(HttpMethod.GET, "/search") // 搜索页
				.antMatchers(HttpMethod.GET, "/review/get*")// 获取专家的评价信息
				.antMatchers(HttpMethod.POST, "/service/recommend.*")// 查询推荐或相关服务
				.antMatchers(HttpMethod.GET, "/register*")// 注册
				.antMatchers(HttpMethod.POST, "/user-auth/register*")// [服务]注册
				.antMatchers(HttpMethod.POST, "/user-auth/send-register-verification-code*")// [服务]发送注册验证码
				.antMatchers(HttpMethod.GET, "/forget*")// 忘记登录密码
				.antMatchers(HttpMethod.POST, "/user-auth/send-forget-verification-code*")// [服务]发送忘记登录密码验证码
				.antMatchers(HttpMethod.POST, "/user-auth/forget*")// [服务]忘记登录密码
				.antMatchers(HttpMethod.GET, "/password/forget*")// 忘记密码
				.antMatchers(HttpMethod.GET, "/image-verify")// 验证码
				
				.antMatchers(HttpMethod.POST, "/baifen/asyn-send-callback")// 百分短信异步请求
				.antMatchers(HttpMethod.GET, "/news", "/news/*", "/cases", "/cases/*")// 新闻、案例
				.antMatchers(HttpMethod.GET, "/help", "/agreement", "/about")// 帮助、协议、关于
				.antMatchers(HttpMethod.POST, "/news/*", "/cases/*")// [服务]新闻、案例
				.antMatchers(HttpMethod.GET, "/partner/*")// 合作伙伴
				.antMatchers(HttpMethod.POST, "/suggest/save.*")// 用户反馈

				.antMatchers(HttpMethod.GET, "/articles")// 文章列表
				.antMatchers(HttpMethod.GET, "/a/*")// 文章详情
				.antMatchers(HttpMethod.GET, "/article/find-article*")// 查找文章
				.antMatchers(HttpMethod.POST, "/article/add-pageview*")// 增加文章PV
				.antMatchers(HttpMethod.GET, "/article/get-reviews*")// 获取文章的评论
				.antMatchers(HttpMethod.GET, "/article/get-expert-article-list*")// 获取专家的文章列表
				
				.antMatchers(HttpMethod.GET, "/xref/**")// 代码阅读
				
				.antMatchers("/401", "/403", "/404", "/500")// 异常处理
				;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		log.trace("Try to configure httpSecurity for security. clazz= {}", http.getClass());
		// 设置通过关键信息验证用户验证过滤器
		http.addFilterBefore(userkeyAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		// 设定验证规则
		http.csrf().disable()//
				.headers().disable()//
				.exceptionHandling()//
				.authenticationEntryPoint(new UserForbiddenEntryPoint())//
				.accessDeniedHandler(new UserAccessDeniedHandler()).and()//
				.authorizeRequests()//
				.antMatchers(HttpMethod.GET, "/login").permitAll()//
				.antMatchers(HttpMethod.GET, "/check/*").permitAll()//
				.antMatchers(HttpMethod.POST, "/check/*").permitAll()//
				.antMatchers(HttpMethod.GET, "/wechat-pubnum-login").permitAll()//
				.anyRequest().authenticated().and()//
				.logout()//
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))//
				.logoutSuccessUrl("/login").deleteCookies("JSESSIONID")//
				.invalidateHttpSession(true).and()//
				.sessionManagement()//
				.invalidSessionUrl("/login?time=" + System.currentTimeMillis()).maximumSessions(1);
	}

	@Bean
	public UserkeyAuthenticationFilter userkeyAuthenticationFilter(UserkeyAuthenticationVerify authenticationVerify, AuthenticationSuccessHandler successHandler,
			AuthenticationFailureHandler failureHandler) throws Exception {
		UserkeyAuthenticationFilter authFilter = new UserkeyAuthenticationFilter("/login.*");
		authFilter.setAuthenticationVerify(authenticationVerify);
		authFilter.setAuthenticationSuccessHandler(successHandler);
		authFilter.setAuthenticationFailureHandler(failureHandler);
		authFilter.setAuthenticationManager(authenticationManagerBean());
		return authFilter;
	}

	@Bean
	public UserkeyAuthenticationProvider userkeyAuthenticationProvider(IUserDetailProvider userDetailProvider, PasswordEncoder passwordEncoder) {
		UserkeyAuthenticationProvider provider = new UserkeyAuthenticationProvider();
		provider.setUserDetailProvider(userDetailProvider);
		provider.setPasswordEncoder(passwordEncoder);
		return provider;
	}

	@Bean
	public IUserDetailProvider UserDetailProvider() {
		// return new UserDetailProvider();
		return new CompatiblePasswordUserDetailProvider();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserkeyAuthenticationVerify authenticationVerify(ValueOperations<String, Serializable> valueOperations) {
		UserkeyAuthenticationVerify authenticationVerify = new UserkeyAuthenticationVerify();
		authenticationVerify.setValueOperations(valueOperations);
		return authenticationVerify;
	}

	@Bean
	public AuthenticationSuccessHandler authenticationSuccessHandler(UserkeyAuthenticationVerify authenticationVerify) {
		KifordAuthSuccessHandler authenticationSuccess = new KifordAuthSuccessHandler();
		authenticationSuccess.setAuthenticationVerify(authenticationVerify);
		return authenticationSuccess;
	}

	@Bean
	public AuthenticationFailureHandler authenticationFailureHandler(UserkeyAuthenticationVerify authenticationVerify) {
		KifordAuthFailureHandler authenticationFailure = new KifordAuthFailureHandler();
		authenticationFailure.setAuthenticationVerify(authenticationVerify);
		return authenticationFailure;
	}

}
