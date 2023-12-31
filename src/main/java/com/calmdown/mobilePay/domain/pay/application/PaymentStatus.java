package com.calmdown.mobilePay.domain.pay.application;

import com.calmdown.mobilePay.domain.merchant.application.MerchantService;
import com.calmdown.mobilePay.domain.merchant.entity.Merchant;

import com.calmdown.mobilePay.domain.pay.StatusCode;
import com.calmdown.mobilePay.domain.pay.dto.*;
import com.calmdown.mobilePay.domain.pay.entity.Cancel;
import com.calmdown.mobilePay.domain.pay.entity.MobileCarrier;
import com.calmdown.mobilePay.domain.pay.entity.Payment;
import com.calmdown.mobilePay.domain.pay.entity.SmsCheck;
import com.calmdown.mobilePay.global.infra.sms.SmsSendUtil;
import com.calmdown.mobilePay.global.exception.errorCode.CommonErrorCode;
import com.calmdown.mobilePay.global.exception.exception.UserException;

import com.calmdown.mobilePay.global.infra.simpleGw.SimpleGwService;
import com.calmdown.mobilePay.global.infra.simpleGw.dto.GatewayResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Random;

import static com.calmdown.mobilePay.global.exception.errorCode.CommonErrorCode.INVALID_STATUS_CODE;

@Slf4j
@RequiredArgsConstructor
@Service
public class PaymentStatus {

    private final PaymentService paymentService;
    private final MerchantService merchantService;
    private final MobileCarrierService mobileCarrierService;
    private final SmsCheckService smsCheckService;
    private final CancelService cancelService;

    private final SmsSendUtil smsSendUtil;
    private final SimpleGwService simpleGwService;

