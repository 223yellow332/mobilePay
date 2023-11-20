package com.calmdown.mobilePay.domain.pay.application;

import com.calmdown.mobilePay.domain.merchant.entity.Merchant;
import com.calmdown.mobilePay.domain.model.ResultCode;
import com.calmdown.mobilePay.domain.pay.StatusCode;
import com.calmdown.mobilePay.domain.pay.entity.Payment;
import com.calmdown.mobilePay.domain.pay.repository.PaymentRepository;
import com.calmdown.mobilePay.global.exception.errorCode.CommonErrorCode;
import com.calmdown.mobilePay.global.exception.exception.UserException;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Transactional
    public Payment save(Payment payment) {
        return paymentRepository.save(payment);
    }

    public Payment checkPaymentIdStatus(Long id, StatusCode statusCode, Merchant merchant) {
        Payment payment = paymentRepository.findByIdAndStatusCode(id, statusCode)
                .orElseThrow(() -> new UserException(CommonErrorCode.INVALID_PAYMENT_ID));
        
        // 거래정보에 등록된 가맹점 정보와 동일한지 확인
        if(payment.getMerchant().getId() != merchant.getId())
            new UserException(CommonErrorCode.MERCHANT_ID_MISMATCH);
        
        return payment;
    }

    @Transactional
    public Payment updateStatus(Payment payment, StatusCode statusCode) {
        payment.updateStatus(statusCode);
        return paymentRepository.save(payment);
    }

}
