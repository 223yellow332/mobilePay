package com.calmdown.mobilePay.domain.pay.dto;

import com.calmdown.mobilePay.domain.merchant.entity.Merchant;
import com.calmdown.mobilePay.domain.pay.StatusCode;
import com.calmdown.mobilePay.domain.pay.entity.CarrierName;
import com.calmdown.mobilePay.domain.pay.entity.Payment;
import com.calmdown.mobilePay.domain.pay.entity.UserInfo;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/*
 * 가맹점 인증 요청
 */
@Getter
@NoArgsConstructor
@ToString(callSuper = true)
@SuperBuilder
public class CertRequestDto {

    // 가맹점ID
    public Long merchantId;

    // 가맹점 주문id
    @NotBlank
    @Length(max = 20)
    public String merchantTrxid;

    // 요청시간 -> 인증때만
//    @Length(min = 14, max = 14)
    @DateTimeFormat(pattern = "yyyyMMddHHmmss")
    public Date requestDatetime;

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

    //결제금액
    @Min(100l)
    @Max(1000000l)
    public Long payAmount;

    //이름
    @Length(max = 20)
    public String userName;

    //email
    @Length(max = 126)
    public String email;

    //상품명
    @Length(max = 126)
    public String itemName;

    public Payment toEntityCertReq(Merchant merchant){
        return Payment.builder()
                .merchant(merchant)
                .carrierName(CarrierName.valueOfStr(mobileCarrier))
                .statusCode(StatusCode.CERT_READY)
                .merchantReqDt(requestDatetime)
                .payAmount(payAmount)
                .phone(phone)
                .userInfo(UserInfo.builder()
                        .userName(userName)
                        .socialNumber(socialNumber)
                        .gender(gender)
                        .email(email).build())
                .merchantTrxid(merchantTrxid)
                .build();
    }


}
