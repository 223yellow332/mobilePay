package com.calmdown.mobilePay.domain.pay.application;

import com.calmdown.mobilePay.domain.pay.entity.SmsCheck;
import com.calmdown.mobilePay.domain.pay.repository.SmsCheckRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SmsCheckService {

    private final SmsCheckRepository smsCheckRepository;

    public SmsCheck save(SmsCheck smsCheck){
        return smsCheckRepository.save(smsCheck);
    }

}
