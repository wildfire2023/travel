package com.waner.primary.web.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Builder
@Accessors(chain = true)
public class SearchInfoWithTag {

  private Integer id;

  private String title;

  private String content;

  private String tag;

  private Integer count;

  @JSONField(format = "yyyy-MM-dd HH:mm:ss")
  private Date createTime;
}
