package com.calmdown.mobilePay.domain.pay.dto;

import com.calmdown.mobilePay.domain.model.ResultCode;
import jakarta.persistence.Column;
import lombok.Getter;

@Getter
public class PaymentCertResponseDto {

    private long merchant_id;
    private long payment_id;

    private String carrierTrxid;
    private String carrierReturnCode;
    private String carrierReturnMsg;
    private Long limitAmount;

}
