package com.calmdown.mobilePay.domain.pay.dto;

import com.calmdown.mobilePay.global.dto.MobilePayMessageHeader;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor
@ToString(callSuper = true)
@SuperBuilder
public class SmsCheckRequestDto extends MobilePayMessageHeader {

    //TRANSACTION_ID
    @NotBlank
    public String transactionId;

    //인증번호
    @NotBlank
    public String smsAuthNumber;

}
