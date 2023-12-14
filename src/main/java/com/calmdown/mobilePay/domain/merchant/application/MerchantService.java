package com.calmdown.mobilePay.domain.merchant.application;

import com.calmdown.mobilePay.domain.merchant.dto.MerchantRequestDto;
import com.calmdown.mobilePay.domain.merchant.dto.MerchantResponseDto;
import com.calmdown.mobilePay.domain.merchant.entity.Merchant;
import com.calmdown.mobilePay.domain.merchant.entity.ProgressCode;
import com.calmdown.mobilePay.domain.merchant.repository.MerchantRepository;
import com.calmdown.mobilePay.domain.model.ResultCode;
import com.calmdown.mobilePay.domain.pay.dto.CertRequestDto;
import com.calmdown.mobilePay.domain.pay.dto.CertResponseDto;
import com.calmdown.mobilePay.domain.pay.entity.Payment;
import com.calmdown.mobilePay.global.exception.errorCode.CommonErrorCode;
import com.calmdown.mobilePay.global.exception.exception.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MerchantService {

    private final MerchantRepository merchantRepository;

    public Merchant findById(Long id) {
        Merchant merchant = merchantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("등록된 상점이 아닙니다. id=" + id));

        if (!merchant.getProgressCode().equals(ProgressCode.AVAILABLE))
            throw new IllegalArgumentException("가맹점 " + id + "는 결제 가능한 상태가 아닙니다. status=" + merchant.getProgressCode());

        return merchant;
    }

    /*
     * 가맹점 등록
     */
    public MerchantResponseDto regist(MerchantRequestDto request) throws Exception{

        // 가맹점 등록 이력 조회
        Merchant merchant = merchantRepository.findById(request.getMerchantId())
                .orElseThrow(()-> new UserException(CommonErrorCode.INVALID_MERCHANT_CONTRACT));

        // 가맹점 등록
        Merchant saveMerchant = request.toEntity();
        merchantRepository.save(saveMerchant);

        return MerchantResponseDto.builder()
                .build();
    }

    /*
     * 가맹점 해지
     */
    public MerchantResponseDto terminate(MerchantRequestDto request) throws Exception{

        // 가맹점 등록 이력 조회
        Merchant merchant = merchantRepository.findById(request.getMerchantId())
                .orElseThrow(()-> new UserException(CommonErrorCode.INVALID_MERCHANT_CONTRACT));

        // 가맹점 해지
        merchant.terminateContract(ProgressCode.STOP);
        merchantRepository.save(merchant);

        return MerchantResponseDto.builder()
                .resultCode(ResultCode.SUCCESS)
                .build();
    }
}