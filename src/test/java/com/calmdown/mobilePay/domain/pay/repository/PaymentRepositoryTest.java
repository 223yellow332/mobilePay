package com.calmdown.mobilePay.domain.pay.repository;

import com.calmdown.mobilePay.domain.merchant.entity.Merchant;
import com.calmdown.mobilePay.domain.merchant.entity.ProgressCode;
import com.calmdown.mobilePay.domain.merchant.repository.MerchantRepository;
import com.calmdown.mobilePay.domain.pay.StatusCode;
import com.calmdown.mobilePay.domain.pay.entity.*;
import com.calmdown.mobilePay.global.exception.errorCode.CommonErrorCode;
import com.calmdown.mobilePay.global.exception.exception.UserException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

//@Disabled
@Slf4j
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class PaymentRepositoryTest {
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private MerchantRepository merchantRepository;

    private Merchant merchant;
    private UserInfo userInfo;

    @BeforeEach
    void init() {
        // 가맹점
        merchant = merchantRepository.save(Merchant.builder()
                .merchantName("테스트 가맹점")
                .progressCode(ProgressCode.AVAILABLE)
                .maxSmsCount(2)
                .build());
        // 고객정보
        userInfo = UserInfo.builder()
                .userName("홍길동")
                .socialNumber("19991225")
                .gender("M")
                .email("test@gmail.com")
                .build();
    }

    @Test
    void 결제정보_저장하기() {
        //given
        Payment payment = paymentRepository.save(Payment.builder()
                        .merchant(merchant)
                        .statusCode(StatusCode.AUTH_READY)
                        .carrierName(CarrierName.KT)
                        .payAmount(24000L)
                        .phone("01012344885")
                        .userInfo(userInfo)
                        .merchantTrxid(UUID.randomUUID().toString().substring(0,13))
                        .merchantReqDt(java.sql.Timestamp.valueOf(LocalDateTime.now()))
                        .build());

        //when
        Payment findPayment = paymentRepository.findById(payment.getId())
                .orElseThrow(IllegalArgumentException::new);

        //then
        log.info("결제정보_저장하기 = {}", findPayment.toString());

        assertEquals(merchant, findPayment.getMerchant());
        assertEquals(payment.getMerchantTrxid(), findPayment.getMerchantTrxid());
    }

    @Test
    void 거래상태기준_조회하기() {
        //given
        Payment payment = paymentRepository.save(Payment.builder()
                .merchant(merchant)
                .statusCode(StatusCode.AUTH_READY)
                .carrierName(CarrierName.KT)
                .payAmount(24000L)
                .phone("01012344885")
                .userInfo(userInfo)
                .merchantTrxid(UUID.randomUUID().toString().substring(0,13))
                .merchantReqDt(java.sql.Timestamp.valueOf(LocalDateTime.now()))
                .build());

        //when
        Payment findPayment = paymentRepository.findByIdAndStatusCode(payment.getId(), StatusCode.AUTH_READY)
                .orElseThrow(IllegalArgumentException::new);

        //then
        log.info("거래상태기준_조회하기 = {}", findPayment.toString());

        assertEquals(StatusCode.AUTH_READY, findPayment.getStatusCode());
        assertEquals(merchant, findPayment.getMerchant());
    }

    @Test
    void 거래상태기준_조회내역없음() {
        //given
        Payment payment = paymentRepository.save(Payment.builder()
                .merchant(merchant)
                .statusCode(StatusCode.AUTH_READY)
                .carrierName(CarrierName.KT)
                .payAmount(24000L)
                .phone("01012344885")
                .userInfo(userInfo)
                .merchantTrxid(UUID.randomUUID().toString().substring(0,13))
                .merchantReqDt(java.sql.Timestamp.valueOf(LocalDateTime.now()))
                .build());

        //when & then
        assertThrows(UserException.class, () -> {
            paymentRepository.findByIdAndStatusCode(payment.getId(), StatusCode.CANCEL_SUCCESS)
                    .orElseThrow(() -> new UserException(CommonErrorCode.INVALID_PAYMENT_ID));
        });
    }

    @Test
    void 거래상태_변경() {
        //given
        Payment payment = paymentRepository.save(Payment.builder()
                .merchant(merchant)
                .statusCode(StatusCode.CERT_SUCCESS)
                .carrierName(CarrierName.KT)
                .payAmount(24000L)
                .phone("01012344885")
                .userInfo(userInfo)
                .merchantTrxid(UUID.randomUUID().toString().substring(0,13))
                .merchantReqDt(java.sql.Timestamp.valueOf(LocalDateTime.now()))
                .build());

        //when
        payment.updateStatus(StatusCode.AUTH_FAILURE);
        paymentRepository.save(payment);

        Payment findPayment = paymentRepository.findById(payment.getId())
                .orElseThrow(IllegalArgumentException::new);

        //then
        log.info("거래상태_변경 = {}", findPayment.toString());

        assertEquals(StatusCode.AUTH_FAILURE, findPayment.getStatusCode());
    }

    @Test
    void 통신사_응답정보_저장() {
        //given
        Payment payment = paymentRepository.save(Payment.builder()
                .merchant(merchant)
                .statusCode(StatusCode.CERT_READY)
                .carrierName(CarrierName.KT)
                .payAmount(24000L)
                .phone("01012344885")
                .userInfo(userInfo)
                .merchantTrxid(UUID.randomUUID().toString().substring(0,13))
                .merchantReqDt(java.sql.Timestamp.valueOf(LocalDateTime.now()))
                .build());

        MobileCarrier mobileCarrier = MobileCarrier.builder()
                .payment(payment)
                .carrierName(CarrierName.SK)
                .carrierTrxid("SK_TEST_"+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddhhmmss")))
                .carrierReturnCode("0")
                .carrierReturnMsg("성공")
                .limitAmount(250000L)
                .build();

        //when
        payment.updateStatus(StatusCode.CERT_SUCCESS);
        payment.setMobileCarrier(mobileCarrier);
        paymentRepository.save(payment);

        Payment findPayment = paymentRepository.findById(payment.getId())
                .orElseThrow(IllegalArgumentException::new);

        //then
        log.info("통신사_응답정보_저장 = {}", findPayment.toString());

        assertEquals(StatusCode.CERT_SUCCESS, findPayment.getStatusCode());
        assertEquals(mobileCarrier, findPayment.getMobileCarrier());
    }

}
