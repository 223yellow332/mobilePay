package com.calmdown.mobilePay.domain.pay.application;

import com.calmdown.mobilePay.domain.pay.StatusCode;
import com.calmdown.mobilePay.domain.pay.entity.MobileCarrier;
import com.calmdown.mobilePay.domain.pay.repository.MobileCarrierRepository;
import com.calmdown.mobilePay.global.infra.simpleGw.dto.GatewayResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MobileCarrierService {
    private  final MobileCarrierRepository mobileCarrierRepository;

    @Transactional
    public MobileCarrier save(MobileCarrier mobileCarrier) {
        return mobileCarrierRepository.save(mobileCarrier);
    }

    @Transactional
    public MobileCarrier updateStatus(MobileCarrier mobileCarrier, GatewayResponse response) {
        mobileCarrier.updateResult(response.getResultCode(), response.getResultMessage());
        return mobileCarrierRepository.save(mobileCarrier);
    }
}
