package com.calmdown.mobilePay.domain.pay.dto;

import com.calmdown.mobilePay.global.dto.MobilePayResposeMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor
@ToString(callSuper = true)
@SuperBuilder
public class SmsCheckResponseDto extends MobilePayResposeMessage {
}
