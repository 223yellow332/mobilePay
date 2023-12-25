package com.calmdown.mobilePay.domain.pay.api;

import com.calmdown.mobilePay.domain.pay.application.PaymentStatus;
import com.calmdown.mobilePay.domain.pay.dto.*;
import com.calmdown.mobilePay.domain.pay.entity.CarrierName;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaymentApiController.class)
public class PaymentApiControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    PaymentStatus paymentStatus;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void 인증성공_테스트() throws Exception {
        //given
        CertRequestDto request = CertRequestDto.builder()
                .merchantId(1L)
                .merchantTrxid("TEST_" + UUID.randomUUID().toString().substring(0,13))
                .requestDatetime(LocalDateTime.now())
                .phone("01012344885")
                .mobileCarrier(CarrierName.KT.str())
                .socialNumber("19881215")
                .gender("F")
                .userName("홍길동")
                .payAmount(15000L)
                .build();

        CertResponseDto response = CertResponseDto.builder()
                .resultCode("0")
                .resultMessage("성공")
                .paymentId("1")
                .payAmount(15000L)
                .limitAmount(240000L)
                .payDateTime("20231112125959")
                .build();

        when(paymentStatus.cert(any(CertRequestDto.class))).thenReturn(response);

        //when
        ResultActions resultActions = mvc.perform(post("/api/cert")
                .content(objectMapper.registerModule(new JavaTimeModule()).writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value("0"))
                .andExpect(jsonPath("$.resultMessage").value("성공"))
                .andDo(MockMvcResultHandlers.print()) // 받은 결과 출력
        ;
    }

    @Test
    void 인증실패_필수값누락_생년월일() throws Exception {
        //given
        CertRequestDto request = CertRequestDto.builder()
                .merchantId(1L)
                .merchantTrxid("TEST_" + UUID.randomUUID().toString().substring(0,13))
                .requestDatetime(LocalDateTime.now())
                .phone("01012344885")
                .mobileCarrier(CarrierName.KT.str())
//                .socialNumber("19881215")
                .gender("F")
                .userName("홍길동")
                .payAmount(15000L)
                .build();

        CertResponseDto response = CertResponseDto.builder()
                .resultCode("0")
                .resultMessage("성공")
                .paymentId("1")
                .payAmount(15000L)
                .limitAmount(240000L)
                .payDateTime("20231112125959")
                .build();

        when(paymentStatus.cert(any(CertRequestDto.class))).thenReturn(response);

        //when
        ResultActions resultActions = mvc.perform(post("/api/cert")
                .content(objectMapper.registerModule(new JavaTimeModule()).writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].field").value("socialNumber"))
                .andExpect(jsonPath("$.errors[0].message").value("must not be blank"))
                .andDo(MockMvcResultHandlers.print()) // 받은 결과 출력
        ;
    }

    @Test
    void 인증실패_필드최대길이초과_가맹점주문id() throws Exception {
        //given
        CertRequestDto request = CertRequestDto.builder()
                .merchantId(1L)
                .merchantTrxid("TEST_" + UUID.randomUUID().toString().substring(0,20))
                .requestDatetime(LocalDateTime.now())
                .phone("01012344885")
                .mobileCarrier(CarrierName.KT.str())
                .socialNumber("19881215")
                .gender("F")
                .userName("홍길동")
                .payAmount(15000L)
                .build();

        CertResponseDto response = CertResponseDto.builder()
                .resultCode("0")
                .resultMessage("성공")
                .paymentId("1")
                .payAmount(15000L)
                .limitAmount(240000L)
                .payDateTime("20231112125959")
                .build();

        when(paymentStatus.cert(any(CertRequestDto.class))).thenReturn(response);

        //when
        ResultActions resultActions = mvc.perform(post("/api/cert")
                .content(objectMapper.registerModule(new JavaTimeModule()).writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].field").value("merchantTrxid"))
                .andExpect(jsonPath("$.errors[0].message").value("length must be between 0 and 20"))
                .andDo(MockMvcResultHandlers.print()) // 받은 결과 출력
        ;
    }

    @Test
    void sms인증확인_테스트() throws Exception {
        //given
        SmsCheckRequestDto request = SmsCheckRequestDto.builder()
                .merchantId("1")
                .paymentId("1")
                .smsCheckNumber("123456")
                .build();

        SmsCheckResponseDto response = SmsCheckResponseDto.builder()
                .resultCode("0")
                .resultMessage("성공")
                .paymentId("1")
                .build();

        when(paymentStatus.smsCheck(any(SmsCheckRequestDto.class))).thenReturn(response);

        //when
        ResultActions resultActions = mvc.perform(post("/api/smsCheck")
                .content(objectMapper.registerModule(new JavaTimeModule()).writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value("0"))
                .andExpect(jsonPath("$.resultMessage").value("성공"))
                .andDo(MockMvcResultHandlers.print()) // 받은 결과 출력
        ;
    }

    @Test
    void 승인성공_테스트() throws Exception {
        //given
        AuthRequestDto request = AuthRequestDto.builder()
                .merchantId("1")
                .paymentId("1")
                .payAmount(15000L)
                .build();

        AuthResponseDto response = AuthResponseDto.builder()
                .resultCode("0")
                .resultMessage("성공")
                .paymentId("1")
                .payAmount(15000L)
                .limitAmount(240000L)
                .payDateTime("20231112125959")
                .build();

        when(paymentStatus.auth(any(AuthRequestDto.class))).thenReturn(response);

        //when
        ResultActions resultActions = mvc.perform(post("/api/auth")
                .content(objectMapper.registerModule(new JavaTimeModule()).writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value("0"))
                .andExpect(jsonPath("$.resultMessage").value("성공"))
                .andDo(MockMvcResultHandlers.print()) // 받은 결과 출력
        ;
    }

    @Test
    void 결제취소성공_테스트() throws Exception {
        //given
        CancelRequestDto request = CancelRequestDto.builder()
                .merchantId("1")
                .merchantTrxid("TEST_" + UUID.randomUUID().toString().substring(0,13))
                .paymentId("1")
                .cancelAmount(15000L)
                .build();

        CancelResponseDto response = CancelResponseDto.builder()
                .resultCode("0")
                .resultMessage("성공")
                .paymentId("1")
                .cancelAmount(15000L)
                .payDateTime("20231112125959")
                .build();

        when(paymentStatus.cancel(any(CancelRequestDto.class))).thenReturn(response);

        //when
        ResultActions resultActions = mvc.perform(post("/api/cancel")
                .content(objectMapper.registerModule(new JavaTimeModule()).writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value("0"))
                .andExpect(jsonPath("$.resultMessage").value("성공"))
                .andDo(MockMvcResultHandlers.print()) // 받은 결과 출력
        ;
    }
}
