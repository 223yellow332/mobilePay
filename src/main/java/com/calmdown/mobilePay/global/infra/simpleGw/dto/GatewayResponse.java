package com.calmdown.mobilePay.global.infra.simpleGw.dto;

import com.calmdown.mobilePay.domain.pay.entity.MobileCarrier;
import com.calmdown.mobilePay.domain.pay.entity.Payment;

public interface GatewayResponse {
    String getMobileTrxid();

    long getLimitAmount();

    String getResultCode();

    String getResultMessage();

    MobileCarrier toEntity(Payment payment);
}
