package com.calmdown.mobilePay.domain.pay.api;

import com.calmdown.mobilePay.domain.pay.application.PaymentStatus;
import com.calmdown.mobilePay.domain.pay.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

//@JsonAutoDetect
@RequiredArgsConstructor
@RestController
public class PaymentApiController {

    private final PaymentStatus paymentStatus;

    /*
     * 인증
     */
    @PostMapping("/api/cert")
    public ResponseEntity<CertResponseDto> cert(@Validated  @RequestBody CertRequestDto certRequest) throws ParseException {
        return new ResponseEntity<>(paymentStatus.cert(certRequest), HttpStatus.OK);
    }

    /*
     * SMS 인증번호 확인
     */
    @PostMapping("/api/smsCheck")
    public ResponseEntity<SmsCheckResponseDto> smsCheck(@RequestBody SmsCheckRequestDto request) throws ParseException {
        return new ResponseEntity<>(paymentStatus.smsCheck(request), HttpStatus.OK);
    }

    /*
     * 승인
     */
    @PostMapping("/api/auth")
    public ResponseEntity<AuthResponseDto> auth(@RequestBody AuthRequestDto request) throws ParseException {
        return new ResponseEntity<>(paymentStatus.auth(request), HttpStatus.OK);
    }

    /*
     * 취소
     */
    @PostMapping("/api/cancel")
    public ResponseEntity<CancelResponseDto> cancel(@RequestBody CancelRequestDto request) throws ParseException {
        return new ResponseEntity<>(paymentStatus.cancel(request), HttpStatus.OK);
    }

}
