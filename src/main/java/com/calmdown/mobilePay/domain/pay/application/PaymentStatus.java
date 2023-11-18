package com.calmdown.mobilePay.domain.pay.application;

import com.calmdown.mobilePay.domain.merchant.application.MerchantService;
import com.calmdown.mobilePay.domain.merchant.entity.Merchant;
import com.calmdown.mobilePay.domain.model.ResultCode;
import com.calmdown.mobilePay.domain.pay.StatusCode;
import com.calmdown.mobilePay.domain.pay.dto.*;
import com.calmdown.mobilePay.domain.pay.entity.Payment;
import com.calmdown.mobilePay.domain.pay.entity.SmsCheck;
import com.calmdown.mobilePay.global.dto.MobilePayResposeMessage;
import com.calmdown.mobilePay.global.infra.SmsSendUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.message.model.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Random;

@Slf4j
@RequiredArgsConstructor
@Service
public class PaymentStatus {

    private final PaymentService paymentService;
    private final MerchantService merchantService;
    private final SmsCheckService smsCheckService;


    private SmsSendUtil smsSendUtil;

    /*
     * 인증
     */
    public CertResponseDto cert(CertRequestDto request) throws ParseException {
        log.info("PaymentCertRequestDto => " + request.toString());

        // 가맹점 ID 유효성 검증
        Merchant merchant = merchantService.findById(request.getMerchantId());

        // Payment 객체 생성
        Payment payment = request.toEntityCertReq();
        payment.setMerchant(merchant);

        // payment 테이블에 결제 정보 저장
        Payment savePayment = paymentService.save(payment);

        // gw 통신
        String gw ="";

        // Payment 거래 상태 변경
        // gw 통신 성공 or 실패
        if(gw != null){
            payment.setStatusCode(StatusCode.CERT_SUCCESS);
            paymentService.save(payment);
        }else{
            payment.setStatusCode(StatusCode.CERT_FAILURE);
            paymentService.save(payment);
            //예외처리
        }


        String randomNum = createRandomNumber();
        // sms발송(인증번호 난수 생성)
        sendSms(request, randomNum);

        SmsCheck smsCheck = new SmsCheckResponseDto();

        SmsCheck saveSmsSendInfo = smsCheckService.save(smsCheck);


        return CertResponseDto.builder()
                .transactionId(String.valueOf(savePayment.getId()))
                .limitAmount(10000L)
                .payAmount(savePayment.getPayAmount())
                .build();
    }

    /*
     * SMS API test
     * */
    public ResponseEntity<ResultCode> sendSms(CertRequestDto request, String code){
        String phoneNum = request.getPhone();
        String verificationCode = code;

        smsSendUtil.sendOne(phoneNum, verificationCode);

        return ResponseEntity.ok(ResultCode.SUCCESS);
    }

    public String createRandomNumber(){
        Random random = new Random();
        String code = "";

        for(int i=0; i<6; i++){
            String randomNum = Integer.toString(random.nextInt(10));
            code += randomNum;
        }
        return code;
    }


    /*
     * SMS 인증번호 확인
     */
    public SmsCheckResponseDto smsCheck(SmsCheckRequestDto request) throws ParseException {
        log.info("PaymentCertRequestDto => " + request.toString());

        // 가맹점 ID 유효성 검증
        Merchant merchant = merchantService.findById(request.getMerchantId());

        // Payment 거래 내역 조회

        // 인증번호 확인

        return SmsCheckResponseDto.builder()
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
                .payAmount(request.getAuthAmount())
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
                .cancelAmount(request.getCancelAmount())
                .build();
    }
}
