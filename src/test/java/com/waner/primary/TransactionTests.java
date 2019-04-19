package com.waner.primary;

import com.waner.primary.web.entity.TravelUser;
import com.waner.primary.web.mapper.TravelUserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PrimaryApplication.class)
public class TransactionTests {

  @Autowired private TravelUserMapper travelUserMapper;

  @Test
  @Transactional
  public void test() {
    TravelUser travelUser = new TravelUser();
    travelUser.setImgUrl("111");
    travelUser.setSysUserId(2234234);
    travelUserMapper.insertTravelUserMapper(travelUser);
    TravelUser travelUser1 = new TravelUser();
    travelUser1.setImgUrl("222");
    travelUserMapper.insertTravelUserMapper(travelUser1);
  }
}
