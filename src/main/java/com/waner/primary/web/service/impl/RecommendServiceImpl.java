package com.waner.primary.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.waner.primary.web.entity.TravelRecommend;
import com.waner.primary.web.mapper.TravelRecommendMapper;
import com.waner.primary.web.service.RecommendService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 推荐内容服务实现类
 *
 * @author Monster
 * @since 1.0.0-SNAPSHOT
 */
@Service
public class RecommendServiceImpl implements RecommendService{

    public final static String USER = "user";
    public final static String ADMINISTRATOR = "administrator";

    private final TravelRecommendMapper travelRecommendMapper;

    public RecommendServiceImpl(TravelRecommendMapper travelRecommendMapper) {
        this.travelRecommendMapper = travelRecommendMapper;
    }


    /**
     * 新增推荐内容
     * @param recommend
     * @return 新增推荐成功标志
     */
    @Override
    public int addRecommend(TravelRecommend recommend) {
        recommend.setCreateTime(new Date());
        int insertRet = travelRecommendMapper.insert(recommend);
        return insertRet;
    }

    @Override
    public List<TravelRecommend> getList(String checkStatus, int limit, int page) {
        // 分页
        Page<TravelRecommend> pageHelper = new Page<>();
        pageHelper.setSize(limit);
        pageHelper.setCurrent(page);

        IPage<TravelRecommend> pageVo = null;
        // 未删除推荐内容

        // 已发表推荐
        if ("pushed".equals(checkStatus)) {
            pageVo = travelRecommendMapper.selectPageVo(pageHelper, 1);
        }else {
            pageVo = travelRecommendMapper.selectPageVo(pageHelper, null);
        }
        return pageVo.getRecords();
    }

    /**
     * 查询未删除的推荐数量
     * @param checkStatus
     * @return
     */
    @Override
    public int getCount(String checkStatus) {
        QueryWrapper<TravelRecommend> wrapper = new QueryWrapper<>();
        // 设置未删除查询条件
        wrapper.eq("del_flag", 0);
        if ("pushed".equals(checkStatus)) {
            wrapper.eq("push_flag", 1);
        }
        return travelRecommendMapper.selectCount(wrapper);
    }
}
