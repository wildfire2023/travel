package com.waner.primary.web.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;


@Data
@Accessors(chain = true)
public class TravelAnswer {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer parentId;

    private Integer sysUserId;

    private String contentImgUrl;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private Byte delFlag;

    private String content;
}
