package com.waner.primary.web.entity;

import lombok.Data;

import java.util.Date;

@Data
public class TravelComment {
    private Integer id;

    private Integer parentId;

    private Integer sysUserId;

    private String content;

    private Date createTime;

    private Byte delFlag;
}