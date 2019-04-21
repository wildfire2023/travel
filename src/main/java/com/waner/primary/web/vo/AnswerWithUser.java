package com.waner.primary.web.vo;

import com.waner.primary.web.entity.TravelAnswer;
import lombok.Data;
import lombok.EqualsAndHashCode;



@Data
@EqualsAndHashCode(callSuper = true)
public class AnswerWithUser extends TravelAnswer {
    private String nickname;
    private String imgUrl;
}


