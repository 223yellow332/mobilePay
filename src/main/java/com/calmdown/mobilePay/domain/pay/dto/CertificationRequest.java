package com.calmdown.mobilePay.domain.pay.dto;

import com.calmdown.mobilePay.global.dto.MobilePayMessageHeader;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

@NoArgsConstructor
@Getter
@ToString(callSuper = true)
@SuperBuilder
public class CertificationRequest extends MobilePayMessageHeader {

    /*
    바디	휴대전화번호	not null	size max = 11	
	통신사	not null		SK, KT, LGU
	생년월일	not null	size = 6	
	성별	not null	F or M	
	이름		size max = 20	
	email 		size max = 50	
	결제금액	not null	length max = 10	
	상품명		size max = 100	
     */
    @NotBlank
    @Length(max = 11, message = "전화번호 길이 오류")
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

    @Length(max = 20)
    //이름
    public String userName;

    @Length(max = 126)
    //email
    public String email;

    @Min(100l)
    @Max(1000000l)
    //결제금액
    public Long payAmount;

    @Length(max = 126)
    //상품명
    public String itemName;
}
