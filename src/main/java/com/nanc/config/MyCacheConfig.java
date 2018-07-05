package com.nanc.config;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;


/**
 * @author chennan
 * @date 2018/6/1 11:09
 */
@Configuration
public class MyCacheConfig {

	/**
	 * 定义自己的 KeyGenerator(@Cacheable中key的生成策略)
	 * @return
	 */
	@Bean("myKeyGenerator")
	public KeyGenerator keyGenerator(){
		//匿名内部类
		return (target, method, params) -> {
			//com.nanc.cache.service.EmployeeService.getById[2]
			return target.getClass().getCanonicalName() + "." + method.getName() + Arrays.asList(params).toString();
		};
	}
}
