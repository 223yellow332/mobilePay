package com.calmdown.mobilePay.domain.pay.application;

import com.calmdown.mobilePay.domain.merchant.application.MerchantService;
import com.calmdown.mobilePay.domain.merchant.entity.Merchant;
import com.calmdown.mobilePay.domain.pay.StatusCode;
import com.calmdown.mobilePay.domain.pay.dto.*;
import com.calmdown.mobilePay.domain.pay.entity.Payment;
import com.calmdown.mobilePay.global.dto.MobilePayResposeMessage;
import com.calmdown.mobilePay.global.exception.errorCode.CommonErrorCode;
import com.calmdown.mobilePay.global.exception.exception.UserException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Slf4j
@RequiredArgsConstructor
@Service
public class PaymentStatus {

    private final PaymentService paymentService;
    private final MerchantService merchantService;

    /*
     * 인증
     */
    public CertResponseDto cert(CertRequestDto request) throws ParseException {
        log.info("PaymentCertRequestDto => " + request.toString());

        // 가맹점 ID 유효성 검증
        Merchant merchant = merchantService.findById(request.getMerchantId());

        // Payment 객체 생성
        Payment payment = request.toEntity();
        payment.setMerchant(merchant);
        log.info(payment.toString());

        // payment 테이블에 결제 정보 저장
        Payment savePayment = paymentService.save(payment);

        // gw 통신

        // 인증번호 난수 생성

        // Payment 거래 상태 변경

        // sms발송(인증번호 난수 생성)

        return CertResponseDto.builder()
                .transactionId(String.valueOf(savePayment.getId()))
                .limitAmount(10000L)
                .payAmount(savePayment.getPayAmount())
                .build();
    }

    /*
     * SMS 인증번호 확인
     */
    public SmsCheckResponseDto smsCheck(SmsCheckRequestDto request){
        log.info("PaymentCertRequestDto => " + request.toString());

        // 가맹점 ID 유효성 검증
        Merchant merchant = merchantService.findById(request.getMerchantId());

        // Payment 거래 내역 조회 (인증 성공)
        Payment payment = paymentService.checkPaymentIdStatus(request.getTransactionId(), StatusCode.CERT_SUCCESS, merchant);

        // 인증번호 확인
        if(payment.getSmsCheckNumber() != request.smsAuthNumber)
            throw new UserException(CommonErrorCode.SMS_CHECK_NUMBER_MISMATCH);

        return SmsCheckResponseDto.builder()
                .resultCode("0")
                .resultMsg(CommonErrorCode.SUCCESS.getMessage())
                .build();
    }

    /*
     * 승인
     */
    public AuthResponseDto auth(AuthRequestDto request) throws ParseException {
        log.info("PaymentCertRequestDto => " + request.toString());

        // 가맹점 ID 유효성 검증
        Merchant merchant = merchantService.findById(request.getMerchantId());
        
        // Payment 거래 내역 조회

        // gw 통신
        
        // Payment 거래 상태 변경

        return AuthResponseDto.builder()
                .transactionId(request.getMerchantTrxid())
                .limitAmount(10000L)
                .payAmount(request.getAmount())
                .build();
    }

    /*
     * 취소
     */
    public CancelResponseDto cancel(CancelRequestDto request) throws ParseException {
        log.info("PaymentCertRequestDto => " + request.toString());

        // 가맹점 ID 유효성 검증
        Merchant merchant = merchantService.findById(request.getMerchantId());

        // Payment 거래 내역 조회

        // 취소 정보 저장

        // gw 통신

        // Payment 거래 상태 변경

        return CancelResponseDto.builder()
                .transactionId(request.getTransactionId())
                .cancelAmount(request.getAmount())
                .build();
    }
}
