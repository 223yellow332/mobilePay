package com.calmdown.mobilePay.domain.pay.application;

import com.calmdown.mobilePay.domain.pay.StatusCode;
import com.calmdown.mobilePay.domain.pay.dto.SmsCheckRequestDto;
import com.calmdown.mobilePay.domain.pay.entity.Payment;
import com.calmdown.mobilePay.domain.pay.entity.SmsCheck;
import com.calmdown.mobilePay.domain.pay.repository.SmsCheckRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class SmsCheckService {

    private final SmsCheckRepository smsCheckRepository;

    @Transactional
    public SmsCheck save(SmsCheckRequestDto request, Payment payment) {
        StatusCode smsCheckStatus;

        // 인증번호 확인
        if(payment.getSmsCheckNumber().equals(request.getSmsCheckNumber()))
            smsCheckStatus = StatusCode.SMS_CHECK_SUCCESS;
        else
            smsCheckStatus = StatusCode.SMS_CHECK_FAILURE;

        SmsCheck saveSmsCheck = smsCheckRepository.save(request.toEntity(payment, smsCheckStatus));
        log.info(saveSmsCheck.toString());

        return saveSmsCheck;
    }

    public SmsCheck saveSmsInfo(SmsCheck smsCheck) {
        return smsCheckRepository.save(smsCheck);

    }
}
