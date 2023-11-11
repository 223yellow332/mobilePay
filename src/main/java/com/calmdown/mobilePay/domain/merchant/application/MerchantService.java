package com.calmdown.mobilePay.domain.merchant.application;

import com.calmdown.mobilePay.domain.merchant.entity.Merchant;
import com.calmdown.mobilePay.domain.merchant.entity.ProgressCode;
import com.calmdown.mobilePay.domain.merchant.repository.MerchantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MerchantService {

    private final MerchantRepository merchantRepository;

    public Merchant findById(Long id) {
        Merchant merchant = merchantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("등록된 상점이 아닙니다. id=" + id));

        if(!merchant.getProgressCode().equals(ProgressCode.AVAILABLE))
            throw new IllegalArgumentException("가맹점 " + id + "는 결제 가능한 상태가 아닙니다. status=" + merchant.getProgressCode());

        return merchant;
    }
}
