package com.calmdown.mobilePay.domain.pay.application;

import com.calmdown.mobilePay.domain.merchant.application.MerchantService;
import com.calmdown.mobilePay.domain.merchant.entity.Merchant;
import com.calmdown.mobilePay.domain.pay.dto.CertificationRequest;
import com.calmdown.mobilePay.domain.pay.dto.CertificationResponse;
import com.calmdown.mobilePay.domain.pay.entity.AuthStatus;
import com.calmdown.mobilePay.domain.pay.entity.CarrierName;
import com.calmdown.mobilePay.domain.pay.entity.Payment;
import com.calmdown.mobilePay.domain.pay.entity.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@RequiredArgsConstructor
@Service
public class PaymentStatus {

    private final PaymentService paymentService;
    private final MerchantService merchantService;

    public CertificationResponse cert(CertificationRequest request) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");

        // 가맹점 ID 유효성 검증
        Merchant merchant = merchantService.findById(request.getMeerchantId());

        // payment 테이블에 결제 정보 저장
        Payment savePayment = paymentService.save(Payment.builder()
                .merchant(merchant)
                .authStatus(AuthStatus.CERT_READY)
                .carrierName(CarrierName.valueOfStr(request.mobileCarrier))
                .payAmount(request.payAmount)
                .phone(request.phone)
                .userInfo(UserInfo.builder()
                        .userName(request.userName)
                        .socialNumber(request.socialNumber)
                        .gender(request.gender)
                        .email(request.email).build())
                .merchantTrxid(request.merchantTrxid)
                .merchantReqDt(formatter.parse(request.getRequestDatetime()))
                .build());

        return CertificationResponse.builder()
                .transactionId(String.valueOf(savePayment.getId()))
                .limitAmount(10000L)
                .payAmount(savePayment.getPayAmount())
                .build();
    }
}
