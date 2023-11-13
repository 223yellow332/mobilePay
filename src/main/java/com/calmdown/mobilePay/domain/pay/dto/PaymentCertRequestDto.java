package com.calmdown.mobilePay.domain.pay.dto;

import com.calmdown.mobilePay.domain.pay.StatusCode;
import com.calmdown.mobilePay.domain.pay.entity.CarrierName;
import com.calmdown.mobilePay.domain.pay.entity.MobileCarrier;
import com.calmdown.mobilePay.domain.pay.entity.Payment;
import com.calmdown.mobilePay.domain.pay.entity.UserInfo;
import jdk.jshell.Snippet;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.swing.text.html.parser.Entity;

@Getter
@NoArgsConstructor
public class PaymentCertRequestDto {

    //가맹점 인증 요청
    private String phone;
    private CarrierName carrierName;
    private UserInfo userInfo;
    private long payAmount;

    @Builder
    public PaymentCertRequestDto(String phone, CarrierName carrierName, UserInfo userInfo, long payAmount){
        this.phone = phone;
        this.carrierName = carrierName;
        this.userInfo = userInfo;
        this.payAmount = payAmount;
    }

    public Payment toEntity(){
        return Payment.certReqBuilder()
                .phone(phone)
                .carrierName(carrierName)
                .userInfo(userInfo)
                .payAmount(payAmount)
                .build();
    }
}
