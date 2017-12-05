package com.codeReading.config;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.codeReading.core.framework.web.exception.SystemExceptionResolver;
import com.codeReading.core.framework.web.interceptor.ParameterValidatorAdapter;
import com.codeReading.core.framework.web.view.JsonViewResolver;
import com.codeReading.core.framework.web.view.XmlViewResolver;

@Configuration
@ComponentScan({ "com.codeReading.busi.action"})
@EnableWebMvc
public class MCVConfig extends WebMvcConfigurerAdapter {
	private Logger log = LoggerFactory.getLogger(MCVConfig.class);

	@Autowired
	private HandlerInterceptorAdapter parameterValidatorAdapter;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/js/**").addResourceLocations("/js/");
		registry.addResourceHandler("/css/**").addResourceLocations("/css/");
		registry.addResourceHandler("/html/**").addResourceLocations("/html/");
		registry.addResourceHandler("/images/**").addResourceLocations("/images/");
		registry.addResourceHandler("/plugins/**").addResourceLocations("/plugins/");
		registry.addResourceHandler("/favicon.ico").addResourceLocations("/favicon.ico");
		//registry.addResourceHandler("/robots.txt").addResourceLocations("/robots.txt");
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(parameterValidatorAdapter);
	}

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		Map<String, MediaType> mediaTypes = new HashMap<String, MediaType>();
		mediaTypes.put("json", MediaType.APPLICATION_JSON);
		mediaTypes.put("xml", MediaType.APPLICATION_XML);
		mediaTypes.put("html", MediaType.TEXT_HTML);
		configurer.favorPathExtension(false).defaultContentType(MediaType.TEXT_HTML).mediaTypes(mediaTypes);
	}

	@Bean
	public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager) {
		List<ViewResolver> viewResolvers = new LinkedList<ViewResolver>();

		viewResolvers.add(new JsonViewResolver());
		viewResolvers.add(new XmlViewResolver(new XStreamMarshaller()));

		InternalResourceViewResolver jspViewResolver = new InternalResourceViewResolver();
		jspViewResolver.setViewClass(JstlView.class);
		jspViewResolver.setPrefix("/WEB-INF/jsp/");
		jspViewResolver.setSuffix(".jsp");
		jspViewResolver.setOrder(2);
		viewResolvers.add(jspViewResolver);

		ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
		resolver.setViewResolvers(viewResolvers);
		resolver.setContentNegotiationManager(manager);
		return resolver;
	}

	@Bean
	public HandlerExceptionResolver systemExceptionResolver() {
		return new SystemExceptionResolver();
	}

	/** 文件上传支持 **/
	@Bean
	public MultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(32 * 1024 * 1024);
		return multipartResolver;
	}

	/**
	 * 参数拦截
	 * @return
	 * @throws ConfigurationException
	 * @throws URISyntaxException
	 */
	@Bean(name = "parameterValidatorAdapter")
	public HandlerInterceptorAdapter parameterValidatorAdapter() throws ConfigurationException, URISyntaxException {
		URL configPath = getClass().getResource("/validations");
		File[] files = new File(configPath.toURI()).listFiles();

		if (log.isTraceEnabled())
			log.trace("Parameter validator adapter, files={}", Arrays.toString(files));

		XMLConfiguration[] configures = new XMLConfiguration[files.length];
		for (int i = 0; i < files.length; i++) {
			configures[i] = new XMLConfiguration(files[i]);
		}

		ParameterValidatorAdapter validator = new ParameterValidatorAdapter();
		validator.setValidatorConfigurations(configures);
		return validator;
	}
}
