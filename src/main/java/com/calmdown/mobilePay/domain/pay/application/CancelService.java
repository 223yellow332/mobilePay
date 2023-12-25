package com.calmdown.mobilePay.domain.pay.application;

import com.calmdown.mobilePay.domain.pay.StatusCode;
import com.calmdown.mobilePay.domain.pay.entity.Cancel;
import com.calmdown.mobilePay.domain.pay.entity.MobileCarrier;
import com.calmdown.mobilePay.domain.pay.entity.Payment;
import com.calmdown.mobilePay.domain.pay.repository.CancelRepository;
import com.calmdown.mobilePay.domain.pay.repository.PaymentRepository;
import com.calmdown.mobilePay.global.exception.errorCode.CommonErrorCode;
import com.calmdown.mobilePay.global.exception.exception.UserException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CancelService {

    private final CancelRepository cancelRepository;
    private final PaymentRepository paymentRepository;

    /**
     * 취소 요청 후 취소 정보 저장
     */
    public Cancel save(Cancel cancel){
        return cancelRepository.save(cancel);
    }

    /**
     * 기 취소 여부 확인
     */
    public StatusCode findCancelStatusById(Long id){
        Cancel cancel = cancelRepository.findById(id)
                .orElseThrow(()-> new UserException(CommonErrorCode.INVALID_CANCEL_STATUS));
        StatusCode statusCode = cancel.getStatusCode();
        return statusCode;
    }

    /**
     * 취소 상태 업데이트(CANCEL_SUCCESS)
     * @param cancel
     * @param payment
     * @param mobileCarrier
     * @return
     */
    @Transactional
    public Cancel updateCancelStatus(Cancel cancel, Payment payment, MobileCarrier mobileCarrier) {
        if(CommonErrorCode.SUCCESS.getResultCode().equals(mobileCarrier.getCarrierReturnCode())) {
            cancel.updateStatus(StatusCode.CANCEL_SUCCESS);
            payment.updateStatus(StatusCode.CANCEL_SUCCESS);
        }
        else{
            cancel.updateStatus(StatusCode.CANCEL_FAIL);
            payment.updateStatus(StatusCode.CANCEL_FAIL);
        }
        paymentRepository.save(payment);
        return cancelRepository.save(cancel);
    }

}
