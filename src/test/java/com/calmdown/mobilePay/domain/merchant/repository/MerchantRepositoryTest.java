package com.calmdown.mobilePay.domain.merchant.repository;

import com.calmdown.mobilePay.domain.merchant.entity.Merchant;
import com.calmdown.mobilePay.domain.merchant.entity.ProgressCode;
import com.calmdown.mobilePay.domain.pay.entity.CarrierName;
import com.calmdown.mobilePay.domain.pay.entity.MobileCarrier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MerchantRepositoryTest {

    @Autowired
    private MerchantRepository merchantRepository;

    @Test
    @DisplayName("저장 후 등록 조회")
    void saveAndSelect() {
        //given
        Merchant merchant = Merchant.builder()
                .merchantName("테스트가맹점")
                .progressCode(ProgressCode.AVAILABLE)
                .maxSmsCount(2)
                .merchantName("online")
                .build();

        Merchant saveMerchant = merchantRepository.save(merchant);

        //when
        Merchant findMer = merchantRepository.findById(saveMerchant.getId())
                .orElseThrow(() -> new IllegalArgumentException());

        //then
        assertThat(findMer.getMerchantName()).isEqualTo(merchant.getMerchantName());

        System.out.println("findAff.toString() = " + findMer.toString());

    }

}