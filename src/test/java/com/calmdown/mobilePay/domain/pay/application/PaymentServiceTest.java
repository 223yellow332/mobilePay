package com.calmdown.mobilePay.domain.pay.application;

import com.calmdown.mobilePay.domain.merchant.entity.Merchant;
import com.calmdown.mobilePay.domain.merchant.entity.ProgressCode;
import com.calmdown.mobilePay.domain.pay.StatusCode;
import com.calmdown.mobilePay.domain.pay.entity.CarrierName;
import com.calmdown.mobilePay.domain.pay.entity.Payment;
import com.calmdown.mobilePay.domain.pay.repository.PaymentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

//@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

//    @InjectMocks
//    PaymentService paymentService;
//
//    @Mock
//    PaymentRepository paymentRepository;
//
//    @Test
//    @DisplayName("거래번호 확인 조회")
//    void checkPayementIdStatusTest() {
//        //given
//        Merchant merchant = Merchant.builder()
//                .merchantName("테스트가맹점")
//                .progressCode(ProgressCode.AVAILABLE)
//                .maxSmsCount(2)
//                .merchantName("online")
//                .build();
//
//        Payment payment = paymentRepository.save(Payment.builder()
//                .merchant(merchant)
//                .statusCode(StatusCode.AUTH_READY)
//                .carrierName(CarrierName.KT)
//                .payAmount(24000L)
//                .phone("01012344885")
//                .build());
//    }


}