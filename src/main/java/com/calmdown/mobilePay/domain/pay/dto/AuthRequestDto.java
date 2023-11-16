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

@NoArgsConstructor
@Getter
@ToString(callSuper = true)
@SuperBuilder
public class AuthRequestDto {

    // 가맹점ID
    @NotNull
    public Long merchantId;

    // 가맹점 주문id
    @NotBlank
    @Length(max = 20)
    public String merchantTrxid;

    //결제금액
    @NotBlank
    @Length(max = 10)
    public Long amount;
}
