package com.calmdown.mobilePay.domain.user.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
@Entity
@Table(name="users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column
    private String phone;

    @Column
    private String socialNumber;

    @Column
    private String gender;

    @Builder
    public Users(String phone, String socialNumber, String gender) {
        this.phone = phone;
        this.socialNumber = socialNumber;
        this.gender = gender;
    }
}
