package com.waner.primary.web.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class TravelEssay {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer sysUserId;

    private String title;

    private String headImgUrl;

    private String contentImgUrl;

    private Date createTime;

    private Byte delFlag;

    private String content;

    private Byte pushFlag;

}