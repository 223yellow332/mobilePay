package com.calmdown.mobilePay.domain.pay.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
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

    @Builder
    public UserInfo (String userName, String socialNumber, String gender, String email) {
        this.userName = userName;
        this.socialNumber = socialNumber;
        this.gender = gender;
        this.email = email;
    }

}
