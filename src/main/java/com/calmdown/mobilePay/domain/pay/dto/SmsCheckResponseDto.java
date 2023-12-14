package com.calmdown.mobilePay.domain.pay.dto;

import com.calmdown.mobilePay.domain.pay.StatusCode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

@Getter
@NoArgsConstructor
@ToString(callSuper = true)
@SuperBuilder
public class SmsCheckResponseDto extends MobilePayCommonRespose {

    //결제 ID
    @NotBlank
    long paymentId;

    //상태코드
    @NotNull
    StatusCode statusCode;

    //인증번호
    @NotBlank
    @Length(max=6)
    String verificationCode;



}
