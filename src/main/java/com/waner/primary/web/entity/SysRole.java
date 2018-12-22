package com.waner.primary.web.entity;

import lombok.Data;

import java.util.Date;

@Data
public class SysRole {
    private Integer id;

    private String name;

    private Byte type;

    private Byte status;

    private String remark;

    private String operator;

    private Date operatorTime;

    private String operatorIp;

}