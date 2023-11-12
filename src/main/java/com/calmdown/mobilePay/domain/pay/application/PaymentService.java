package com.calmdown.mobilePay.domain.pay.application;

import com.calmdown.mobilePay.domain.merchant.entity.Merchant;
import com.calmdown.mobilePay.domain.merchant.repository.MerchantRepository;
import com.calmdown.mobilePay.domain.model.ResultCode;
import com.calmdown.mobilePay.domain.pay.dto.PaymentCertRequestDto;
import com.calmdown.mobilePay.domain.pay.entity.Payment;
import com.calmdown.mobilePay.domain.pay.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
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
