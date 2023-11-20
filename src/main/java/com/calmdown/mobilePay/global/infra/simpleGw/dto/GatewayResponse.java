package com.calmdown.mobilePay.global.infra.simpleGw.dto;

public interface GatewayResponse {
    String getMobileTrxid();
    String getResultCode();

    String getResultMessage();
}
