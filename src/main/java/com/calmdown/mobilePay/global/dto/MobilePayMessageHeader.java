package com.calmdown.mobilePay.global.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@NoArgsConstructor
@Getter
@SuperBuilder
public abstract class MobilePayMessageHeader {

    // 가맹점ID
    @NotNull
    public Long meerchantId;

    // 가맹점 주묹id
    @NotBlank
    @Length(max = 20)
    public String merchantTrxid;

    // 요청시간
//    @NotBlank
//    @Length(min = 14, max = 14)
    @DateTimeFormat(pattern = "yyyyMMddhhmmss")
    public Date requestDatetime;
}
