
package com.calmdown.mobilePay.domain.pay.api;

import com.calmdown.mobilePay.domain.pay.application.PaymentStatus;
import com.calmdown.mobilePay.domain.pay.dto.CertRequestDto;
import com.calmdown.mobilePay.domain.pay.dto.CertResponseDto;
import com.calmdown.mobilePay.domain.pay.entity.CarrierName;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/*
 * @MockBean(JpaMetamodelMappingContext.class)
 * @WebMvcTest는 JPA생성과 관련된 작업을 진행하지 않습니다.
 * 하지만 MobilePayApplication에서 @EnableJpaAuditing를 선언하였기에 "JPA metamodel must not be empty" 오류가 발생합니다.
 *
 */

/*@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(PaymentApi.class)*/
class PaymentApiTest {
/*

    @Autowired
    MockMvc mvc;

    @MockBean
    PaymentStatus paymentStatus;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("결제 인증 테스트")
    void getMemberListTest() throws Exception {
        CertRequestDto request = CertRequestDto.builder()
                .merchantId(1L)
                .merchantTrxid("TEST_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddhhmmss")))
                .requestDatetime(Date.valueOf("20231112125959"))
                .phone("01012344885")
                .mobileCarrier(CarrierName.KT.str())
                .socialNumber("19881215")
                .gender("F")
                .userName("홍길동")
                .payAmount(15000L)
                .build();

        CertResponseDto response = CertResponseDto.builder()
                .merchantId(1L)
                .merchantTrxid("TEST_"+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddhhmmss")))
                .requestDatetime(Date.valueOf("20231112125959"))
                .resultCode("0")
                .resultMsg("성공")
                .transactionId("1")
                .payAmount(15000L)
                .limitAmount(240000L)
                .payDateTime("20231112125959")
                .build();

        given(paymentStatus.cert(request)).willReturn(response);

        mvc.perform(post("/payment/cert")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print()) // 받은 결과 출력
                ;
    }
*/

}
