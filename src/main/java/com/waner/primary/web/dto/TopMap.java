package com.waner.primary.web.dto;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Builder
@Accessors(chain = true)
public class TopMap {
	private Integer articleId;
	private Integer viewNUm;
}
