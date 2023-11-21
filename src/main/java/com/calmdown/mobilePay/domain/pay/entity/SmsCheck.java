package com.calmdown.mobilePay.domain.pay.entity;

import com.calmdown.mobilePay.domain.model.BaseTimeEntity;
import com.calmdown.mobilePay.domain.pay.StatusCode;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
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
    @Column(name="result_code")
    @Enumerated(EnumType.STRING) //[SMS_CHECK_READY, SMS_CHECK_SUCCESS,SMS_CHECK_FAILURE]
    private StatusCode smsCheckStatus;

    //SMS 인증번호
    @Column(name="sms_check_number")
    //private Long smsCheckNumber;
    private String smsCheckNumber;

    @Builder
    public SmsCheck(Payment payment, StatusCode smsCheckStatus, String smsCheckNumber) {
        this.payment = payment;
        this.smsCheckStatus = smsCheckStatus;
        this.smsCheckNumber = smsCheckNumber;
    }

}
