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

    /**
     * gw 통신 결과 저장
     * @param mobileCarrier
     * @return
     */
    @Transactional
    public MobileCarrier save(MobileCarrier mobileCarrier) {
        return mobileCarrierRepository.save(mobileCarrier);
    }

    /**
     * gw 통신 결과 업데이트
     * @param mobileCarrier
     * @param response
     * @return
     */
    @Transactional
    public MobileCarrier updateStatus(MobileCarrier mobileCarrier, GatewayResponse response) {
        mobileCarrier.updateResult(response.getMobileTrxid(), response.getResultCode(), response.getResultMessage());
        return mobileCarrierRepository.save(mobileCarrier);
    }
}
