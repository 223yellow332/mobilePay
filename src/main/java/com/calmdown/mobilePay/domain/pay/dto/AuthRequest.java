package com.calmdown.mobilePay.domain.pay.dto;

import com.calmdown.mobilePay.global.dto.MobilePayMessageHeader;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public class AuthRequest extends MobilePayMessageHeader {

    /*
바디	TRANSACTION_ID	not null		INT
	휴대전화번호	not null	size max = 11
	통신사	not null		SKT, KT, LGU
	생년월일	not null	length = 6
	성별	not null	F or M
	결제금액	not null	length max = 10
     */
    @NotBlank
    //TRANSACTION_ID
    public String transactionId;

    @NotBlank
    @Length(max = 11)
    //휴대전화번호
    public String phone;

    @NotBlank
    //통신사
    public String mobileCarrier;

    @NotBlank
    @Length(max = 8)
    //생년월일
    public String socialNumber;

    @NotBlank
    @Length(min = 1, max = 1)
    //성별
    public String gender;

    @NotBlank
    @Length(max = 10)
    //결제금액
    public Long payAmount;
}
