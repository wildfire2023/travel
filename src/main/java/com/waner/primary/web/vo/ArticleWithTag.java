package com.waner.primary.web.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * To display the article with tags.
 * @author Monster
 * @since 1.0.0-SNAPSHOT
 */
@Data
@Builder
@Accessors(chain = true)
public class ArticleWithTag {
  private Integer id;

  private String title;

  @JSONField(format = "yyyy-MM-dd HH:mm:ss")
  private Date createTime;

  private Byte delFLag;

  /** Tag to display in the user page. */
  private String tag;
}
