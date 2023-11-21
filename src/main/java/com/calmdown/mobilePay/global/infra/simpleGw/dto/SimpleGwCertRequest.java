package com.calmdown.mobilePay.global.infra.simpleGw.dto;

import com.calmdown.mobilePay.domain.pay.dto.CertRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder
public class SimpleGwCertRequest implements GatewayRequest{
  private String phone;
  private String mobileCarrier;
  private Long payAmount;
  
}
