package com.waner.primary.web.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
public class TravelRecommend {

  @TableId(type = IdType.AUTO)
  private Integer id;

  private String title;

  private String headImgUrl;

  private String contentImgUrl;

  @JSONField(format = "yyyy-MM-dd HH:mm:ss")
  private Date createTime;

  private Byte delFlag;

  private Byte pushFlag;

  private String content;
}
