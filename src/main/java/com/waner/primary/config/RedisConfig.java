package com.waner.primary.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.waner.primary.common.cache.Fastjson2JsonRedisSerializer;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * Redis配置
 *
 * @author Monster
 * @since 1.0.0-SNAPSHOT
 */
@Configuration
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
  public RedisTemplate<String, Object> redisTemplate(
      RedisConnectionFactory factory, RedisSerializer fastjson2JsonRedisServer) {
    RedisTemplate<String, Object> template = new RedisTemplate<>();
    // 设置连接工厂
    template.setConnectionFactory(factory);
    // 设置redis value序列化方式
    template.setValueSerializer(fastjson2JsonRedisServer);
    // 设置redis key 序列化方式（一般为String）
    template.setKeySerializer(new StringRedisSerializer());
    // 设置事务支持
    template.setEnableTransactionSupport(true);
    template.afterPropertiesSet();
    return template;
  }

  @Bean
  public PlatformTransactionManager transactionManager(DataSource dataSource) {
    return new DataSourceTransactionManager(dataSource);
  }

  /**
   * 引入druid数据源操作数据库
   *
   * @return
   */
  @Bean
  public DataSource dataSource() {
    return DruidDataSourceBuilder.create().build();
  }
}
