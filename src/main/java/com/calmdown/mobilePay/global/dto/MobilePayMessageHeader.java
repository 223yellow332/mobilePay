package com.calmdown.mobilePay.global.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public abstract class MobilePayMessageHeader {

    // 가맹점ID
    @NotBlank
    public Long meerchantId;

    // 가맹점 주묹id
    @NotBlank
    @Length(max = 20)
    public String merchantTrxid;

    // 요청시간
    @NotBlank
    @Length(min = 14, max = 14)
    public String requestDatetime;
}