    /**
     * 인증
     */
    public CertResponseDto cert(CertRequestDto request) throws Exception {
        log.info("PaymentCertRequestDto => " + request.toString());

        // 가맹점 ID 유효성 검증
        Merchant merchant = merchantService.findById(request.getMerchantId());

        // Payment 객체 생성
        Payment payment = request.toEntityCertReq(merchant);

        // payment 테이블에 결제 정보 저장
        Payment savePayment = paymentService.save(payment);

        // gw 통신
        GatewayResponse gwResponse = simpleGwService.cert(savePayment);

        // gw 통신 결과 저장과 Payment 거래 상태 변경
        MobileCarrier mobileCarrier = mobileCarrierService.save(gwResponse.toEntity(payment));
        paymentService.saveMobileResponse(savePayment, mobileCarrier);

        if(CommonErrorCode.SUCCESS.getResultCode().equals(mobileCarrier.getCarrierReturnCode())) {
            // SMS 전송 / 인증번호 난수 생성 후 SMS 전송 Api 호출 (SMS API 전송 성공 코드:2000)
            String randomNum = createRandomNumber();
            sendSms(request, randomNum);

            //SMS 인증번호 Save
            payment.updateSmsCheckNumber(randomNum);
            paymentService.save(payment);
        }

        return CertResponseDto.builder()
                .paymentId(String.valueOf(savePayment.getId()))
                .payAmount(savePayment.getPayAmount())
                .limitAmount(gwResponse.getLimitAmount())
                .resultCode(gwResponse.getResultCode())
                .resultMessage(gwResponse.getResultMessage())
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

    /**
     * SMS 인증번호 확인
     */
    public SmsCheckResponseDto smsCheck(SmsCheckRequestDto request) {
        CommonErrorCode errorCode;

        // 가맹점 ID 유효성 검증
        Merchant merchant = merchantService.findById(Long.valueOf(request.getMerchantId()));

        // Payment 거래 내역 조회 (인증 성공)

        Payment payment = paymentService.checkPaymentIdStatus(Long.valueOf(request.getPaymentId()),
                                                                StatusCode.CERT_SUCCESS, merchant);

        // 인증번호 확인 최대 횟수 초과했는지 체크
        if (merchant.getMaxSmsCount() <= payment.getSmsChecks().size()) {
            throw new UserException(CommonErrorCode.SMS_CHECK_NUMBER_OVER_REQUEST);
        }

        // 인증 가능 시간 확인 : 3분이하만 허용
        Duration duration = Duration.between(payment.getModifiedDateTime(), LocalDateTime.now());
        long seconds = duration.getSeconds();
        log.info("SMS 인증번호 확인 진행 시간 = {}", seconds);
        if(seconds > 180) {
            throw new UserException(CommonErrorCode.SMS_CHECK_NUMBER_OVER_REQUEST);
        }

        // 인증번호 확인
        log.info("[{}] PaymentSmsCheck 인증 번호 {}, 요청 인증 번호 {}", request.getPaymentId()
                , payment.getSmsCheckNumber(), request.getSmsCheckNumber());

        // SMS 인증번호 확인 내역 저장
        SmsCheck smsCheck = smsCheckService.save(request, payment);

        if (StatusCode.SMS_CHECK_SUCCESS.equals(smsCheck.getStatusCode()))
            errorCode = CommonErrorCode.SUCCESS;
        else
            errorCode = CommonErrorCode.SMS_CHECK_NUMBER_MISMATCH;

        return SmsCheckResponseDto.builder()
                .paymentId(String.valueOf(payment.getId()))
                .resultCode(errorCode.getResultCode())
                .resultMessage(errorCode.getMessage())
                .build();
    }

    /**
     * 승인
     */
    public AuthResponseDto auth(AuthRequestDto request) {

        // 가맹점 ID 유효성 검증
        Merchant merchant = merchantService.findById(Long.valueOf(request.getMerchantId()));
        
        // Payment 거래 내역 조회
        Payment payment = paymentService.checkPaymentIdStatus(Long.valueOf(request.getPaymentId()),
                StatusCode.CERT_SUCCESS, merchant);

        // Payment 거래 상태 변경 (승인대기)
        paymentService.updateStatus(payment, StatusCode.AUTH_READY);

        // SMS 인증번호 완료된 거래만 결제 가능
        StatusCode smCheckResult = payment.getSmsChecks().stream()
                .map(SmsCheck::getStatusCode)
                .filter(x -> x.equals(StatusCode.SMS_CHECK_SUCCESS))
                .findFirst()
                .orElseThrow(() -> new UserException(CommonErrorCode.INVALID_AUTH_STATUS_SMS_CHECK));

        // gw 통신
        GatewayResponse gwResponse = simpleGwService.auth(payment);
        
        // Payment 거래 상태 변경
        paymentService.updateMobileResponse(payment, gwResponse);
        mobileCarrierService.updateStatus(payment.getMobileCarrier(), gwResponse);

        AuthResponseDto response = AuthResponseDto.builder()
                .paymentId(String.valueOf(payment.getId()))
                .payAmount(payment.getPayAmount())
                .resultMessage(gwResponse.getResultMessage())
                .limitAmount(gwResponse.getLimitAmount())
                .resultCode(gwResponse.getResultCode())
                .build()
                ;

        return response;
    }

    /**
     * 취소
     */
    public CancelResponseDto cancel(CancelRequestDto request) {
        log.info("PaymentCancelRequestDto => " + request.toString());

        // 가맹점 ID 유효성 검증
        Merchant merchant = merchantService.findById(Long.valueOf(request.getPaymentId()));

        // Payment 승인성공(AUTH_SUCCESS) 거래 내역 조회
        Payment payment = paymentService.checkPaymentIdStatus(Long.valueOf(request.getPaymentId()), StatusCode.AUTH_SUCCESS, merchant);

        //기취소 여부 확인(CANCEL_SUCCESS)
        if(cancelService.findCancelStatusById(Long.valueOf(request.getPaymentId())) == StatusCode.CANCEL_SUCCESS)
            throw new UserException(CommonErrorCode.INVALID_CANCEL_STATUS);

        // 취소 요청 -->
        if(payment.getPayAmount()>request.getCancelAmount())
            throw new UserException(CommonErrorCode.INVALID_CANCEL_AMOUNT);

        Cancel cancel = request.toEntity(payment);
        Cancel saveCancel = cancelService.save(cancel);

        // gw 통신 및 결과 UPDATE
        GatewayResponse gwResponse = simpleGwService.cancel(payment);
//        MobileCarrier mobileCarrier = mobileCarrierService.save(gwResponse.toEntity());

        // 취소 승인 -->
        cancelService.updateCancelStatus(saveCancel, payment, gwResponse);

        return CancelResponseDto.builder()
                .paymentId(request.getPaymentId())
                .cancelAmount(request.getCancelAmount())
                .build();
    }

    /**
     * 거래상태 조회
     */
    public String searchPaymentId(String merchantId, String paymentId, String statusCode) {
        String result = "";
        try {
            // 가맹점 ID 유효성 검증
            Merchant merchant = merchantService.findById(Long.valueOf(merchantId));

            // 거래상태 코드 유효성 검증
            StatusCode sCode = Arrays.stream(StatusCode.values()).filter(s -> s.toString().equalsIgnoreCase(statusCode))
                    .findFirst()
                    .orElseThrow(() -> new UserException(INVALID_STATUS_CODE));

            // Payment 거래 내역 조회
            paymentService.checkPaymentIdStatus(
                    Long.valueOf(paymentId), sCode, merchant
            );

        } catch (NumberFormatException e) {
            StackTraceElement[] stackTrace = e.getStackTrace();
            log.error("{} \n \t {}", e.getMessage(), stackTrace[0]);

            return "merchantId/paymentId 형식 오류입니다.";
        } catch (UserException e) {
            StackTraceElement[] stackTrace = e.getStackTrace();
            log.error("{} \n \t {}", e.getMessage(), stackTrace[0]);

            return e.getMessage();
        } catch (Exception e) {
            StackTraceElement[] stackTrace = e.getStackTrace();
            log.error("{} \n \t {}", e.getMessage(), stackTrace[0]);

            return "거래조회에 실패했습니다.";
        }

        return "성공";
    }
}
