package com.calmdown.mobilePay.domain.pay.repository;

import com.calmdown.mobilePay.domain.pay.StatusCode;
import com.calmdown.mobilePay.domain.pay.entity.Cancel;
import com.calmdown.mobilePay.domain.pay.entity.CarrierName;
import com.calmdown.mobilePay.domain.pay.entity.Payment;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class CancelRepositoryTest {

    @Autowired
    private CancelRepository cancelRepository;
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
    void 취소정보_저장하기() {
        //given
        Cancel cancel = cancelRepository.save(Cancel.builder()
                        .payment(payment)
                        .statusCode(StatusCode.CANCEL_READY)
                        .cancelAmount(payment.getPayAmount())
                        .build());

        //when
        Cancel findCancel = cancelRepository.findById(cancel.getId())
                .orElseThrow(IllegalArgumentException::new);

        //then
        log.info("취소정보_저장하기 = {}", findCancel.toString());

        assertEquals(StatusCode.CANCEL_READY, findCancel.getStatusCode());
        assertEquals(payment.getPayAmount(), findCancel.getCancelAmount());
    }

    @Test
    void 취소_거래상태_변경() {
        //given
        Cancel cancel = cancelRepository.save(Cancel.builder()
                .payment(payment)
                .statusCode(StatusCode.CANCEL_READY)
                .cancelAmount(payment.getPayAmount())
                .build());

        //when
        cancel.updateStatus(StatusCode.CANCEL_SUCCESS);
        cancelRepository.save(cancel);

        Cancel findCancel = cancelRepository.findById(cancel.getId())
                .orElseThrow(IllegalArgumentException::new);

        //then
        log.info("취소_거래상태_변경 = {}", findCancel.toString());

        assertEquals(StatusCode.CANCEL_SUCCESS, findCancel.getStatusCode());
    }

    @Test
    void 취소정보_payment에서_조회() {
        //given
        Cancel cancel = cancelRepository.save(Cancel.builder()
                .payment(payment)
                .statusCode(StatusCode.CANCEL_READY)
                .cancelAmount(payment.getPayAmount())
                .build());

        //when
        Payment findPayment = paymentRepository.findById(payment.getId())
                .orElseThrow(IllegalArgumentException::new);

        //then
        log.info("취소정보_payment에서_조회 = {}", findPayment.getCancels().toString());

        assertEquals(StatusCode.CANCEL_READY, findPayment.getCancels().get(0).getStatusCode());
        assertEquals(payment.getPayAmount(), findPayment.getCancels().get(0).getCancelAmount());
    }
}