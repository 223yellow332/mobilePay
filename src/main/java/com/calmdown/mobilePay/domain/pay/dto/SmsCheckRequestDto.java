package com.calmdown.mobilePay.domain.pay.dto;

import com.calmdown.mobilePay.domain.pay.StatusCode;
import com.calmdown.mobilePay.domain.pay.entity.Payment;
import com.calmdown.mobilePay.domain.pay.entity.SmsCheck;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor
@ToString
@SuperBuilder
public class SmsCheckRequestDto {

    // 가맹점ID
    @NotBlank
    @Pattern(regexp = "^[0-9]*$", message = "가맹점ID 형식이 잘못되었습니다.")
    public String merchantId;

    //TRANSACTION_ID
    @NotBlank
    @Pattern(regexp = "^[0-9]*$", message = "결제ID 형식이 잘못되었습니다.")
    public String transactionId;

    //인증번호
    @NotBlank
    @Pattern(regexp = "^[0-9]*$", message = "SMS 인증번호 형식이 잘못되었습니다.")
    public String smsCheckNumber;

    public SmsCheck toEntity(Payment payment, StatusCode smsCheckStatus){
        return SmsCheck.builder()
                .payment(payment)
                .smsCheckStatus(smsCheckStatus)
                .smsCheckNumber(smsCheckNumber)
                .build()
                ;
    }

/*  //인증번호
    @NotNull
    public Long smsAuthNumber;*/

    //인증번호 [String]
/*    @NotBlank
    public String smsAuthNumber;*/

    public SmsCheck toEntitySendSmsReq(String smsCheckNumber){
        return SmsCheck.smsSendingBuilder()
                .statusCode(StatusCode.SMS_CHECK_SUCCESS)
                .smsCheckNumber(smsCheckNumber)
                .build();
    }

}
