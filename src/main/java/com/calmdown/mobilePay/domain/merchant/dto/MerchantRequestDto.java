package com.calmdown.mobilePay.domain.merchant.dto;

import com.calmdown.mobilePay.domain.merchant.entity.Merchant;
import com.calmdown.mobilePay.domain.merchant.entity.ProgressCode;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@Getter
@ToString(callSuper = true)
@SuperBuilder
public class MerchantRequestDto {

    //가맹점 아이디
    public Long merchantId;

    //가맹점명
    public String merchantName;

    //계약상태
    @Enumerated
    public ProgressCode progressCode;

    //SMS 인증 최대 횟수
    public int maxSmsCount;

    //업종
    public String category;

    public Merchant toEntity(){
        return Merchant.builder()
                .merchantName(merchantName)
                .progressCode(ProgressCode.WAIT)
                .maxSmsCount(maxSmsCount)
                .category(category)
                .build();
    }


}
