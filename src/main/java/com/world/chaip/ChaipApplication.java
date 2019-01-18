package com.world.chaip;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * 入口程序
 *
 */
@MapperScan("com.world.chaip.mapper")
@SpringBootApplication
public class ChaipApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(ChaipApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		// 注意这里要指向原先用main方法执行的Application启动类
		return builder.sources(ChaipApplication.class);
	}
}
