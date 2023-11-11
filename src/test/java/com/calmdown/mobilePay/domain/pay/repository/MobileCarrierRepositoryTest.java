package com.calmdown.mobilePay.domain.pay.repository;

import com.calmdown.mobilePay.domain.pay.entity.CarrierName;
import com.calmdown.mobilePay.domain.pay.entity.MobileCarrier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MobileCarrierRepositoryTest {

    @Autowired
    private MobileCarrierRepository mobileCarrierRepository;

    @Test
    @DisplayName("저장 후 등록 조회")
    void saveAndSelect() {
        //given
        MobileCarrier mobileCarrier = MobileCarrier.builder()
                .carrierName(CarrierName.SK)
                .carrierTrxid("SK_TEST_"+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddhhmmss")))
                .carrierReturnCode("0")
                .carrierReturnMsg("성공")
                .limitAmount(250000L)
                .build();

        MobileCarrier save = mobileCarrierRepository.save(mobileCarrier);

        //when
        MobileCarrier find = mobileCarrierRepository.findById(save.getId())
                .orElseThrow(() -> new IllegalArgumentException());

        //then
        assertThat(find.getCarrierTrxid()).isEqualTo(mobileCarrier.getCarrierTrxid());
        assertThat(find.getCarrierReturnCode()).isEqualTo(mobileCarrier.getCarrierReturnCode());
        assertThat(find.getCarrierReturnMsg()).isEqualTo(mobileCarrier.getCarrierReturnMsg());
        assertThat(find.getLimitAmount()).isEqualTo(mobileCarrier.getLimitAmount());

        System.out.println("findTelInfo.toString() = " + find.toString());

    }
}