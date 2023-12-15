package com.calmdown.mobilePay.global.exception.errorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode{

    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "999", "Invalid parameter included"),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "999", "Resource not exists"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "999", "Internal server error"),
    
    /*
     * 커스텀 에러코드
     * -1 ~ 30
     * 0은 성공 그외 실패
     */
    SUCCESS(HttpStatus.OK, "0", "성공"),
    INVALID_PAYMENT_ID(HttpStatus.BAD_REQUEST, "-1", "사용 불가한 거래번호입니다."),
//    LIMIT_AMOUNT_ERROR(HttpStatus.BAD_REQUEST, "한도 초과입니다."),
    MERCHANT_ID_MISMATCH(HttpStatus.BAD_REQUEST, "1", "인증 요청된 가맹점 정보와 다른 요청입니다."),

    /*
     * SMS 인증번호 확인
     * 30 ~ 50
     */
    SMS_CHECK_NUMBER_OVER_REQUEST(HttpStatus.BAD_REQUEST, "31", "SMS 인증번호 확인 요청 횟수 초과"),
    SMS_CHECK_NUMBER_MISMATCH(HttpStatus.BAD_REQUEST, "32", "SMS 인증번호 불일치입니다."),
    SMS_CHECK_NUMBER_TIME_OVER(HttpStatus.BAD_REQUEST, "33", "SMS 인증번호 확인시간 초과입니다."),

    /*
     * 승인
     * 100 ~ 199
     */
    INVALID_AUTH_STATUS_SMS_CHECK(HttpStatus.BAD_REQUEST, "100", "승인요청 전 인증번호 확인이 진행되지않았습니다."),

    /*
     * 취소
     * 200 ~ 299
     */
    INVALID_CANCEL_STATUS(HttpStatus.BAD_REQUEST, "200", "취소 가능한 상태가 아닙니다."),
    INVALID_CANCEL_AMOUNT(HttpStatus.BAD_REQUEST, "201", "취소 요청 금액이 승인 금액 보다 클 수 없습니다."),

    /*
     * SMS 전송
     * 300 ~ 399
     */
    SMS_API_SERVER_ERROR(HttpStatus.BAD_REQUEST, "300", "SMS 전송에 실패했습니다."),
    MOBILE_CARRIER_SERVER_ERROR(HttpStatus.BAD_REQUEST, "301", "통신사 응답을 받지 못했습니다."),

    /*
     * 가맹점 계약
     * 400 ~ 499
     */
    INVALID_MERCHANT_CONTRACT(HttpStatus.BAD_REQUEST, "400", "계약 중인 가맹점이 아닙니다."),

    /*
     * 거래내역 조회
     * 500 ~ 599
     */
    INVALID_STATUS_CODE(HttpStatus.BAD_REQUEST, "400", "잘못된 형식의 거래상태 요청입니다."),

    ;

    private final HttpStatus httpStatus;
    private final String resultCode;
    private final String message;

}