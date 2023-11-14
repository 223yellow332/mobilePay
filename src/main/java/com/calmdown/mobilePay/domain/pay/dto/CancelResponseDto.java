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
public class CancelResponseDto extends MobilePayResposeMessage {

    //TRANSACTION_ID
    public String transactionId;

    //취소금액
    public Long cancelAmount;

    //승인 처리 일시
    public String payDateTime;
}