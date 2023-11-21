package com.calmdown.mobilePay.global.infra.simpleGw.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder
public class SimpleGwCancelRequest implements GatewayRequest{
  private String mobileTrxid;
  private String phone;
  private String mobileCarrier;
  private Long payAmount;
}
