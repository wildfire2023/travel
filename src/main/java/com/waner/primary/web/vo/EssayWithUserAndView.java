package com.waner.primary.web.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class EssayWithUserAndView extends EssayWithUser {
	private Long viewNum;
}
