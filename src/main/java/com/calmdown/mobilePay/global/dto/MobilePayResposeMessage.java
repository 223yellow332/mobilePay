package com.calmdown.mobilePay.global.dto;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@SuperBuilder
public abstract class MobilePayResposeMessage {

    // 결과 코드
    public String resultCode;

    // 결과 메시지
    public String resultMsg;
}
