package com.waner.primary.common.cache;

import com.alibaba.fastjson.JSON;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;

/**
 * 自实现的Fastjson转储redis序列化方式
 * @author Monster
 * @since 1.0.0-SNAPSHOT
 */
public class Fastjson2JsonRedisSerializer<T> implements RedisSerializer<T> {

    /**
     * 默认编码
     */
    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    /**
     * 获取要解析的类class对象
     */
    private Class<T> clazz;

    public Fastjson2JsonRedisSerializer(Class<T> clazz) {
        super();
        this.clazz = clazz;
    }

    /**
     * 序列化方式
     * @param t 待序列化对象
     * @return
     * @throws SerializationException
     */
    @Override
    public byte[] serialize(T t) throws SerializationException {
        if (t == null) {
            return new byte[0];
        }
        return JSON.toJSONString(t).getBytes(DEFAULT_CHARSET);
    }

    /**
     * 解序列化
     * @param bytes
     * @return
     * @throws SerializationException
     */
    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        String str = new String(bytes, DEFAULT_CHARSET);
        return JSON.parseObject(str, clazz);
    }
}
