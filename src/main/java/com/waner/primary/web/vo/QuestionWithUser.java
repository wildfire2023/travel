package com.waner.primary.web.vo;

import com.waner.primary.web.entity.TravelQuestion;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class QuestionWithUser extends TravelQuestion {
    /**
     * 昵称
     */
    private String nickname;

    /**
     * 用户头像
     */
    private String imgUrl;
}
