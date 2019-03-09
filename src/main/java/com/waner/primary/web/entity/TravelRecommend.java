package com.waner.primary.web.entity;

import lombok.Data;

import java.util.Date;

@Data
public class TravelRecommend {
    private Integer id;

    private String title;

    private String headImgUrl;

    private String contentImgUrl;

    private Date createTime;

    private Byte delFlag;

    private Byte pushFlag;

    private String content;
}