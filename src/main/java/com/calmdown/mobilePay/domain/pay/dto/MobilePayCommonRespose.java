package com.calmdown.mobilePay.domain.pay.dto;

import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@ToString
@NoArgsConstructor
@SuperBuilder
public abstract class MobilePayCommonRespose {

    // 결과 코드
    public String resultCode;

    // 결과 메시지
    public String resultMessage;
}
