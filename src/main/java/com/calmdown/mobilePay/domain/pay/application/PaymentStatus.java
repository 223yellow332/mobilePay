package com.calmdown.mobilePay.domain.pay.application;

import com.calmdown.mobilePay.domain.merchant.application.MerchantService;
import com.calmdown.mobilePay.domain.merchant.entity.Merchant;
import com.calmdown.mobilePay.domain.model.ResultCode;

import com.calmdown.mobilePay.domain.pay.StatusCode;
import com.calmdown.mobilePay.domain.pay.dto.*;
import com.calmdown.mobilePay.domain.pay.entity.Cancel;
import com.calmdown.mobilePay.domain.pay.entity.Payment;
import com.calmdown.mobilePay.domain.pay.entity.SmsCheck;
import com.calmdown.mobilePay.global.dto.MobilePayResposeMessage;
import com.calmdown.mobilePay.global.infra.SmsSendUtil;
import com.calmdown.mobilePay.global.exception.errorCode.CommonErrorCode;
import com.calmdown.mobilePay.global.exception.exception.UserException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
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
    private final CancelService cancelService;



    private final SmsSendUtil smsSendUtil;

    /*
     * 인증
     */
    public CertResponseDto cert(CertRequestDto request) throws Exception {
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
            throw new UserException(CommonErrorCode.MOBILE_CARRIER_SERVER_ERROR);
        }

        // SMS 전송 / 인증번호 난수 생성 후 SMS 전송 Api 호출 (SMS API 전송 성공 코드:2000)
        String randomNum = createRandomNumber();
        sendSms(request, randomNum);

        //SMS 인증번호 Save
        payment.setSmsCheckNumber(randomNum);
        paymentService.save(payment);

        return CertResponseDto.builder()
                .transactionId(String.valueOf(savePayment.getId()))
                .limitAmount(10000L)
                .payAmount(savePayment.getPayAmount())
                .resultCode(String.valueOf(ResultCode.SUCCESS))
                .resultMsg(CommonErrorCode.SUCCESS.getMessage())
                .build();
    }

    /*
     * SMS API
     * */
    public String sendSms(CertRequestDto request, String code){
        String phoneNum = request.getPhone();
        String verificationCode = code;

        SingleMessageSentResponse smsApiResponse = smsSendUtil.sendOne(phoneNum, verificationCode);

        return smsApiResponse.getStatusCode();
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
    public SmsCheckResponseDto smsCheck(SmsCheckRequestDto request){
        log.info("[{}] PaymentSmsCheck => {}", request.getTransactionId(), request.toString());

        // 가맹점 ID 유효성 검증
        Merchant merchant = merchantService.findById(Long.valueOf(request.getMerchantId()));

        // Payment 거래 내역 조회 (인증 성공)
        Payment payment = paymentService.checkPaymentIdStatus(Long.valueOf(request.getTransactionId()), StatusCode.CERT_SUCCESS, merchant);

        log.info("[{}] PaymentSmsCheck 최대 인증 횟수 {}, 이전 인증 횟수 {}", request.getTransactionId(),
                    merchant.getMaxSmsCount(), payment.getSmsChecks().size());
        // 인증번호 확인 최대 횟수 초과했는지 체크
        if(merchant.getMaxSmsCount() <= payment.getSmsChecks().size()) {
            throw new UserException(CommonErrorCode.SMS_CHECK_NUMBER_OVER_REQUEST);
        }

        // 인증번호 확인
        SmsCheck smsCheck = smsCheckService.save(request, payment);
        if(smsCheck.getSmsCheckStatus().equals(StatusCode.SMS_CHECK_FAILURE)) {
            log.info("[{}] PaymentSmsCheck 인증 번호 {}, 요청 인증 번호 {}", request.getTransactionId(),
                    payment.getSmsCheckNumber(), request.getSmsCheckNumber());
            throw new UserException(CommonErrorCode.SMS_CHECK_NUMBER_MISMATCH);
        }

        SmsCheckResponseDto response = SmsCheckResponseDto.builder()
                .resultCode(CommonErrorCode.SUCCESS.name())
                .resultMsg(CommonErrorCode.SUCCESS.getMessage())
                .build();

        log.info("[{}] PaymentSmsCheck => {}", request.getTransactionId(), response.toString());
        return response;
    }

    /*
     * 승인
     */
    public AuthResponseDto auth(AuthRequestDto request) throws ParseException {
        log.info("[{}] PaymentAuth => {}", request.getTransactionId(), request.toString());

        // 가맹점 ID 유효성 검증
        Merchant merchant = merchantService.findById(Long.valueOf(request.getMerchantId()));

        // Payment 거래 내역 조회 (인증 성공)
        Payment payment = paymentService.checkPaymentIdStatus(Long.valueOf(request.getTransactionId()), StatusCode.CERT_SUCCESS, merchant);

        // SMS 인증번호 완료된 거래만 결제 가능
        StatusCode smCheckResult = payment.getSmsChecks().stream()
                .map(SmsCheck::getSmsCheckStatus)
                .filter(x -> x.equals(StatusCode.SMS_CHECK_SUCCESS))
                .findFirst()
                .orElseThrow(() -> new UserException(CommonErrorCode.INVALID_AUTH_STATUS_SMS_CHECK));

        // gw 통신
        
        // Payment 거래 상태 변경
        payment.setStatusCode(StatusCode.AUTH_SUCCESS);
        paymentService.save(payment);

        AuthResponseDto response = AuthResponseDto.builder()
                .resultCode(CommonErrorCode.SUCCESS.name())
                .resultMsg(CommonErrorCode.SUCCESS.getMessage())
                .transactionId(request.getTransactionId())
                .limitAmount(10000L)
                .payAmount(request.getAmount())
                .build()
                ;

        log.info("[{}] PaymentAuth => {}", request.getTransactionId(), response.toString());
        return response;
    }

    /*
     * 취소
     */
    public CancelResponseDto cancel(CancelRequestDto request) {
        log.info("PaymentCertRequestDto => " + request.toString());

        // 가맹점 ID 유효성 검증
        Merchant merchant = merchantService.findById(request.getMerchantId());

        // Payment 승인성공(AUTH_SUCCESS) 거래 내역 조회
        Payment payment = paymentService.checkPaymentIdStatus(Long.valueOf(request.getTransactionId()), StatusCode.AUTH_SUCCESS, merchant);

        //기취소 여부 확인(CANCEL_SUCCESS)
        if(cancelService.findCancelStatusById(Long.valueOf(request.getTransactionId())) == StatusCode.CANCEL_SUCCESS)
            throw new UserException(CommonErrorCode.INVALID_CANCEL_STATUS);

        // 취소 정보 저장 (CANCEL_READY)
        Cancel cancel = request.toEntity(payment);
        cancelService.save(cancel);

        // gw 통신
        String gw ="";

        // 사용 가능 한도액 (한도++)
        // Cancel 테이블 거래 상태 변경 (CANCEL_SUCCESS or CANCEL_FAILURE)
        cancel.setStatusCode(StatusCode.CANCEL_SUCCESS);
        cancelService.save(cancel);

        return CancelResponseDto.builder()
                .transactionId(request.getTransactionId())
                .cancelAmount(request.getCancelAmount())
                .build();
    }
}
