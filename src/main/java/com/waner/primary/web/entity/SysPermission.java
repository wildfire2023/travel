package com.waner.primary.web.entity;

import lombok.Data;

import java.util.Date;

@Data
public class SysPermission {
  private Integer id;

  private String code;

  private String name;

  private String url;

  private Byte type;

  private Byte status;

  private String remark;

  private String operator;

  private Date operatorTime;

  private String operatorIp;
}
