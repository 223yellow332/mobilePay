package com.calmdown.mobilePay.domain.pay.api;


import com.calmdown.mobilePay.domain.merchant.entity.Merchant;
import com.calmdown.mobilePay.domain.merchant.entity.ProgressCode;
import com.calmdown.mobilePay.domain.merchant.repository.MerchantRepository;
import com.calmdown.mobilePay.domain.pay.dto.CertRequestDto;
import com.calmdown.mobilePay.domain.pay.dto.CertResponseDto;
import com.calmdown.mobilePay.domain.pay.entity.CarrierName;
import com.calmdown.mobilePay.domain.pay.entity.UserInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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

/**
 *
 */
@Slf4j
@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class PaymentApiIntegreTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private MerchantRepository merchantRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void init() {
        // 가맹점
        Merchant merchant = merchantRepository.save(Merchant.builder()
                .merchantName("테스트 가맹점")
                .progressCode(ProgressCode.AVAILABLE)
                .maxSmsCount(2)
                .build());
    }

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
}
