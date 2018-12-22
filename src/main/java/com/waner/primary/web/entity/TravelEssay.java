package com.waner.primary.web.entity;

import lombok.Data;

import java.util.Date;

@Data
public class TravelEssay {
    private Integer id;

    private Integer sysUserId;

    private String title;

    private String headImgUrl;

    private String contentImgUrl;

    private Date createTime;

    private Byte delFlag;

    private String content;

}