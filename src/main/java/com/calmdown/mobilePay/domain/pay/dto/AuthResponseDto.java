package com.calmdown.mobilePay.domain.pay.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@Getter
@ToString(callSuper = true)
@SuperBuilder
public class AuthResponseDto extends MobilePayCommonRespose {

    //TRANSACTION_ID -->PAYMENT_ID
    public String paymentId;

    //결제금액
    public Long payAmount;

    //사용가능 한도액
    public Long limitAmount;

    //승인 처리 일시
    public String payDateTime;
}