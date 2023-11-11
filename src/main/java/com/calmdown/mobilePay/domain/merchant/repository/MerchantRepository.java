package com.calmdown.mobilePay.domain.merchant.repository;

import com.calmdown.mobilePay.domain.merchant.entity.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchantRepository extends JpaRepository<Merchant, Long> {
}
