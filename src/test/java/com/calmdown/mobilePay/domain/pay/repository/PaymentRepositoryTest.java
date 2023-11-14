package com.calmdown.mobilePay.domain.pay.repository;

import com.calmdown.mobilePay.domain.merchant.entity.Merchant;
import com.calmdown.mobilePay.domain.merchant.entity.ProgressCode;
import com.calmdown.mobilePay.domain.merchant.repository.MerchantRepository;
import com.calmdown.mobilePay.domain.pay.StatusCode;
import com.calmdown.mobilePay.domain.pay.entity.CarrierName;
import com.calmdown.mobilePay.domain.pay.entity.Payment;
import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PaymentRepositoryTest {
    @Autowired
    private MerchantRepository merchantRepository;
    @Autowired
    private PaymentRepository paymentRepository;

    @Test
    @DisplayName("가맹점 연관관계 테스트")
    void saveAndSelect() {
        //given
        Merchant merchant = Merchant.builder()
                            .merchantName("테스트가맹점")
                            .progressCode(ProgressCode.AVAILABLE)
                            .maxSmsCount(2)
                            .merchantName("online")
                            .build();
        merchantRepository.save(merchant);

        Payment payment = paymentRepository.save(Payment.builder()
                        .merchant(merchant)
                        .statusCode(StatusCode.AUTH_READY)
                        .carrierName(CarrierName.KT)
                        .payAmount(24000L)
                        .phone("01012344885")
                        .build());

        //when
        Payment findPayment = paymentRepository.findById(payment.getId())
                .orElseThrow(() -> new IllegalArgumentException());

        //then
        System.out.println("findPayment.toString() = " + findPayment.toString());

        assertThat(findPayment.getMerchant().getMerchantName()).isEqualTo(merchant.getMerchantName());
        assertThat(findPayment.getMerchant()).isEqualTo(merchant);

    }
}

