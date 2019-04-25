package com.waner.primary.web.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Builder
@Accessors(chain = true)
public class Reply {
	private Integer id;

	private String userHeadImgUrl;

	private String userNickname;

	private String title;

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	private String tag;
}
