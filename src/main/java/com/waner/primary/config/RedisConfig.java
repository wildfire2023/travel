package com.waner.primary.config;

import com.waner.primary.common.cache.Fastjson2JsonRedisSerializer;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis配置
 *
 * @author Monster
 * @date 2019/2/4 8:09
 * @since 1.7
 */
@SpringBootConfiguration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    /**
     * 缓存管理，默认使用redis
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
        CacheManager cacheManager = new RedisCacheManager(redisTemplate);
        return cacheManager;
    }

    /**
     * 使用自实现RedisSerializer
     *
     * @return
     */
    @Bean
    public RedisSerializer<Object> fastjson2JsonRedisSerializer() {
        return new Fastjson2JsonRedisSerializer<>(Object.class);
    }


    /**
     * redis String格式序列化方式
     *
     * @param factory
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory, RedisSerializer fastjson2JsonRedisServer) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        // 设置连接工厂
        template.setConnectionFactory(factory);
        // 设置redis value序列化方式
        template.setValueSerializer(fastjson2JsonRedisServer);
        // 设置redis key 序列化方式（一般为String）
        template.setKeySerializer(new StringRedisSerializer());
        //
        template.afterPropertiesSet();
        return template;
    }


}
