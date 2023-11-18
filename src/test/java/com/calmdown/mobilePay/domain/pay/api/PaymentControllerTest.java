package com.calmdown.mobilePay.domain.pay.api;

import com.calmdown.mobilePay.domain.merchant.repository.MerchantRepository;
import com.calmdown.mobilePay.domain.pay.repository.PaymentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.servlet.MockMvc;
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
    @DisplayName("인증")
    void certTest(){

        //given

        //when



        //then
        System.out.println("성공");

    }


}
