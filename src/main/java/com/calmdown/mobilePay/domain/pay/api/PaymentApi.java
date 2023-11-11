package com.calmdown.mobilePay.domain.pay.api;

import com.calmdown.mobilePay.domain.pay.application.PaymentService;
import com.calmdown.mobilePay.domain.pay.application.PaymentStatus;
import com.calmdown.mobilePay.domain.pay.dto.CertificationRequest;
import com.calmdown.mobilePay.domain.pay.dto.CertificationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RequiredArgsConstructor
@RestController
public class PaymentApi {

    private final PaymentStatus paymentStatus;

    @PostMapping("/payment/cert")
    public ResponseEntity<CertificationResponse> cert(@RequestBody CertificationRequest certRequest) throws ParseException {
        return new ResponseEntity<>(paymentStatus.cert(certRequest), HttpStatus.OK);
    }
}
