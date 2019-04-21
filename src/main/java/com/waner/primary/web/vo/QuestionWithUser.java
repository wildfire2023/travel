package com.waner.primary.web.vo;

import com.waner.primary.web.entity.TravelQuestion;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
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
