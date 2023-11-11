package com.calmdown.mobilePay.global.dto;

public abstract class MobilePayResposeMessage extends MobilePayMessageHeader{

    // 결과 코드
    public String resultCode;

    // 결과 메시지
    public String resultMsg;
}
