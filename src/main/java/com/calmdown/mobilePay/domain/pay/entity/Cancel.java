package com.calmdown.mobilePay.domain.pay.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Table(name="payments_cancel")
@Getter
@Entity
public class Cancel {

    @Id @GeneratedValue
    @Column(name="cancel_id")
    private long id;

    //결제(1) : 취소(N)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    //취소금액
    private int cancelAmount;

    //취소상태 [CANCEL_READY, CANCEL_SUCCESS, CANCEL_FAIL]
    @Enumerated(EnumType.STRING)
    private CancelStatus cancelStatus;


}
