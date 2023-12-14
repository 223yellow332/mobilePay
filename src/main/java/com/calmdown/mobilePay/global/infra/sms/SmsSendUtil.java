package com.calmdown.mobilePay.global.infra.sms;


import com.calmdown.mobilePay.global.exception.errorCode.CommonErrorCode;
import com.calmdown.mobilePay.global.exception.errorCode.ErrorCode;
import com.calmdown.mobilePay.global.exception.exception.UserException;
import jakarta.annotation.PostConstruct;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;

@Component
public class SmsSendUtil {

    @Value("${coolsms.api.key}")
    private String apiKey;

    @Value("${coolsms.api.secret}")
    private String apiSecretKey;

    @Value("${SMS_ADMIN}")
    private String adminPhone;

    @PostConstruct
    private void init(){
        this.messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecretKey, "https://api.coolsms.co.kr");
    }

    private DefaultMessageService messageService;

    public SingleMessageSentResponse sendOne(String to, String verificationCode) {
        Message message = new Message();

        message.setFrom(adminPhone);
        message.setTo(to);
        message.setText("[Mobile Pay] 인증번호 \n" +"[" + verificationCode + "]를 입력해주세요.");

        try {
            SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
            return response;
        }catch (Exception e){
            throw new UserException(CommonErrorCode.SMS_API_SERVER_ERROR);
        }
    }
}
