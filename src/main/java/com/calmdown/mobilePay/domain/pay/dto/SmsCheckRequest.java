package com.calmdown.mobilePay.domain.pay.dto;

import com.calmdown.mobilePay.global.dto.MobilePayMessageHeader;

public class SmsCheckRequest extends MobilePayMessageHeader {

    /*
바디	TRANSACTION_ID	not null		INT
	취소금액	not null	lenght max= 10
 */
    //TRANSACTION_ID
    public String transactionId;

    //인증번호
    public String smsAuthNumber;

}
