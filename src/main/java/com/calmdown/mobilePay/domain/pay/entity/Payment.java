package com.calmdown.mobilePay.domain.pay.entity;

import com.calmdown.mobilePay.domain.merchant.entity.Merchant;
import com.calmdown.mobilePay.domain.model.BaseTimeEntity;
import com.calmdown.mobilePay.domain.pay.StatusCode;
import com.calmdown.mobilePay.domain.pay.repository.MobileCarrierRepository;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Getter
@ToString
@Table(name="payments")
@Entity
public class Payment extends BaseTimeEntity {

    //결제 ID
    @Id
    @GeneratedValue
    @Column(name="payment_id")
    private long id;

    //가맹점 ID
    //결제(N) : 가맹점(1)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id")
    private Merchant merchant;

    //결제(1) : 취소(N)
    @OneToMany(mappedBy = "payment")
    private List<Cancel> cancels = new ArrayList<Cancel>();

    //결제(1) : SMS(N)
    @OneToMany(mappedBy = "payment")
    private List<SmsCheck> smsChecks = new ArrayList<SmsCheck>();

    @OneToOne(mappedBy = "payment")
    @PrimaryKeyJoinColumn
    private MobileCarrier mobileCarrier;

    //상태코드 [AUTH_READY, AUTH_SUCCESS, AUTH_FAILURE]
    @Enumerated(EnumType.STRING)
    @Column(name="status_code")
    private StatusCode statusCode;

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
    @Column(name="sms_auth_number")
    private String smsCheckNumber;


    //Embedded: 상세 고객 정보 (고객이름, 생년월일, 성별, Email)
    @Embedded
    private UserInfo userInfo;

    //가맹점 거래번호
    @Column(name="merchant_trxid")
    private String merchantTrxid;

    //가맹점 요청일시
    @Temporal(TemporalType.TIMESTAMP)
    private Date merchantReqDt;

    @Builder
    public Payment(Merchant merchant, StatusCode statusCode, CarrierName carrierName, long payAmount, String phone
    ,UserInfo userInfo, String merchantTrxid, Date merchantReqDt) {
        this.merchant = merchant;
        this.statusCode = statusCode;
        this.carrierName = carrierName;
        this.payAmount = payAmount;
        this.phone = phone;
        this.userInfo = userInfo;
        this.merchantTrxid = merchantTrxid;
        this.merchantReqDt = merchantReqDt;
    }

    @Builder(builderMethodName = "certReqBuilder")
    public Payment(String phone, CarrierName carrierName, StatusCode statusCode,
                   UserInfo userInfo, long payAmount, String merchantTrxid, Date merchantReqDt){
        this.phone = phone;
        this.carrierName = carrierName;
        this.statusCode = statusCode;
        this.userInfo = userInfo;
        this.payAmount = payAmount;
        this.merchantTrxid = merchantTrxid;
        this.merchantReqDt = merchantReqDt;
    }

    //TODO: set 코드 개선
    public void updateStatus(StatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public void setMobileCarrier(MobileCarrier mobileCarrier) {
        this.mobileCarrier = mobileCarrier;
    }

    public void setSmsCheckNumber(String smsCheckNumber) {
        this.smsCheckNumber = smsCheckNumber;
    }

}
