package com.calmdown.mobilePay.domain.pay.entity;

import com.calmdown.mobilePay.domain.pay.StatusCode;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
@Entity
@Table(name="mobile_carrier")
public class MobileCarrier {

    @Id
    @Column(name = "payment_id")
    private long id;

    // 결제(1) : 통신사정보(1)
    @OneToOne
    @MapsId
    @JoinColumn(name = "payment_id", referencedColumnName = "payment_id")
    private Payment payment;

    // 통신사명
    @Enumerated(EnumType.STRING)
    private CarrierName carrierName;

    // 통신사 거래번호
    @Column
    private String carrierTrxid;

    // 통신사 응답코드
    @Column(length = 50)
    private String carrierReturnCode;

    // 통신사 응답메시지
    @Column(length = 256)
    private String carrierReturnMsg;

    // 사용가능 한도액
    @Column
    private Long limitAmount;

    @Builder
    public MobileCarrier(Payment payment, CarrierName carrierName, String carrierTrxid, String carrierReturnCode
            , String carrierReturnMsg, Long limitAmount) {
        this.payment = payment;
        this.carrierName = carrierName;
        this.carrierTrxid = carrierTrxid;
        this.carrierReturnCode = carrierReturnCode;
        this.carrierReturnMsg = carrierReturnMsg;
        this.limitAmount = limitAmount;
    }

    public void updateResult(String carrierReturnCode, String carrierReturnMsg) {
        this.carrierReturnCode = carrierReturnCode;
        this.carrierReturnMsg = carrierReturnMsg;
    }
}
