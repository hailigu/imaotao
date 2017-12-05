package com.codeReading.config;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import com.codeReading.core.framework.db.BaseDao;
import com.codeReading.core.framework.db.CommonDao;
import com.codeReading.core.framework.db.ICommonDao;
import com.codeReading.core.framework.db.PageBean;
import com.codeReading.core.framework.db.PageIntercept;
import com.codeReading.core.framework.db.QueryBean;
import com.codeReading.core.util.DaoUtil;

@Configuration
public class MybatisConfig {

	@Bean
	public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource, ApplicationContext applicationContext) throws Exception {
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		sessionFactory.setTypeAliasesPackage("com.codeReading.busi.dal.model");
		Class<?>[] clazzes = { PageBean.class, QueryBean.class };
		sessionFactory.setTypeAliases(clazzes);
		Interceptor[] interceptors = { new PageIntercept("postgres") };
		sessionFactory.setPlugins(interceptors);
		Resource[] mapperLocations = applicationContext.getResources("classpath:mapper/**/*.xml");
		sessionFactory.setMapperLocations(mapperLocations);
		return sessionFactory;
	}

	@Bean
	public MapperScannerConfigurer mapperScannerConfigurer() {
		MapperScannerConfigurer configurer = new MapperScannerConfigurer();
		configurer.setBasePackage("com.codeReading.busi.dal.iface");
		configurer.setMarkerInterface(BaseDao.class);
		configurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
		return configurer;
	}

	@Bean
	public ICommonDao commonDao(SqlSessionFactory factory) throws Exception {
		CommonDao dao = new CommonDao();
		dao.setSqlSessionFactory(factory);
		return dao;
	}

	@Bean
	public DaoUtil daoUtil(ICommonDao commonDao) throws Exception {
		return new DaoUtil(commonDao);
	}
}
