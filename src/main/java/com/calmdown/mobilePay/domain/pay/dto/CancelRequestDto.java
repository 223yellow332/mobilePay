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
public class CancelRequestDto extends MobilePayMessageHeader {

    //TRANSACTION_ID
    @NotBlank
    public String transactionId;

    //취소금액
    @NotBlank
    @Length(max = 10)
    public Long cancelAmount;
}
