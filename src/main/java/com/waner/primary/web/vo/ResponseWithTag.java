package com.waner.primary.web.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Builder
@Accessors(chain = true)
public class ResponseWithTag {
  private Integer articleId;

  private String articleTitle;

  @JSONField(format = "yyyy-MM-dd HH:mm:ss")
  private Date articleCreateTime;

  private String tag;

  @JSONField(format = "yyyy-MM-dd HH:mm:ss")
  private Date responseCreateTime;

  private String responseContent;

  private Integer commentsCount;

  private String userHeadImgUrl;

  private String userNickname;
}
