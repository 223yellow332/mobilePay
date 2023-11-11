package com.calmdown.mobilePay.domain.pay.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class UserInfo {

    //고객 이름
    @Column(name="user_name")
    private String userName;

    //생년월일
    @Column(name="social_number")
    private String socialNumber;

    //성별
    private String gender;

    //E-mail
    private String email;

}
