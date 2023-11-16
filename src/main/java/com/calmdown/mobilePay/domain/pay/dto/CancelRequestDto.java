package com.calmdown.mobilePay.domain.pay.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

@NoArgsConstructor
@Getter
@ToString(callSuper = true)
@SuperBuilder
public class CancelRequestDto {

    // 가맹점ID
    @NotNull
    public Long merchantId;

/*    // 가맹점 주문id
    @NotBlank
    @Length(max = 20)
    public String merchantTrxid;*/

    //TRANSACTION_ID
    @NotBlank
    public String transactionId;

    //취소금액
    @NotBlank
    @Length(max = 10)
    public Long amount;
}
