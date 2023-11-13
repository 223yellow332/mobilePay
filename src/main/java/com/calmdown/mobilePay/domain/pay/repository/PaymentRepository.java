package com.calmdown.mobilePay.domain.pay.repository;

import com.calmdown.mobilePay.domain.pay.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {




}
