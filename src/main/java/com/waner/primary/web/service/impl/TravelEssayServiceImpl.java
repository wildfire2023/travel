package com.waner.primary.web.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.waner.primary.common.result.Response;
import com.waner.primary.web.entity.TravelEssay;
import com.waner.primary.web.entity.TravelUser;
import com.waner.primary.web.mapper.TravelEssayMapper;
import com.waner.primary.web.mapper.TravelUserMapper;
import com.waner.primary.web.service.TravelEssayService;
import com.waner.primary.web.vo.EssayWithUser;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TravelEssayServiceImpl implements TravelEssayService {

    private final TravelEssayMapper essayMapper;
    private final TravelUserMapper userMapper;

    public TravelEssayServiceImpl(TravelEssayMapper essayMapper, TravelUserMapper userMapper) {
        this.essayMapper = essayMapper;
        this.userMapper = userMapper;
    }

    /**
     * 执行保存操作
     *
     * @param essay
     * @return
     */
    @Override
    public int save(TravelEssay essay) {
        return essayMapper.insert(essay);
    }

    /**
     * 根据搜索条件查询列表
     *
     * @param essay
     * @param limit
     * @param page
     * @param checkPushFlag
     * @return
     */
    @Override
    public List<EssayWithUser> getList(
            EssayWithUser essay, int limit, int page, boolean checkPushFlag) {
        // 分页
        Page<EssayWithUser> pageHelper = new Page<>();
        pageHelper.setSize(limit);
        pageHelper.setCurrent(page);

        IPage<EssayWithUser> pageVo = null;

        // 前台查询
        if (checkPushFlag) {
            pageVo = essayMapper.selectPageVo(pageHelper, essay, 1);
            // 后台所有查询
        } else {
            pageVo = essayMapper.selectPageVo(pageHelper, essay, null);
        }
        return pageVo.getRecords();
    }

    /**
     * 查询总条数
     *
     * @param essay
     * @param checkPushFlag
     * @return
     */
    @Override
    public int getCount(EssayWithUser essay, boolean checkPushFlag) {
        if (checkPushFlag) {
            return essayMapper.count(essay, 1);
        } else {
            return essayMapper.count(essay, null);
        }
    }

    /**
     * 审核游记
     *
     * @param essay
     * @return
     */
    @Override
    public int modify(TravelEssay essay) {
        // 设置发表标志位1
        essay.setPushFlag((byte) 1);
        return essayMapper.updateByPrimaryKeySelective(essay);
    }

    /**
     * 批量删除游记
     *
     * @param essays
     * @return
     */
    @Override
    public int remove(TravelEssay[] essays) {
        List<Integer> ids =
                Lists.newArrayList(essays)
                        .parallelStream()
                        .map(TravelEssay::getId)
                        .collect(Collectors.toList());
        return essayMapper.deleteBatchIds(ids);
    }

    /**
     * 根据游记主键获取游记详情信息
     *
     * @param id
     * @return
     */
    @Override
    @Transactional
    public Response<EssayWithUser> getEssayDetail(Integer id) {
        TravelEssay travelEssay = essayMapper.selectByPrimaryKey(id);
        int userId = travelEssay.getSysUserId();
        TravelUser travelUser = userMapper.selectByPrimaryKey(userId);
        EssayWithUser essayWithUser = new EssayWithUser();
        // 复制properties
        BeanUtils.copyProperties(travelEssay, essayWithUser);
        BeanUtils.copyProperties(travelUser, essayWithUser);
        return Response.success(essayWithUser);
    }
}
