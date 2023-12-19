package com.calmdown.mobilePay.domain.dordy.api;

import com.calmdown.mobilePay.domain.merchant.application.MerchantService;
import com.calmdown.mobilePay.domain.merchant.entity.Merchant;
import com.calmdown.mobilePay.domain.pay.StatusCode;
import com.calmdown.mobilePay.domain.pay.application.*;
import com.calmdown.mobilePay.domain.pay.dto.*;
import com.calmdown.mobilePay.domain.pay.entity.CarrierName;
import com.calmdown.mobilePay.domain.pay.entity.Payment;
import com.calmdown.mobilePay.domain.pay.entity.UserInfo;
import com.calmdown.mobilePay.global.infra.simpleGw.SimpleGwService;
import com.calmdown.mobilePay.global.infra.sms.SmsSendUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Date;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    @MockBean
    private SimpleGwService simpleGwService;
    @MockBean
    private  MerchantService merchantService;
    @MockBean
    private  MobileCarrierService mobileCarrierService;
    @MockBean
    private  SmsCheckService smsCheckService;
    @MockBean
    private  CancelService cancelService;
    @MockBean
    private  SmsSendUtil smsSendUtil;

    @Test
    @DisplayName("cert 테스트")
    void cert_test() throws Exception{

        //given
        CertRequestDto certRequestDto = new CertRequestDto();
        String contents = new ObjectMapper().writeValueAsString(new CertResponseDto());
        when(paymentStatus.cert(certRequestDto)).thenReturn(new CertResponseDto());

        //when
        ResultActions resultActions = mockMvc.perform(post("/api/cert")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(contents)
                .accept(MediaType.APPLICATION_JSON_UTF8));

        //then
        resultActions
                //.andExpect(status().isCreated())
                //.andExpect(jsonPath("$.id").value(1L))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("sms Check 테스트")
    void smsCheck_test() throws Exception{

        //given
        SmsCheckRequestDto smsCheckRequestDto = new SmsCheckRequestDto();
        String contents = new ObjectMapper().writeValueAsString(new CertResponseDto());
        when(paymentStatus.smsCheck(smsCheckRequestDto)).thenReturn(new SmsCheckResponseDto());

        //when
        ResultActions resultActions = mockMvc.perform(post("/api/smsCheck")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(contents)
                .accept(MediaType.APPLICATION_JSON_UTF8));

        //then
        resultActions
                //.andExpect(status().isCreated())
                //.andExpect(jsonPath("$.id").value(1L))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("auth 테스트")
    void auth_test() throws Exception{

        //given
        AuthRequestDto authRequestDto = new AuthRequestDto();
        String contents = new ObjectMapper().writeValueAsString(new CertResponseDto());
        when(paymentStatus.auth(authRequestDto)).thenReturn(new AuthResponseDto());

        //when
        ResultActions resultActions = mockMvc.perform(post("/api/auth")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(contents)
                .accept(MediaType.APPLICATION_JSON_UTF8));

        //then
        resultActions
                //.andExpect(status().isCreated())
                //.andExpect(jsonPath("$.id").value(1L))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("cancel 테스트")
    void cancel_test() throws Exception{

        //given
        CancelRequestDto cancelRequestDto = new CancelRequestDto();
        String contents = new ObjectMapper().writeValueAsString(new CertResponseDto());
        when(paymentStatus.cancel(cancelRequestDto)).thenReturn(new CancelResponseDto());

        //when
        ResultActions resultActions = mockMvc.perform(post("/api/cancel")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(contents)
                .accept(MediaType.APPLICATION_JSON_UTF8));

        //then
        resultActions
                //.andExpect(status().isCreated())
                //.andExpect(jsonPath("$.id").value(1L))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    @DisplayName("search 테스트")
    void search_test() throws Exception{

        //given

        //when

        //then

    }



}
