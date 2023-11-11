package com.calmdown.mobilePay.domain.user.repository;

import com.calmdown.mobilePay.domain.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {
}
