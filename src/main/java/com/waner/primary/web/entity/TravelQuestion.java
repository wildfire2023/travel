package com.waner.primary.web.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class TravelQuestion {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer sysUserId;

    private String title;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private Byte delFlag;

    private String content;
}
