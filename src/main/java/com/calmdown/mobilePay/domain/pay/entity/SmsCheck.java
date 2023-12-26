package com.calmdown.mobilePay.domain.pay.entity;

import com.calmdown.mobilePay.domain.model.BaseTimeEntity;
import com.calmdown.mobilePay.domain.pay.StatusCode;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString(exclude = "payment")
@Getter
@Entity
public class SmsCheck extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name="sms_check_id")
    private long id;

    //SMS(N) : 결제(1)
    @JoinColumn(name="payment_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Payment payment;

    //SMS 인증상태
    @Enumerated(EnumType.STRING) //[SMS_CHECK_READY, SMS_CHECK_SUCCESS,SMS_CHECK_FAILURE]
    private StatusCode statusCode;

    //SMS 인증번호
    @Column(name="sms_check_number")
    private String smsCheckNumber;

    @Builder
    public SmsCheck(Payment payment, StatusCode statusCode, String smsCheckNumber) {
        this.payment = payment;
        payment.getSmsChecks().add(this);
        this.statusCode = statusCode;
        this.smsCheckNumber = smsCheckNumber;
    }

}
