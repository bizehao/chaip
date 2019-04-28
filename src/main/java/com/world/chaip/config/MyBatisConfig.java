package com.world.chaip.config;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/**
 * 数据配置
 *
 * @author LIUWY
 */
@Configuration
public class MyBatisConfig {

	@Bean
	public static SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		Interceptor[] interceptors = new Interceptor[1];
		interceptors[0] = new SqlTimeInterceptor();
		sqlSessionFactoryBean.setPlugins(interceptors);
		PathMatchingResourcePatternResolver matchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
		Resource[] mapperLocations = matchingResourcePatternResolver.getResources("classpath*:mappings/*Mapper.xml");
		sqlSessionFactoryBean.setMapperLocations(mapperLocations);
		sqlSessionFactoryBean.setTypeAliasesPackage("com.world.chaip.entity");
		return sqlSessionFactoryBean.getObject();
	}

	@Bean
	public DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
}
