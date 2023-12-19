package com.calmdown.mobilePay.domain.dordy;

import com.calmdown.mobilePay.domain.pay.dto.CertRequestDto;
import com.calmdown.mobilePay.domain.pay.dto.CertResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Slf4j
@Transactional
@AutoConfigureMockMvc
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class PaymentControllerIntegreTest {

    @Autowired
    private MockMvc mockMvc;
/*

    @DisplayName("cert 테스트")
    @Test
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
*/


}
