package com.calmdown.mobilePay.domain.dordy.repository;

import com.calmdown.mobilePay.domain.merchant.entity.Merchant;
import com.calmdown.mobilePay.domain.merchant.entity.ProgressCode;
import com.calmdown.mobilePay.domain.merchant.repository.MerchantRepository;
import com.calmdown.mobilePay.domain.pay.StatusCode;
import com.calmdown.mobilePay.domain.pay.application.PaymentService;
import com.calmdown.mobilePay.domain.pay.entity.*;
import com.calmdown.mobilePay.domain.pay.repository.CancelRepository;
import com.calmdown.mobilePay.domain.pay.repository.PaymentRepository;
import com.calmdown.mobilePay.global.exception.errorCode.CommonErrorCode;
import com.calmdown.mobilePay.global.exception.exception.UserException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import static java.util.Optional.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@Transactional
//ANY: 내장DB / NONE: 실제DB
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@Commit
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PaymentRepositoryUnitTest {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private CancelRepository cancelRepository;


    /**
     * 인증
     */
    @DisplayName("결제 정보 저장 성공 테스트")
    @Test
    //@Order(1)
    void cert_save_test(){

        Merchant merchant = Merchant.builder()
                .merchantName("인증테스트가맹점")
                .progressCode(ProgressCode.AVAILABLE)
                .maxSmsCount(2)
                .merchantName("online")
                .build();

        merchantRepository.save(merchant);

        //given
        Payment payment = Payment.builder()
                .merchant(merchant)
                .carrierName(CarrierName.KT)
                .statusCode(StatusCode.CERT_READY)
                .merchantReqDt(LocalDateTime.now())
                .payAmount(9199)
                .phone("01000000000")
                .userInfo(UserInfo.builder()
                        .userName("CHIM")
                        .socialNumber("870222")
                        .gender("M")
                        .email("chim@naver.com").build())
                .merchantTrxid("9")
                .build();

        //when
        paymentRepository.save(payment);

        //then
        assertEquals(1L, payment.getId());
    }

    //ID 와 Status로 Payment 반환
    @DisplayName("결제 이력 조회")
    @Test
    void findByIdAndStatusCode_test(){

        //given
        Merchant merchant = Merchant.builder()
                .merchantName("인증테스트가맹점")
                .progressCode(ProgressCode.AVAILABLE)
                .maxSmsCount(2)
                .merchantName("online")
                .build();

        merchantRepository.save(merchant);

        //given
        Payment payment = Payment.builder()
                .merchant(merchant)
                .carrierName(CarrierName.KT)
                .statusCode(StatusCode.CERT_READY)
                .merchantReqDt(LocalDateTime.now())
                .payAmount(9199)
                .phone("01000000000")
                .userInfo(UserInfo.builder()
                        .userName("CHIM")
                        .socialNumber("870222")
                        .gender("M")
                        .email("chim@naver.com").build())
                .merchantTrxid("9")
                .build();

        //when
        Payment savePayment = paymentRepository.save(payment);
        Optional<Payment> targetPayment = paymentRepository.findByIdAndStatusCode(1L, StatusCode.CERT_READY);

        //then
        assertEquals(savePayment.getPayAmount(), targetPayment.get().getPayAmount());

    }

    /**
     * 취소
     */
    @DisplayName("취소 요청")
    @Test
    @Order(3)
    void cancel_request_test(){

        //given
        Merchant merchant = Merchant.builder()
                .merchantName("인증테스트가맹점")
                .progressCode(ProgressCode.AVAILABLE)
                .maxSmsCount(2)
                .merchantName("online")
                .build();

        Payment payment = Payment.certReqBuilder()
                //.merchantTrxid(merchant)
                .carrierName(CarrierName.KT)
                .statusCode(StatusCode.CERT_READY)
                .merchantReqDt(LocalDateTime.now())
                .payAmount(9199)
                .phone("01000000000")
                .userInfo(UserInfo.builder()
                        .userName("CHIM")
                        .socialNumber("870222")
                        .gender("M")
                        .email("chim@naver.com").build())
                .merchantTrxid("9")
                .build();
        //paymentRepository.save(payment);

/*
        Payment request = (paymentRepository.findById(Long.valueOf(2))
                .orElseThrow(() -> new UserException(CommonErrorCode.INVALID_CANCEL_STATUS)));

        Cancel cancel = Cancel.builder()
                .payment(request)
                .cancelAmount(9999)
                .statusCode(StatusCode.CANCEL_READY)
                .build();

        //when
        cancelRepository.save(cancel);
*/
        //then

    }


}
