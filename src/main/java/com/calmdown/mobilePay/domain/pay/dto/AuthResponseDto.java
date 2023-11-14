package com.calmdown.mobilePay.domain.pay.dto;

import com.calmdown.mobilePay.global.dto.MobilePayResposeMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@Getter
@ToString(callSuper = true)
@SuperBuilder
public class AuthResponseDto extends MobilePayResposeMessage {

    //TRANSACTION_ID
    public String transactionId;

    //결제금액
    public Long payAmount;

    //사용가능 한도액
    public Long limitAmount;

    //승인 처리 일시
    public String payDateTime;
}