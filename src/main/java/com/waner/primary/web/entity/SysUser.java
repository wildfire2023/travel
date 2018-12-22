package com.waner.primary.web.entity;

import lombok.Data;

import java.util.Date;

@Data
public class SysUser {
    private Integer id;

    private String username;

    private String password;

    private String nickname;

    private String email;

    private String phone;

    private Byte status;

    private String remark;

    private String operator;

    private Date operatorTime;

    private String operatorIp;
}