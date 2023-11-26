package com.calmdown.mobilePay.domain.dordy.application;

import com.calmdown.mobilePay.domain.merchant.application.MerchantService;
import com.calmdown.mobilePay.domain.merchant.entity.Merchant;
import com.calmdown.mobilePay.domain.merchant.entity.ProgressCode;
import com.calmdown.mobilePay.domain.pay.StatusCode;
import com.calmdown.mobilePay.domain.pay.application.*;
import com.calmdown.mobilePay.domain.pay.dto.CertRequestDto;
import com.calmdown.mobilePay.domain.pay.entity.CarrierName;
import com.calmdown.mobilePay.domain.pay.entity.MobileCarrier;
import com.calmdown.mobilePay.domain.pay.entity.Payment;
import com.calmdown.mobilePay.domain.pay.entity.UserInfo;
import com.calmdown.mobilePay.domain.pay.repository.PaymentRepository;
import com.calmdown.mobilePay.global.infra.SmsSendUtil;
import com.calmdown.mobilePay.global.infra.simpleGw.SimpleGwService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import java.util.Date;
import java.util.Optional;

import static com.calmdown.mobilePay.domain.pay.entity.CarrierName.KT;
import static org.assertj.core.api.BDDAssumptions.given;

@ExtendWith(MockitoExtension.class)
@Commit
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class PaymentServiceUnitTest {

    @InjectMocks
    private PaymentStatus paymentStatus;
    @Mock
    private PaymentService paymentService;
    @Mock
    private   MerchantService merchantService;
    @Mock
    private   MobileCarrierService mobileCarrierService;
    @Mock
    private   SmsCheckService smsCheckService;
    @Mock
    private SmsSendUtil smsSendUtil;
    @Mock
    private   SimpleGwService simpleGwService;
    @Mock
    private PaymentRepository paymentRepository;

    @Test
    @DisplayName("cert 테스트")
    void cert_test() throws Exception {
        //given
        Merchant merchant = Merchant.builder()
                .merchantName("테스트가맹점")
                .category("1")
                .maxSmsCount(2)
                .progressCode(ProgressCode.AVAILABLE)
                .build();

        CertRequestDto certRequestDto = CertRequestDto.builder()
                .phone("01011111111")
                .merchantId(1L)
                .merchantTrxid("1")
                .mobileCarrier(String.valueOf(KT))
                .payAmount(1231L)
                .build();

        Payment payment = certRequestDto.toEntityCertReq(merchant);
        paymentStatus.cert(certRequestDto);

        //when
        paymentRepository.save(payment);
        System.out.println( paymentRepository.save(payment));

        //then
    }

}
