package com.calmdown.mobilePay.domain.merchant.dto;

import com.calmdown.mobilePay.domain.model.ResultCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@Getter
@ToString(callSuper = true)
@SuperBuilder
public class MerchantResponseDto {
    
    public ResultCode resultCode;

}
