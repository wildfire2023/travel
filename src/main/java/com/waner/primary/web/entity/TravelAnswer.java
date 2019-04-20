package com.waner.primary.web.entity;

import lombok.Data;

import java.util.Date;

@Data
public class TravelAnswer {
    private Integer id;

    private Integer parentId;

    private Integer sysUserId;

    private String contentImgUrl;

    private Date createTime;

    private Byte delFlag;

    private String content;
}
