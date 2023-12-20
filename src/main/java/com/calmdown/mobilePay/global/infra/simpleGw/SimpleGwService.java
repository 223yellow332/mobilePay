package com.calmdown.mobilePay.global.infra.simpleGw;

import com.calmdown.mobilePay.domain.pay.dto.CertRequestDto;
import com.calmdown.mobilePay.domain.pay.entity.Cancel;
import com.calmdown.mobilePay.domain.pay.entity.Payment;
import com.calmdown.mobilePay.global.infra.simpleGw.dto.GatewayRequest;
import com.calmdown.mobilePay.global.infra.simpleGw.dto.GatewayResponse;
import com.calmdown.mobilePay.global.infra.simpleGw.dto.SimpleGwCertRequest;
import com.calmdown.mobilePay.global.infra.simpleGw.dto.SimpleGwResponse;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@RequiredArgsConstructor
@Component
public class SimpleGwService {

    private WebClient webClient;
    private final SimpleGwMsgConverter simpleGwMsgConverter;

    @Value("${calmdown.simpleGw.http.auth-token-header.name}")
    private String principalRequestHeader;
    @Value("${calmdown.simpleGw.http.auth-token}")
    private String principalRequestValue;

    @PostConstruct
    private void init(){
        webClient = WebClient.builder()
                .baseUrl("http://localhost:12030")
                .defaultHeader(principalRequestHeader, principalRequestValue)
                .build();
    }

    public GatewayResponse cert(Payment payment) {
        return comm("/v1/pay/cert", simpleGwMsgConverter.toGatewayCertRequest(payment));
    }

    public GatewayResponse auth(Payment payment) {
        return comm("/v1/pay/auth", simpleGwMsgConverter.toGatewayAuthRequest(payment));
    }

    public GatewayResponse cancel(Payment payment) {
        return comm("/v1/pay/cancel", simpleGwMsgConverter.toGatewayCancelRequest(payment));
    }

    /*
     * simple gateway 통신
     */
    public SimpleGwResponse comm(String uri, GatewayRequest request) {
        log.info(request.toString());

        SimpleGwResponse response = webClient.post()
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(SimpleGwResponse.class)
                .block();

        log.info(response.toString());
        return response;
    }
}
