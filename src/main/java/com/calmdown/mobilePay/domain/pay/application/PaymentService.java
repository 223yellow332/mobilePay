package com.calmdown.mobilePay.domain.pay.application;

import com.calmdown.mobilePay.domain.merchant.entity.Merchant;
import com.calmdown.mobilePay.domain.model.ResultCode;
import com.calmdown.mobilePay.domain.pay.StatusCode;
import com.calmdown.mobilePay.domain.pay.entity.Cancel;
import com.calmdown.mobilePay.domain.pay.entity.MobileCarrier;
import com.calmdown.mobilePay.domain.pay.entity.Payment;
import com.calmdown.mobilePay.domain.pay.repository.PaymentRepository;
import com.calmdown.mobilePay.global.exception.errorCode.CommonErrorCode;
import com.calmdown.mobilePay.global.exception.exception.UserException;
import com.calmdown.mobilePay.global.infra.simpleGw.dto.GatewayResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Cache;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Transactional
    public Payment save(Payment payment) {
        return paymentRepository.save(payment);
    }

    public Payment checkPaymentIdStatus(Long id, StatusCode statusCode, Merchant merchant) {
        Payment payment = paymentRepository.findByIdAndStatusCode(id, statusCode)
                .orElseThrow(() -> new UserException(CommonErrorCode.INVALID_PAYMENT_ID));
        
        // 거래정보에 등록된 가맹점 정보와 동일한지 확인
        if(payment.getMerchant().getId() != merchant.getId())
            new UserException(CommonErrorCode.MERCHANT_ID_MISMATCH);
        
        return payment;
    }

    /**
     * 거래내역의 거래 상태값 변경
     * @param payment
     * @param statusCode
     * @return
     */
    @Transactional
    public Payment updateStatus(Payment payment, StatusCode statusCode) {
        payment.updateStatus(statusCode);
        return paymentRepository.save(payment);
    }

    /**
     * gw 통신 성공 or 실패 결과 저장
     * @param payment
     * @param mobileCarrier
     * @return
     */
    @Transactional
    public Payment saveMobileResponse(Payment payment, MobileCarrier mobileCarrier) {
        if(CommonErrorCode.SUCCESS.getResultCode().equals(mobileCarrier.getCarrierReturnCode()))
            payment.updateStatus(StatusCode.CERT_SUCCESS);
        else
            payment.updateStatus(StatusCode.CERT_FAILURE);
        
        payment.setMobileCarrier(mobileCarrier);

        return paymentRepository.save(payment);
    }

    /**
     * gw 통신 결과 업데이트
     * @param payment
     * @param gwResponse
     * @return
     */
    @Transactional
    public Payment updateMobileResponse(Payment payment, GatewayResponse gwResponse) {
        if(CommonErrorCode.SUCCESS.getResultCode().equals(gwResponse.getResultCode()))
            payment.updateStatus(StatusCode.AUTH_SUCCESS);
        else
            payment.updateStatus(StatusCode.AUTH_FAILURE);

        payment.getMobileCarrier()
                .updateResult(gwResponse.getMobileTrxid(),
                        gwResponse.getResultCode(),
                        gwResponse.getResultMessage());

        return paymentRepository.save(payment);
    }





}
