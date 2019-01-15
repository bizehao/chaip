package com.world.chaip;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * 入口程序
 *
 */
@MapperScan("com.world.chaip.mapper")
@SpringBootApplication
public class ChaipApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChaipApplication.class, args);
	}
}
