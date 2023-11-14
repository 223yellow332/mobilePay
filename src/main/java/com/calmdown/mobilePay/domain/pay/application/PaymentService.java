package com.calmdown.mobilePay.domain.pay.application;

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

    @Transactional
    public Payment save(Payment payment) {
        return paymentRepository.save(payment);
    }

}
