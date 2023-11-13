package com.calmdown.mobilePay.domain.pay.application;

import com.calmdown.mobilePay.domain.merchant.application.MerchantService;
import com.calmdown.mobilePay.domain.merchant.repository.MerchantRepository;
import com.calmdown.mobilePay.domain.model.ResultCode;
import com.calmdown.mobilePay.domain.pay.dto.PaymentCertRequestDto;
import com.calmdown.mobilePay.domain.pay.entity.Payment;
import com.calmdown.mobilePay.domain.pay.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final MerchantService merchantService;

    @Transactional
    public Payment save(Payment payment) {
        return paymentRepository.save(payment);
    }

    private final MerchantRepository merchantRepository;

    @Transactional
    //인증요청 (가맹점--> 결제엔진)
    public ResultCode cert(PaymentCertRequestDto paymentCertRequestDto){

//        Merchant merchant = merchantRepository.findById(paymentCertRequestDto.toEntity().getId())
//                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 가맹점"));

        //가맹점 정보 있으면, 결제정보테이블 상태정보 (CERT_READY)
        paymentRepository.save(paymentCertRequestDto.toEntity());
        return ResultCode.SUCCESS;
    }


}
