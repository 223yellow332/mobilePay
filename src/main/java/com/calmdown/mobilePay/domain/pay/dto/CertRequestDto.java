package com.calmdown.mobilePay.domain.pay.dto;

import com.calmdown.mobilePay.domain.pay.StatusCode;
import com.calmdown.mobilePay.domain.pay.entity.CarrierName;
import com.calmdown.mobilePay.domain.pay.entity.Payment;
import com.calmdown.mobilePay.domain.pay.entity.UserInfo;
import com.calmdown.mobilePay.global.dto.MobilePayMessageHeader;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

/*
 * 가맹점 인증 요청
 */
@Getter
@NoArgsConstructor
@ToString(callSuper = true)
@SuperBuilder
public class CertRequestDto extends MobilePayMessageHeader {


    //휴대전화번호
    @NotBlank
    @Length(max = 11, message = "전화번호 길이 오류")
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

    //이름
    @Length(max = 20)
    public String userName;

    //email
    @Length(max = 126)
    public String email;

    //결제금액
    @Min(100l)
    @Max(1000000l)
    public Long payAmount;

    //상품명
    @Length(max = 126)
    public String itemName;

    public Payment toEntity(){
        return Payment.certReqBuilder()
                .carrierName(CarrierName.valueOfStr(mobileCarrier))
                .statusCode(StatusCode.CERT_READY)
                .payAmount(payAmount)
                .phone(phone)
                .userInfo(UserInfo.builder()
                        .userName(userName)
                        .socialNumber(socialNumber)
                        .gender(gender)
                        .email(email).build())
                .merchantTrxid(merchantTrxid)
                .merchantReqDt(requestDatetime)
                .build();
    }
}
