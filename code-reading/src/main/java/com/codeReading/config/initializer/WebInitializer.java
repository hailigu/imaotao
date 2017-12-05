package com.codeReading.config.initializer;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.codeReading.config.AppConfig;
import com.codeReading.config.CacheConfig;
import com.codeReading.config.DataSourceConfig;
import com.codeReading.config.ElasticsearchConfig;
import com.codeReading.config.MCVConfig;
import com.codeReading.config.MybatisConfig;
import com.codeReading.config.OAuthConfig;
import com.codeReading.config.SessionConfig;

public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		super.onStartup(servletContext);
		FilterRegistration.Dynamic encodingFilter = servletContext.addFilter("encoding-filter", CharacterEncodingFilter.class);
		encodingFilter.setInitParameter("encoding", "UTF-8");
		encodingFilter.setInitParameter("forceEncoding", "true");
		encodingFilter.setAsyncSupported(true);
		encodingFilter.addMappingForUrlPatterns(null, true, "/*");
	}

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] {AppConfig.class, DataSourceConfig.class, MybatisConfig.class, SessionConfig.class, CacheConfig.class, ElasticsearchConfig.class, OAuthConfig.class };

	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { MCVConfig.class};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	@Override
	protected void registerDispatcherServlet(ServletContext servletContext) {

		WebApplicationContext servletAppContext = createServletApplicationContext();
		Assert.notNull(servletAppContext, "createServletApplicationContext() did not return an application " + "context for servlet [code-reading]");
		DispatcherServlet dispatcherServlet = new DispatcherServlet(servletAppContext);
		ServletRegistration.Dynamic registration = servletContext.addServlet("code-reading", dispatcherServlet);
		Assert.notNull(registration, "Failed to register servlet with name 'code-reading'." + "Check if there is another servlet registered under the same name.");

		registration.setLoadOnStartup(1);
		registration.addMapping(getServletMappings());
		registration.setAsyncSupported(isAsyncSupported());

		Filter[] filters = getServletFilters();
		if (!ObjectUtils.isEmpty(filters)) {
			for (Filter filter : filters) {
				registerServletFilter(servletContext, filter);
			}
		}

		customizeRegistration(registration);
	}

}
