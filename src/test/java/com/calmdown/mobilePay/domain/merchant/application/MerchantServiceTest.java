package com.calmdown.mobilePay.domain.merchant.application;

import com.calmdown.mobilePay.domain.merchant.entity.Merchant;
import com.calmdown.mobilePay.domain.merchant.entity.ProgressCode;
import com.calmdown.mobilePay.domain.merchant.repository.MerchantRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MerchantServiceTest {

    @Mock
    private MerchantRepository merchantRepository;

    @InjectMocks
    private MerchantService merchantService;

    @Test
    @DisplayName("가맹점 조회 및 상태 성공 확인")
    void chekSuccess() {

        //given
        Merchant merchant = Merchant.builder()
                .merchantName("테스트가맹점")
                .progressCode(ProgressCode.AVAILABLE)
                .maxSmsCount(2)
                .category("online")
                .build();

        given(merchantRepository.findById(0L)).willReturn(Optional.of(merchant));

        //when
        Merchant find = merchantService.findById(0L);

        //then
        assertThat(find.getMaxSmsCount()).isEqualTo(2);
        assertThat(find.getMerchantName()).isEqualTo(merchant.getMerchantName());
        assertThat(find.getProgressCode()).isEqualTo(merchant.getProgressCode());

        System.out.println("find = " + find.toString());

    }

    @Test
    @DisplayName("등록안된 가맹점 테스트")
    void unregister_affInfo () {
        //given
        given(merchantRepository.findById(0l)).willReturn(Optional.ofNullable(null));

        //when & then
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> {merchantService.findById(0l);});
    }

    @Test
    @DisplayName("사용 불가능한 가맹점 테스트")
    void waiting_status_affInfo () {
        //given
        Merchant merchant = Merchant.builder()
                .merchantName("테스트가맹점")
                .progressCode(ProgressCode.WAIT)
                .maxSmsCount(2)
                .category("online")
                .build();

        given(merchantRepository.findById(0l)).willReturn(Optional.of(merchant));

        //when & then
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> {merchantService.findById(0l);});
    }


}