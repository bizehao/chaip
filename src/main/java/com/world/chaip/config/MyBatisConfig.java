package com.world.chaip.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 数据配置
 * @author LIUWY
 *
 */
@Component
@Configuration
public class MyBatisConfig {

	@Bean
	@ConfigurationProperties(prefix="spring.datasource")
	public DataSource dataSource() {
		//return new ComboPooledDataSource();
		return DataSourceBuilder.create().type(ComboPooledDataSource.class).build();
	}

	@Bean
	public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean=new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		PathMatchingResourcePatternResolver matchingResourcePatternResolver=new PathMatchingResourcePatternResolver();
		Resource[] mapperLocations=	matchingResourcePatternResolver.getResources("classpath*:mappings/*Mapper.xml");
		sqlSessionFactoryBean.setMapperLocations(mapperLocations);
		sqlSessionFactoryBean.setTypeAliasesPackage("com.world.chaip.entity");
		return sqlSessionFactoryBean.getObject();
	}

	@Bean
	public MapperScannerConfigurer mapperScannerConfigurer() {
		MapperScannerConfigurer mapperScannerConfigurer =new MapperScannerConfigurer();
		mapperScannerConfigurer.setBasePackage("com.world.chaip.mapper");
		return mapperScannerConfigurer;
	}

	@Bean
	public DataSourceTransactionManager dataSourceTransactionManager(@Qualifier("dataSource")DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
}
