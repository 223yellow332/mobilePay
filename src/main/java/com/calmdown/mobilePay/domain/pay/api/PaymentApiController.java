package com.calmdown.mobilePay.domain.pay.api;

import com.calmdown.mobilePay.domain.model.ResultCode;
import com.calmdown.mobilePay.domain.pay.application.PaymentService;
import com.calmdown.mobilePay.domain.pay.dto.PaymentCertRequestDto;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

//@JsonAutoDetect
@RequiredArgsConstructor
@RestController
public class PaymentApiController {

    private final PaymentService paymentService;

    @PostMapping("/api/cert")
    public ResultCode cert(@RequestBody PaymentCertRequestDto paymentCertRequestDto){
        paymentService.cert(paymentCertRequestDto);
        return ResultCode.SUCCESS;
    }




}
