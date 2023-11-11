package com.calmdown.mobilePay.domain.pay.repository;

import com.calmdown.mobilePay.domain.pay.entity.MobileCarrier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MobileCarrierRepository extends JpaRepository<MobileCarrier, Long> {
}
