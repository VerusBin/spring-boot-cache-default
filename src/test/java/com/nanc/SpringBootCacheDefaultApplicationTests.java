package com.nanc;

import com.nanc.entity.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootCacheDefaultApplicationTests {

	@Test
	public void contextLoads() {
	}


	/**
	 * 操作<K V>的
	 */
	@Autowired
	private RedisTemplate redisTemplate;
	/**
	 * 操作字符串的
	 */
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Test
	public void testRedis(){
		stringRedisTemplate.opsForValue().set("aaa", "aaa");

		redisTemplate.opsForValue().set("hhh", Employee.builder().id(1).lastName("aaaaa").email("xxxx@xxx.com").gender(1).dId(1).build());
	}
}
