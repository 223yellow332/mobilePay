package com.calmdown.mobilePay.domain.user.entity;

import com.calmdown.mobilePay.domain.merchant.entity.MerchantUser;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "merchant")
    private List<MerchantUser> merchantUsers = new ArrayList<>();

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
