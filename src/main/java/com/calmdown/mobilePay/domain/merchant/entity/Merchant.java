package com.calmdown.mobilePay.domain.merchant.entity;

import com.calmdown.mobilePay.domain.pay.entity.Payment;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@ToString(exclude = "payments")
@Entity
@Table(name="merchant")
public class Merchant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "merchant_id")
    private long id;

    @OneToMany(mappedBy = "merchant", cascade = CascadeType.ALL)
    private List<MerchantUser> merchantUsers = new ArrayList<>();

    @OneToMany(mappedBy = "merchant", fetch = FetchType.LAZY)
    private List<Payment> payments = new ArrayList<>();

    @Column(length = 50, nullable = false)
    private  String merchantName;

    @Enumerated(EnumType.STRING)
    private ProgressCode progressCode;

    @Column(columnDefinition = "bigint default 1", nullable = false)
    private int maxSmsCount;

    @Column(length = 50)
    private String category;

    @Builder
    public Merchant(String merchantName, ProgressCode progressCode, int maxSmsCount, String category) {
        this.merchantName = merchantName;
        this.progressCode = progressCode;
        this.maxSmsCount = maxSmsCount;
        this.category = category;
    }

    public void terminateContract(ProgressCode progressCode){
        this.progressCode = progressCode;
    }
}
