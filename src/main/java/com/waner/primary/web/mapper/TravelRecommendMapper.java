package com.waner.primary.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.waner.primary.web.entity.TravelRecommend;
import org.apache.ibatis.annotations.Param;

/**
 * 推荐内容Mapper映射
 * @author Monster
 * @since 1.0.0-SNAPSHOT
 */
public interface TravelRecommendMapper extends BaseMapper<TravelRecommend> {

    /**
     * 分页搜索查询
     * @param page
     * @param checkStatus
     * @param title
     * @return
     */
    IPage<TravelRecommend> selectPageVo(Page page, @Param("checkStatus") Integer checkStatus,
                                                   @Param("title") String title);

}