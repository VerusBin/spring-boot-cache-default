package com.nanc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@MapperScan({"com.nanc.mapper"})
@SpringBootApplication
@EnableCaching
public class SpringBootCacheDefaultApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootCacheDefaultApplication.class, args);
	}
}
