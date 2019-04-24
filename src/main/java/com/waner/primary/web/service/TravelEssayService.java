package com.waner.primary.web.service;

import com.waner.primary.common.result.Response;
import com.waner.primary.web.entity.TravelEssay;
import com.waner.primary.web.vo.EssayWithUser;

import java.util.List;

public interface TravelEssayService {
    int save(TravelEssay essay);

    List<EssayWithUser> getList(EssayWithUser essay, int limit, int page, boolean checkPushFlag);

    int getCount(EssayWithUser essay, boolean checkPushFlag);

    int modify(TravelEssay essay);

    int remove(TravelEssay[] essays);

    Response<EssayWithUser> getEssayDetail(Integer id);

	List<TravelEssay> queryEssayByUser(Integer userId);
}
