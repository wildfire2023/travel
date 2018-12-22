package com.waner.primary.web.entity;

import lombok.Data;

import java.util.Date;

@Data
public class TravelQuestion {
    private Integer id;

    private Integer sysUserId;

    private String title;

    private Date createTime;

    private Byte delFlag;

    private String content;
}