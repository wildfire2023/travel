package com.waner.primary;

import com.waner.primary.common.cache.TestKey;
import com.waner.primary.common.util.RedisUtil;
import com.waner.primary.web.entity.SysUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PrimaryApplicationTests {

  @Autowired private RedisUtil redisUtil;

  @Test
  public void contextLoads() {
    SysUser sysUser = new SysUser();
    sysUser.setNickname("薛本刚");
    redisUtil.set(TestKey.TEST, "test", sysUser);
  }
}
