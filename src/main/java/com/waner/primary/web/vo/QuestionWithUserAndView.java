package com.waner.primary.web.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class QuestionWithUserAndView extends QuestionWithUser {
	private Long viewNum;
}
