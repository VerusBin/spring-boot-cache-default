package com.nanc.config;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import com.google.common.collect.ImmutableMap;
import com.nanc.entity.Employee;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.net.UnknownHostException;
import java.time.Duration;
import java.util.Map;

/**
 * 主要用于配置 redis的key，value的序列化器
 * @author chennan
 * @date 2018/6/1 15:22
 */
@Configuration
public class MyRedisConfig {

	@Bean
	public RedisTemplate<Object, Object> redisTemplate(
			RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
		RedisTemplate<Object, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(redisConnectionFactory);

		//fastjson的序列化器
		//FastJsonRedisSerializer serializer = new FastJsonRedisSerializer(Object.class);

		Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer(Object.class);
		//设置序列化器
		template.setDefaultSerializer(serializer);
		return template;
	}

	@Bean
	public StringRedisTemplate stringRedisTemplate(
			RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
		StringRedisTemplate template = new StringRedisTemplate();

		//fastjson的序列化器
		FastJsonRedisSerializer serializer = new FastJsonRedisSerializer(Object.class);

		//Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer(Object.class);
		template.setValueSerializer(serializer);

		//设置序列化器
		template.setConnectionFactory(redisConnectionFactory);
		return template;
	}


	/**
	 * @Primary  配置默认的缓存管理器
	 *    valueSerializationPair：使用默认的JdkSerializationRedisSerializer()
	 *    使用方式：如果不指定cacheManagers属性，就会使用默认的CacheManager
	 *    @Cacheable(value = "cache_1_minutes",keyGenerator = "myKeyGenerator")
	 * @param redisConnectionFactory
	 * @return
	 */
	@Primary
	@Bean
	public RedisCacheManager defaultCacheManager(RedisConnectionFactory redisConnectionFactory) {
		RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager
				.builder(redisConnectionFactory)
				.cacheDefaults(redisCacheConfiguration(null, Duration.ofDays(30)));


		//可以抽取的公共配置
		Map<String, RedisCacheConfiguration> map = ImmutableMap.<String,RedisCacheConfiguration>builder()
				.put("cache_1_minutes", redisCacheConfiguration(null, Duration.ofMinutes(1)))
				.put("cache_10_minutes", redisCacheConfiguration(null, Duration.ofMinutes(10)))
				.put("cache_1_hour", redisCacheConfiguration(null, Duration.ofHours(1)))
				.put("cache_10_hour", redisCacheConfiguration(null,Duration.ofHours(10)))
				.put("cache_1_day", redisCacheConfiguration(null,Duration.ofDays(1)))
				.put("cache_7_days", redisCacheConfiguration(null,Duration.ofDays(7)))
				.put("cache_30_days", redisCacheConfiguration(null,Duration.ofDays(30)))
				.build();

		builder.withInitialCacheConfigurations(map);

		return builder.build();
	}


	/**
	 * 为employee配置一个单独的RedisCacheManager
	 * 同理，如果Department也想把数据以json的形式放入缓存，再添加一个RedisCacheManager即可
	 *
	 *
	 * 使用方式：@Cacheable(value = "cache_1_minutes",keyGenerator = "myKeyGenerator",cacheManager = "employeeCacheManager")
	 * @param redisConnectionFactory
	 * @return
	 */
	@Bean
	public RedisCacheManager employeeCacheManager(RedisConnectionFactory redisConnectionFactory) {

		//employee的使用FastJSON的序列化器
		FastJsonRedisSerializer serializer = new FastJsonRedisSerializer(Employee.class);

		RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager
				.builder(redisConnectionFactory)
				.cacheDefaults(redisCacheConfiguration(serializer, Duration.ofDays(30)));

		Map<String, RedisCacheConfiguration> map = ImmutableMap.<String,RedisCacheConfiguration>builder()
				.put("cache_1_minutes", redisCacheConfiguration(serializer, Duration.ofMinutes(1)))
				.put("cache_10_minutes", redisCacheConfiguration(serializer, Duration.ofMinutes(10)))
				.put("cache_1_hour", redisCacheConfiguration(serializer, Duration.ofHours(1)))
				.put("cache_10_hour", redisCacheConfiguration(serializer,Duration.ofHours(10)))
				.put("cache_1_day", redisCacheConfiguration(serializer,Duration.ofDays(1)))
				.put("cache_7_days", redisCacheConfiguration(serializer,Duration.ofDays(7)))
				.put("cache_30_days", redisCacheConfiguration(serializer,Duration.ofDays(30)))
				.build();

		builder.withInitialCacheConfigurations(map);

		return builder.build();
	}






	/**
	 * 启用@Cacheable等注解时，redis里面用到的key--value的序列化
	 *                  key = new StringRedisSerializer()
	 *                  value = new JdkSerializationRedisSerializer()
	 *  以及缓存的时效
	 *
	 *
	 * @return
	 */
	public org.springframework.data.redis.cache.RedisCacheConfiguration redisCacheConfiguration(RedisSerializer serializer, Duration duration){

		RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig();
		if (null != serializer) {
			configuration = configuration.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer));
		}
		//key的前缀不添加 cacheNames
		configuration = configuration.disableKeyPrefix();
		//设置缓存的时效
		configuration = configuration.entryTtl(duration);
		//configuration.s
		return configuration;
	}

}
