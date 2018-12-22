package com.waner.primary.web.entity;

import lombok.Data;

import java.util.Date;

@Data
public class TravelUser {
    private Integer sysUserId;

    private String nickname;

    private Byte sex;

    private String city;

    private Date birth;

    private String intro;

    private String imgUrl;

    private String cover;
}