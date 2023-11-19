package com.calmdown.mobilePay.global.infra;


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

    /**
     * API 연동 오류 (NurigoBadRequestException: API KEY 가 16자리가 아니라는 에러 메세지)
     * */
    /*
    @Value("${coolsms.api.key}")
    private String apiKey;

    @Value("${coolsms.api.secret}")
    private String apiSecretKey;

    @PostConstruct
    private void init(){
        this.messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecretKey, "https://api.coolsms.co.kr");
    }*/

    private DefaultMessageService messageService;

    @PostConstruct
    private void init(){
        this.messageService = NurigoApp.INSTANCE.initialize("NCSG2SHVUBXIACQW", "5O9DXK8IGW69NHLKGQSFBPOTKPB8GFYM", "https://api.coolsms.co.kr");
    }

    public SingleMessageSentResponse sendOne(String to, String verificationCode){
        Message message = new Message();

        message.setFrom("01053490561");
        message.setTo(to);

        message.setText("[Mobile Pay] 인증번호 \n" +"[" + verificationCode + "]를 입력해주세요.");
        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
        return response;
    }

}
