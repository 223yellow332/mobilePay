package com.calmdown.mobilePay.global.infra.simpleGw.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class SimpleGwResponse implements GatewayResponse{

    private String mobileTrxid;
    private long limitAmount;
    private String resultCode;
    private String resultMessage;

    @Builder
    SimpleGwResponse (String mobileTrxid, Long limitAmount, String resultCode, String resultMessage) {
        this.mobileTrxid = mobileTrxid;
        this.limitAmount = limitAmount;
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
    }
}
