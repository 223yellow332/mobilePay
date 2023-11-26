package com.calmdown.mobilePay.domain.merchant.api;

import com.calmdown.mobilePay.domain.merchant.application.MerchantService;
import com.calmdown.mobilePay.domain.merchant.dto.MerchantRequestDto;
import com.calmdown.mobilePay.domain.merchant.dto.MerchantResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequiredArgsConstructor
@RestController
public class MerchantApiController {

    private final MerchantService merchantService;

    /*
     * 가맹점 등록
     */
    @PostMapping("/api/merchant/regist")
    public ResponseEntity<MerchantResponseDto> regist(@RequestBody MerchantRequestDto merchantRequest) throws Exception{
        return new ResponseEntity<>(merchantService.regist(merchantRequest), HttpStatus.OK);
    }

    /*
     * 가맹점 해지
     */
    @PostMapping("/api/merchant/terminate")
    public ResponseEntity<MerchantResponseDto> terminate(@RequestBody MerchantRequestDto merchantRequest) throws Exception{
        return new ResponseEntity<>(merchantService.terminate(merchantRequest), HttpStatus.OK);
    }




}
