package com.calmdown.mobilePay.global.exception.errorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode{

    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "Invalid parameter included"),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "Resource not exists"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"),
    
    /*
     * 커스텀 에러코드
     */
    SUCCESS(HttpStatus.OK, "성공"),
    INVALID_PAYMENT_ID(HttpStatus.BAD_REQUEST, "사용 불가한 거래번호입니다."),
    LIMIT_AMOUNT_ERROR(HttpStatus.BAD_REQUEST, "한도 초과입니다."),
    MERCHANT_ID_MISMATCH(HttpStatus.BAD_REQUEST, "인증 요청된 가맹점 정보와 다른 요청입니다."),
    SMS_CHECK_NUMBER_OVER_REQUEST(HttpStatus.BAD_REQUEST, "SMS 인증번호 확인 요청 횟수 초과"),
    SMS_CHECK_NUMBER_MISMATCH(HttpStatus.BAD_REQUEST, "SMS 인증번호 불일치입니다."),
    INVALID_AUTH_STATUS_SMS_CHECK(HttpStatus.BAD_REQUEST, "승인요청 전 인증번호 확인이 진행되지않았습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;

}