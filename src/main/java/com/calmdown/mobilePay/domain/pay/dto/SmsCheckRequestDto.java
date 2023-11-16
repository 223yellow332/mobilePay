package com.calmdown.mobilePay.domain.pay.dto;

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

    /**
     * Header
     * */
    // 가맹점ID
    @NotNull
    public Long merchantId;

    // 가맹점 주문id
    @NotBlank
    @Length(max = 20)
    public String merchantTrxid;

    /**
     * Body
     * */
    //TRANSACTION_ID
    @NotBlank
    public String transactionId;

    //인증번호
    @NotBlank
    public String smsAuthNumber;

}
