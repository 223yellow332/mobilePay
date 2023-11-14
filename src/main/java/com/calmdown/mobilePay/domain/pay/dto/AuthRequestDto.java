package com.calmdown.mobilePay.domain.pay.dto;

import com.calmdown.mobilePay.global.dto.MobilePayMessageHeader;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

@NoArgsConstructor
@Getter
@ToString(callSuper = true)
@SuperBuilder
public class AuthRequestDto extends MobilePayMessageHeader {

    //TRANSACTION_ID
    @NotBlank
    public String transactionId;

    //휴대전화번호
    @NotBlank
    @Length(max = 11)
    public String phone;

    //통신사
    @NotBlank
    public String mobileCarrier;

    //생년월일
    @NotBlank
    @Length(min = 8, max = 8)
    public String socialNumber;

    //성별
    @NotBlank
    @Length(min = 1, max = 1)
    public String gender;

    //결제금액
    @NotBlank
    @Length(max = 10)
    public Long payAmount;
}
