package com.calmdown.mobilePay.domain.pay.repository;

import com.calmdown.mobilePay.domain.merchant.entity.Merchant;
import com.calmdown.mobilePay.domain.merchant.entity.ProgressCode;
import com.calmdown.mobilePay.domain.merchant.repository.MerchantRepository;
import com.calmdown.mobilePay.domain.pay.StatusCode;
import com.calmdown.mobilePay.domain.pay.entity.CarrierName;
import com.calmdown.mobilePay.domain.pay.entity.MobileCarrier;
import com.calmdown.mobilePay.domain.pay.entity.Payment;

import com.calmdown.mobilePay.domain.pay.entity.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class MobileCarrierRepositoryTest {

    @Autowired
    private MobileCarrierRepository mobileCarrierRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private MerchantRepository merchantRepository;

    private Payment payment;

    @BeforeEach
    void init() {
        //가맹점 정보
        Merchant merchant = merchantRepository.save(Merchant.builder()
                .merchantName("테스트 가맹점")
                .progressCode(ProgressCode.AVAILABLE)
                .maxSmsCount(2)
                .build());

        // 결제정보
        payment = paymentRepository.save(Payment.builder()
                .merchant(merchant)
                .statusCode(StatusCode.CERT_READY)
                .carrierName(CarrierName.KT)
                .payAmount(24000L)
                .phone("01012344885")
                .merchantTrxid(UUID.randomUUID().toString().substring(0,13))
                .merchantReqDt(LocalDateTime.now())
                .build());
    }
    @Test
    void 통신사응답정보_저장하기() {
        //given
        MobileCarrier mobileCarrier = mobileCarrierRepository.save(MobileCarrier.builder()
                .payment(payment)
                .carrierName(CarrierName.SK)
                .carrierTrxid("SK_TEST_"+ System.currentTimeMillis())
                .carrierReturnCode("0")
                .carrierReturnMsg("성공")
                .limitAmount(250000L)
                .build());

        //when
        MobileCarrier findMobileCarr = mobileCarrierRepository.findById(mobileCarrier.getId())
                .orElseThrow(IllegalArgumentException::new);

        //then
        log.info("통신사응답정보_저장하기 = {}", findMobileCarr.toString());

        assertEquals(payment, findMobileCarr.getPayment());
        assertEquals(mobileCarrier.getCarrierTrxid(), findMobileCarr.getCarrierTrxid());
    }

    @Test
    void 통신사응답정보_업데이트() {
        //given
        MobileCarrier mobileCarrier = mobileCarrierRepository.save(MobileCarrier.builder()
                .payment(payment)
                .carrierName(CarrierName.SK)
                .carrierTrxid("SK_TEST1_"+ System.currentTimeMillis())
                .carrierReturnCode("0")
                .carrierReturnMsg("성공")
                .limitAmount(250000L)
                .build());
        //payment와 mobileCarrier 연관관계
        payment.setMobileCarrier(mobileCarrier);
        paymentRepository.save(payment);

        //when
        MobileCarrier extractMobileCarrier= payment.getMobileCarrier();
        extractMobileCarrier.updateResult("SK_TEST2_"+ System.currentTimeMillis(), "-1", "실패");
        mobileCarrierRepository.save(extractMobileCarrier);

        MobileCarrier findMobileCarrier = mobileCarrierRepository.findById(mobileCarrier.getId())
                .orElseThrow(IllegalArgumentException::new);

        //then
        log.info("통신사응답정보_업데이트 [findMobileCarrier] = {}", findMobileCarrier.toString());

        assertEquals(payment.getMobileCarrier(), findMobileCarrier);
    }
}