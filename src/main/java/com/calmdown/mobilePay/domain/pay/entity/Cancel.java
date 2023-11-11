package com.calmdown.mobilePay.domain.pay.entity;

import com.calmdown.mobilePay.domain.model.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Table(name="payments_cancel")
@Getter
@Entity
public class Cancel extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name="cancel_id")
    private long id;

    //취소(N) : 결제(1)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    //취소금액
    private int cancelAmount;

    //취소상태 [CANCEL_READY, CANCEL_SUCCESS, CANCEL_FAIL]
    @Enumerated(EnumType.STRING)
    private CancelStatus cancelStatus;


}
