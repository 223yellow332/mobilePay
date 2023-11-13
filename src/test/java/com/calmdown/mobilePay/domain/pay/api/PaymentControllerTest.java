package com.calmdown.mobilePay.domain.pay.api;

import com.calmdown.mobilePay.domain.merchant.repository.MerchantRepository;
import com.calmdown.mobilePay.domain.pay.dto.PaymentCertRequestDto;
import com.calmdown.mobilePay.domain.pay.repository.PaymentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.http.RequestEntity.post;


public class PaymentControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private MerchantRepository merchantRepository;
    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;


    @Test
    @DisplayName("가맹점 인증 요청 수신 후 결제 정보 조회")
    void certTest(){
        //given
//        long id = 1;
//        String phone = "01012344885";
//        PaymentCertRequestDto paymentCertRequestDto = PaymentCertRequestDto.builder()
//                .id(id)
//                .phone(phone)
//                .build();
//
//        String url = "http://localhost:" + port + "/api/cert";

        //paymentRepository.save(payment);

        //when

/*
        Payment payment = paymentRepository.findById(requestDto.toEntity().getId())
                .orElseThrow(() -> new IllegalArgumentException());
*/

        //then
        System.out.println("성공");

    }


}
