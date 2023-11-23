package com.calmdown.mobilePay.domain.dordy.repository;

import com.calmdown.mobilePay.domain.merchant.entity.Merchant;
import com.calmdown.mobilePay.domain.merchant.entity.ProgressCode;
import com.calmdown.mobilePay.domain.pay.StatusCode;
import com.calmdown.mobilePay.domain.pay.application.PaymentService;
import com.calmdown.mobilePay.domain.pay.entity.CarrierName;
import com.calmdown.mobilePay.domain.pay.entity.MobileCarrier;
import com.calmdown.mobilePay.domain.pay.entity.Payment;
import com.calmdown.mobilePay.domain.pay.entity.UserInfo;
import com.calmdown.mobilePay.domain.pay.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;

import java.util.Date;

@Transactional
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@Commit
public class PaymentRepositoryUnitTest {

    @Autowired
    private PaymentRepository paymentRepository;


    @DisplayName("CERT_READY 결제 정보 저장")
    @Test
    void cert_test(){
        //given
        Merchant merchant = Merchant.builder()
                .merchantName("인증테스트가맹점")
                .progressCode(ProgressCode.AVAILABLE)
                .maxSmsCount(2)
                .merchantName("online")
                .build();

        Payment payment = Payment.certReqBuilder()
                //.merchant(merchant.getId())
                .carrierName(CarrierName.KT)
                .statusCode(StatusCode.CERT_READY)
                .merchantReqDt(new Date())
                .payAmount(999)
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
        System.out.println("HI");


    }



}
