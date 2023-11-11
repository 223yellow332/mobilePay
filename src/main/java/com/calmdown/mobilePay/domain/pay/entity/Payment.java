package com.calmdown.mobilePay.domain.pay.entity;

import com.calmdown.mobilePay.domain.model.BaseTimeEntity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table(name="payments")
@Entity
public class Payment extends BaseTimeEntity {

    //결제 ID
    @Id
    @GeneratedValue
    @Column(name="payment_id")
    private long id;

    //결제(1) : 취소(N)
    @OneToMany(mappedBy = "payment")
    private List<Cancel> cancels = new ArrayList<Cancel>();

    //결제(1) : SMS(N)
    @OneToMany(mappedBy = "payment")
    private List<SmsCheck> smsChecks = new ArrayList<SmsCheck>();

    //상태코드 [AUTH_READY, AUTH_SUCCESS, AUTH_FAILURE]
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

    //인증번호

    //Embedded: 상세 고객 정보 (고객이름, 생년월일, 성별, Email)
    @Embedded
    private UserInfo userInfo;

    //가맹점 거래번호
    @Column(name="merchant_trxid")
    private String merchantTrxid;

    //가맹점 요청일시
    @Temporal(TemporalType.TIMESTAMP)
    private Date merchantReqDt;

}
