package com.calmdown.mobilePay.domain.pay.dto;

import com.calmdown.mobilePay.global.dto.MobilePayResposeMessage;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@Getter
@ToString(callSuper = true)
@SuperBuilder
public class CertificationResponse extends MobilePayResposeMessage {
    /*
    바디	결과코드	not null	max 10	max 10
	결과메시지	not null	max 100	max 100
	TRANSACTION_ID	not null		INT
	결제금액		max 10	max 10
	남은 한도		INT	INT
	인증 처리 일시		yyyyMMddHHmmss	yyyyMMddHHmmss
     */
    //TRANSACTION_ID
    public String transactionId;

    //결제금액
    public Long payAmount;

    //사용가능 한도액
    public Long limitAmount;

    //인증 처리 일시
    public String payDateTime;
}
