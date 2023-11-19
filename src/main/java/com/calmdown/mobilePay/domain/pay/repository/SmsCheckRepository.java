package com.calmdown.mobilePay.domain.pay.repository;

import com.calmdown.mobilePay.domain.pay.entity.SmsCheck;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SmsCheckRepository extends JpaRepository<SmsCheck, Long> {
}
