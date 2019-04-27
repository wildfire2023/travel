package com.waner.primary.web.vo;

import com.waner.primary.web.entity.TravelRecommend;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class RecommendWithView extends TravelRecommend {
	private Long viewNum;
}


