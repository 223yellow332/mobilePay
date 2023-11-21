package com.calmdown.mobilePay.global.infra.simpleGw;

import com.calmdown.mobilePay.global.infra.simpleGw.dto.GatewayResponse;
import com.calmdown.mobilePay.global.infra.simpleGw.dto.SimpleGwCertRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@MockBean(JpaMetamodelMappingContext.class)
@ExtendWith(SpringExtension.class)
class SimpleGwServiceTest {

    @Autowired
    private SimpleGwService simpleGwService;

    @DisplayName("Simple gateway 인증 성공 테스트")
    @Test
    void certTest() {
        SimpleGwCertRequest request = SimpleGwCertRequest.builder()
                .phone("01048851004")
                .payAmount(1000L)
                .mobileCarrier("SK")
                .build();
        GatewayResponse response = simpleGwService.comm("/v1/pay/cert", request);
    }

}