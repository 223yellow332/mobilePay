package com.calmdown.mobilePay.domain.dordy.api;

import com.calmdown.mobilePay.domain.pay.application.PaymentService;
import com.calmdown.mobilePay.domain.pay.application.PaymentStatus;
import com.calmdown.mobilePay.global.infra.SmsSendUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@MockBean(JpaMetamodelMappingContext.class)
@Slf4j
@WebMvcTest
@ExtendWith(SpringExtension.class)
public class PaymentControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    @MockBean
    private PaymentStatus paymentStatus;

    @Test
    void cert_test(){

        //given

        //when

        //then

    }



}
