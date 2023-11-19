package com.calmdown.mobilePay.domain.merchant.entity;

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
@Table(name="merchant")
public class Merchant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "merchant_id")
    private long id;

    @OneToMany(mappedBy = "merchant", cascade = CascadeType.ALL)
    private List<MerchantUser> merchantUsers = new ArrayList<>();

    @Column(length = 50, nullable = false)
    private  String merchantName;

    @Enumerated(EnumType.STRING)
    private ProgressCode progressCode;

    @Column(columnDefinition = "bigint default 1", nullable = false)
    private long maxSmsCount;

    @Column(length = 50)
    private String category;

    @Builder
    public Merchant(String merchantName, ProgressCode progressCode, long maxSmsCount, String category) {
        this.merchantName = merchantName;
        this.progressCode = progressCode;
        this.maxSmsCount = maxSmsCount;
        this.category = category;
    }
}
