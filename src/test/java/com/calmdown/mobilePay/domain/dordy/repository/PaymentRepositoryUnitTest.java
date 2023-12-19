package com.calmdown.mobilePay.domain.dordy.repository;

import com.calmdown.mobilePay.domain.merchant.entity.Merchant;
import com.calmdown.mobilePay.domain.merchant.entity.ProgressCode;
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

import java.util.Date;
import java.util.Optional;

import static java.util.Optional.*;
import static org.junit.Assert.assertEquals;

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
    private CancelRepository cancelRepository;


    @DisplayName("CERT_READY 결제 정보 저장")
    @Test
    @Order(1)
    void cert_request_test(){

        //given
        Merchant merchant = Merchant.builder()
                .merchantName("인증테스트가맹점")
                .progressCode(ProgressCode.AVAILABLE)
                .maxSmsCount(2)
                .merchantName("online")
                .build();

        Payment payment = Payment.builder()
                .merchant(merchant)
                .carrierName(CarrierName.KT)
                .statusCode(StatusCode.CERT_READY)
                .merchantReqDt(new Date())
                .payAmount(9199)
                .phone("01000000000")
                .userInfo(UserInfo.builder()
                        .userName("CHIM")
                        .socialNumber("870222")
                        .gender("M")
                        .email("chim@naver.com").build())
                .merchantTrxid("9")
                .build();

       /* //when
        paymentRepository.save(payment);

        //then
        assertEquals(1L, payment.getId());*/
    }

    @DisplayName("CERT_SUCCESS 저장")
    @Test
    @Order(2)
    void cert_success_test(){

        //given
        Payment payment = Payment.certReqBuilder()
                //.merchant(merchant.getId())
                .carrierName(CarrierName.KT)
                .statusCode(StatusCode.CERT_READY)
                .merchantReqDt(new Date())
                .payAmount(9199)
                .phone("01000000000")
                .userInfo(UserInfo.builder()
                        .userName("CHIM")
                        .socialNumber("870222")
                        .gender("M")
                        .email("chim@naver.com").build())
                .merchantTrxid("9")
                .build();
/*        paymentRepository.save(payment);
        payment.updateStatus(StatusCode.CERT_SUCCESS);

        //when
        paymentRepository.save(payment);

        //then*/

    }

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
                .merchantReqDt(new Date())
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
