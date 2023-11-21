package com.calmdown.mobilePay.global.infra.simpleGw;

import com.calmdown.mobilePay.domain.pay.dto.AuthRequestDto;
import com.calmdown.mobilePay.domain.pay.dto.CertRequestDto;
import com.calmdown.mobilePay.domain.pay.entity.Payment;
import com.calmdown.mobilePay.global.infra.simpleGw.dto.SimpleGwAuthRequest;
import com.calmdown.mobilePay.global.infra.simpleGw.dto.SimpleGwCancelRequest;
import com.calmdown.mobilePay.global.infra.simpleGw.dto.SimpleGwCertRequest;
import org.springframework.stereotype.Component;

@Component
public class SimpleGwMsgConverter {

    public SimpleGwCertRequest toGatewayCertRequest(Payment payment) {
        return SimpleGwCertRequest.builder()
                .phone(payment.getPhone())
                .payAmount(payment.getPayAmount())
                .mobileCarrier(payment.getCarrierName().str())
                .build();
    }

    public SimpleGwAuthRequest toGatewayAuthRequest(Payment payment) {
        return SimpleGwAuthRequest.builder()
                .mobileTrxid(payment.getMobileCarrier().getCarrierTrxid())
                .phone(payment.getPhone())
                .payAmount(payment.getPayAmount())
                .mobileCarrier(payment.getCarrierName().str())
                .build();
    }

    public SimpleGwCancelRequest toGatewayCancelRequest(Payment payment) {
        return SimpleGwCancelRequest.builder()
                .mobileTrxid(payment.getMobileCarrier().getCarrierTrxid())
                .phone(payment.getPhone())
                .payAmount(payment.getPayAmount())
                .mobileCarrier(payment.getCarrierName().str())
                .build();
    }
}
