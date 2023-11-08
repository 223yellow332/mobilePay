package com.calmdown.mobilePay.domain.pay.entity;

import com.calmdown.mobilePay.domain.model.BaseTimeEntity;
import jakarta.persistence.*;

@Table(name="payments")
@Entity
public class Payment extends BaseTimeEntity {

    //결제 ID
    @Id
    @GeneratedValue
    @Column(name="payment_id")
    private long id;

    //가맹점 ID (N:1)

    //상태코드 [AUTH_SUCCESS, AUTH_FAILURE]
    @Enumerated(EnumType.STRING)
    @Column(name="status_code")
    private AuthStatus authStatus;

    //통신사명 [SK, KT, LGU]
    @Enumerated(EnumType.STRING)
    private CarrierName carrierName;

    //결제금액
    @Column(name="pay_amount")
    private long payAmount;

    //휴대폰 번호
    @Column(nullable = false)
    private String phone;

    //고객 이름
    @Column(name="user_name")
    private String userName;

    //생년월일
    @Column(name="birthday", nullable = false)
    private String socialNumber;

    //성별
    @Column(nullable = false)
    private String gender;

    //E-mail
    private String email;

    //가맹점 거래번호
    @Column(name="merchant_trxid")
    private String merchantTrxid;

    //가맹점 요청일시

}
