package com.calmdown.mobilePay.domain.pay.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@NoArgsConstructor
@Getter
@ToString(callSuper = true)
@SuperBuilder
public class AuthRequestDto {

    // 가맹점ID
    @NotBlank
    @Pattern(regexp = "^[0-9]*$", message = "가맹점ID 형식이 잘못되었습니다.")
    public String merchantId;

    //TRANSACTION_ID
    @NotBlank
    @Pattern(regexp = "^[0-9]*$", message = "결제ID 형식이 잘못되었습니다.")
    public String transactionId;

    //결제금액
    @Positive(message = "결제금액은 1원 이상부터 가능합니다.")
    @Max(1000000)
    public Long amount;
}
