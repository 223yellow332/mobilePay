package com.calmdown.mobilePay.global.infra.simpleGw.dto;

import com.calmdown.mobilePay.domain.pay.entity.MobileCarrier;
import com.calmdown.mobilePay.domain.pay.entity.Payment;
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

    public MobileCarrier toEntity() {
        return MobileCarrier.builder()
                .carrierTrxid(mobileTrxid)
                .carrierReturnCode(resultCode)
                .carrierReturnMsg(resultMessage)
                .limitAmount(limitAmount)
                .build();
    }

    @Builder
    SimpleGwResponse (String mobileTrxid, Long limitAmount, String resultCode, String resultMessage) {
        this.mobileTrxid = mobileTrxid;
        this.limitAmount = limitAmount;
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
    }
}
