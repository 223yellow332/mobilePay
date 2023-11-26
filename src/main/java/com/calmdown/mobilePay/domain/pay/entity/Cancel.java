package com.calmdown.mobilePay.domain.pay.entity;

import com.calmdown.mobilePay.domain.model.BaseTimeEntity;
import com.calmdown.mobilePay.domain.pay.StatusCode;
import jakarta.persistence.*;
import lombok.Builder;
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
    @Column(name="cancel_amount")
    private long cancelAmount;

    //취소상태 [CANCEL_READY, CANCEL_SUCCESS, CANCEL_FAIL]
    @Enumerated(EnumType.STRING)
    private StatusCode statusCode;

    @Builder
    public Cancel(Payment payment, long cancelAmount, StatusCode statusCode){
        this.payment = payment;
        this.cancelAmount = cancelAmount;
        this.statusCode = statusCode;
    }

    public void updateStatus(StatusCode statusCode) {
        this.statusCode = statusCode;
    }




}
