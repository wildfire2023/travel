package com.waner.primary.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.waner.primary.web.entity.TravelEssay;
import com.waner.primary.web.entity.TravelRecommend;
import com.waner.primary.web.vo.EssayWithUser;
import org.apache.ibatis.annotations.Param;

public interface TravelEssayMapper extends BaseMapper<TravelEssay> {
    int deleteByPrimaryKey(Integer id);

    int insertTravelEssayMapper(TravelEssay record);

    int insertSelective(TravelEssay record);

    TravelEssay selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TravelEssay record);

    int updateByPrimaryKeyWithBLOBs(TravelEssay record);

    int updateByPrimaryKey(TravelEssay record);

    IPage<EssayWithUser> selectPageVo(Page pageHelper, @Param("essay") EssayWithUser essay, @Param("checkStatus") Integer checkStatus);

    int count(@Param("essay")EssayWithUser essay, @Param("checkStatus")Integer checkPushFlag);
}