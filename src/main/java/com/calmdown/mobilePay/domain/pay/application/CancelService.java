package com.calmdown.mobilePay.domain.pay.application;

import com.calmdown.mobilePay.domain.pay.StatusCode;
import com.calmdown.mobilePay.domain.pay.entity.Cancel;
import com.calmdown.mobilePay.domain.pay.repository.CancelRepository;
import com.calmdown.mobilePay.global.exception.errorCode.CommonErrorCode;
import com.calmdown.mobilePay.global.exception.exception.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CancelService {

    private final CancelRepository cancelRepository;

    public Cancel save(Cancel cancel){
        return cancelRepository.save(cancel);
    }

    public StatusCode findCancelStatusById(Long id){
        Cancel cancel = cancelRepository.findById(id)
                .orElseThrow(()-> new UserException(CommonErrorCode.INVALID_CANCEL_STATUS));

        StatusCode statusCode = cancel.getStatusCode();
        return statusCode;
    }

    public boolean existById(Long id){
         if(cancelRepository.existsById(id)){
             throw new UserException(CommonErrorCode.INVALID_CANCEL_STATUS);
         }else
             return true;
    }
}
