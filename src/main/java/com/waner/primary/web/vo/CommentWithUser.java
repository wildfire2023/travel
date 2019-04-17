package com.waner.primary.web.vo;

import com.waner.primary.web.entity.TravelComment;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CommentWithUser extends TravelComment {
    private String nickname;
    private String headImgUrl;
}
