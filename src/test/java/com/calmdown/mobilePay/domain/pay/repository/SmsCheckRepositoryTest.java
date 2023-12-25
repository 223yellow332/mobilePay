package com.calmdown.mobilePay.domain.pay.repository;

import com.calmdown.mobilePay.domain.pay.StatusCode;
import com.calmdown.mobilePay.domain.pay.entity.Cancel;
import com.calmdown.mobilePay.domain.pay.entity.CarrierName;
import com.calmdown.mobilePay.domain.pay.entity.Payment;
import com.calmdown.mobilePay.domain.pay.entity.SmsCheck;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class SmsCheckRepositoryTest {

    @Autowired
    SmsCheckRepository smsCheckRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    private Payment payment;

    @BeforeEach
    void init() {
        // 결제정보
        payment = paymentRepository.save(Payment.builder()
                .merchant(null)
                .statusCode(StatusCode.CERT_READY)
                .carrierName(CarrierName.KT)
                .payAmount(24000L)
                .phone("01012344885")
                .merchantTrxid(UUID.randomUUID().toString().substring(0,13))
                .merchantReqDt(java.sql.Timestamp.valueOf(LocalDateTime.now()))
                .build());
    }

    @Test
    void sms인증정보_저장하기() {
        //given
        SmsCheck smsCheck = smsCheckRepository.save(SmsCheck.builder()
                .payment(payment)
                .statusCode(StatusCode.SMS_CHECK_SUCCESS)
                .smsCheckNumber("123456")
                .build());

        //when
        SmsCheck findSmsCheck = smsCheckRepository.findById(smsCheck.getId())
                .orElseThrow(IllegalArgumentException::new);

        //then
        log.info("sms인증정보_저장하기 = {}", findSmsCheck.toString());

        assertEquals(StatusCode.SMS_CHECK_SUCCESS, findSmsCheck.getStatusCode());
        assertEquals("123456", findSmsCheck.getSmsCheckNumber());
    }

    @Test
    void sms인증정보_payment에서_조회() {
        //given
        SmsCheck smsCheck = smsCheckRepository.save(SmsCheck.builder()
                .payment(payment)
                .statusCode(StatusCode.SMS_CHECK_SUCCESS)
                .smsCheckNumber("123456")
                .build());

        //when
        Payment findPayment = paymentRepository.findById(payment.getId())
                .orElseThrow(IllegalArgumentException::new);

        //then
        log.info("sms인증정보_payment에서_조회 = {}", findPayment.getSmsChecks().toString());

        assertEquals(StatusCode.SMS_CHECK_SUCCESS, findPayment.getSmsChecks().get(0).getStatusCode());
        assertEquals("123456", findPayment.getSmsChecks().get(0).getSmsCheckNumber());
    }


}