package com.calmdown.mobilePay.domain.pay.dto;

import com.calmdown.mobilePay.domain.pay.StatusCode;
import com.calmdown.mobilePay.domain.pay.entity.SmsCheck;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@NoArgsConstructor
@ToString(callSuper = true)
@SuperBuilder
public class SmsCheckRequestDto {

    // 가맹점ID
    @NotNull
    public Long merchantId;

    //TRANSACTION_ID
    @NotNull
    public Long transactionId;

    //인증번호
    @NotNull
    public Long smsAuthNumber;

/*    //인증번호 [String]
    @NotBlank
    public String smsAuthNumber;*/

    public SmsCheck toEntitySendSmsReq(){
        return SmsCheck.smsSendingBuilder()
                .statusCode(StatusCode.SMS_CHECK_SUCCESS)
                .smsAuthNumber(smsAuthNumber)
                .build();
    }

}
