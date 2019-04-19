package com.waner.primary.web.vo;

import com.waner.primary.web.entity.TravelEssay;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 带用户信息的游记
 *
 * @author Monster
 * @since 1.0.0-SNAPSHOT
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class EssayWithUser extends TravelEssay {

  private String nickname;

  private String imgUrl;
}
