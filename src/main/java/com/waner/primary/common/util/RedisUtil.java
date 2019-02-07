package com.waner.primary.common.util;


import com.waner.primary.web.entity.SysUser;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@Component
public class RedisUtil {

    private final RedisTemplate<String, Object> template;


    public RedisUtil(RedisTemplate<String, Object> redisTemplate) {
        this.template = redisTemplate;
    }


    public boolean set() {
        ValueOperations<String, Object> valueOperations = template.opsForValue();
        SysUser sysUser = new SysUser();
        sysUser.setEmail("651833918");
        sysUser.setPassword("123");
        sysUser.setNickname("薛本刚");
        valueOperations.set("111",sysUser);
        return true;
    }

}
